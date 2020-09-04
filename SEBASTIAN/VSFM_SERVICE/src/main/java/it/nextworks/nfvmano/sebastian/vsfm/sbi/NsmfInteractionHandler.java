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
package it.nextworks.nfvmano.sebastian.vsfm.sbi;

import java.util.*;

import it.nextworks.nfvmano.catalogue.domainLayer.*;
import it.nextworks.nfvmano.catalogue.domainLayer.customDomainLayer.NHNspDomainLayer;
import it.nextworks.nfvmano.catalogue.domainLayer.customDomainLayer.OsmNfvoDomainLayer;
import it.nextworks.nfvmano.catalogue.domainLayer.customDomainLayer.SebastianLocalNspDomainLayer;
import it.nextworks.nfvmano.catalogue.domainLayer.customDomainLayer.SonataNspDomainLayer;
import it.nextworks.nfvmano.catalogues.domainLayer.services.DomainCatalogueService;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.*;
import it.nextworks.nfvmano.sebastian.admin.AdminService;
import it.nextworks.nfvmano.sebastian.nsmf.NsLcmService;
import it.nextworks.nfvmano.sebastian.vsfm.VsLcmService;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.nextworks.nfvmano.libs.ifa.common.messages.GeneralizedQueryRequest;
import it.nextworks.nfvmano.sebastian.nsmf.interfaces.NsmfLcmProviderInterface;
import it.nextworks.nfvmano.sebastian.nsmf.messages.CreateNsiIdRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.InstantiateNsiRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.ModifyNsiRequest;
import it.nextworks.nfvmano.sebastian.nsmf.messages.TerminateNsiRequest;
import it.nextworks.nfvmano.sebastian.record.elements.NetworkSliceInstance;


import javax.annotation.PostConstruct;

/**
 * Class used to manage the interaction with external NSMF when this is exposed via REST.
 *
 * @author nextworks
 */
@Service
public class NsmfInteractionHandler implements NsmfLcmProviderInterface {

    private static final Logger log = LoggerFactory.getLogger(NsmfInteractionHandler.class);

    // domainId -> Interface
    private Map<String, NsmfLcmProviderInterface> drivers = new HashMap<String, NsmfLcmProviderInterface>();

    //this is used for single domain scenarios

    @Value("${nsmf.local_domain_id:LOCAL}")
    private String defaultDriver;

    //private NsmfRestClient nsmfRestClient;

    @Autowired
    private AdminService adminService;



    @Autowired
    private DomainCatalogueService domainCatalogueService;

    @Autowired
    private CommonUtils utils;


    @Autowired
    private NsmfLcmOperationPollingManager nsmfLcmOperationPollingManager;

    @Autowired
    private VsLcmService vsLcmService;



    public void addDriver(String domain, NsmfLcmProviderInterface driver){
        log.debug("Adding driver for domain:"+domain);
        drivers.put(domain, driver);
    }




    @Override
    public String createNetworkSliceIdentifier(CreateNsiIdRequest request, String domainId, String tenantId)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
            MalformattedElementException, NotPermittedOperationException {
        if (domainId == null) domainId = defaultDriver;
        return drivers.get(domainId).createNetworkSliceIdentifier(request, domainId, tenantId);
    }

    @Override
    public void instantiateNetworkSlice(InstantiateNsiRequest request, String domainId, String tenantId)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
            MalformattedElementException, NotPermittedOperationException {
        if (domainId == null) domainId = defaultDriver;
        drivers.get(domainId).instantiateNetworkSlice(request, domainId, tenantId);
    }

    @Override
    public void modifyNetworkSlice(ModifyNsiRequest request, String domainId, String tenantId)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
            MalformattedElementException, NotPermittedOperationException {
        if (domainId == null) domainId = defaultDriver;
        drivers.get(domainId).modifyNetworkSlice(request, domainId, tenantId);
    }

    @Override
    public void terminateNetworkSliceInstance(TerminateNsiRequest request, String domainId, String tenantId)
            throws NotExistingEntityException, MethodNotImplementedException, FailedOperationException,
            MalformattedElementException, NotPermittedOperationException {
        if (domainId == null) domainId = defaultDriver;
        drivers.get(domainId).terminateNetworkSliceInstance(request, domainId, tenantId);
    }

    @Override
    public List<NetworkSliceInstance> queryNetworkSliceInstance(GeneralizedQueryRequest request, String domainId, String tenantId)
            throws MethodNotImplementedException, FailedOperationException, MalformattedElementException {
        if (domainId == null) domainId = defaultDriver;
        return drivers.get(domainId).queryNetworkSliceInstance(request, domainId, tenantId);
    }

}
