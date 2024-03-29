package it.nextworks.nfvmano.sebastian.arbitrator.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorRequest;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorTrainedAlgorithmUpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;
import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.util.UriComponentsBuilder;

public class ExternalArbitratorRestClient {

    private static final Logger log = LoggerFactory.getLogger(ExternalArbitratorRestClient.class);

    private RestTemplate restTemplate;

    private String arbitratorBaseUrl;

    public ExternalArbitratorRestClient(String baseUrl){
        this.arbitratorBaseUrl= baseUrl;
        this.restTemplate= new RestTemplate();

    }


    private ResponseEntity<Map<String,String>> performHTTPRequest(Object request, String url, HttpMethod httpMethod) {
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");

        ParameterizedTypeReference<Map<String, String>> typeRef = new ParameterizedTypeReference<Map<String, String>>() {};
        HttpEntity<?> httpEntity = new HttpEntity<>(request, header);


            //log.info("URL performing the request to: "+url);
            ResponseEntity<Map<String, String>> httpResponse =
                    restTemplate.exchange(url, httpMethod, httpEntity, typeRef);
            HttpStatus code = httpResponse.getStatusCode();
            //log.info("Response code: " + httpResponse.getStatusCode().toString());
            return httpResponse;

    }




    private Map<String,String> manageHTTPResponse(ResponseEntity<Map<String,String>>  httpResponse, String errMsg, String okCodeMsg, HttpStatus httpStatusExpected) {
        if (httpResponse == null) {
            log.info(errMsg);
            return null;
        }

        if (httpResponse.getStatusCode().equals(httpStatusExpected)) log.info(okCodeMsg);
        else log.info(errMsg);

        log.info("Response code: " + httpResponse.getStatusCode().toString());

        if(httpResponse.getBody()==null) return null;

        log.info(("Body response: "+httpResponse.getBody().toString()));
        return httpResponse.getBody();

    }

    public Map<String,String> requestArbitration(List<ArbitratorRequest> request) throws FailedOperationException {
        log.debug("Received request to compute arbitration");
        String url = arbitratorBaseUrl + "/arbitrator/computeArbitration";
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            log.debug("Received request:"+objectMapper.writeValueAsString(request.get(0)));
            ResponseEntity<Map<String, String>> httpResponse = performHTTPRequest(request, url, HttpMethod.POST);
            return manageHTTPResponse(httpResponse, "Error while requesting external arbitration", "External arbitration correctly requested", HttpStatus.CREATED);
        }catch(RestClientException e){
            throw new FailedOperationException("failed to send arbitration request", e);

        } catch (JsonProcessingException e) {
            log.error("Error processing arbitrator request:",e);
            throw new FailedOperationException("failed to send arbitration request", e);
        }

    }

    public void updateTrainedFile(String fileId, String filePath) throws FailedOperationException{
        log.debug("Received request to update trained file");
        String url = arbitratorBaseUrl + "/arbitrator/updateArbitratorModel";
        try{
            ObjectMapper objectMapper = new ObjectMapper();


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, Object> body
                    = new LinkedMultiValueMap<>();
            body.add("file", new FileSystemResource(new File(filePath)));

            HttpEntity<MultiValueMap<String, Object>> requestEntity
                    = new HttpEntity<>(body, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(url+"/"+fileId,HttpMethod.PUT, requestEntity, String.class);
            //ResponseEntity<String> response = restTemplate.exchange(builder.build().toString(),HttpMethod.PUT, requestEntity, String.class);
            //ResponseEntity<String> response = restTemplate
            //        .postForEntity(url, requestEntity, String.class);

            //return manageHTTPResponse(response, "Error while updating trained algorithm", "trained model updated correctly", HttpStatus.CREATED);
        }catch(RestClientException e){
            throw new FailedOperationException("failed to send arbitration request", e);

        }
    }

}
