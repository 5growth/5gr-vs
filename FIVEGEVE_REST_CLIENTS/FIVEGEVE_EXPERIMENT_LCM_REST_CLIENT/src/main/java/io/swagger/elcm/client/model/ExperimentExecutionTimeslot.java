/*
 * 5GEVE Experiment LCM
 * The API of the 5GEVE Experiment Lifecycle Manager
 *
 * OpenAPI spec version: 1.0
 * Contact: info@nextworks.it
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.elcm.client.model;

import java.util.Objects;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import org.threeten.bp.OffsetDateTime;

/**
 * ExperimentExecutionTimeslot
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-27T08:54:11.910Z")



public class ExperimentExecutionTimeslot {
  @SerializedName("startTime")
  //private OffsetDateTime startTime = null;
  private String startTime = null;

  @SerializedName("stopTime")
  //private OffsetDateTime stopTime = null;
  private String stopTime = null;

  //public ExperimentExecutionTimeslot startTime(OffsetDateTime startTime) {
  public ExperimentExecutionTimeslot startTime(String startTime) {
    this.startTime = startTime;
    return this;
  }

   /**
   * Get startTime
   * @return startTime
  **/
  @ApiModelProperty(value = "")
  //public OffsetDateTime getStartTime() {
  public String getStartTime() {
    return startTime;
  }

  //public void setStartTime(OffsetDateTime startTime) {
  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  //public ExperimentExecutionTimeslot stopTime(OffsetDateTime stopTime) {
  public ExperimentExecutionTimeslot stopTime(String stopTime) {
    this.stopTime = stopTime;
    return this;
  }

   /**
   * Get stopTime
   * @return stopTime
  **/
  @ApiModelProperty(value = "")
  //public OffsetDateTime getStopTime() {
  public String getStopTime() {

    return stopTime;
  }

  //public void setStopTime(OffsetDateTime stopTime) {
  public void setStopTime(String stopTime) {

    this.stopTime = stopTime;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExperimentExecutionTimeslot experimentExecutionTimeslot = (ExperimentExecutionTimeslot) o;
    return Objects.equals(this.startTime, experimentExecutionTimeslot.startTime) &&
        Objects.equals(this.stopTime, experimentExecutionTimeslot.stopTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startTime, stopTime);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExperimentExecutionTimeslot {\n");
    
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    stopTime: ").append(toIndentedString(stopTime)).append("\n");
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

