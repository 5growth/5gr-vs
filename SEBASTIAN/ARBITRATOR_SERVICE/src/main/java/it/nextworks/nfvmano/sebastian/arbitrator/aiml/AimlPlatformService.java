package it.nextworks.nfvmano.sebastian.arbitrator.aiml;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.sebastian.arbitrator.aiml.drivers.FGrowthAimlpDriver;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

@Service
public class AimlPlatformService implements AimlPlatformInterface{


    @Value("${aimlp.type:NONE}")
    private String aimlpType;

    @Value("${aimlp.trained_model_folder:/tmp/aimlp/}")
    private String folder;

    @Value("${aimlp.address:NONE}")
    private String address;

    private AimlPlatformInterface driver;

    @PostConstruct
    private void configureAimlP(){
        if(aimlpType.equals("5GROWTH")){
            driver = new FGrowthAimlpDriver(address, folder);
        }

    }

    @Override
    public String retrieveTrainedModelFile(String modelId) throws FailedOperationException{
        if(driver!=null){
            return driver.retrieveTrainedModelFile(modelId);
        }else throw  new FailedOperationException("AIMLP not configured");

    }



}
