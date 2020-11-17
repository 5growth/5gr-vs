/*
 * Api Documentation
 * Api Documentation
 *
 * OpenAPI spec version: 1.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.eveportal.client.model;

import java.util.Objects;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Vnffgd
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-26T20:09:54.213Z")



public class Vnffgd {
  @SerializedName("cpdPoolId")
  private List<String> cpdPoolId = null;

  @SerializedName("nfpd")
  private List<Nfpd> nfpd = null;

  @SerializedName("pnfdId")
  private List<String> pnfdId = null;

  @SerializedName("virtualLinkDescId")
  private List<String> virtualLinkDescId = null;

  @SerializedName("vnfdId")
  private List<String> vnfdId = null;

  @SerializedName("vnffgdId")
  private String vnffgdId = null;

  public Vnffgd cpdPoolId(List<String> cpdPoolId) {
    this.cpdPoolId = cpdPoolId;
    return this;
  }

  public Vnffgd addCpdPoolIdItem(String cpdPoolIdItem) {
    if (this.cpdPoolId == null) {
      this.cpdPoolId = new ArrayList<String>();
    }
    this.cpdPoolId.add(cpdPoolIdItem);
    return this;
  }

   /**
   * Get cpdPoolId
   * @return cpdPoolId
  **/
  @ApiModelProperty(value = "")
  public List<String> getCpdPoolId() {
    return cpdPoolId;
  }

  public void setCpdPoolId(List<String> cpdPoolId) {
    this.cpdPoolId = cpdPoolId;
  }

  public Vnffgd nfpd(List<Nfpd> nfpd) {
    this.nfpd = nfpd;
    return this;
  }

  public Vnffgd addNfpdItem(Nfpd nfpdItem) {
    if (this.nfpd == null) {
      this.nfpd = new ArrayList<Nfpd>();
    }
    this.nfpd.add(nfpdItem);
    return this;
  }

   /**
   * Get nfpd
   * @return nfpd
  **/
  @ApiModelProperty(value = "")
  public List<Nfpd> getNfpd() {
    return nfpd;
  }

  public void setNfpd(List<Nfpd> nfpd) {
    this.nfpd = nfpd;
  }

  public Vnffgd pnfdId(List<String> pnfdId) {
    this.pnfdId = pnfdId;
    return this;
  }

  public Vnffgd addPnfdIdItem(String pnfdIdItem) {
    if (this.pnfdId == null) {
      this.pnfdId = new ArrayList<String>();
    }
    this.pnfdId.add(pnfdIdItem);
    return this;
  }

   /**
   * Get pnfdId
   * @return pnfdId
  **/
  @ApiModelProperty(value = "")
  public List<String> getPnfdId() {
    return pnfdId;
  }

  public void setPnfdId(List<String> pnfdId) {
    this.pnfdId = pnfdId;
  }

  public Vnffgd virtualLinkDescId(List<String> virtualLinkDescId) {
    this.virtualLinkDescId = virtualLinkDescId;
    return this;
  }

  public Vnffgd addVirtualLinkDescIdItem(String virtualLinkDescIdItem) {
    if (this.virtualLinkDescId == null) {
      this.virtualLinkDescId = new ArrayList<String>();
    }
    this.virtualLinkDescId.add(virtualLinkDescIdItem);
    return this;
  }

   /**
   * Get virtualLinkDescId
   * @return virtualLinkDescId
  **/
  @ApiModelProperty(value = "")
  public List<String> getVirtualLinkDescId() {
    return virtualLinkDescId;
  }

  public void setVirtualLinkDescId(List<String> virtualLinkDescId) {
    this.virtualLinkDescId = virtualLinkDescId;
  }

  public Vnffgd vnfdId(List<String> vnfdId) {
    this.vnfdId = vnfdId;
    return this;
  }

  public Vnffgd addVnfdIdItem(String vnfdIdItem) {
    if (this.vnfdId == null) {
      this.vnfdId = new ArrayList<String>();
    }
    this.vnfdId.add(vnfdIdItem);
    return this;
  }

   /**
   * Get vnfdId
   * @return vnfdId
  **/
  @ApiModelProperty(value = "")
  public List<String> getVnfdId() {
    return vnfdId;
  }

  public void setVnfdId(List<String> vnfdId) {
    this.vnfdId = vnfdId;
  }

  public Vnffgd vnffgdId(String vnffgdId) {
    this.vnffgdId = vnffgdId;
    return this;
  }

   /**
   * Get vnffgdId
   * @return vnffgdId
  **/
  @ApiModelProperty(value = "")
  public String getVnffgdId() {
    return vnffgdId;
  }

  public void setVnffgdId(String vnffgdId) {
    this.vnffgdId = vnffgdId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Vnffgd vnffgd = (Vnffgd) o;
    return Objects.equals(this.cpdPoolId, vnffgd.cpdPoolId) &&
        Objects.equals(this.nfpd, vnffgd.nfpd) &&
        Objects.equals(this.pnfdId, vnffgd.pnfdId) &&
        Objects.equals(this.virtualLinkDescId, vnffgd.virtualLinkDescId) &&
        Objects.equals(this.vnfdId, vnffgd.vnfdId) &&
        Objects.equals(this.vnffgdId, vnffgd.vnffgdId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cpdPoolId, nfpd, pnfdId, virtualLinkDescId, vnfdId, vnffgdId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Vnffgd {\n");
    
    sb.append("    cpdPoolId: ").append(toIndentedString(cpdPoolId)).append("\n");
    sb.append("    nfpd: ").append(toIndentedString(nfpd)).append("\n");
    sb.append("    pnfdId: ").append(toIndentedString(pnfdId)).append("\n");
    sb.append("    virtualLinkDescId: ").append(toIndentedString(virtualLinkDescId)).append("\n");
    sb.append("    vnfdId: ").append(toIndentedString(vnfdId)).append("\n");
    sb.append("    vnffgdId: ").append(toIndentedString(vnffgdId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
