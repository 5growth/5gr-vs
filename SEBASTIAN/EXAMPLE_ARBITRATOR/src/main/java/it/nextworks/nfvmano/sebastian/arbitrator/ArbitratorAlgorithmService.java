package it.nextworks.nfvmano.sebastian.arbitrator;

import java.util.*;

import it.nextworks.nfvmano.sebastian.arbitrator.interfaces.ArbitratorInterface;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorRequest;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	public ArbitratorAlgorithmService() { }


	public Map<String,String>  computeArbitratorSolution(List<ArbitratorRequest> request)
			throws NotExistingEntityException, FailedOperationException{
		log.debug("Processing request.");
		Map<String, String> sampleResponse = new HashMap<>();
		sampleResponse.put(request.get(0).getRequestId(), UUID.randomUUID().toString());
		return sampleResponse;

	}




}
