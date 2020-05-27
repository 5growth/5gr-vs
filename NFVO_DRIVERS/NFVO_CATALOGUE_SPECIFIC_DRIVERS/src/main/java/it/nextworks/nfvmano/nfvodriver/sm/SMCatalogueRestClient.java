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
import it.nextworks.nfvmano.libs.ifa.common.exceptions.*;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;


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

 public class SMCatalogueRestClient {

	private static final Logger log = LoggerFactory.getLogger(SMCatalogueRestClient.class);

	private RestTemplate restTemplate;

	//private String timeoUrl;
	private String smUrl;
	private String nsdServiceUrlTemplate;

	private String vnfdServiceUrlTemplate;

	private String appdServiceUrlTemplate;



	/**
	 * Constructor
	 *
	 * @param smUrl root URL to interact with TIMEO
	 * @param restTemplate REST template
	 */
	public SMCatalogueRestClient(String smUrl,
                                 RestTemplate restTemplate) {
		this.smUrl = smUrl;
		this.nsdServiceUrlTemplate = smUrl + "/nfvo/nsdManagement";
		this.vnfdServiceUrlTemplate = smUrl + "/nfvo/vnfdManagement";
		this.appdServiceUrlTemplate = smUrl + "/nfvo/appdManagement";
		this.restTemplate = restTemplate;
	}
	
	

	
	//********************** NSD methods ********************************//
	
	
	public String onboardNsd(String urlTemplate,OnboardNsdRequest request) throws MalformattedElementException, AlreadyExistingEntityException, FailedOperationException {
		//TODO: Pending discussions at SM level
		log.debug("Building HTTP request to onboard NSD.");
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> postEntity = new HttpEntity<>(request, header);
		
		String url = this.smUrl+urlTemplate;
		
		
		try {
			
			log.debug("Sending HTTP request to onboard NSD.");
			ResponseEntity<String> httpResponse =
					restTemplate.exchange(url, HttpMethod.POST, postEntity, String.class);
			
			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.OK)) {
				String nsdInfoId = httpResponse.getBody();
				log.debug("NSD correctly onboarded with Nsd info ID " + nsdInfoId);
				return nsdInfoId;
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during NSD onboarding at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.CONFLICT)) {
				throw new AlreadyExistingEntityException("Error during NSD onboarding at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error on NFVO during NSD onboarding");
			}
			
		} catch (RestClientException e) {
			log.debug("error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO NSD catalogue at url " + url);
		}
	}
	
	public String onboardPnfd(String urlTemplate, OnboardPnfdRequest request) throws MalformattedElementException, AlreadyExistingEntityException, FailedOperationException {
		//TODO
		log.debug("Building HTTP request to onboard PNFD.");
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> postEntity = new HttpEntity<>(request, header);
		
		String url = urlTemplate + "/pnfd";
		
		try {
			log.debug("Sending HTTP request to onboard PNFD.");
			
			ResponseEntity<String> httpResponse =
					restTemplate.exchange(url, HttpMethod.POST, postEntity, String.class);
			
			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.CREATED)) {
				String pnfdInfoId = httpResponse.getBody();
				log.debug("PNFD correctly onboarded with PNFD info ID " + pnfdInfoId);
				return pnfdInfoId;
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during PNFD onboarding at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.CONFLICT)) {
				throw new AlreadyExistingEntityException("Error during PNFD onboarding at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error on NFVO during PNFD onboarding");
			}
			
		} catch (RestClientException e) {
			log.debug("error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO NSD catalogue at url " + url);
		}
	}
	
	@SuppressWarnings("unchecked")
	public QueryNsdResponse queryNsd(String urlTemplate,GeneralizedQueryRequest request) throws MalformattedElementException, NotExistingEntityException, FailedOperationException {
		//TODO
		log.debug("Building HTTP request to query NSD.");
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> getEntity = new HttpEntity<>(request, header);
		String nsdId = request.getFilter().getParameters().get("NSD_ID");
		String nsdVersion = request.getFilter().getParameters().get("NSD_VERSION");
		
		String url = this.smUrl + urlTemplate.replaceAll("\\{nsdid\\}", nsdId);
		url = url.replaceAll("\\{nsdversion\\}", nsdVersion);
		
		try {
			log.debug("Sending HTTP request to retrieve NSD.");
			log.debug("NSD_ID:"+nsdId+"NSD_VERSION:"+nsdVersion+"GET: "+url);
			
			/*ResponseEntity<QueryNsdIdVersionResponse> httpResponse =
					restTemplate.exchange(url, HttpMethod.GET, getEntity, QueryNsdIdVersionResponse.class);
			*/
			ResponseEntity<QueryNsdResponse> httpResponse =
					restTemplate.exchange(url, HttpMethod.GET, getEntity, QueryNsdResponse.class);
			
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.OK)) {
				log.debug("HttpStatus.OK!");
				ObjectMapper mapper = new ObjectMapper();
				JsonNode rootNode  = (ObjectNode) mapper.valueToTree(httpResponse.getBody());
				((ObjectNode)rootNode.path("queryResult").get(0)).put("nsdInfoId", nsdId);
				QueryNsdResponse finalResponse = mapper.treeToValue(rootNode, QueryNsdResponse.class);
				return finalResponse;
				
			} else if (code.equals(HttpStatus.NOT_FOUND)) {
				throw new NotExistingEntityException("Error during NSD retrieval at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during NSD retrieval at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error on NFVO during NSD retrieval");
			}
		} catch (IOException  e3) {
			log.debug("error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO NSD catalogue at url " + url);
		
		}catch (RestClientException e) {
			log.debug("error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO NSD catalogue at url " + url);
		}
		
	}
	
	public QueryPnfdResponse queryPnfd(String urlTemplate, GeneralizedQueryRequest request) throws MalformattedElementException, NotExistingEntityException, FailedOperationException {
		log.debug("Building HTTP request to query PNFD.");
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> postEntity = new HttpEntity<>(request, header);
		
		String url = urlTemplate + "/pnfd/query";
		
		try {
			log.debug("Sending HTTP request to retrieve PNFD.");
			
			ResponseEntity<QueryPnfdResponse> httpResponse =
					restTemplate.exchange(url, HttpMethod.POST, postEntity, QueryPnfdResponse.class);
			
			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.OK)) {
				log.debug("PNFD correctly retrieved");
				return httpResponse.getBody();
			} else if (code.equals(HttpStatus.NOT_FOUND)) {
				throw new NotExistingEntityException("Error during PNFD retrieval at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during PNFD retrieval at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error on NFVO during PNFD retrieval");
			}
			
		} catch (RestClientException e) {
			log.debug("error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO NSD catalogue at url " + url);
		}
	}
	
	//********************** VNF package methods ********************************//
	
	public OnBoardVnfPackageResponse onBoardVnfPackage(String urlTemplate, OnBoardVnfPackageRequest request)
			throws AlreadyExistingEntityException, FailedOperationException, MalformattedElementException {
		log.debug("Building HTTP request to onboard VNF package.");
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> postEntity = new HttpEntity<>(request, header);
		
		String url = this.smUrl + urlTemplate;
		
		try {
			log.debug("Sending HTTP request to onboard VNF package.");
			
			ResponseEntity<OnBoardVnfPackageResponse> httpResponse =
					restTemplate.exchange(url, HttpMethod.POST, postEntity, OnBoardVnfPackageResponse.class);
			
			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.OK)) {
				log.debug("VNF package correctly onboarded");
				return httpResponse.getBody();
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during VNF package onboarding at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.CONFLICT)) {
				throw new AlreadyExistingEntityException("Error during VNF package onboarding at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error on NFVO during VNF package onboarding");
			}
			
		} catch (RestClientException e) {
			log.debug("error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO VNFD catalogue at url " + url);
		}
	}
	
	
	public QueryOnBoardedVnfPkgInfoResponse queryVnfPackageInfo(String urlTemplate, GeneralizedQueryRequest request)
			throws NotExistingEntityException, MalformattedElementException {
		log.debug("Building HTTP request to query VNF package.");
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> getEntity = new HttpEntity<>(request, header);
		String vnfdId = request.getFilter().getParameters().get("VNFD_ID");
		Map<String, String> parameters = request.getFilter().getParameters();
		String vnfdVersion = "NONE";
		if( parameters.containsKey("VNFD_VERSION")){
			vnfdVersion=parameters.get("VNFD_VERSION");
		}
		
		String url = this.smUrl+ urlTemplate.replaceAll("\\{vnfdid\\}", vnfdId);
		url = url.replaceAll("\\{vnfdversion\\}", vnfdVersion);
		
		try {
			log.debug("Sending HTTP request to retrieve VNF Package.");
			
			log.debug("VNFD_ID:"+vnfdId+" VNFD_VERSION:"+vnfdVersion+" GET: "+url);
			ResponseEntity<QueryOnBoardedVnfPkgInfoResponse> httpResponse =
					restTemplate.exchange(url, HttpMethod.GET, getEntity, QueryOnBoardedVnfPkgInfoResponse.class);
			
			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.OK)) {
				log.debug("VNF package correctly retrieved");
				return httpResponse.getBody();
			} else if (code.equals(HttpStatus.NOT_FOUND)) {
				throw new NotExistingEntityException("Error during VNF package retrieval at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during VNF package retrieval at NFVO: " + httpResponse.getBody());
			} else {
				throw new NotExistingEntityException("Generic error during VNF package retrieval at NFVO: " + httpResponse.getBody());
			}
			
		} catch (RestClientException e) {
			log.debug("error while interacting with NFVO.");
			throw new NotExistingEntityException("Error while interacting with NFVO VNFD catalogue at url " + url);
		}
	}
	
	//********************** MEC App package methods ********************************//
	
	public QueryOnBoadedAppPkgInfoResponse queryApplicationPackage(String urlTemplate, GeneralizedQueryRequest request)
			throws NotExistingEntityException, MalformattedElementException {
		log.debug("Building HTTP request to query MEC App package.");
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> postEntity = new HttpEntity<>(request, header);
		
		String url = urlTemplate + "/appd/query";
		
		try {
			log.debug("Sending HTTP request to retrieve MEC App Package.");
			
			ResponseEntity<QueryOnBoadedAppPkgInfoResponse> httpResponse =
					restTemplate.exchange(url, HttpMethod.POST, postEntity, QueryOnBoadedAppPkgInfoResponse.class);
			
			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.OK)) {
				log.debug("MEC App package correctly retrieved");
				return httpResponse.getBody();
			} else if (code.equals(HttpStatus.NOT_FOUND)) {
				throw new NotExistingEntityException("Error during MEC App package retrieval at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during MEC App package retrieval at NFVO: " + httpResponse.getBody());
			} else {
				throw new NotExistingEntityException("Generic error during MEC App package retrieval at NFVO: " + httpResponse.getBody());
			}
			
		} catch (RestClientException e) {
			log.debug("error while interacting with NFVO.");
			throw new NotExistingEntityException("Error while interacting with NFVO MEC App catalogue at url " + url);
		}
	}
	
	public OnboardAppPackageResponse onboardAppPackage(String urlTemplate,OnboardAppPackageRequest request)
			throws AlreadyExistingEntityException, FailedOperationException, MalformattedElementException {
		log.debug("Building HTTP request to onboard a MEC App package.");
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json");
		HttpEntity<?> postEntity = new HttpEntity<>(request, header);
		
		String url = urlTemplate + "/appd";
		
		try {
			log.debug("Sending HTTP request to onboard a MEC App Package.");
			
			ResponseEntity<OnboardAppPackageResponse> httpResponse =
					restTemplate.exchange(url, HttpMethod.POST, postEntity, OnboardAppPackageResponse.class);
			
			log.debug("Response code: " + httpResponse.getStatusCode().toString());
			HttpStatus code = httpResponse.getStatusCode();
			
			if (code.equals(HttpStatus.CREATED)) {
				log.debug("MEC App package correctly onboarded");
				return httpResponse.getBody();
			} else if (code.equals(HttpStatus.CONFLICT)) {
				throw new AlreadyExistingEntityException("Error during MEC App package onboarding at NFVO: " + httpResponse.getBody());
			} else if (code.equals(HttpStatus.BAD_REQUEST)) {
				throw new MalformattedElementException("Error during MEC App package onboarding at NFVO: " + httpResponse.getBody());
			} else {
				throw new FailedOperationException("Generic error during MEC App package onboarding at NFVO: " + httpResponse.getBody());
			}
			
		} catch (RestClientException e) {
			log.debug("error while interacting with NFVO.");
			throw new FailedOperationException("Error while interacting with NFVO MEC App catalogue at url " + url);
		}
	}

}
