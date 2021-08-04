package it.nextworks.nfvmano.sebastian.arbitrator.aiml.drivers;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.sebastian.arbitrator.aiml.AimlPlatformInterface;

import org.apache.commons.io.FileUtils;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FGrowthAimlpDriver implements AimlPlatformInterface {

    private String baseUrl;
    private String folder;
    private static final Logger log = LoggerFactory.getLogger(FGrowthAimlpDriver.class);

    public FGrowthAimlpDriver(String baseUrl, String folder){
        this.baseUrl=baseUrl;
        this.folder= folder;

    }

    @Override
    public String retrieveTrainedModelFile(String modelId) throws FailedOperationException {
        log.debug("Retrieving AIMLL trained file from platform for model:"+modelId);
        String remoteFilePath = baseUrl+"/models/"+modelId+"/file";
        String fileName = UUID.randomUUID().toString();
        String dstFilePath = folder + fileName;
        File destination = new File(dstFilePath);


        try {
                InputStream in = new URL(remoteFilePath).openStream();
                Files.copy(in, Paths.get(dstFilePath), StandardCopyOption.REPLACE_EXISTING);
                log.debug("Successfully retrieved file");
                return dstFilePath;
        } catch (MalformedURLException ex) {
           throw  new FailedOperationException(ex);
        } catch (IOException ex) {
            throw  new FailedOperationException(ex);
        }



    }
}
