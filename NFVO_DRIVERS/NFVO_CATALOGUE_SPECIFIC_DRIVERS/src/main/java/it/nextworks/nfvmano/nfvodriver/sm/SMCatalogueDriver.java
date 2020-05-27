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


import it.nextworks.nfvmano.nfvodriver.NfvoCatalogueAbstractDriver;
import it.nextworks.nfvmano.nfvodriver.NfvoCatalogueDriverType;
import org.springframework.web.client.RestTemplate;

import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.MecAppPackageManagementConsumerInterface;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.NsdManagementConsumerInterface;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.VnfPackageManagementConsumerInterface;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.DeleteNsdRequest;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.DeleteNsdResponse;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.DeletePnfdRequest;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.DeletePnfdResponse;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.DeleteVnfPackageRequest;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.DisableNsdRequest;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.DisableVnfPackageRequest;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.EnableNsdRequest;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.EnableVnfPackageRequest;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.FetchOnboardedVnfPackageArtifactsRequest;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.OnBoardVnfPackageRequest;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.OnBoardVnfPackageResponse;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.OnboardAppPackageRequest;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.OnboardAppPackageResponse;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.OnboardNsdRequest;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.OnboardPnfdRequest;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.QueryNsdResponse;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.QueryOnBoadedAppPkgInfoResponse;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.QueryOnBoardedVnfPkgInfoResponse;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.QueryPnfdResponse;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.UpdateNsdRequest;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.UpdatePnfdRequest;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.AlreadyExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.common.messages.SubscribeRequest;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.Properties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Created by Juan Brenes on 20/04/18.
 *
 * @author Juan Brenes <juan.brenesbaranzano AT atos.net>
 */

public class SMCatalogueDriver extends NfvoCatalogueAbstractDriver {

    private static final Logger log = LoggerFactory.getLogger(SMCatalogueDriver.class);
    private SMCatalogueRestClient restClient;




    private String baseUrl;
    private Properties props = new Properties();

    private String queryNsdTemplate = "/ns/nsd/{nsdid}/{nsdversion}";
    private String queryOnBoardedVnfPkgInfoResponseTemplate = "/ns/vnfd/{vnfdid}/{vnfdversion}";

    private String onboardNsdTemplate = "/ns/nsdManagement/nsd";
    private String onboardVnfd = "/ns/vnfdManagement/vnfPackage";

    public SMCatalogueDriver(){

        super(NfvoCatalogueDriverType.SM, "localhost", null);
        this.loadProperties();
        String baseAddress = this.baseUrl.replaceAll("\\{nfvoaddress\\}", "localhost");
        this.restClient = new SMCatalogueRestClient(baseAddress, new RestTemplate());

    }
    public SMCatalogueDriver(String nfvoAddress) {
        super(NfvoCatalogueDriverType.SM, nfvoAddress, null);
        this.loadProperties();
        String baseAddress = this.baseUrl.replaceAll("\\{nfvoaddress\\}", nfvoAddress);
        this.restClient = new SMCatalogueRestClient(baseAddress, new RestTemplate());

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
        
        this.baseUrl = props.getProperty("base_url", "http://{nfvoaddress}:8080/5gt/so/v1");
        log.info("baseUrl: "+ this.baseUrl);
         this.onboardNsdTemplate = props.getProperty("onboard_nsd", "/ns/nsdManagement/nsd");
    }

    public File fetchOnboardedApplicationPackage(String s) throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
        log.info("Received call to fetchOnboardedApplicationPackage, parameters {}.", s);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    @Override
    public QueryOnBoadedAppPkgInfoResponse queryApplicationPackage(GeneralizedQueryRequest generalizedQueryRequest) throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
        log.info("Received call to queryApplicationPackage, parameters {}.", generalizedQueryRequest);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    public String subscribeMecAppPackageInfo(SubscribeRequest subscribeRequest, MecAppPackageManagementConsumerInterface mecAppPackageManagementConsumerInterface) throws MethodNotImplementedException, MalformattedElementException, FailedOperationException {
        log.info("Received call to subscribeMecAppPackageInfo, parameters {}; {}.", subscribeRequest, mecAppPackageManagementConsumerInterface);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    public void unsubscribeMecAppPackageInfo(String s) throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
        log.info("Received call to unsubscribeMecAppPackageInfo, parameter {}.", s);
    }

    public OnboardAppPackageResponse onboardAppPackage(OnboardAppPackageRequest onboardAppPackageRequest) throws MethodNotImplementedException, AlreadyExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to onboardAppPackage, parameter {}.", onboardAppPackageRequest);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    public void enableAppPackage(String s) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to enableAppPackage, parameter {}.", s);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    public void disableAppPackage(String s) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to disableAppPackage, parameter {}.", s);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    public void deleteAppPackage(String s) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to deleteAppPackage, parameter {}.", s);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    public void abortAppPackageDeletion(String s) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to abortAppPackageDeletion, parameter {}.", s);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    public String onboardNsd(OnboardNsdRequest onboardNsdRequest) throws MethodNotImplementedException, MalformattedElementException, AlreadyExistingEntityException, FailedOperationException {
        //TODO: Pending discussions. Just verify the NSD, call the rest client and return
        log.info("Received call to onboardNsd, parameter {}.", onboardNsdRequest);
        onboardNsdRequest.isValid();
        log.info("onboardNsdRequest is valid!");
        return this.restClient.onboardNsd(onboardNsdTemplate, onboardNsdRequest);
    }

    public void enableNsd(EnableNsdRequest enableNsdRequest) throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException {
        log.info("Received call to enableNsd, parameter {}.", enableNsdRequest);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    public void disableNsd(DisableNsdRequest disableNsdRequest) throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException {
        log.info("Received call to disableNsd, parameter {}.", disableNsdRequest);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    public String updateNsd(UpdateNsdRequest updateNsdRequest) throws MethodNotImplementedException, MalformattedElementException, AlreadyExistingEntityException, NotExistingEntityException, FailedOperationException {
        log.info("Received call to updateNsd, parameter {}.", updateNsdRequest);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    public DeleteNsdResponse deleteNsd(DeleteNsdRequest deleteNsdRequest) throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException {
        log.info("Received call to deleteNsd, parameter {}.", deleteNsdRequest);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    public QueryNsdResponse queryNsd(GeneralizedQueryRequest generalizedQueryRequest) throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException {
        log.info("Received call to queryNsd, parameter {}.", generalizedQueryRequest);
        return this.restClient.queryNsd(this.queryNsdTemplate, generalizedQueryRequest);
    }

    public String subscribeNsdInfo(SubscribeRequest subscribeRequest, NsdManagementConsumerInterface nsdManagementConsumerInterface) throws MethodNotImplementedException, MalformattedElementException, FailedOperationException {
        log.info("Received call to subscribeNsdInfo, parameters {}; {}.", subscribeRequest, nsdManagementConsumerInterface);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    public void unsubscribeNsdInfo(String s) throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException {
        log.info("Received call to unsubscribeNsdInfo, parameter {}.", s);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    public String onboardPnfd(OnboardPnfdRequest onboardPnfdRequest) throws MethodNotImplementedException, MalformattedElementException, AlreadyExistingEntityException, FailedOperationException {
        log.info("Received call to onboardPnfd, parameter {}.", onboardPnfdRequest);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    public String updatePnfd(UpdatePnfdRequest updatePnfdRequest) throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, AlreadyExistingEntityException, FailedOperationException {
        log.info("Received call to updatePnfd, parameter {}.", updatePnfdRequest);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    public DeletePnfdResponse deletePnfd(DeletePnfdRequest deletePnfdRequest) throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException {
        log.info("Received call to deletePnfd, parameter {}.", deletePnfdRequest);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    public QueryPnfdResponse queryPnfd(GeneralizedQueryRequest generalizedQueryRequest) throws MethodNotImplementedException, MalformattedElementException, NotExistingEntityException, FailedOperationException {
        log.info("Received call to queryPnfd, parameter {}.", generalizedQueryRequest);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    public OnBoardVnfPackageResponse onBoardVnfPackage(OnBoardVnfPackageRequest onBoardVnfPackageRequest) throws MethodNotImplementedException, AlreadyExistingEntityException, FailedOperationException, MalformattedElementException {
        //COMMENT: Following the swagger server format (R2) from the SM:
        log.info("Received call to onboardVnfPackage, parameter {}.", onBoardVnfPackageRequest);
        onBoardVnfPackageRequest.isValid();
        log.info("onboardNsdRequest is valid!");
        return this.restClient.onBoardVnfPackage(onboardVnfd, onBoardVnfPackageRequest);
    }
    //Previous development for validating VNF Packages in SMDriver:

    //log.warn("Received call to onBoardVnfPackage, parameter {}.", onBoardVnfPackageRequest);
    //onBoardVnfPackageRequest.isValid();
    //log.warn("Received call to onBoardVnfPackage name:", onBoardVnfPackageRequest.getName());
    //String vnfdId = "";
    //String onboardedVnfPkgInfoId = "";
    //try {
    //String vnfPath = onBoardVnfPackageRequest.getVnfPackagePath();
    //String fileName = vnfPath.substring(vnfPath.lastIndexOf('/')+1);
    //Process process = Runtime.getRuntime().exec("wget -P /tmp/ "+vnfPath);
    //processErrorLog(process);
    //process = Runtime.getRuntime().exec("tar xf /tmp/"+fileName+" -C /tmp");
    //processErrorLog(process);
    //String jsonFileName= fileName.substring(0,fileName.lastIndexOf('.')+1)+"json";
    //log.debug("JSON FILENAME:", jsonFileName);

    //String[] cmd = {
    //	"/bin/bash",
    //"-c",
    //"cat /tmp/"+jsonFileName+" | jq -r '.vnfdId'"
    //};
    //process = Runtime.getRuntime().exec(cmd);
    //processErrorLog(process);
    //BufferedReader input =  new BufferedReader
    //          (new InputStreamReader(process.getInputStream()));
    //vnfdId = input.readLine();
    //log.debug("obtained vnfdId:", vnfdId);
    //} catch (IOException e) {
    //log.error(e.getMessage());
    //}
    //return new OnBoardVnfPackageResponse(vnfdId,onboardedVnfPkgInfoId);
    //throw new MethodNotImplementedException("not supported in SM.");
    //}

    private void processErrorLog(Process process) throws IOException {
        InputStream stderr = process.getErrorStream ();
        BufferedReader input =  new BufferedReader(new InputStreamReader(stderr));
        String line;
        while((line=input.readLine())!=null) {
            log.error(line);
        }

    }

    public void enableVnfPackage(EnableVnfPackageRequest enableVnfPackageRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to enableVnfPackage, parameter {}.", enableVnfPackageRequest);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    public void disableVnfPackage(DisableVnfPackageRequest disableVnfPackageRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to disableVnfPackage, parameter {}.", disableVnfPackageRequest);
        throw new MethodNotImplementedException("not supported in SM.");
    }
    //TODO: DeleteNsdRequest class has no filter, the way to access to nsdVersion.
    public void deleteVnfPackage(DeleteVnfPackageRequest deleteVnfPackageRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to deleteVnfPackage, parameter {}.", deleteVnfPackageRequest);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    public QueryOnBoardedVnfPkgInfoResponse queryVnfPackageInfo(GeneralizedQueryRequest generalizedQueryRequest) throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
        log.info("Received call to queryVnfPackageInfo, parameter {}.", generalizedQueryRequest);
        return restClient.queryVnfPackageInfo(this.queryOnBoardedVnfPkgInfoResponseTemplate, generalizedQueryRequest);
    }

    public String subscribeVnfPackageInfo(SubscribeRequest subscribeRequest, VnfPackageManagementConsumerInterface vnfPackageManagementConsumerInterface) throws MethodNotImplementedException, MalformattedElementException, FailedOperationException {
        log.info("Received call to subscribeVnfPackageInfo, parameters {}; {}.", subscribeRequest, vnfPackageManagementConsumerInterface);
        throw new MethodNotImplementedException("not supported in SM.");    }

    public void unsubscribeVnfPackageInfo(String s) throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
        log.info("Received call to unsubscribeVnfPackageInfo, parameter {}.", s);
        throw new MethodNotImplementedException("not supported in SM.");
    }

    public File fetchOnboardedVnfPackage(String s) throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
        log.info("Received call to fetchOnboardedVnfPackage, parameter {}.", s);
        throw new MethodNotImplementedException("fetchOnboardedVnfPackageArtifacts not supported in SM.");
    }

    public List<File> fetchOnboardedVnfPackageArtifacts(FetchOnboardedVnfPackageArtifactsRequest fetchOnboardedVnfPackageArtifactsRequest) throws MethodNotImplementedException, NotExistingEntityException, MalformattedElementException {
        log.info("Received call to fetchOnboardedVnfPackageArtifacts, parameter {}.", fetchOnboardedVnfPackageArtifactsRequest);
        throw new MethodNotImplementedException("fetchOnboardedVnfPackageArtifacts not supported in SM.");
    }

    public void abortVnfPackageDeletion(String s) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to abortVnfPackageDeletion, parameter {}.", s);
        throw new MethodNotImplementedException("abortVnfPackageDeletion not supported in SM.");

    }

    public void queryVnfPackageSubscription(GeneralizedQueryRequest generalizedQueryRequest) throws MethodNotImplementedException, NotExistingEntityException, FailedOperationException, MalformattedElementException {
        log.info("Received call to queryVnfPackageSubscription, parameter {}.", generalizedQueryRequest);
        throw new MethodNotImplementedException("queryVnfPackageSubscription not supported in SM.");
    }


}
