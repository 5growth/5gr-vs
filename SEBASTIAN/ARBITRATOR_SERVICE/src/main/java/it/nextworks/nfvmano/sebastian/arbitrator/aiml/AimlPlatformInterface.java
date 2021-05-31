package it.nextworks.nfvmano.sebastian.arbitrator.aiml;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;

public interface AimlPlatformInterface {


    String retrieveTrainedModelFile(String modelId) throws FailedOperationException;
}
