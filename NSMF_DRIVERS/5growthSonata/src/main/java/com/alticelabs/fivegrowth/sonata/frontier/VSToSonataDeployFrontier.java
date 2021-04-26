package com.alticelabs.fivegrowth.sonata.frontier;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import it.nextworks.nfvmano.libs.ifa.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotPermittedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;

import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmProviderInterface;
import it.nextworks.nfvmano.sebastian.nsmf.messages.CreateNsiIdRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.InstantiateNsiRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.ModifyNsiRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.TerminateNsiRequest;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;

public class VSToSonataDeployFrontier implements NsmfLcmProviderInterface {
	private static final Logger log = LoggerFactory.getLogger(VSToSonataDeployFrontier.class);

	private RestTemplate restTemplate;
	private String cookies;

	private String sonataUrl;
//	private Authenticator authenticator;


	public VSToSonataDeployFrontier(String baseUrl)
		{
		this.sonataUrl = baseUrl + "/api/v1/ns/";
		this.restTemplate = new RestTemplate();
//		this.authenticator = new Authenticator(baseUrl, adminService);
		}

	/*
	 * private void performAuth(String tenantId){
	 * authenticator.authenticate(tenantId); 
	 * if(authenticator.isAuthenticated()){
	 * cookies=authenticator.getCookie(); } }
	 */

	//In order to test the Sonata client
	public void setCookies(String cookies)
		{
		this.cookies=cookies;
		}

	private ResponseEntity<String> performHTTPRequest(Object request, String url, HttpMethod httpMethod, String tenantID)
		{
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
//		performAuth(tenantID);
		if(this.cookies != null)
			{
			header.add("Cookie", this.cookies);
			}

		HttpEntity<?> httpEntity = new HttpEntity<>(request, header);

		try {
			//log.info("URL performing the request to: "+url);
			ResponseEntity<String> httpResponse = restTemplate.exchange(url, httpMethod, httpEntity, String.class);
			HttpStatus code = httpResponse.getStatusCode();
			//log.info("Response code: " + httpResponse.getStatusCode().toString());
			return httpResponse;
			}
		catch (RestClientException e)
			{
			log.info("Message received: " + e.getMessage());
			return null;
			}
		}

	private String manageHTTPResponse(ResponseEntity<?> httpResponse, String errMsg, String okCodeMsg, HttpStatus httpStatusExpected) {
		if (httpResponse == null) 
			{
			log.info(errMsg);
			return null;
			}

		if (httpResponse.getStatusCode().equals(httpStatusExpected)) 
			log.info(okCodeMsg);
		else 
			log.info(errMsg);

		log.info("Response code: " + httpResponse.getStatusCode().toString());

		if(httpResponse.getBody() == null) 
			return null;

		log.info(("Body response: " + httpResponse.getBody().toString()));
		return httpResponse.getBody().toString();
		}
	
	@Override
	public String createNetworkSliceIdentifier(CreateNsiIdRequest request, String domain, String tenantId)
			throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
			MalformattedElementException, NotPermittedOperationException 
		{
		ResponseEntity<String> httpResponse = performHTTPRequest(request, sonataUrl, HttpMethod.POST, tenantId);
		return manageHTTPResponse(httpResponse, "Error while creating network slice identifier", "Network slice identifier correctly created",HttpStatus.CREATED);
		}
	
	@Override
	public void instantiateNetworkSlice(InstantiateNsiRequest request, String domain, String tenantId)
			throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
			MalformattedElementException, NotPermittedOperationException 
		{
		log.info("Sending request to instantiate network Slice");
		String url = sonataUrl + request.getNsiId() + "/action/instantiate";
		ResponseEntity<String> httpResponse = performHTTPRequest(request, url, HttpMethod.PUT, tenantId);
		String bodyResponse = manageHTTPResponse(httpResponse, "Error while instantiating network slice", "Network slice instantiation correctly performed",HttpStatus.ACCEPTED);
		}
	
	@Override
	public void modifyNetworkSlice(ModifyNsiRequest request, String domain, String tenantId)
			throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
			MalformattedElementException, NotPermittedOperationException	
		{
		log.info("Sending request to modify network slice");
		String url = sonataUrl + request.getNsiId() + "/action/modify";
		ResponseEntity<String> httpResponse = performHTTPRequest(request, url, HttpMethod.PUT, tenantId);
		String bodyResponse = manageHTTPResponse(httpResponse, "Error while modifying network slice", "Network slice modification correctly performed",HttpStatus.ACCEPTED);
		}

	@Override
	public void terminateNetworkSliceInstance(TerminateNsiRequest request, String domain, String tenantId)
			throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
			MalformattedElementException, NotPermittedOperationException
		{
		log.info("Sending request to terminate network slice");
		String url = sonataUrl + request.getNsiId() + "/action/terminate";
		ResponseEntity<String> httpResponse = performHTTPRequest(request, url, HttpMethod.PUT, tenantId);
		String bodyResponse = manageHTTPResponse(httpResponse, "Error while terminating network slice", "Network slice termination correctly performed",HttpStatus.ACCEPTED);
		}
	
	@Override
	public List<NetworkSliceInstance> queryNetworkSliceInstance(GeneralizedQueryRequest request, String domain, String tenantId)
			throws MethodNotImplementedException, FailedOperationException, MalformattedElementException
		{
		log.info("Sending request to query network slice instance");
		String url = sonataUrl;
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
//		performAuth(tenantId);
		if(this.cookies!=null)
			{
			header.add("Cookie", this.cookies);
			}

		HttpEntity<?> httpEntity = new HttpEntity<>(request, header);

		try {
			//If the nsiID is specified a NetworkSliceInstance is sent, viceversa a List<NetworkSliceInstance> .
			if(request.getFilter() != null)
				{
            	String nsiID = request.getFilter().getParameters().getOrDefault("NSI_ID","");
				url += nsiID;
				ResponseEntity <NetworkSliceInstance> httpResponseAtMostOneNSI =
					restTemplate.exchange(url, HttpMethod.GET, httpEntity, NetworkSliceInstance.class);

				List<NetworkSliceInstance> nsii = new ArrayList<NetworkSliceInstance>();
				nsii.add(httpResponseAtMostOneNSI.getBody());
				log.info("Response code: " + httpResponseAtMostOneNSI.getStatusCode().toString());
				manageHTTPResponse(httpResponseAtMostOneNSI, "Error querying network slice instance", "Query to network slice instance performed correctly",HttpStatus.OK);
				return nsii;
				}
			else
				{
				ResponseEntity<List<NetworkSliceInstance>> httpResponse = restTemplate.exchange(url, HttpMethod.GET, httpEntity,  new ParameterizedTypeReference<List<NetworkSliceInstance>>() {});
				log.info("Response code: " + httpResponse.getStatusCode().toString());
				manageHTTPResponse(httpResponse, "Error querying network slice instance", "Query to network slice instance performed correctly",HttpStatus.OK);
				return httpResponse.getBody();
				}
			}
		catch (RestClientException e)
			{
			e.printStackTrace();
			return null;
			}
	}

	
	
			
}
