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
 * NsVirtualLinkDesc
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-26T20:09:54.213Z")



public class NsVirtualLinkDesc {
  @SerializedName("connectivityType")
  private ConnectivityType connectivityType = null;

  @SerializedName("description")
  private String description = null;

  @SerializedName("security")
  private SecurityParameters security = null;

  @SerializedName("testAccess")
  private List<String> testAccess = null;

  @SerializedName("virtuaLinkDescVersion")
  private String virtuaLinkDescVersion = null;

  @SerializedName("virtualLinkDescId")
  private String virtualLinkDescId = null;

  @SerializedName("virtualLinkDescProvider")
  private String virtualLinkDescProvider = null;

  @SerializedName("virtualLinkDf")
  private List<VirtualLinkDf> virtualLinkDf = null;

  public NsVirtualLinkDesc connectivityType(ConnectivityType connectivityType) {
    this.connectivityType = connectivityType;
    return this;
  }

   /**
   * Get connectivityType
   * @return connectivityType
  **/
  @ApiModelProperty(value = "")
  public ConnectivityType getConnectivityType() {
    return connectivityType;
  }

  public void setConnectivityType(ConnectivityType connectivityType) {
    this.connectivityType = connectivityType;
  }

  public NsVirtualLinkDesc description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(value = "")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public NsVirtualLinkDesc security(SecurityParameters security) {
    this.security = security;
    return this;
  }

   /**
   * Get security
   * @return security
  **/
  @ApiModelProperty(value = "")
  public SecurityParameters getSecurity() {
    return security;
  }

  public void setSecurity(SecurityParameters security) {
    this.security = security;
  }

  public NsVirtualLinkDesc testAccess(List<String> testAccess) {
    this.testAccess = testAccess;
    return this;
  }

  public NsVirtualLinkDesc addTestAccessItem(String testAccessItem) {
    if (this.testAccess == null) {
      this.testAccess = new ArrayList<String>();
    }
    this.testAccess.add(testAccessItem);
    return this;
  }

   /**
   * Get testAccess
   * @return testAccess
  **/
  @ApiModelProperty(value = "")
  public List<String> getTestAccess() {
    return testAccess;
  }

  public void setTestAccess(List<String> testAccess) {
    this.testAccess = testAccess;
  }

  public NsVirtualLinkDesc virtuaLinkDescVersion(String virtuaLinkDescVersion) {
    this.virtuaLinkDescVersion = virtuaLinkDescVersion;
    return this;
  }

   /**
   * Get virtuaLinkDescVersion
   * @return virtuaLinkDescVersion
  **/
  @ApiModelProperty(value = "")
  public String getVirtuaLinkDescVersion() {
    return virtuaLinkDescVersion;
  }

  public void setVirtuaLinkDescVersion(String virtuaLinkDescVersion) {
    this.virtuaLinkDescVersion = virtuaLinkDescVersion;
  }

  public NsVirtualLinkDesc virtualLinkDescId(String virtualLinkDescId) {
    this.virtualLinkDescId = virtualLinkDescId;
    return this;
  }

   /**
   * Get virtualLinkDescId
   * @return virtualLinkDescId
  **/
  @ApiModelProperty(value = "")
  public String getVirtualLinkDescId() {
    return virtualLinkDescId;
  }

  public void setVirtualLinkDescId(String virtualLinkDescId) {
    this.virtualLinkDescId = virtualLinkDescId;
  }

  public NsVirtualLinkDesc virtualLinkDescProvider(String virtualLinkDescProvider) {
    this.virtualLinkDescProvider = virtualLinkDescProvider;
    return this;
  }

   /**
   * Get virtualLinkDescProvider
   * @return virtualLinkDescProvider
  **/
  @ApiModelProperty(value = "")
  public String getVirtualLinkDescProvider() {
    return virtualLinkDescProvider;
  }

  public void setVirtualLinkDescProvider(String virtualLinkDescProvider) {
    this.virtualLinkDescProvider = virtualLinkDescProvider;
  }

  public NsVirtualLinkDesc virtualLinkDf(List<VirtualLinkDf> virtualLinkDf) {
    this.virtualLinkDf = virtualLinkDf;
    return this;
  }

  public NsVirtualLinkDesc addVirtualLinkDfItem(VirtualLinkDf virtualLinkDfItem) {
    if (this.virtualLinkDf == null) {
      this.virtualLinkDf = new ArrayList<VirtualLinkDf>();
    }
    this.virtualLinkDf.add(virtualLinkDfItem);
    return this;
  }

   /**
   * Get virtualLinkDf
   * @return virtualLinkDf
  **/
  @ApiModelProperty(value = "")
  public List<VirtualLinkDf> getVirtualLinkDf() {
    return virtualLinkDf;
  }

  public void setVirtualLinkDf(List<VirtualLinkDf> virtualLinkDf) {
    this.virtualLinkDf = virtualLinkDf;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsVirtualLinkDesc nsVirtualLinkDesc = (NsVirtualLinkDesc) o;
    return Objects.equals(this.connectivityType, nsVirtualLinkDesc.connectivityType) &&
        Objects.equals(this.description, nsVirtualLinkDesc.description) &&
        Objects.equals(this.security, nsVirtualLinkDesc.security) &&
        Objects.equals(this.testAccess, nsVirtualLinkDesc.testAccess) &&
        Objects.equals(this.virtuaLinkDescVersion, nsVirtualLinkDesc.virtuaLinkDescVersion) &&
        Objects.equals(this.virtualLinkDescId, nsVirtualLinkDesc.virtualLinkDescId) &&
        Objects.equals(this.virtualLinkDescProvider, nsVirtualLinkDesc.virtualLinkDescProvider) &&
        Objects.equals(this.virtualLinkDf, nsVirtualLinkDesc.virtualLinkDf);
  }

  @Override
  public int hashCode() {
    return Objects.hash(connectivityType, description, security, testAccess, virtuaLinkDescVersion, virtualLinkDescId, virtualLinkDescProvider, virtualLinkDf);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsVirtualLinkDesc {\n");
    
    sb.append("    connectivityType: ").append(toIndentedString(connectivityType)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    security: ").append(toIndentedString(security)).append("\n");
    sb.append("    testAccess: ").append(toIndentedString(testAccess)).append("\n");
    sb.append("    virtuaLinkDescVersion: ").append(toIndentedString(virtuaLinkDescVersion)).append("\n");
    sb.append("    virtualLinkDescId: ").append(toIndentedString(virtualLinkDescId)).append("\n");
    sb.append("    virtualLinkDescProvider: ").append(toIndentedString(virtualLinkDescProvider)).append("\n");
    sb.append("    virtualLinkDf: ").append(toIndentedString(virtualLinkDf)).append("\n");
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

