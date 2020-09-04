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

package it.nextworks.nfvmano.sebastian.vsfm.engine.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.nextworks.nfvmano.sebastian.common.VsNssiAction;

import java.util.Map;

public class CoordinateVsiNssiRequest extends VsmfEngineMessage{
    @JsonProperty("vsiCoordinatorId")
    private String vsiCoordinatorId;

    @JsonProperty("vsiId")
    private String vsiId;

    @JsonProperty("vsNssiActions")
    private Map<String, VsNssiAction> vsNssiActions;  //Note <vsiId, VsNssiAction>


    /**
     *
     * @param vsiCoordinatorId
     * @param vsNsiActions
     */
    @JsonCreator
    public CoordinateVsiNssiRequest(@JsonProperty("vsiCoordinatorId") String vsiCoordinatorId,
                                    @JsonProperty("vsNssiActions") Map<String, VsNssiAction> vsNsiActions) {
        this.type = VsmfEngineMessageType.COORDINATE_VSI_NSSI_REQUEST;
        this.vsiCoordinatorId = vsiCoordinatorId;
        if(vsNssiActions != null)
            this.vsNssiActions = vsNssiActions;

    }

    /**
     *
     * @return vsiCoordinatorId
     */
    public String getVsiCoordinatorId() {
        return vsiCoordinatorId;
    }

    /**
     *
     * @return vsNssiActions
     */
    public Map<String, VsNssiAction> getVsNssiActions() {
        return vsNssiActions;
    }
}
