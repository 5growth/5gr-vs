package it.nextworks.nfvmano.sebastian.arbitrator.elements;


import it.nextworks.nfvmano.catalogue.translator.NfvNsInstantiationInfo;


public class VNFAction {

    private String vnfdId;
    private VNFActionType vnfActionType;

    //NSD corresponding to the NS where the VNF should be scaled. If null the VS will search for the first NSD containing the VNFD
    private String nsdId;

    //NS DF to which the NS. If null the VS will use the first.
    private String nsDf;

    //NS Instantiation level to which scale
    private String nsInstantiationLevel;

    public VNFAction(String vnfdId, VNFActionType vnfActionType, String nsdId, String nsDf, String nsInstantiationLevel) {
        this.vnfdId = vnfdId;
        this.nsDf=nsDf;
        this.nsdId = nsdId;
        this.nsInstantiationLevel = nsInstantiationLevel;
        this.vnfActionType = vnfActionType;
    }

    public String getVnfdId() {
        return vnfdId;
    }


    public VNFActionType getVnfActionType() {
        return vnfActionType;
    }

    public String getNsdId() {
        return nsdId;
    }

    public String getNsDf() {
        return nsDf;
    }

    public String getNsInstantiationLevel() {
        return nsInstantiationLevel;
    }
}
