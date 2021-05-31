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

public class FGrowthAimlpDriver implements AimlPlatformInterface {

    private String baseUrl;
    private String folder;

    public FGrowthAimlpDriver(String baseUrl, String folder){
        this.baseUrl=baseUrl;
        this.folder= folder;

    }

    @Override
    public String retrieveTrainedModelFile(String modelId) throws FailedOperationException {

        String remoteFilePath = baseUrl+"/models/"+modelId+"/file";
        String fileName = UUID.randomUUID().toString();
        String dstFilePath = folder + "/" + fileName;
        File destination = new File(dstFilePath);


        try {
                InputStream in = new URL(remoteFilePath).openStream();
                Files.copy(in, Paths.get(dstFilePath), StandardCopyOption.REPLACE_EXISTING);
                return dstFilePath;
        } catch (MalformedURLException ex) {
           throw  new FailedOperationException(ex);
        } catch (IOException ex) {
            throw  new FailedOperationException(ex);
        }



    }
}
