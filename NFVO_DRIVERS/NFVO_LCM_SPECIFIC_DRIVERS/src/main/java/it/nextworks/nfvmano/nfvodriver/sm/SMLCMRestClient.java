/*
* Copyright 2018 Nextworks s.r.l.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package it.nextworks.nfvmano.nfvodriver.sm;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.*;
import it.nextworks.nfvmano.libs.ifa.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.*;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages.*;
import it.nextworks.nfvmano.nfvodriver.sm.messages.CreateNSIdentifierResponse;
import it.nextworks.nfvmano.nfvodriver.sm.messages.OperationResponse;
import it.nextworks.nfvmano.nfvodriver.sm.messages.OperationStatusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;


/**
 * REST client to interact with 5GT-SO SM.
 * 
 *
 * Created by Juan Brenes on 20/04/18.
 *
 * Adapted from TIMEO Driver by NXW
 * 
 * @author Juan Brenes <juan.brenesbaranzano AT atos.net>
 *
 */

 public class SMLCMRestClient {

	private static final Logger log = LoggerFactory.getLogger(SMLCMRestClient.class);

	private RestTemplate restTemplate;

	//private String timeoUrl;
	private String smUrl;


	private String nsLifecycleServiceUrlTemplate;

	/**
	 * Constructor
	 *
	 * @param smUrl root URL to interact with TIMEO
	 * @param restTemplate REST template
	 */
	public SMLCMRestClient(String smUrl,
                           RestTemplate restTemplate) {
		this.smUrl = smUrl;



		this.nsLifecycleServiceUrlTemplate = smUrl + "/nfvo/nsLifecycle";
		this.restTemplate = restTemplate;
	}
	
	
	//********************** NS LCM methods ********************************//
	
	public CreateNSIdentifierResponse createNsIdentifier(String urlTemplate, CreateNsIdentifierRequest request) throws NotExistingEntityException, FailedOperationException, MalformattedElementException {

		log.debug("Building HTTP request to create NS ID.");
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> postEntity = new HttpEntity<>(request, header);


		String url = smUrl+ urlTemplate;
		log.debug("createNSIdentifier:"+url);
		try {
			log.debug("Sending HTTP request to create NS ID.");
			ResponseEntity<String> httpResponse = 
    				restTemplate.exchange(url, HttpMethod.POST, postEntity, String.class);
			
			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.CREATED) || code.equals(HttpStatus.OK)) {
				CreateNSIdentifierResponse response=null;
				try{
					response = new ObjectMapper().readValue(httpResponse.getBody(), CreateNSIdentifierResponse.class);
					log.debug("Created NS ID at NFVO: " + response.getNSId());
				}catch(IOException e1){
					log.warn(e1.getMessage());
				}
				return response;
			} else if (code.equals(HttpStatus.NOT_FOUND)) {
				throw new NotExistingEntityException("Error during NS ID creation at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during NS ID creation at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error on NFVO during NS ID creation");
			}
				
		} catch (RestClientException ex) {
			log.debug("Error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO LCM at url " + url);
		}
	}

	public OperationResponse instantiateNs(String urlTemplate, InstantiateNsRequest request) throws NotExistingEntityException, FailedOperationException, MalformattedElementException {
		String nsId = request.getNsInstanceId();
		log.debug("Building HTTP request to instantiate NS instance " + nsId);
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> putEntity = new HttpEntity<>(request, header);

		String url = smUrl + urlTemplate.replaceAll("\\{nsid\\}", nsId);
		log.debug("instantiateNS: "+url);
		try {
			log.debug("Sending HTTP request to instantiate NS.");
			ResponseEntity<String> httpResponse =
					restTemplate.exchange(url, HttpMethod.PUT, putEntity, String.class);
			
			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.OK)) {
				
				OperationResponse response=null;
				try{
					response = new ObjectMapper().readValue(httpResponse.getBody(), OperationResponse.class);
					log.debug("Created NS Instance OperationId: " + response.getOperationId());
				}catch(IOException e1){
					log.warn(e1.getMessage());
				}
				return response;
				
			} else if (code.equals(HttpStatus.NOT_FOUND)) {
				throw new NotExistingEntityException("Error during NS instantiation at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during NS instantiation at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error on NFVO during NS instantiation");
			}
			
		} catch (RestClientException e) {
			log.debug("Error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO LCM at url " + url);
		}
	}
	
	/// R2 feature: SCALE NS ///
	
	public OperationResponse scaleNs(String urlTemplate, ScaleNsRequest request) throws NotExistingEntityException, FailedOperationException, MalformattedElementException {
		String nsId = request.getNsInstanceId();
		log.debug("Building HTTP request to scale NS instance " + nsId);

		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> putEntity = new HttpEntity<>(request, header);
		String url = smUrl + urlTemplate.replaceAll("\\{nsid\\}", nsId);
		log.debug("scaleNS: "+url);
		try {
			log.debug("Sending HTTP request to scale NS.");
			ResponseEntity<String> httpResponse =
					restTemplate.exchange(url, HttpMethod.PUT, putEntity, String.class);

			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();

			if (code.equals(HttpStatus.OK)) {

				OperationResponse response=null;
				try{
					response = new ObjectMapper().readValue(httpResponse.getBody(), OperationResponse.class);
					log.debug("Scaled NS Instance OperationId: " + response.getOperationId());
				}catch(IOException e1){
					log.warn(e1.getMessage());
				}
				return response;

			} else if (code.equals(HttpStatus.NOT_FOUND)) {
				throw new NotExistingEntityException("Error during NS escalation at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during NS escalation at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error on NFVO during NS escalation");
			}

		} catch (RestClientException e) {
			log.debug("Error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO LCM at url " + url);
		}
	}
	
	public QueryNsResponse queryNs(String urlTemplate, GeneralizedQueryRequest data) throws NotExistingEntityException, FailedOperationException, MalformattedElementException {
		String nsInstanceId = data.getFilter().getParameters().get("NS_ID");
		log.debug("Building HTTP request for querying NS instance with ID " + nsInstanceId);
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> getEntity = new HttpEntity<>(header);
		
		String url = this.smUrl+urlTemplate.replaceAll("\\{nsiid\\}", nsInstanceId);
		
		try {
			log.debug("Sending HTTP request to retrieve NS instance.");
			
			ResponseEntity<QueryNsResponse> httpResponse =
					restTemplate.exchange(url, HttpMethod.GET, getEntity, QueryNsResponse.class);
			
			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.OK)) {
				log.debug("NS instance correctly retrieved");
				return httpResponse.getBody();
			} else if (code.equals(HttpStatus.NOT_FOUND)) {
				throw new NotExistingEntityException("Error during NS retrieval at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during NS retrieval at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error on NFVO during NS retrieval");
			}
			
		} catch (Exception e) {
			log.debug("Error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO LCM at url " + url);
		}
	}
	
	public OperationResponse terminateNs(String urlTemplate, TerminateNsRequest request) throws NotExistingEntityException, FailedOperationException, MalformattedElementException {
		String nsInstanceId = request.getNsInstanceId();
		log.debug("Building HTTP request for terminating NS instance " + nsInstanceId);
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> putEntity = new HttpEntity<>(request, header);

		String url = this.smUrl + urlTemplate.replaceAll("\\{nsid\\}", nsInstanceId);
		
		try {
			log.debug("Sending HTTP request to terminate NS.");
			ResponseEntity<String> httpResponse =
					restTemplate.exchange(url, HttpMethod.PUT, putEntity, String.class);
			
			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.OK)) {
				OperationResponse response=null;
				try{
					response = new ObjectMapper().readValue(httpResponse.getBody(), OperationResponse.class);
					log.debug("Created NS Instance OperationId: " + response.getOperationId());
				}catch(IOException e1){
					log.warn(e1.getMessage());
				}
				log.debug("Started NS termination at NFVO. Operation ID: " + response.getOperationId());
				return response;				
			
			} else if (code.equals(HttpStatus.NOT_FOUND)) {
				throw new NotExistingEntityException("Error during NS termination at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during NS termination at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error on NFVO during NS termination");
			}
			
		} catch (RestClientException e) {
			log.debug("Error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO LCM at url " + url);
		}
	}
	
	public void deleteNsIdentifier(String ulrTemplate, String nsInstanceIdentifier)
			throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException {
		//TODO
		log.debug("Building HTTP request for deleting NS instance identifier " + nsInstanceIdentifier);

		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> deleteEntity = new HttpEntity<>(header);

		String url = ulrTemplate + "/ns/" + nsInstanceIdentifier;
		
		try {
			log.debug("Sending HTTP request to delete NS ID.");
			ResponseEntity<String> httpResponse =
					restTemplate.exchange(url, HttpMethod.DELETE, deleteEntity, String.class);
			
			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.OK)) {
				log.debug("NS ID removed at NFVO.");
				return;
			} else if (code.equals(HttpStatus.NOT_FOUND)) {
				throw new NotExistingEntityException("Error during NS ID removal at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error on NFVO during NS ID removal");
			}
			
		} catch (RestClientException e) {
			log.debug("Error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO LCM at url " + url);
		}
	}
	
	public OperationStatus getOperationStatus(String urlTemplate,String operationId) throws NotExistingEntityException, FailedOperationException, MalformattedElementException {
		log.debug("Building HTTP request to retrieve status for NS LCM operation " + operationId);

		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> getEntity = new HttpEntity<>(header);

		String url = smUrl + urlTemplate.replaceAll("\\{operationid\\}", operationId);

		try {
			log.debug("Sending HTTP request to retrieve operation status.");
			ResponseEntity<String> httpResponse =
					restTemplate.exchange(url, HttpMethod.GET, getEntity, String.class);

			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();

			if (code.equals(HttpStatus.OK)) {
				log.debug("Operation status correctly retrieved.");
				String operationStatus="FAILED";
				try{
					operationStatus =  new ObjectMapper().readValue(httpResponse.getBody(), OperationStatusResponse.class).getOperationStatus();
					log.debug("Received operation status: "+operationStatus);
				}catch(IOException e1){
					log.warn(e1.getMessage());
					throw new FailedOperationException("Unknow operation status returned.");
				}
				if (operationStatus.equals("PROCESSING")) return OperationStatus.PROCESSING;
				else if (operationStatus.equals("SUCCESSFULLY_DONE")) return OperationStatus.SUCCESSFULLY_DONE;
				else if (operationStatus.equals("FAILED")) return OperationStatus.FAILED;
				else {
					log.error("Unknow operation status returned.");
					throw new FailedOperationException("Unknow operation status returned.");
				}
			} else if (code.equals(HttpStatus.NOT_FOUND)) {
				throw new NotExistingEntityException("Error during retrieval of operation status at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during retrieval of operation status at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error on NFVO during retrieval of operation status");
			}

		} catch (RestClientException e) {
			log.debug("Error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO LCM at url " + url);
		}

	}
	

}
