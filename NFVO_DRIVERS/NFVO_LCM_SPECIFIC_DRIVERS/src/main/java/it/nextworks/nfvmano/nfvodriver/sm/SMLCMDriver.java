/*
* Copyright 2018 ATOS.
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


import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.MecAppPackageManagementConsumerInterface;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.NsdManagementConsumerInterface;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.VnfPackageManagementConsumerInterface;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.*;
import it.nextworks.nfvmano.libs.ifa.common.enums.OperationStatus;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.*;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.common.messages.SubscribeRequest;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.NsLcmConsumerInterface;
import it.nextworks.nfvmano.libs.ifa.osmanfvo.nslcm.interfaces.messages.*;


import it.nextworks.nfvmano.nfvodriver.NfvoLcmAbstractDriver;
import it.nextworks.nfvmano.nfvodriver.NfvoLcmDriverType;
import it.nextworks.nfvmano.nfvodriver.NfvoLcmOperationPollingManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.List;
import java.util.Properties;

/**
 * Created by Juan Brenes on 20/04/18.
 *
 * @author Juan Brenes <juan.brenesbaranzano AT atos.net>
 */

 public class SMLCMDriver extends NfvoLcmAbstractDriver {

    private static final Logger log = LoggerFactory.getLogger(SMLCMDriver.class);
    private SMLCMRestClient restClient;
    @Autowired
	private NfvoLcmOperationPollingManager nfvoOperationPollingManager;


    private String createNsIdentifierTemplate;
    private String baseUrl;

    private String instantiateNsTemplate =  "/ns/{nsid}/instantiate";
    private String scaleNsTemplate = "/ns/{nsid}/scale";
    private String nSStatusTemplate;
    private String getOperationStatusTemplate = "/operation/{operationid}";
    private Properties props = new Properties();
    private String terminateNsTemplate = "/ns/{nsid}/terminate";
    private String queryNsiStatusTemplate = "/ns/{nsiid}";

    public SMLCMDriver(){

        super(NfvoLcmDriverType.SM, "localhost", null);
        this.loadProperties();
        String baseAddress = this.baseUrl.replaceAll("\\{nfvoaddress\\}", "localhost");
        this.restClient = new SMLCMRestClient(baseAddress, new RestTemplate());

    }
    public SMLCMDriver(String nfvoAddress, NfvoLcmOperationPollingManager nfvoOperationPollingManager ) {
        super(NfvoLcmDriverType.SM, nfvoAddress, null);
        log.info("createNsIdentifierTemplate: "+ createNsIdentifierTemplate);
        this.loadProperties();
        String baseAddress = this.baseUrl.replaceAll("\\{nfvoaddress\\}", nfvoAddress);   
        this.restClient = new SMLCMRestClient(baseAddress, new RestTemplate());
        this.nfvoOperationPollingManager = nfvoOperationPollingManager;
    }

    private void loadProperties(){
        InputStream fins = getClass().getClassLoader().getResourceAsStream("sm_driver.properties");
        try{
            if(fins!=null){
                    props.load(fins);
            }else{
                log.warn("Null props file");
            }
        }catch (IOException e) {
            log.warn(e.getMessage());
        }
        this.createNsIdentifierTemplate = props.getProperty("create_ns_identifier", "/ns");
        log.info("createNsIdentifierTemplate: "+ createNsIdentifierTemplate);
        this.instantiateNsTemplate = props.getProperty("instantiate_ns", "/ns/{nsid}/instantiate");
        log.info("instantiateNsTemplate: "+ this.instantiateNsTemplate);
		this.scaleNsTemplate = props.getProperty("scale_ns", "/ns/{nsid}/scale");
		log.info("scaleNsTemplate: "+ this.scaleNsTemplate);
        this.getOperationStatusTemplate = props.getProperty("get_operation_status", "/operation/{operationid}");
        log.info("getOperationStatusTemplate: "+ this.getOperationStatusTemplate);
        this.terminateNsTemplate = props.getProperty("terminate_ns", "/ns/{nsid}/terminate");
        log.info("terminateNsTemplate: "+ this.terminateNsTemplate);
        this.baseUrl = props.getProperty("base_url", "http://{nfvoaddress}:8080/5gt/so/v1");
        log.info("baseUrl: "+ this.baseUrl);
        this.nSStatusTemplate=props.getProperty("ns_status", "/ns/{nsid}");
        log.info("nsStatusTemplate: "+ this.nSStatusTemplate);

    }


    private void processErrorLog(Process process) throws IOException {
    	InputStream stderr = process.getErrorStream ();
    	BufferedReader input =  new BufferedReader(new InputStreamReader(stderr));
    	String line;
    	while((line=input.readLine())!=null) {
    		log.error(line);
    	}

    }


    public String createNsIdentifier(CreateNsIdentifierRequest createNsIdentifierRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to createNsIdentifier, parameter {}.", createNsIdentifierRequest);
        return this.restClient.createNsIdentifier(this.createNsIdentifierTemplate, createNsIdentifierRequest).getNSId();
    }

    public String instantiateNs(InstantiateNsRequest instantiateNsRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to instantiateNs, parameter {}.", instantiateNsRequest);
        	
        	String operationId =  this.restClient.instantiateNs(this.instantiateNsTemplate, instantiateNsRequest).getOperationId();
        nfvoOperationPollingManager.addOperation(operationId, OperationStatus.SUCCESSFULLY_DONE, instantiateNsRequest.getNsInstanceId(), "NS_INSTANTIATION");
		log.debug("Added polling task for NFVO operation " + operationId);
        return operationId;
    }

    public String scaleNs(ScaleNsRequest scaleNsRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
	    log.info("Received call to scaleNs, parameter {}.", scaleNsRequest);
        String operationId =  this.restClient.scaleNs(this.scaleNsTemplate, scaleNsRequest).getOperationId();
        nfvoOperationPollingManager.addOperation(operationId, OperationStatus.SUCCESSFULLY_DONE, scaleNsRequest.getNsInstanceId(), "NS_SCALING");
        log.debug("Added polling task for NFVO operation " + operationId);
        return operationId;
    }

    public UpdateNsResponse updateNs(UpdateNsRequest updateNsRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to updateNs, parameter {}.", updateNsRequest);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    public QueryNsResponse queryNs(GeneralizedQueryRequest generalizedQueryRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to queryNs, parameter {}.", generalizedQueryRequest);
        return this.restClient.queryNs(this.queryNsiStatusTemplate, generalizedQueryRequest);
    }

    public String terminateNs(TerminateNsRequest terminateNsRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to terminateNs, parameter {}.", terminateNsRequest);
        String operationId = this.restClient.terminateNs(terminateNsTemplate, terminateNsRequest).getOperationId();
		nfvoOperationPollingManager.addOperation(operationId, OperationStatus.SUCCESSFULLY_DONE, terminateNsRequest.getNsInstanceId(), "NS_TERMINATION");
		log.debug("Added polling task for NFVO operation " + operationId);
        return operationId;
    }
//TODO: DeleteNsdRequest class has no filter, the way to access to nsdVersion.
    public void deleteNsIdentifier(String s) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException {
        log.info("Received call to deleteNsIdentifier, parameter {}.", s);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    public String healNs(HealNsRequest healNsRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to healNs, parameter {}.", healNsRequest);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    public OperationStatus getOperationStatus(String s) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to getOperationStatus, parameter {}.", s);
        return this.restClient.getOperationStatus(this.getOperationStatusTemplate,s);
    }

    public String subscribeNsLcmEvents(SubscribeRequest subscribeRequest, NsLcmConsumerInterface nsLcmConsumerInterface) throws MethodNotImplementedException, MalformattedElementException, FailedOperationException {
        log.info("Received call to subscribeNsLcmEvents, parameters {}; {}.", subscribeRequest, nsLcmConsumerInterface);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    public void unsubscribeNsLcmEvents(String s) throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException {
        log.info("Received call to unsubscribeNsLcmEvents, parameter {}.", s);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    public void queryNsSubscription(GeneralizedQueryRequest generalizedQueryRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to queryNsSubscription, parameter {}.", generalizedQueryRequest);
        throw new MethodNotImplementedException("not supported in SM.");
    }
}
