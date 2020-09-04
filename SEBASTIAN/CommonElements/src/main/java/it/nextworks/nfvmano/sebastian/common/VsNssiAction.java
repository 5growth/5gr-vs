/*
 * Copyright 2019 Nextworks s.r.l.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.nextworks.nfvmano.sebastian.common;

import it.nextworks.nfvmano.catalogue.translator.NfvNsInstantiationInfo;

import javax.persistence.Embeddable;

@Embeddable
public class VsNssiAction {
    private String vsiId;
    private VsNssiActionType actionType;

    //NetworkSliceSubnet instance id
    private String nssiId;


    private NfvNsInstantiationInfo nsInstantiationInfo;

    public VsNssiAction() {
    }

    /**
     *
     * @param vsiId ID of Vsi the action belongs to
     * @param actionType type of action
     * @param nsInstantiationInfo Information about the associated NS
     */
    public VsNssiAction(String vsiId, VsNssiActionType actionType, String nssiId, NfvNsInstantiationInfo nsInstantiationInfo) {
        this.vsiId = vsiId;
        this.actionType = actionType;
        this.nsInstantiationInfo = nsInstantiationInfo;
        this.nssiId= nssiId;
    }


    public String getNssiId() {
        return nssiId;
    }

    /**
     *
     * @return vsiId
     */

    public String getVsiId() {
        return vsiId;
    }

    /**
     *
     * @return actionType
     */
    public VsNssiActionType getActionType() {
        return actionType;
    }

    /**
     *
     * @return nsInstantiationInfo
     */
    public NfvNsInstantiationInfo getNsInstantiationInfo() {
        return nsInstantiationInfo;
    }
}
