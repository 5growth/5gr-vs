package it.nextworks.nfvmano;

import javax.annotation.PostConstruct;

import it.nextworks.nfvmano.catalogue.domainLayer.*;
import it.nextworks.nfvmano.catalogue.domainLayer.customDomainLayer.SebastianLocalNspDomainLayer;
import it.nextworks.nfvmano.catalogues.domainLayer.services.DomainCatalogueService;
import it.nextworks.nfvmano.sebastian.vsfm.sbi.NsmfInteractionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import it.nextworks.nfvmano.sebastian.nsmf.NsLcmService;
import it.nextworks.nfvmano.sebastian.vsfm.VsLcmService;
import it.nextworks.nfvmano.sebastian.vsfm.VsmfUtils;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.*;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to link the different components of the slicer 
 * after their initialization
 * 
 * @author nextworks
 *
 */
@Service
public class SlicerConfigurator {
	private static final Logger log = LoggerFactory.getLogger(SlicerConfigurator.class);


	@Autowired
	private DomainCatalogueService domainCatalogueService;

	@Autowired
	private NsmfInteractionHandler nsmfInteractionHandler;

	@Autowired
	private NsLcmService nsLcmService;
	
	@Autowired
	private VsLcmService vsLcmService;
	
	@Autowired
    private VsmfUtils vsmfUtils;

	@Value("${nsmf.local_domain_id:LOCAL}")
	private String localDomainId;
	
	@PostConstruct
	public void init() {
		//in the all-in-one version the consumer of the NSMF notification is the VSMF service
		nsLcmService.setNotificationDispatcher(vsLcmService);
		//vsLcmService.setNsmfLcmProvider(nsLcmService);
		vsmfUtils.setNsmfLcmProvider(nsLcmService);


		log.debug("Adding local Sebastian NSP domain");
		//HERE we should configure the available domains. For the moment only the local domain is configures
		Domain localDomain = new Domain(localDomainId);
		localDomain.setName(localDomainId);
		localDomain.setOwner(localDomainId);
		localDomain.setAdmin(localDomainId);
		localDomain.setDomainStatus(DomainStatus.ACTIVE);
		localDomain.setDomainInterface(new DomainInterface("",Integer.MIN_VALUE, false, InterfaceType.LOCAL));
		DomainLayer localDomainLayer = new SebastianLocalNspDomainLayer(localDomainId);
		ArrayList<DomainLayer> ownedDomainLayers = new ArrayList();
		ownedDomainLayers.add(localDomainLayer);
		localDomain.setOwnedLayers(ownedDomainLayers);
		localDomainLayer.setDomainLayerType(DomainLayerType.NETWORK_SLICE_PROVIDER);
		try {
			domainCatalogueService.onBoardDomain(localDomain);

			nsmfInteractionHandler.addDriver(localDomain.getDomainId(), nsLcmService );
			nsmfInteractionHandler.setDefaultDriver(localDomain.getDomainId());
			vsLcmService.setNsmfLcmProvider(nsmfInteractionHandler);
		} catch (MalformattedElementException e) {
			log.error("Failed to onboard domain:" , e);
		} catch (AlreadyExistingEntityException e) {
			log.error("Failed to onboard domain:" , e);
		}

	}




	
}
