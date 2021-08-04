package it.nextworks.nfvmano.sebastian.arbitrator.external;

import it.nextworks.nfvmano.catalogue.translator.NfvNsInstantiationInfo;
import it.nextworks.nfvmano.sebastian.arbitrator.aiml.AimlPlatformService;

import it.nextworks.nfvmano.catalogue.arbitrator.im.ArbitratorPolicyInfo;
import it.nextworks.nfvmano.catalogue.arbitrator.services.ArbitratorPolicyService;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.sebastian.arbitrator.AbstractArbitrator;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorRequest;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ExternalArbitrator extends AbstractArbitrator {

    private static final Logger log = LoggerFactory.getLogger(ExternalArbitrator.class);
    private ExternalArbitratorService externalArbitratorService;
    private ExternalArbitratorRestClient externalArbitratorRestClient;

    private ArbitratorPolicyService arbitratorPolicyService;

    private AimlPlatformService aimlPlatformService;

    public ExternalArbitrator(ExternalArbitratorService externalArbitratorService, String arbitratorBaseUrl, ArbitratorPolicyService arbitratorPolicyService, AimlPlatformService aimlPlatformService){
        this.externalArbitratorService= externalArbitratorService;
        externalArbitratorRestClient = new ExternalArbitratorRestClient(arbitratorBaseUrl);
        this.aimlPlatformService=aimlPlatformService;
        this.arbitratorPolicyService =arbitratorPolicyService;
    }

    @Override
    public List<ArbitratorResponse> computeArbitratorSolution(List<ArbitratorRequest> requests) throws FailedOperationException, NotExistingEntityException {
        log.debug("Received request to compute external arbitration");
        //this will be completed by the ExternalArbitratorService
        if(requests==null || requests.size()!=1)
            throw new FailedOperationException("Only one Arbitration Request is supported at the moment");
        CompletableFuture<ArbitratorResponse> arbitratorResponseFuture = new CompletableFuture();

        NfvNsInstantiationInfo data = requests.get(0).getInstantiationNsd().get(requests.get(0).getVsd().getVsDescriptorId());
        ArbitratorPolicyInfo arbitratorPolicyInfo =
                arbitratorPolicyService.getArbitratorPolicyForRequest(requests.get(0).getTenantId(),
                        requests.get(0).getVsd().getVsBlueprintId(),
                        data.getNstId());
        List<ArbitratorRequest> uRequests = new ArrayList<>();
        ArbitratorRequest req = requests.get(0);
        if(arbitratorPolicyInfo!=null){
            if(arbitratorPolicyInfo.trainedFileUpdateRequired()) {

                String filePath = aimlPlatformService.retrieveTrainedModelFile(arbitratorPolicyInfo.getArbitratorPolicy().getModelId());
                arbitratorPolicyService.updateArbitratorPolicyInfoFile(arbitratorPolicyInfo.getArbitratorPolicyInfoId(), new File(filePath));
                externalArbitratorRestClient.updateTrainedFile(new File(filePath).getName(), filePath);
            }
            else arbitratorPolicyService.updateArbitratorPolicyUsage(arbitratorPolicyInfo.getArbitratorPolicyInfoId());
            uRequests.add(new ArbitratorRequest(req.getRequestId(),req.getTenantId(),req.getVsd(), req.getInstantiationNsd(), arbitratorPolicyService.getArbitratorPolicyInfoFile(arbitratorPolicyInfo.getArbitratorPolicyInfoId()) ));
        }

        //Key request id, value operation id
        Map<String, String> operationIds=externalArbitratorRestClient.requestArbitration(uRequests);
        if(operationIds== null || operationIds.size()!=1){
            throw new FailedOperationException("Wrong return from the arbitrator. ONLY ONE CONCURRENT OPERATION SUPPORTED");
        }

        String operationId = operationIds.values().iterator().next();
        log.debug("External arbitration request id:"+operationId);
        externalArbitratorService.registerPendingResponse(operationId, arbitratorResponseFuture);
        log.debug("Waiting external arbitrator response");
        try {
            //The thread execution is locked until receiving the response on the ExternalArbitratorService.
            ArbitratorResponse response = arbitratorResponseFuture.get();
            log.debug("Received external arbitrator response");
            List<ArbitratorResponse> responseList = new ArrayList<>();
            responseList.add(response);
            return responseList;
        } catch (InterruptedException e) {
            log.error("Exception during external arbitration procedure", e);
            throw  new FailedOperationException(e);
        } catch (ExecutionException e) {
            log.error("Exception during external arbitration procedure", e);
            throw  new FailedOperationException(e);
        }

    }

    @Override
    public List<ArbitratorResponse> arbitrateVsScaling(List<ArbitratorRequest> requests) throws FailedOperationException, NotExistingEntityException, MethodNotImplementedException {
        throw new MethodNotImplementedException("VS scaling method not implemented for the External Arbitrator");
    }
}
