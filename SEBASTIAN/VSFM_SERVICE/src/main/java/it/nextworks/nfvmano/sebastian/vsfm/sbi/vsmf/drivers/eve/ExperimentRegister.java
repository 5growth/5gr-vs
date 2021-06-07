package it.nextworks.nfvmano.sebastian.vsfm.sbi.vsmf.drivers.eve;

import io.swagger.elcm.client.model.Experiment;
import it.nextworks.nfvmano.sebastian.admin.elements.RemoteTenantInfo;

import java.util.HashMap;
import java.util.Map;

public class ExperimentRegister {

    private String name;
    private String expdId;
    private String expId;
    private Experiment.StatusEnum experimentStatus;
    private RemoteTenantInfo remoteTenantInfo;
    private Map<String, String> userData= new HashMap<>();
    private long requestTimestamp;
    private long instantiatedTimestamp;

    public long getAcceptedTimestamp() {
        return acceptedTimestamp;
    }

    public void setAcceptedTimestamp(long acceptedTimestamp) {
        this.acceptedTimestamp = acceptedTimestamp;
    }

    private long acceptedTimestamp;

    public ExperimentRegister(String name, String expdId, String expId, Experiment.StatusEnum experimentStatus, RemoteTenantInfo remoteTenantInfo, Map<String, String> userData, long requestTimestamp) {
        this.name = name;
        this.expdId = expdId;
        this.expId = expId;
        this.experimentStatus = experimentStatus;
        this.remoteTenantInfo = remoteTenantInfo;
        if(userData!=null) this.userData=userData;
        this.requestTimestamp= requestTimestamp;
    }

    public String getName() {
        return name;
    }

    public RemoteTenantInfo getRemoteTenantInfo(){
        return remoteTenantInfo;
    }

    public String getExpdId() {
        return expdId;
    }

    public Map<String, String> getUserData() {
        return userData;
    }

    public String getExpId() {
        return expId;
    }

    public long getInstantiatedTimestamp() {
        return instantiatedTimestamp;
    }

    public void setInstantiatedTimestamp(long instantiatedTimestamp) {
        this.instantiatedTimestamp = instantiatedTimestamp;
    }

    public Experiment.StatusEnum getExperimentStatus() {
        return experimentStatus;
    }

    public void setExperimentStatus(Experiment.StatusEnum experimentStatus) {
        this.experimentStatus = experimentStatus;
    }

    public long getRequestTimestamp() {
        return requestTimestamp;
    }
}
