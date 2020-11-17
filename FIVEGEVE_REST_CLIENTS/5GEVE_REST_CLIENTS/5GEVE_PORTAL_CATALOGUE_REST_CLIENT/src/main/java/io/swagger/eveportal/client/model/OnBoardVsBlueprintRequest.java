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
 * OnBoardVsBlueprintRequest
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-26T20:09:54.213Z")



public class OnBoardVsBlueprintRequest {
  @SerializedName("nsds")
  private List<Nsd> nsds = null;

  @SerializedName("translationRules")
  private List<VsdNsdTranslationRule> translationRules = null;

  @SerializedName("vsBlueprint")
  private VsBlueprint vsBlueprint = null;

  public OnBoardVsBlueprintRequest nsds(List<Nsd> nsds) {
    this.nsds = nsds;
    return this;
  }

  public OnBoardVsBlueprintRequest addNsdsItem(Nsd nsdsItem) {
    if (this.nsds == null) {
      this.nsds = new ArrayList<Nsd>();
    }
    this.nsds.add(nsdsItem);
    return this;
  }

   /**
   * Get nsds
   * @return nsds
  **/
  @ApiModelProperty(value = "")
  public List<Nsd> getNsds() {
    return nsds;
  }

  public void setNsds(List<Nsd> nsds) {
    this.nsds = nsds;
  }

  public OnBoardVsBlueprintRequest translationRules(List<VsdNsdTranslationRule> translationRules) {
    this.translationRules = translationRules;
    return this;
  }

  public OnBoardVsBlueprintRequest addTranslationRulesItem(VsdNsdTranslationRule translationRulesItem) {
    if (this.translationRules == null) {
      this.translationRules = new ArrayList<VsdNsdTranslationRule>();
    }
    this.translationRules.add(translationRulesItem);
    return this;
  }

   /**
   * Get translationRules
   * @return translationRules
  **/
  @ApiModelProperty(value = "")
  public List<VsdNsdTranslationRule> getTranslationRules() {
    return translationRules;
  }

  public void setTranslationRules(List<VsdNsdTranslationRule> translationRules) {
    this.translationRules = translationRules;
  }

  public OnBoardVsBlueprintRequest vsBlueprint(VsBlueprint vsBlueprint) {
    this.vsBlueprint = vsBlueprint;
    return this;
  }

   /**
   * Get vsBlueprint
   * @return vsBlueprint
  **/
  @ApiModelProperty(value = "")
  public VsBlueprint getVsBlueprint() {
    return vsBlueprint;
  }

  public void setVsBlueprint(VsBlueprint vsBlueprint) {
    this.vsBlueprint = vsBlueprint;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OnBoardVsBlueprintRequest onBoardVsBlueprintRequest = (OnBoardVsBlueprintRequest) o;
    return Objects.equals(this.nsds, onBoardVsBlueprintRequest.nsds) &&
        Objects.equals(this.translationRules, onBoardVsBlueprintRequest.translationRules) &&
        Objects.equals(this.vsBlueprint, onBoardVsBlueprintRequest.vsBlueprint);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nsds, translationRules, vsBlueprint);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OnBoardVsBlueprintRequest {\n");
    
    sb.append("    nsds: ").append(toIndentedString(nsds)).append("\n");
    sb.append("    translationRules: ").append(toIndentedString(translationRules)).append("\n");
    sb.append("    vsBlueprint: ").append(toIndentedString(vsBlueprint)).append("\n");
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
