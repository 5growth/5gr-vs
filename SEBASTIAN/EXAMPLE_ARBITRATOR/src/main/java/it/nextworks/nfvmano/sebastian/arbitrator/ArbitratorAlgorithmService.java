package it.nextworks.nfvmano.sebastian.arbitrator;

import java.util.*;

import it.nextworks.nfvmano.sebastian.arbitrator.interfaces.ArbitratorInterface;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorRequest;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorResponse;
import it.nextworks.nfvmano.sebastian.common.VsAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;

/**
 * This class implements an example of Arbitrator algorithm
 * 
 * 
 * @author nextworks
 *
 */
@Service
public class ArbitratorAlgorithmService {

	private static final Logger log = LoggerFactory.getLogger(ArbitratorAlgorithmService.class);
	private ArbitratorNotificationRestClient arbitratorNotificationRestClient;

	public ArbitratorAlgorithmService() {
		this.arbitratorNotificationRestClient= new ArbitratorNotificationRestClient("http://localhost:8082");
	}


	public Map<String,String>  computeArbitratorSolution(List<ArbitratorRequest> request)
			throws NotExistingEntityException, FailedOperationException{
		log.debug("Processing request.");
		log.debug("Executing in thread:"+Thread.currentThread().getName());
		Map<String, String> sampleResponse = new HashMap<>();
		sampleResponse.put(request.get(0).getRequestId(), UUID.randomUUID().toString());

		return sampleResponse;

	}


	@Async("threadPoolTaskExecutor")
	public void sendResponse(Map<String, String> generatedIds){
		log.debug("Executing in thread:"+Thread.currentThread().getName());
		try {
			Thread.sleep(100000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(Map.Entry<String, String> entry: generatedIds.entrySet()){
			log.debug("Generating response for operationId:"+entry.getKey()+" requestId: "+entry.getValue());
			ArbitratorResponse response = new ArbitratorResponse(entry.getValue(), true,
					true, "", false, new HashMap<>(), new HashMap<>(), new HashMap<>());
			arbitratorNotificationRestClient.sendArbtirationResponse(entry.getKey(), response);
			log.debug("Sent arbitration response for operation:"+entry.getKey());
		}

	}




}
