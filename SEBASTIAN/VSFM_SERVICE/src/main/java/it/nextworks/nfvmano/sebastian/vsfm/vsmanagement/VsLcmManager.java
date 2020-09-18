/*
 * Copyright (c) 2019 Nextworks s.r.l
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.nextworks.nfvmano.sebastian.vsfm.vsmanagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import it.nextworks.nfvmano.catalogue.blueprint.BlueprintCatalogueUtilities;
import it.nextworks.nfvmano.catalogue.blueprint.elements.*;
import it.nextworks.nfvmano.catalogue.blueprint.messages.QueryVsBlueprintResponse;
import it.nextworks.nfvmano.catalogue.blueprint.services.VsBlueprintCatalogueService;
import it.nextworks.nfvmano.catalogues.template.services.NsTemplateCatalogueService;
import it.nextworks.nfvmano.libs.ifa.common.elements.Filter;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.NsdManagementProviderInterface;
import it.nextworks.nfvmano.libs.ifa.catalogues.interfaces.messages.QueryNsdResponse;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MalformattedElementException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.MethodNotImplementedException;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;
import it.nextworks.nfvmano.catalogue.blueprint.services.VsDescriptorCatalogueService;
import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.NsDf;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.NsLevel;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.Nsd;
import it.nextworks.nfvmano.libs.ifa.descriptors.nsd.VnfToLevelMapping;
import it.nextworks.nfvmano.sebastian.arbitrator.elements.VNFAction;
import it.nextworks.nfvmano.sebastian.common.VirtualResourceCalculatorService;
import it.nextworks.nfvmano.sebastian.common.VsNssiAction;
import it.nextworks.nfvmano.sebastian.common.VsNssiActionType;
import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmProviderInterface;
import it.nextworks.nfvmano.sebastian.nsmf.messages.CreateNsiIdRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.InstantiateNsiRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.ModifyNsiRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.NetworkSliceStatusChange;
import it.nextworks.nfvmano.sebastian.nsmf.messages.TerminateNsiRequest;
import it.nextworks.nfvmano.sebastian.record.elements.*;
import it.nextworks.nfvmano.sebastian.vsfm.VsLcmService;
import it.nextworks.nfvmano.sebastian.vsfm.VsmfUtils;
import it.nextworks.nfvmano.sebastian.vsfm.engine.messages.VsmfEngineMessage;
import it.nextworks.nfvmano.sebastian.vsfm.engine.messages.VsmfEngineMessageType;
import it.nextworks.nfvmano.sebastian.vsfm.engine.messages.InstantiateVsiRequestMessage;
import it.nextworks.nfvmano.sebastian.vsfm.engine.messages.ModifyVsiRequestMessage;
import it.nextworks.nfvmano.sebastian.vsfm.engine.messages.NotifyNsiStatusChange;
import it.nextworks.nfvmano.sebastian.vsfm.engine.messages.NotifyResourceGranted;
import it.nextworks.nfvmano.sebastian.vsfm.engine.messages.TerminateVsiRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.admin.elements.VirtualResourceUsage;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorRequest;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorResponse;
import it.nextworks.nfvmano.sebastian.arbitrator.ArbitratorService;

import it.nextworks.nfvmano.sebastian.record.VsRecordService;
import it.nextworks.nfvmano.catalogue.translator.NfvNsInstantiationInfo;
import it.nextworks.nfvmano.catalogue.translator.TranslatorService;

/**
 * Entity in charge of managing the lifecycle
 * of a single vertical service instance
 *
 * @author nextworks
 *
 */
public class VsLcmManager {

    private static final Logger log = LoggerFactory.getLogger(VsLcmManager.class);
    private String vsiId;
    private String vsiName;
    private VsRecordService vsRecordService;
    private TranslatorService translatorService;
    private ArbitratorService arbitratorService;
    private VsDescriptorCatalogueService vsDescriptorCatalogueService;

    //Used to retrieve the vsb atomic component placement
    private VsBlueprintCatalogueService vsBlueprintCatalogueService;
    private AdminService adminService;
    private VsLcmService vsLcmService;
    private VirtualResourceCalculatorService virtualResourceCalculatorService;
    private VerticalServiceStatus internalStatus;
    private NsmfLcmProviderInterface nsmfLcmProvider;
    private NsdManagementProviderInterface nsdManagementProviderInterface;
    private VsmfUtils vsmfUtils;

    private List<String> nestedVsi = new ArrayList<>();

    private boolean isMultidomain;
    private String networkSliceId;

    //Key: VSD ID; Value: VSD
    private Map<String, VsDescriptor> vsDescriptors = new HashMap<>();
    private String tenantId;

    //Key: nssiIdM Value: NfvNsInstantiationInfo
    private Map<String, NfvNsInstantiationInfo> nssNfvInstantiationInfos = new HashMap<>();

    // the following is for the WAITING_FOR_RESOURCES status
    ArbitratorResponse storedResponse;
    NfvNsInstantiationInfo storedNfvNsInstantiationInfo;
    InstantiateVsiRequestMessage storedInstantiateVsiRequestMessage;

    ObjectMapper mapper = new ObjectMapper();

    /**
     * Constructor
     *
     * @param vsiId                  ID of the vertical service instance
     * @param vsRecordService        wrapper of VSI record
     * @param vsDescriptorCatalogueService repo of VSDs
     * @param translatorService      translator service
     * @param arbitratorService      arbitrator service
     * @param adminService           admin service
     * @param vsLcmService                 engine
     * @param virtualResourceCalculatorService virtual resource calculator service
     * @param nsmfLcmProvider
     * @param vsmfUtils
     */
    public VsLcmManager(String vsiId,
                        String vsiName,
                        VsRecordService vsRecordService,
                        VsDescriptorCatalogueService vsDescriptorCatalogueService,
                        VsBlueprintCatalogueService vsBlueprintCatalogueService,
                        TranslatorService translatorService,
                        ArbitratorService arbitratorService,
                        AdminService adminService,
                        VsLcmService vsLcmService,
                        VirtualResourceCalculatorService virtualResourceCalculatorService,
                        NsmfLcmProviderInterface nsmfLcmProvider,
                        NsdManagementProviderInterface nsdManagementProviderInterface,
                        VsmfUtils vsmfUtils

    ) {
        this.vsiId = vsiId;
        this.vsiName=vsiName;
        this.vsRecordService = vsRecordService;
        this.vsDescriptorCatalogueService = vsDescriptorCatalogueService;
        this.translatorService = translatorService;
        this.arbitratorService = arbitratorService;
        this.adminService = adminService;
        this.internalStatus = VerticalServiceStatus.INSTANTIATING;
        this.vsLcmService = vsLcmService;
        this.virtualResourceCalculatorService=virtualResourceCalculatorService;
        this.nsmfLcmProvider = nsmfLcmProvider;
        this.vsmfUtils = vsmfUtils;
        this.vsBlueprintCatalogueService=vsBlueprintCatalogueService;
        this.nsdManagementProviderInterface = nsdManagementProviderInterface;
    }

    /**
     * Method used to receive messages about VSI lifecycle from the Rabbit MQ
     *
     * @param message received message
     */
    public void receiveMessage(String message) {
        log.debug("Received message for VSI " + vsiId + "\n" + message);

        try {
            ObjectMapper mapper = new ObjectMapper();
            VsmfEngineMessage em = mapper.readValue(message, VsmfEngineMessage.class);
            VsmfEngineMessageType type = em.getType();

            switch (type) {
                case INSTANTIATE_VSI_REQUEST: {
                    log.debug("Processing VSI instantiation request.");
                    InstantiateVsiRequestMessage instantiateVsRequestMsg = (InstantiateVsiRequestMessage) em;
                    processInstantiateRequest(instantiateVsRequestMsg);
                    break;
                }

                case MODIFY_VSI_REQUEST: {
                    log.debug("Processing VSI termination request.");
                    ModifyVsiRequestMessage modifyVsRequestMsg = (ModifyVsiRequestMessage) em;
                    processModifyRequest(modifyVsRequestMsg);
                    break;
                }

                case TERMINATE_VSI_REQUEST: {
                    log.debug("Processing VSI termination request.");
                    TerminateVsiRequestMessage terminateVsRequestMsg = (TerminateVsiRequestMessage) em;
                    processTerminateRequest(terminateVsRequestMsg);
                    break;
                }

                case NOTIFY_NSI_STATUS_CHANGE: {
                    log.debug("Processing NSI status change notification.");
                    NotifyNsiStatusChange notifyNsiStatusChangeMsg = (NotifyNsiStatusChange) em;
                    processNsiStatusChangeNotification(notifyNsiStatusChangeMsg);
                    break;
                }
                case RESOURCES_GRANTED: {
                    log.debug("Processing resources granted notification.");
                    NotifyResourceGranted notifyResourceGranted = (NotifyResourceGranted) em;
                    processResourcesGrantedNotification(notifyResourceGranted);
                    break;
                }

                default:
                    log.error("Received message with not supported type. Skipping.");
                    break;
            }

        } catch (JsonParseException e) {
            manageVsError("Error while parsing message: " + e.getMessage());
        } catch (JsonMappingException e) {
            manageVsError("Error in Json mapping: " + e.getMessage());
        } catch (IOException e) {
            manageVsError("IO error when receiving json message: " + e.getMessage());
        }
    }

    void setNetworkSliceId(String networkSliceId) {
        this.networkSliceId = networkSliceId;
    }

    void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    void processInstantiateRequest(InstantiateVsiRequestMessage msg) {


        if (internalStatus != VerticalServiceStatus.INSTANTIATING) {
            manageVsError("Received instantiation request in wrong status. Skipping message.");
            return;
        }
        String vsdId = msg.getRequest().getVsdId();
        log.debug("Instantiating Vertical Service " + vsiId + " with VSD " + vsdId);
        try {
            VsDescriptor vsd = vsDescriptorCatalogueService.getVsd(vsdId);
            this.vsDescriptors.put(vsdId, vsd);
            this.tenantId = msg.getRequest().getTenantId();

            List<String> vsdIds = new ArrayList<>();
            vsdIds.add(vsdId);

            Map<String, NfvNsInstantiationInfo> nsInfo = translatorService.translateVsd(vsdIds);
            log.debug("The VSD has been translated in the required network slice characteristics.");

            List<ArbitratorRequest> arbitratorRequests = new ArrayList<>();
            //only a single request is supported at the moment
            ArbitratorRequest arbitratorRequest = new ArbitratorRequest("requestId", tenantId, vsd, nsInfo);
            arbitratorRequests.add(arbitratorRequest);
            ArbitratorResponse arbitratorResponse = arbitratorService.computeArbitratorSolution(arbitratorRequests).get(0);
            //PERFORM ARBITRATION ACTIONS
            if (!(arbitratorResponse.isAcceptableRequest())) {
                if(arbitratorResponse.getImpactedVerticalServiceInstances().isEmpty()) {
                    manageVsError("Error while instantiating VS " + vsiId + ": no solution returned from the arbitrator");

                } else {
                    //TODO: This is not handled in multi-domain case, since no impacted vertical service instances are returned
                    setInternalStatus(VerticalServiceStatus.WAITING_FOR_RESOURCES);
                    vsRecordService.setVsStatus(vsiId, VerticalServiceStatus.WAITING_FOR_RESOURCES);
                    // store interesting info
                    storedResponse = arbitratorResponse;
                    storedNfvNsInstantiationInfo = nsInfo.get(vsdId);
                    storedInstantiateVsiRequestMessage = msg;

                    //invoke engine for VsGroupCoordinator.
                    vsLcmService.requestVsiCoordination(vsiId, arbitratorResponse.getImpactedVerticalServiceInstances());

                }
                return;
            }

            NfvNsInstantiationInfo nsiInfo = nsInfo.get(vsdId);
            isMultidomain = !nsiInfo.getNsstDomain().isEmpty();
            //Trigger NsSubnetSlice scaling to add the resources required by the incoming vertical service
            //This decision is taken at the arbitrator based on the NetworkSliceSubnet VNF placement
            if(arbitratorResponse.getImpactedVnfs()!=null && !arbitratorResponse.getImpactedVnfs().isEmpty()){
                log.debug("Triggering VS NsSliceSubnet scaling due to a shared VNF");
                if(!isMultidomain){
                    Map<String, VsNssiAction> actions = new HashMap<>();
                    for(Entry<String, VNFAction> vnfActionEntry : arbitratorResponse.getImpactedVnfs().entrySet()){
                        //TODO
                        String nssiId = "";
                        VerticalServiceInstance ownerVsi = vsRecordService.getNssiOwner(nssiId, null);
                        //TODO: add control to see if the vertical service instance is shared
                        VNFAction vnfAction = vnfActionEntry.getValue();
                        String nstId = ownerVsi.getNssis().get(nssiId).getNsstId();
                        NfvNsInstantiationInfo instantiationInfo = new NfvNsInstantiationInfo(nstId, vnfAction.getNsdId(),
                                null, vnfAction.getNsDf(),vnfAction.getNsInstantiationLevel(), null, null, null );
                        //TODO: add domain id
                        String actionId = nssiId;
                        VsNssiAction currentAction  = new VsNssiAction(ownerVsi.getVsiId(), VsNssiActionType.MODIFY, nssiId, instantiationInfo.getNstId(), instantiationInfo.getDeploymentFlavourId(),
                                instantiationInfo.getInstantiationLevelId(), null);
                        actions.put(actionId, currentAction);

                    }
                    for( Entry<String, VsNssiAction> entry: actions.entrySet()){
                        vsLcmService.requestVsNsiCoordination(this.vsiId, entry.getValue());
                    }

                }else{
                    manageVsError("NsSliceSubnet modification/sharing it is not supported in multidomain NS environments");
                }


                return;
            }
            if (arbitratorResponse.isNewSliceRequired()) {
                log.debug("A new network slice should be instantiated for the Vertical Service instance " + vsiId);

                log.debug("NfvInstantiationInfo for VSI " + vsiId + ": " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nsiInfo));
                //TODO: to be extended for composite VSDs
                Map<String, Object> sliceParameters = new HashMap<>();
                if(nsiInfo.getSliceServiceParameters()!=null){
                    sliceParameters= nsiInfo.getSliceServiceParameters().getSliceServiceParameters();
                }
                List<String> nsSubnetInstanceIds;
                if (arbitratorResponse.getExistingSliceSubnets().isEmpty())
                    nsSubnetInstanceIds = new ArrayList<>();
                else
                    nsSubnetInstanceIds = new ArrayList<>(arbitratorResponse.getExistingSliceSubnets().keySet());

                CreateNsiIdRequest request;
                if (isMultidomain) {



                    for (Map.Entry<String, String> nsst : nsiInfo.getNsstDomain().entrySet()) {
                        String nsstId = nsst.getKey();
                        String nsstDomain = nsst.getValue();

                        request = new CreateNsiIdRequest(nsstId,
                                "NS - " + vsiName + " - " + nsstDomain,
                                "Network slice for VS " + vsiName + " in domain " + nsstDomain);

                        String nssiId = nsmfLcmProvider.createNetworkSliceIdentifier(request, nsstDomain, tenantId);
                        log.debug("Network Slice Subnet ID " + nssiId + " created for VSI " + vsiId);

                        //TODO add VNf placement info and instantiation level/df
                        NetworkSliceSubnetInstance nsi = new NetworkSliceSubnetInstance(nssiId, nsstId, nsstDomain,null, null, NetworkSliceStatus.INSTANTIATING, null);
                        vsRecordService.addNssiInVsi(vsiId, nsi );

                        log.debug("Record updated with info about NSSI and VSI association.");


                        InstantiateNsiRequest instantiateNssiReq = new InstantiateNsiRequest(nssiId,
                                nsstId,
                                nsInfo.get(vsdId).getDeploymentFlavourId(),
                                nsInfo.get(vsdId).getInstantiationLevelId(),
                                nsSubnetInstanceIds,
                                msg.getRequest().getUserData(),
                                msg.getRequest().getLocationConstraints(),
                                msg.getRanEndpointId(), sliceParameters
                        );
                        log.debug("Sending request to instantiate network slice ");
                        nsmfLcmProvider.instantiateNetworkSlice(instantiateNssiReq, nsstDomain, tenantId);
                    }
                } else {
                    log.debug("Using VSMF single domain functionality");
                    //it means it is not multidomain

                    request = new CreateNsiIdRequest(nsiInfo.getNstId(),
                            "NS - " + vsiName,
                            "Network slice for VS " + vsiName);
                    String nsiId = nsmfLcmProvider.createNetworkSliceIdentifier(request, null, tenantId);


                    /*String nsiId = vsRecordService.createNetworkSliceForVsi(vsiId, nsiInfo.getNfvNsdId(), nsiInfo.getVnfdVersion(), nsiInfo.getDeploymentFlavourId(),
                        nsiInfo.getInstantiationLevelId(), nsSubnetInstanceIds, tenantId, msg.getRequest().getName(), msg.getRequest().getDescription());*/
                    log.debug("Network Slice ID " + nsiId + " created for VSI " + vsiId);
                    networkSliceId = nsiId;
                    vsRecordService.setNsiInVsi(vsiId, nsiId);

                    
                    /*//Add nested VSI if any
                    for (String nestedNsiId : nsSubnetInstanceIds) {
                        VerticalServiceInstance nestedVsi = vsRecordService.getVsInstancesFromNetworkSlice(nestedNsiId).get(0);
                        this.nestedVsi.add(nestedVsi.getVsiId());
                        // TODO notify record to add sub-instance to main instance
                        vsRecordService.addNestedVsInVerticalServiceInstance(vsiId, nestedVsi);

                    }*/


                    Map<String, String> userData = msg.getRequest().getUserData();

                    log.debug("Retrieving VSB atomic component placement");
                    Map<String, VsComponentPlacement> vnfPlacement = retrieveVsBlueprintVnfPlacement(vsd.getVsBlueprintId());
                    Map<String, NetworkSliceVnfPlacement> nsVnfPlacements = getNetworkSliceVnfPlacements(vnfPlacement);
                    Map<String, String> userDataPlacements = getUserDataVnfPlacement(nsVnfPlacements);
                    userData.putAll(userDataPlacements);
                    NetworkSliceSubnetInstance nsi = new NetworkSliceSubnetInstance(nsiId, request.getNstId(),
                            null, nsiInfo.getDeploymentFlavourId(), nsiInfo.getInstantiationLevelId(), NetworkSliceStatus.INSTANTIATING,null );
                    vsRecordService.addNssiInVsi(vsiId, nsi);
                    log.debug("Record updated with info about NSI and VSI association.");
                    InstantiateNsiRequest instantiateNsiReq = new InstantiateNsiRequest(nsiId,
                            nsiInfo.getNstId(),
                            nsiInfo.getDeploymentFlavourId(),
                            nsiInfo.getInstantiationLevelId(),
                            nsSubnetInstanceIds,
                            userData,
                            msg.getRequest().getLocationConstraints(),
                            msg.getRanEndpointId(), sliceParameters);
                    log.debug("Sending request to instantiate network slice ");
                    nsmfLcmProvider.instantiateNetworkSlice(instantiateNsiReq, null, tenantId);

                    /*vsLocalEngine.initNewNsLcmManager(networkSliceId, tenantId, msg.getRequest().getName(), msg.getRequest().getDescription());
                    vsLocalEngine.instantiateNs(nsiId, tenantId, nsiInfo.getNfvNsdId(), nsiInfo.getVnfdVersion(),
                        nsiInfo.getDeploymentFlavourId(), nsiInfo.getInstantiationLevelId(), vsiId, nsSubnetInstanceIds);*/
                }

            } else {
                //slice to be shared, not supported at the moment
                manageVsError("Error while instantiating VS " + vsiId + ": solution with slice sharing returned from the arbitrator. Not supported at the moment.");
            }
        } catch (Exception e) {
            manageVsError("Error while instantiating VS " + vsiId + ": " + e.getMessage());
        }
    }

    void processResourcesGrantedNotification(NotifyResourceGranted message) {
        if (internalStatus != VerticalServiceStatus.WAITING_FOR_RESOURCES) {
            manageVsError("Received resource granted notification in wrong status. Skipping message.");
            return;
        }
        if (!message.getVsiId().equals(vsiId)) {
            manageVsError("Received resource granted notification with wrong vsiId. Skipping message");
            return;
        }

        try{
            internalStatus = VerticalServiceStatus.INSTANTIATING;
            vsRecordService.setVsStatus(vsiId, VerticalServiceStatus.INSTANTIATING);

            InstantiateVsiRequestMessage msg = storedInstantiateVsiRequestMessage;
            if (storedResponse.isNewSliceRequired()) {
                log.debug("A new network slice should be instantiated for the Vertical Service instance " + vsiId);


                NfvNsInstantiationInfo nsiInfo = storedNfvNsInstantiationInfo;
                //TODO: to be extended for composite VSDs
                Map<String,Object> sliceParameters = nsiInfo.getSliceServiceParameters().getSliceServiceParameters();

                List<String> nsSubnetInstanceIds;
                if (storedResponse.getExistingSliceSubnets().isEmpty())
                    nsSubnetInstanceIds = new ArrayList<>();
                else
                    nsSubnetInstanceIds = new ArrayList<>(storedResponse.getExistingSliceSubnets().keySet());

                if (!nsiInfo.getNsstDomain().isEmpty()) {
                    //it means it is a VS with multi-domain network slices
                    isMultidomain = true;
                    for (Map.Entry<String, String> nsst : nsiInfo.getNsstDomain().entrySet()) {
                        String nsstId = nsst.getKey();
                        String nsstDomain = nsst.getValue();

                        CreateNsiIdRequest request = new CreateNsiIdRequest(nsstId,
                                "NS - " + vsiName + " - " + nsstDomain,
                                "Network slice for VS " + vsiName + " in domain " + nsstDomain);

                        String nssiId = nsmfLcmProvider.createNetworkSliceIdentifier(request, nsstDomain, tenantId);
                        log.debug("Network Slice Subnet ID " + nssiId + " created for VSI " + vsiId);

                        //TODO add vnf placement info and il/df info
                        NetworkSliceSubnetInstance nsi = new NetworkSliceSubnetInstance(nssiId, nsstId, nsstDomain, null, null, NetworkSliceStatus.INSTANTIATING, new HashMap<>());
                        vsRecordService.addNssiInVsi(vsiId, nsi);

                        log.debug("Record updated with info about NSSI and VSI association.");
                        //In CroCo Df and Il are not considered, for getting this information here we have to add
                        // nsTemplateCatalogueService and nfvoCatalogueService to VsLcmManager constructor
                        InstantiateNsiRequest instantiateNssiReq = new InstantiateNsiRequest(nssiId,
                                nsstId,
                                null,
                                null,
                                nsSubnetInstanceIds,
                                msg.getRequest().getUserData(),
                                msg.getRequest().getLocationConstraints(),
                                msg.getRanEndpointId(), sliceParameters);
                        log.debug("Sending request to instantiate network slice ");
                        nsmfLcmProvider.instantiateNetworkSlice(instantiateNssiReq, nsstDomain, tenantId);
                    }
                } else {
                    isMultidomain = false;
                    CreateNsiIdRequest request = new CreateNsiIdRequest(nsiInfo.getNstId(),
                            "NS - " + vsiName,
                            "Network slice for VS " + vsiName);
                    String nsiId = nsmfLcmProvider.createNetworkSliceIdentifier(request, null, tenantId);

                    //String nsiId = vsRecordService.createNetworkSliceForVsi(vsiId, nsiInfo.getNfvNsdId(), nsiInfo.getVnfdVersion(), nsiInfo.getDeploymentFlavourId(),
                    //        nsiInfo.getInstantiationLevelId(), nsSubnetInstanceIds, tenantId, msg.getRequest().getName(), msg.getRequest().getDescription());
                    log.debug("Network Slice ID " + nsiId + " created for VSI " + vsiId);
                    networkSliceId = nsiId;
                    vsRecordService.setNsiInVsi(vsiId, nsiId);

                    //Add nested VSI if any
                    for (String nestedNsiId : nsSubnetInstanceIds) {
                        VerticalServiceInstance nestedVsi = vsRecordService.getVsInstancesFromNetworkSlice(nestedNsiId).get(0);
                        this.nestedVsi.add(nestedVsi.getVsiId());
                        // TODO notify record to add sub-instance to main instance
                        vsRecordService.addNestedVsInVerticalServiceInstance(vsiId, nestedVsi);

                    }
                    log.debug("Record updated with info about NSI and VSI association.");

                    InstantiateNsiRequest instantiateNsiReq = new InstantiateNsiRequest(nsiId,
                            nsiInfo.getNstId(),
                            nsiInfo.getDeploymentFlavourId(),
                            nsiInfo.getInstantiationLevelId(),
                            nsSubnetInstanceIds,
                            msg.getRequest().getUserData(),
                            msg.getRequest().getLocationConstraints(),
                            msg.getRanEndpointId(), sliceParameters);
                    log.debug("Sending request to instantiate network slice ");
                    nsmfLcmProvider.instantiateNetworkSlice(instantiateNsiReq, null, tenantId);
                }

                //vsLocalEngine.initNewNsLcmManager(networkSliceId, tenantId, msg.getRequest().getName(), msg.getRequest().getDescription());
                //vsLocalEngine.instantiateNs(nsiId, tenantId, nsiInfo.getNfvNsdId(), nsiInfo.getVnfdVersion(),
                //        nsiInfo.getDeploymentFlavourId(), nsiInfo.getInstantiationLevelId(), vsiId, nsSubnetInstanceIds);


                // put all stored object @null
                storedInstantiateVsiRequestMessage = null;
                storedNfvNsInstantiationInfo = null;
                storedResponse = null;
            } else {
                //slice to be shared, not supported at the moment
                manageVsError("Error while instantiating VS " + vsiId + ": solution with slice sharing returned from the arbitrator. Not supported at the moment.");
            }
        } catch (Exception e) {
            manageVsError("Error while instantiating VS " + vsiId + ": " + e.getMessage());
        }
    }

    //TODO: modify this to throw an expection!
    void processModifyRequest(ModifyVsiRequestMessage msg){
        if (!msg.getVsiId().equals(vsiId)) {
            throw new IllegalArgumentException(String.format("Wrong VSI ID: %s", msg.getVsiId()));
        }
        if (internalStatus != VerticalServiceStatus.INSTANTIATED) {
            manageVsError("Received termination request in wrong status. Skipping message.");
            return;
        }
        log.debug("Modifying Vertical Service " + vsiId);
        this.internalStatus = VerticalServiceStatus.UNDER_MODIFICATION;

        String vsdId = msg.getRequest().getVsdId();

        try {

            VsDescriptor vsd = vsDescriptorCatalogueService.getVsd(vsdId);

            List<String> vsdIds = new ArrayList<>();
            vsdIds.add(vsdId);

            //Translate VSDId into NfvNsInstantiationInfo
            Map<String, NfvNsInstantiationInfo> nsInfos = translatorService.translateVsd(vsdIds);
            log.debug("The VSD has been translated in the required network slice characteristics.");
            // assuming one
            NfvNsInstantiationInfo nsInfo = nsInfos.get(vsdId);
            String newNstIt = nsInfo.getNstId();
            String newDfId = nsInfo.getDeploymentFlavourId();
            String newInstantiationLevelId = nsInfo.getInstantiationLevelId();
            String newNsdId = nsInfo.getNfvNsdId();

            // retrieve info about current NSI
            //NetworkSliceInstance nsi = vsRecordService.getNsInstance(networkSliceId);


            if (!isMultidomain) {

                NetworkSliceInstance nsi = vsmfUtils.readNetworkSliceInstanceInformation(networkSliceId, null, tenantId);
                String currentDfId = nsi.getDfId();
                String currentInstantiationLevelId = nsi.getInstantiationLevelId();
                String currentNsdId = nsi.getNsdId();

        		/*compare the triples
				Case 1: only ILs are different -> Scale
				Case 2: nothing is different -> set the new VSDid
				Case 3: either nsdIds or Dfs are different -> Error
			 */
                if (newNsdId.equals(currentNsdId) && newDfId.equals(currentDfId)) {
                    if (newInstantiationLevelId.equals(currentInstantiationLevelId)) {
                        //Case 2
                        log.debug("New vsd set.");
                        this.vsDescriptors.put(vsdId, vsd);
                    } else {
                        //Case 1
                        //Assemble Arbitrator request
                        //this is a trick: in this way, the current Nsi (networkSliceId) and the new one (nsInfos) are both on the same request
                        nsInfos.remove(vsdId);
                        nsInfos.put(networkSliceId, nsInfo);

                        List<ArbitratorRequest> arbitratorRequests = new ArrayList<>();
                        ArbitratorRequest arbitratorRequest = new ArbitratorRequest("scaleRequest", tenantId, vsd, nsInfos);
                        arbitratorRequests.add(arbitratorRequest);
                        ArbitratorResponse arbitratorResponse = arbitratorService.arbitrateVsScaling(arbitratorRequests).get(0);
                        if (!(arbitratorResponse.isAcceptableRequest())) {
                            manageVsError("Error while trying modify VS " + vsiId + ": no solution returned from the arbitrator");
                            return;
                        }
                        //TODO Addititional controls on ArbitratorResponse might be required

                        ModifyNsiRequest modifyNsiRequest = new ModifyNsiRequest(nsi.getNsiId(),
                                newNstIt,
                                newDfId,
                                newInstantiationLevelId,
                                currentNsdId);
                        nsmfLcmProvider.modifyNetworkSlice(modifyNsiRequest, null, tenantId);
                        //vsLocalEngine.modifyNs(nsi.getNsiId(), tenantId, currentNsdId, nsInfo.getVnfdVersion(), newDfId, newInstantiationLevelId, vsiId);
                    }
                } else {
                    // Case 3
                    manageVsError("Error while modifying VS " + vsiId + ": Deployment Flavour and Nsd update are not supported yet");
                }
            } else {
                manageVsError("Scaling not supported for multi-domain scenarios");
                return;
            }
        } catch (Exception e) {
            log.error("Error while modifying VS " + vsiId + ": " + e.getMessage(),e);
            manageVsError("Error while modifying VS " + vsiId + ": " + e.getMessage());
        }

    }

    void processTerminateRequest(TerminateVsiRequestMessage msg) {
        if (!msg.getVsiId().equals(vsiId)) {
            throw new IllegalArgumentException(String.format("Wrong VSI ID: %s", msg.getVsiId()));
        }
        if (internalStatus != VerticalServiceStatus.INSTANTIATED) {
            manageVsError("Received termination request in wrong status. Skipping message.");
            return;
        }

        log.debug("Terminating Vertical Service " + vsiId);
        this.internalStatus = VerticalServiceStatus.TERMINATING;
        try {
            if (isMultidomain) {
                vsRecordService.setVsStatus(vsiId, VerticalServiceStatus.TERMINATING);

                VerticalServiceInstance verticalServiceInstance = vsRecordService.getVsInstance(vsiId);
                Map<String, NetworkSliceSubnetInstance> nssis = verticalServiceInstance.getNssis();

                for (Map.Entry<String, NetworkSliceSubnetInstance> nssi : nssis.entrySet()) {
                    log.debug("Network slice subnet " + nssi.getValue().getNssiId() + " must be terminated.");
                    nsmfLcmProvider.terminateNetworkSliceInstance(new TerminateNsiRequest(nssi.getValue().getNssiId()), nssi.getValue().getDomainId(), tenantId);
                }
            } else {
                vsRecordService.setVsStatus(vsiId, VerticalServiceStatus.TERMINATING);
                List<VerticalServiceInstance> vsis = vsRecordService.getVsInstancesFromNetworkSlice(networkSliceId);
                // Shared NSI support: if vsis > 1 nsi is shared.
                if (vsis.size() > 1) {
                    nsStatusChangeOperations(VerticalServiceStatus.TERMINATED, null);
                } else {
                    log.debug("Network slice " + networkSliceId + " must be terminated.");
                    nsmfLcmProvider.terminateNetworkSliceInstance(new TerminateNsiRequest(networkSliceId), null, tenantId);
                    //vsLocalEngine.terminateNs(networkSliceId);
                }
            }
        } catch (Exception e) {
            manageVsError("Error while terminating VS " + vsiId + ": " + e.getMessage());
        }
    }

    void setInternalStatus(VerticalServiceStatus status) {
        this.internalStatus = status;
    }

    VerticalServiceStatus getInternalStatus() {
        return this.internalStatus;
    }

    //TODO: manage the multi-domain case
    private void nsStatusChangeOperations(VerticalServiceStatus status, String nsiId) throws NotExistingEntityException, Exception {
        VerticalServiceInstance vsi = vsRecordService.getVsInstance(vsiId);
        if (isMultidomain) {

            Map<String, NetworkSliceSubnetInstance> nssis = vsi.getNssis();

            for (Map.Entry<String, NetworkSliceSubnetInstance> nssiEntry : nssis.entrySet()) {
                NetworkSliceSubnetInstance nssi = nssiEntry.getValue();
                String nssiId = nssiEntry.getKey();

                if (nssiId.equalsIgnoreCase(nsiId)) {
                    //VirtualResourceUsage nssiResourceUsage = virtualResourceCalculatorService.computeVirtualResourceUsage(nssi);

                    if (status == VerticalServiceStatus.INSTANTIATED && internalStatus == VerticalServiceStatus.INSTANTIATING) {
                        //adminService.addUsedResourcesInTenant(tenantId, nssiResourceUsage);
                        vsRecordService.updateNssiStatusInVsi(vsiId, nssiId, NetworkSliceStatus.INSTANTIATED);

                        if (vsRecordService.allNssiStatusInVsi(vsiId, NetworkSliceStatus.INSTANTIATED)) {
                            internalStatus = status;
                            vsRecordService.setVsStatus(vsiId, status);
                            log.debug("Updated resource usage for tenant " + tenantId + ". Instantiation procedure completed");
                        } else {
                            log.debug("Updated resource usage for tenant " + tenantId + ". Instantiation procedure still ongoing");
                        }
                    } else if (status == VerticalServiceStatus.TERMINATED && internalStatus == VerticalServiceStatus.TERMINATING) {
                        //adminService.removeUsedResourcesInTenant(tenantId, nssiResourceUsage);
                        vsRecordService.updateNssiStatusInVsi(vsiId, nssi.getNssiId(), NetworkSliceStatus.TERMINATED);
                        if (vsRecordService.allNssiStatusInVsi(vsiId, NetworkSliceStatus.TERMINATED)) {
                            log.debug("Updated resource usage for tenant " + tenantId + ". Termination procedure completed. - Notifying the engine");
                            vsLcmService.notifyVsiTermination(vsiId);
                            internalStatus = status;
                            vsRecordService.setVsStatus(vsiId, status);
                        } else {
                            log.debug("Updated resource usage for tenant " + tenantId + ". Termination procedure completed still ongoing");
                        }
                    } else if (status == VerticalServiceStatus.MODIFIED) {
                        manageVsError("NSI modification not yet supported in multi-domain scenario");
                        return;
                    } else {
                        manageVsError("Received notification about NSI + " + nsiId + " creation/termination in wrong status: " + status + "/" + internalStatus);
                        return;
                    }
                }
            }
        } else {
            NetworkSliceInstance nsi = vsmfUtils.readNetworkSliceInstanceInformation(networkSliceId, null, tenantId);
            //NetworkSliceInstance nsi = vsRecordService.getNsInstance(networkSliceId);
            VirtualResourceUsage resourceUsage = virtualResourceCalculatorService.computeVirtualResourceUsage(nsi, true);
            if (status == VerticalServiceStatus.INSTANTIATED && internalStatus == VerticalServiceStatus.INSTANTIATING) {
                log.debug("Updating Vertical service instance internal network slice subnet");
                //In the single domain case, a NetworkSliceSubnet was added to store the information about
                //the VNF placement

                Map<String, NetworkSliceSubnetInstance> nsis = vsi.getNssis();
                //Removed size control
                if(nsis!=null && nsis.containsKey(nsiId)){
                    this.retrieveNssiTree(nsiId, null);
                    vsRecordService.updateNssiStatusInVsi(vsiId, nsiId, NetworkSliceStatus.INSTANTIATED);
                }else{
                    log.error("Invalid VSi to NSI mapping. This should not happen");
                    manageVsError("Invalid VSi to NSI mapping. This should not happen");
                }
                adminService.addUsedResourcesInTenant(tenantId, resourceUsage);
                log.debug("Updated resource usage for tenant " + tenantId + ". Instantiation procedure completed.");

            } else if (status == VerticalServiceStatus.TERMINATED && internalStatus == VerticalServiceStatus.TERMINATING) {
                Map<String, NetworkSliceSubnetInstance> nsis = vsi.getNssis();
                if(nsis!=null && nsis.containsKey(nsiId) && nsis.size()==1){
                    vsRecordService.updateNssiStatusInVsi(vsiId, nsiId, NetworkSliceStatus.INSTANTIATED);
                }else{
                    log.error("Invalid VSi to NSI mapping. This should not happen");
                    manageVsError("Invalid VSi to NSI mapping. This should not happen");
                }

                adminService.removeUsedResourcesInTenant(tenantId, resourceUsage);
                log.debug("Updated resource usage for tenant " + tenantId + ". Termination procedure completed. - Notifying the engine");
                //vsLocalEngine.notifyVsiTermination(vsiId);
                vsLcmService.notifyVsiTermination(vsiId);

            } else if (status == VerticalServiceStatus.MODIFIED && internalStatus == VerticalServiceStatus.UNDER_MODIFICATION) {
                VirtualResourceUsage oldResourceUsage = virtualResourceCalculatorService.computeVirtualResourceUsage(nsi, false);
                adminService.removeUsedResourcesInTenant(tenantId, oldResourceUsage);
                adminService.addUsedResourcesInTenant(tenantId, resourceUsage);

                //TODO: check with Pietro what this is...
                //Shall we put this into the NSMF? Commented at the moment
                //vsRecordService.resetOldNsInstantiationLevel(nsi.getNsiId());

                internalStatus = VerticalServiceStatus.INSTANTIATED;
                log.debug("VS Modification procedure completed.");

            } else {
                manageVsError("Received notification about NSI creation in wrong status.");
                return;
            }
            this.internalStatus = status;
            vsRecordService.setVsStatus(vsiId, status);
        }

    }

    void processNsiStatusChangeNotification(NotifyNsiStatusChange msg) {
        if (!((internalStatus == VerticalServiceStatus.INSTANTIATING) || (internalStatus == VerticalServiceStatus.TERMINATING) || (internalStatus == VerticalServiceStatus.UNDER_MODIFICATION))) {
            manageVsError("Received NSI status change notification in wrong status. Skipping message.");
            return;
        }
        NetworkSliceStatusChange nsStatusChange = msg.getStatusChange();
        String nsiId = msg.getNsiId();
        try {
            switch (nsStatusChange) {
                case NSI_CREATED: {

                    nsStatusChangeOperations(VerticalServiceStatus.INSTANTIATED, nsiId);
                    break;
                }
                case NSI_MODIFIED: {
                    nsStatusChangeOperations(VerticalServiceStatus.MODIFIED, nsiId);
                    break;
                }
                case NSI_TERMINATED: {
                    nsStatusChangeOperations(VerticalServiceStatus.TERMINATED, nsiId);
                    break;
                }

                case NSI_FAILED: {
                    manageVsError("Received notification about network slice " + msg.getNsiId() + " failure");
                    break;
                }

                default:
                    break;

            }
        } catch (Exception e) {
            manageVsError("Error while processing NSI status change notification: " + e.getMessage());
        }
    }

        private Map<String, String> getUserDataVnfPlacement(Map<String, NetworkSliceVnfPlacement> nsVnfPlacements){
        log.debug("Mapping NS VNF placement to userData parameters");
        Map<String, String> userData = new HashMap<>();
        if(nsVnfPlacements!=null){
            for(Entry<String, NetworkSliceVnfPlacement> placementEntry : nsVnfPlacements.entrySet()){
                userData.put("vnf.placement."+placementEntry.getKey(), placementEntry.getValue().toString());
            }
        }
        log.debug(userData.toString());
        return userData;

    }

    private Map<String, NetworkSliceVnfPlacement> getNetworkSliceVnfPlacements(Map<String, VsComponentPlacement> vnfPlacement) throws FailedOperationException {
        log.debug("Retrieving VS desired VNF placement in NS");
        Map<String, NetworkSliceVnfPlacement> nsVnfPlacement = new HashMap<>();
        for(Entry<String, VsComponentPlacement> placementEntry: vnfPlacement.entrySet()){

            NetworkSliceVnfPlacement nsEntryPlacement;
            if(placementEntry.getValue()==VsComponentPlacement.CLOUD){
                nsEntryPlacement = NetworkSliceVnfPlacement.CLOUD;
            }else if(placementEntry.getValue()==VsComponentPlacement.EDGE){
                nsEntryPlacement = NetworkSliceVnfPlacement.EDGE;
            }else throw new FailedOperationException("Unsupported VNF placement type:"+placementEntry.getValue());

            nsVnfPlacement.put(placementEntry.getKey(), nsEntryPlacement);
        }
        log.debug(nsVnfPlacement.toString());
        return nsVnfPlacement;
    }


    //key vnfdId, value the correspondent VsComponentPlacement

    private Map<String, VsComponentPlacement> retrieveVsBlueprintVnfPlacement(String vsBlueprintId) throws FailedOperationException, NotExistingEntityException {
        log.debug("Retrieving VSB Component Placement info for vsb:"+vsBlueprintId);

        try {
            QueryVsBlueprintResponse response =
                    vsBlueprintCatalogueService.queryVsBlueprint(
                            new GeneralizedQueryRequest(BlueprintCatalogueUtilities.buildVsBlueprintFilter(vsBlueprintId), null));
            log.debug("Assuming one VSB");
            List<VsComponent> atomicComponents = response.getVsBlueprintInfo().get(0).getVsBlueprint().getAtomicComponents();
            if(atomicComponents!=null){
                Map<String,VsComponentPlacement> componentPlacement = new HashMap<>();
                for (VsComponent currentComponent: atomicComponents){
                    if(currentComponent.getType()== VsComponentType.VNF ){
                        if(currentComponent.getPlacement()!=null) {
                            String key = currentComponent.getComponentId();
                            log.debug("Adding component placement:" + key + " - " + currentComponent.getPlacement());
                            componentPlacement.put(key, currentComponent.getPlacement());
                        }
                    }


                }
                return componentPlacement;
            }else{
                log.debug("No atomic components identified");
                return new HashMap<>();
            }
        } catch (MethodNotImplementedException e) {
            log.error("Exception while retrieving VSB:"+vsBlueprintId, e);
            throw new FailedOperationException(e);
        } catch (MalformattedElementException e) {
            log.error("Exception while retrieving VSB:"+vsBlueprintId, e);
            throw new FailedOperationException(e);
        }
    }

    //This method is used to retrieve the NSSI tree once the NS has been instantiated
    private void retrieveNssiTree(String nssiId, String domain) throws MalformattedElementException, FailedOperationException, MethodNotImplementedException, NotExistingEntityException {
        log.debug("Retrieving NSSI tree for NSI:"+nssiId);
        Map<String,String> filterParms = new HashMap<>();
        filterParms.put("NSI_ID", nssiId);
        Filter filter = new Filter(filterParms);
        GeneralizedQueryRequest request = new GeneralizedQueryRequest(filter, null);

        List<NetworkSliceInstance> nsis = nsmfLcmProvider.queryNetworkSliceInstance(request, domain, tenantId);
        if(nsis!=null && ! nsis.isEmpty()){
            NetworkSliceInstance currentNsi= nsis.get(0);
            Map<String, NetworkSliceSubnetInstance> vsiNsi = vsRecordService.getNssiInVsi(this.vsiId);
            if(!vsiNsi.containsKey(nssiId)){
                NetworkSliceSubnetInstance currentNssi = new NetworkSliceSubnetInstance(nssiId, currentNsi.getNstId(), domain, currentNsi.getDfId(),
                        currentNsi.getInstantiationLevelId(), NetworkSliceStatus.INSTANTIATED, null);
                vsRecordService.addNssiInVsi(vsiId, currentNssi);
            }
            vsRecordService.updateVsiNsiVnfPlacement(this.vsiId, nssiId,currentNsi.getVnfPlacement() );
            if(currentNsi.getNetworkSliceSubnetInstances()!=null) {
                for (String subNssiId : currentNsi.getNetworkSliceSubnetInstances()){
                    retrieveNssiTree(subNssiId, domain);
                }
            }
        }else throw new FailedOperationException("Unable to retrieve NSI with id:"+nssiId);

    }

    private void manageVsError(String errorMessage) {
        log.error(errorMessage);
        vsRecordService.setVsFailureInfo(vsiId, errorMessage);
    }
}
