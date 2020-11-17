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
 * NsLevel
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-26T20:09:54.213Z")



public class NsLevel {
  @SerializedName("description")
  private String description = null;

  @SerializedName("nsLevelId")
  private String nsLevelId = null;

  @SerializedName("nsToLevelMapping")
  private List<NsToLevelMapping> nsToLevelMapping = null;

  @SerializedName("virtualLinkToLevelMapping")
  private List<VirtualLinkToLevelMapping> virtualLinkToLevelMapping = null;

  @SerializedName("vnfToLevelMapping")
  private List<VnfToLevelMapping> vnfToLevelMapping = null;

  public NsLevel description(String description) {
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

  public NsLevel nsLevelId(String nsLevelId) {
    this.nsLevelId = nsLevelId;
    return this;
  }

   /**
   * Get nsLevelId
   * @return nsLevelId
  **/
  @ApiModelProperty(value = "")
  public String getNsLevelId() {
    return nsLevelId;
  }

  public void setNsLevelId(String nsLevelId) {
    this.nsLevelId = nsLevelId;
  }

  public NsLevel nsToLevelMapping(List<NsToLevelMapping> nsToLevelMapping) {
    this.nsToLevelMapping = nsToLevelMapping;
    return this;
  }

  public NsLevel addNsToLevelMappingItem(NsToLevelMapping nsToLevelMappingItem) {
    if (this.nsToLevelMapping == null) {
      this.nsToLevelMapping = new ArrayList<NsToLevelMapping>();
    }
    this.nsToLevelMapping.add(nsToLevelMappingItem);
    return this;
  }

   /**
   * Get nsToLevelMapping
   * @return nsToLevelMapping
  **/
  @ApiModelProperty(value = "")
  public List<NsToLevelMapping> getNsToLevelMapping() {
    return nsToLevelMapping;
  }

  public void setNsToLevelMapping(List<NsToLevelMapping> nsToLevelMapping) {
    this.nsToLevelMapping = nsToLevelMapping;
  }

  public NsLevel virtualLinkToLevelMapping(List<VirtualLinkToLevelMapping> virtualLinkToLevelMapping) {
    this.virtualLinkToLevelMapping = virtualLinkToLevelMapping;
    return this;
  }

  public NsLevel addVirtualLinkToLevelMappingItem(VirtualLinkToLevelMapping virtualLinkToLevelMappingItem) {
    if (this.virtualLinkToLevelMapping == null) {
      this.virtualLinkToLevelMapping = new ArrayList<VirtualLinkToLevelMapping>();
    }
    this.virtualLinkToLevelMapping.add(virtualLinkToLevelMappingItem);
    return this;
  }

   /**
   * Get virtualLinkToLevelMapping
   * @return virtualLinkToLevelMapping
  **/
  @ApiModelProperty(value = "")
  public List<VirtualLinkToLevelMapping> getVirtualLinkToLevelMapping() {
    return virtualLinkToLevelMapping;
  }

  public void setVirtualLinkToLevelMapping(List<VirtualLinkToLevelMapping> virtualLinkToLevelMapping) {
    this.virtualLinkToLevelMapping = virtualLinkToLevelMapping;
  }

  public NsLevel vnfToLevelMapping(List<VnfToLevelMapping> vnfToLevelMapping) {
    this.vnfToLevelMapping = vnfToLevelMapping;
    return this;
  }

  public NsLevel addVnfToLevelMappingItem(VnfToLevelMapping vnfToLevelMappingItem) {
    if (this.vnfToLevelMapping == null) {
      this.vnfToLevelMapping = new ArrayList<VnfToLevelMapping>();
    }
    this.vnfToLevelMapping.add(vnfToLevelMappingItem);
    return this;
  }

   /**
   * Get vnfToLevelMapping
   * @return vnfToLevelMapping
  **/
  @ApiModelProperty(value = "")
  public List<VnfToLevelMapping> getVnfToLevelMapping() {
    return vnfToLevelMapping;
  }

  public void setVnfToLevelMapping(List<VnfToLevelMapping> vnfToLevelMapping) {
    this.vnfToLevelMapping = vnfToLevelMapping;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NsLevel nsLevel = (NsLevel) o;
    return Objects.equals(this.description, nsLevel.description) &&
        Objects.equals(this.nsLevelId, nsLevel.nsLevelId) &&
        Objects.equals(this.nsToLevelMapping, nsLevel.nsToLevelMapping) &&
        Objects.equals(this.virtualLinkToLevelMapping, nsLevel.virtualLinkToLevelMapping) &&
        Objects.equals(this.vnfToLevelMapping, nsLevel.vnfToLevelMapping);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, nsLevelId, nsToLevelMapping, virtualLinkToLevelMapping, vnfToLevelMapping);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NsLevel {\n");
    
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    nsLevelId: ").append(toIndentedString(nsLevelId)).append("\n");
    sb.append("    nsToLevelMapping: ").append(toIndentedString(nsToLevelMapping)).append("\n");
    sb.append("    virtualLinkToLevelMapping: ").append(toIndentedString(virtualLinkToLevelMapping)).append("\n");
    sb.append("    vnfToLevelMapping: ").append(toIndentedString(vnfToLevelMapping)).append("\n");
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
