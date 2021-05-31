package it.nextworks.nfvmano.sebastian.arbitrator.messages;

import it.nextworks.nfvmano.libs.ifa.common.InterfaceMessage;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;

public class ArbitratorTrainedAlgorithmUpdateRequest  implements InterfaceMessage {

    private String fileId;

    public ArbitratorTrainedAlgorithmUpdateRequest(String fileId) {
        this.fileId = fileId;
    }

    public String getFileId() {
        return fileId;
    }

    @Override
    public void isValid() throws MalformattedElementException {
        if(fileId==null || fileId.equals("")) throw new MalformattedElementException("ArbitratorTrainedAlgorithmUpdateRequest without fileId");
    }
}
