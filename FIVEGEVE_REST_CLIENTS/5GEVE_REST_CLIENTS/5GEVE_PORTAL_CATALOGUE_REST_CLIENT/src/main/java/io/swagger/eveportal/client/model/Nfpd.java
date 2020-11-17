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
 * Nfpd
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-26T20:09:54.213Z")



public class Nfpd {
  @SerializedName("cpd")
  private List<String> cpd = null;

  @SerializedName("nfpId")
  private String nfpId = null;

  @SerializedName("nfpRule")
  private Rule nfpRule = null;

  @SerializedName("qos")
  private QoS qos = null;

  public Nfpd cpd(List<String> cpd) {
    this.cpd = cpd;
    return this;
  }

  public Nfpd addCpdItem(String cpdItem) {
    if (this.cpd == null) {
      this.cpd = new ArrayList<String>();
    }
    this.cpd.add(cpdItem);
    return this;
  }

   /**
   * Get cpd
   * @return cpd
  **/
  @ApiModelProperty(value = "")
  public List<String> getCpd() {
    return cpd;
  }

  public void setCpd(List<String> cpd) {
    this.cpd = cpd;
  }

  public Nfpd nfpId(String nfpId) {
    this.nfpId = nfpId;
    return this;
  }

   /**
   * Get nfpId
   * @return nfpId
  **/
  @ApiModelProperty(value = "")
  public String getNfpId() {
    return nfpId;
  }

  public void setNfpId(String nfpId) {
    this.nfpId = nfpId;
  }

  public Nfpd nfpRule(Rule nfpRule) {
    this.nfpRule = nfpRule;
    return this;
  }

   /**
   * Get nfpRule
   * @return nfpRule
  **/
  @ApiModelProperty(value = "")
  public Rule getNfpRule() {
    return nfpRule;
  }

  public void setNfpRule(Rule nfpRule) {
    this.nfpRule = nfpRule;
  }

  public Nfpd qos(QoS qos) {
    this.qos = qos;
    return this;
  }

   /**
   * Get qos
   * @return qos
  **/
  @ApiModelProperty(value = "")
  public QoS getQos() {
    return qos;
  }

  public void setQos(QoS qos) {
    this.qos = qos;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Nfpd nfpd = (Nfpd) o;
    return Objects.equals(this.cpd, nfpd.cpd) &&
        Objects.equals(this.nfpId, nfpd.nfpId) &&
        Objects.equals(this.nfpRule, nfpd.nfpRule) &&
        Objects.equals(this.qos, nfpd.qos);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cpd, nfpId, nfpRule, qos);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Nfpd {\n");
    
    sb.append("    cpd: ").append(toIndentedString(cpd)).append("\n");
    sb.append("    nfpId: ").append(toIndentedString(nfpId)).append("\n");
    sb.append("    nfpRule: ").append(toIndentedString(nfpRule)).append("\n");
    sb.append("    qos: ").append(toIndentedString(qos)).append("\n");
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
