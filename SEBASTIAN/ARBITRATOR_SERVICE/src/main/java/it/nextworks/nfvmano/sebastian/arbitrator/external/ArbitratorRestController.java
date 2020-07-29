package it.nextworks.nfvmano.sebastian.arbitrator.external;


import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.nextworks.nfvmano.libs.ifa.common.exceptions.FailedOperationException;
import it.nextworks.nfvmano.sebastian.arbitrator.messages.ArbitratorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import it.nextworks.nfvmano.libs.ifa.common.exceptions.NotExistingEntityException;

@Api(tags = "Arbitrator notification API")
@RestController
@CrossOrigin
@RequestMapping("/arbitrator")
public class ArbitratorRestController {

    private static final Logger log = LoggerFactory.getLogger(ArbitratorRestController.class);

    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")})

    @RequestMapping(value = "/notifyArbitrationResponse/{operationId}", method = RequestMethod.POST)

    public ResponseEntity<?> notifyArbirationResponse(@PathVariable(value="operationId") String operationId,@RequestBody ArbitratorResponse response) {
        log.debug("Received Arbitration Response");
        return new ResponseEntity<>("", HttpStatus.OK);
    }


}
