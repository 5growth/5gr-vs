package it.nextworks.nfvmano.sebastian.nsmf.nbi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import it.nextworks.nfvmano.libs.ifa.common.elements.Filter;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotPermittedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.nsmf.NsLcmService;
import it.nextworks.nfvmano.sebastian.nsmf.messages.CreateNsiIdRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.InstantiateNsiRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.ModifyNsiRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.TerminateNsiRequest;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;
@Api(tags = "Network Slice LCM API")
@RestController
@CrossOrigin
@RequestMapping("/vs/basic/nslcm")
public class NsmfRestController {

	private static final Logger log = LoggerFactory.getLogger(NsmfRestController.class);
	
	@Autowired
	private NsLcmService nsLcmService;
	
	@Value("${sebastian.admin}")
	private String adminTenant;

	@Value("${nsmf.local_domain_id:LOCAL}")
	private String localDomainId;
	
	private static String getUserFromAuth(Authentication auth) {
		Object principal = auth.getPrincipal();
		if (!UserDetails.class.isAssignableFrom(principal.getClass())) {
			throw new IllegalArgumentException("Auth.getPrincipal() does not implement UserDetails");
		}
		return ((UserDetails) principal).getUsername();
	}
	
	public NsmfRestController() { }
	
	@RequestMapping(value = "/ns", method = RequestMethod.POST)
	public ResponseEntity<?> createNsId(@RequestBody CreateNsiIdRequest request, Authentication auth) {
		log.debug("Received request to create a new network slice instance ID.");
		try {
			String tenantId = getUserFromAuth(auth);
			String nsiId = nsLcmService.createNetworkSliceIdentifier(request,localDomainId, tenantId);
			return new ResponseEntity<>(nsiId, HttpStatus.CREATED);	
		} catch (NotExistingEntityException e) {
			log.error("NS ID creation failed due to missing elements in DB.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (MalformattedElementException e) {
			log.error("NS ID creation failed due to bad-formatted request.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotPermittedOperationException e) {
			log.error("NS ID creation failed due to missing permission.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			log.error("NS ID creation failed due to internal errors.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@ApiOperation(value = "Get the Network Slice Instance with the specified ID ")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The Network Slice Instance.", response = NetworkSliceInstance.class),
			//@ApiResponse(code = 400, message = "The request contains elements impossible to process", response = ResponseEntity.class),
			//@ApiResponse(code = 409, message = "There is a conflict with the request", response = ResponseEntity.class),
			//@ApiResponse(code = 500, message = "Status 500", response = ResponseEntity.class)

	})
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/ns/{nsiId}", method = RequestMethod.GET)
	public ResponseEntity<?> getNsInstance(@PathVariable String nsiId, Authentication auth) {
		log.debug("Received query for network slice instance with ID " + nsiId);
		try {
			String tenantId = getUserFromAuth(auth);
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("NSI_ID", nsiId);
			Filter filter = new Filter(parameters);
			GeneralizedQueryRequest query = new GeneralizedQueryRequest(filter, null);
			List<NetworkSliceInstance> nsis = nsLcmService.queryNetworkSliceInstance(query, localDomainId, tenantId);
			if (nsis.isEmpty()) {
				log.error("Network slice instance with ID " + nsiId + " not found");
				return new ResponseEntity<>("Network slice instance with ID " + nsiId + " not found", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<NetworkSliceInstance>(nsis.get(0), HttpStatus.OK);	
		} catch (MalformattedElementException e) {
			log.error("NS ID creation failed due to bad-formatted request.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("NS ID creation failed due to internal errors.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Get all the Network Slice Instances")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The list of Network Slice Instances.", response = NetworkSliceInstance.class, responseContainer = "Set"),
			//@ApiResponse(code = 400, message = "The request contains elements impossible to process", response = ResponseEntity.class),
			//@ApiResponse(code = 409, message = "There is a conflict with the request", response = ResponseEntity.class),
			//@ApiResponse(code = 500, message = "Status 500", response = ResponseEntity.class)

	})
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/ns", method = RequestMethod.GET)
	public ResponseEntity<?> getNsInstance(Authentication auth) {
		log.debug("Received query for all network slice instances.");
		try {
			String tenantId = getUserFromAuth(auth);
			Map<String, String> parameters = new HashMap<String, String>();
			Filter filter = new Filter(parameters);
			GeneralizedQueryRequest query = new GeneralizedQueryRequest(filter, null);
			List<NetworkSliceInstance> nsis = nsLcmService.queryNetworkSliceInstance(query, localDomainId, tenantId);
			return new ResponseEntity<List<NetworkSliceInstance>>(nsis, HttpStatus.OK);	
		} catch (MalformattedElementException e) {
			log.error("NS ID creation failed due to bad-formatted request.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("NS ID creation failed due to internal errors.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Get all the Network Slice Instance IDSs")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The list of Network Slice Instances IDS.", response = String.class, responseContainer = "Set"),
			//@ApiResponse(code = 400, message = "The request contains elements impossible to process", response = ResponseEntity.class),
			//@ApiResponse(code = 409, message = "There is a conflict with the request", response = ResponseEntity.class),
			//@ApiResponse(code = 500, message = "Status 500", response = ResponseEntity.class)

	})
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/networksliceids", method = RequestMethod.GET)
	public ResponseEntity<?> getNsInstanceIds(Authentication auth) {
		log.debug("Received query for all network slice instances.");
		try {
			String tenantId = getUserFromAuth(auth);
			Map<String, String> parameters = new HashMap<String, String>();
			Filter filter = new Filter(parameters);
			GeneralizedQueryRequest query = new GeneralizedQueryRequest(filter, null);
			List<String> nsis = nsLcmService.queryNetworkSliceInstance(query, localDomainId, tenantId).stream()
					.map(nsInstance -> nsInstance.getNsiId())
					.collect(Collectors.toList());

			return new ResponseEntity<List<String>>(nsis, HttpStatus.OK);
		} catch (MalformattedElementException e) {
			log.error("NS ID creation failed due to bad-formatted request.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("NS ID creation failed due to internal errors.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/ns/{nsiId}/action/instantiate", method = RequestMethod.PUT)
	public ResponseEntity<?> instantiateNsi(@PathVariable String nsiId, @RequestBody InstantiateNsiRequest request, Authentication auth) {
		log.debug("Received request to instantiate network slice " + nsiId);
		try {
			String tenantId = getUserFromAuth(auth);
			nsLcmService.instantiateNetworkSlice(request,localDomainId, tenantId);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);	
		} catch (NotExistingEntityException e) {
			log.error("NS instantiation failed due to missing elements in DB.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (MalformattedElementException e) {
			log.error("NS instantiation failed due to bad-formatted request.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotPermittedOperationException e) {
			log.error("NS instantiation failed due to missing permission or conflicting state.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			log.error("NS instantiation failed due to internal errors.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/ns/{nsiId}/action/modify", method = RequestMethod.PUT)
	public ResponseEntity<?> modifyNsi(@PathVariable String nsiId, @RequestBody ModifyNsiRequest request, Authentication auth) {
		log.debug("Received request to modify network slice " + nsiId);
		try {
			String tenantId = getUserFromAuth(auth);
			nsLcmService.modifyNetworkSlice(request,localDomainId, tenantId);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);	
		} catch (NotExistingEntityException e) {
			log.error("NS modification failed due to missing elements in DB.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (MalformattedElementException e) {
			log.error("NS modification failed due to bad-formatted request.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotPermittedOperationException e) {
			log.error("NS modification failed due to missing permission or conflicting state.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			log.error("NS modification failed due to internal errors.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/ns/{nsiId}/action/terminate", method = RequestMethod.PUT)
	public ResponseEntity<?> terminateNsi(@PathVariable String nsiId, @RequestBody TerminateNsiRequest request, Authentication auth) {
		log.debug("Received request to terminate network slice " + nsiId);
		try {
			String tenantId = getUserFromAuth(auth);
			nsLcmService.terminateNetworkSliceInstance(request,localDomainId, tenantId);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);	
		} catch (NotExistingEntityException e) {
			log.error("NS termination failed due to missing elements in DB.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (MalformattedElementException e) {
			log.error("NS termination failed due to bad-formatted request.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotPermittedOperationException e) {
			log.error("NS termination failed due to missing permission or conflicting state.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			log.error("NS termination failed due to internal errors.");
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
