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

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModelProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ExperimentSchedulingRequest
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-27T08:54:11.910Z")



public class ExperimentSchedulingRequest {
  @SerializedName("experimentName")
  private String experimentName = null;

  @SerializedName("experimentDescriptorId")
  private String experimentDescriptorId = null;

  @SerializedName("proposedTimeSlot")
  private ExperimentExecutionTimeslot proposedTimeSlot = null;

  /**
   * Gets or Sets targetSites
   */
  @JsonAdapter(TargetSitesEnum.Adapter.class)
  public enum TargetSitesEnum {
    ITALY_TURIN("ITALY_TURIN"),
    
    SPAIN_5TONIC("SPAIN_5TONIC"),
    
    FRANCE_PARIS("FRANCE_PARIS"),
    
    FRANCE_NICE("FRANCE_NICE"),
    
    SPAIN_5GROWTH_INNOVALIA("SPAIN_5GROWTH_INNOVALIA"),

    ITALY_5GROWTH_COMAU("ITALY_5GROWTH_COMAU"),
    
    FRANCE_RENNES("FRANCE_RENNES"),
    
    GREECE_ATHENS("GREECE_ATHENS");

    private String value;

    TargetSitesEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static TargetSitesEnum fromValue(String text) {
      for (TargetSitesEnum b : TargetSitesEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<TargetSitesEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final TargetSitesEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public TargetSitesEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return TargetSitesEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("targetSites")
  private List<TargetSitesEnum> targetSites = null;

  @SerializedName("useCase")
  private String useCase = null;

  public ExperimentSchedulingRequest experimentName(String experimentName) {
    this.experimentName = experimentName;
    return this;
  }

   /**
   * Get experimentName
   * @return experimentName
  **/
  @ApiModelProperty(value = "")
  public String getExperimentName() {
    return experimentName;
  }

  public void setExperimentName(String experimentName) {
    this.experimentName = experimentName;
  }

  public ExperimentSchedulingRequest experimentDescriptorId(String experimentDescriptorId) {
    this.experimentDescriptorId = experimentDescriptorId;
    return this;
  }

   /**
   * Get experimentDescriptorId
   * @return experimentDescriptorId
  **/
  @ApiModelProperty(value = "")
  public String getExperimentDescriptorId() {
    return experimentDescriptorId;
  }

  public void setExperimentDescriptorId(String experimentDescriptorId) {
    this.experimentDescriptorId = experimentDescriptorId;
  }

  public ExperimentSchedulingRequest proposedTimeSlot(ExperimentExecutionTimeslot proposedTimeSlot) {
    this.proposedTimeSlot = proposedTimeSlot;
    return this;
  }

   /**
   * Get proposedTimeSlot
   * @return proposedTimeSlot
  **/
  @ApiModelProperty(value = "")
  public ExperimentExecutionTimeslot getProposedTimeSlot() {
    return proposedTimeSlot;
  }

  public void setProposedTimeSlot(ExperimentExecutionTimeslot proposedTimeSlot) {
    this.proposedTimeSlot = proposedTimeSlot;
  }

  public ExperimentSchedulingRequest targetSites(List<TargetSitesEnum> targetSites) {
    this.targetSites = targetSites;
    return this;
  }

  public ExperimentSchedulingRequest addTargetSitesItem(TargetSitesEnum targetSitesItem) {
    if (this.targetSites == null) {
      this.targetSites = new ArrayList<TargetSitesEnum>();
    }
    this.targetSites.add(targetSitesItem);
    return this;
  }

   /**
   * Get targetSites
   * @return targetSites
  **/
  @ApiModelProperty(value = "")
  public List<TargetSitesEnum> getTargetSites() {
    return targetSites;
  }

  public void setTargetSites(List<TargetSitesEnum> targetSites) {
    this.targetSites = targetSites;
  }

  public ExperimentSchedulingRequest useCase(String useCase) {
    this.useCase = useCase;
    return this;
  }

   /**
   * Get useCase
   * @return useCase
  **/
  @ApiModelProperty(value = "")
  public String getUseCase() {
    return useCase;
  }

  public void setUseCase(String useCase) {
    this.useCase = useCase;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExperimentSchedulingRequest experimentSchedulingRequest = (ExperimentSchedulingRequest) o;
    return Objects.equals(this.experimentName, experimentSchedulingRequest.experimentName) &&
        Objects.equals(this.experimentDescriptorId, experimentSchedulingRequest.experimentDescriptorId) &&
        Objects.equals(this.proposedTimeSlot, experimentSchedulingRequest.proposedTimeSlot) &&
        Objects.equals(this.targetSites, experimentSchedulingRequest.targetSites) &&
        Objects.equals(this.useCase, experimentSchedulingRequest.useCase);
  }

  @Override
  public int hashCode() {
    return Objects.hash(experimentName, experimentDescriptorId, proposedTimeSlot, targetSites, useCase);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExperimentSchedulingRequest {\n");
    
    sb.append("    experimentName: ").append(toIndentedString(experimentName)).append("\n");
    sb.append("    experimentDescriptorId: ").append(toIndentedString(experimentDescriptorId)).append("\n");
    sb.append("    proposedTimeSlot: ").append(toIndentedString(proposedTimeSlot)).append("\n");
    sb.append("    targetSites: ").append(toIndentedString(targetSites)).append("\n");
    sb.append("    useCase: ").append(toIndentedString(useCase)).append("\n");
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

