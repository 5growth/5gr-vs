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

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * AutoscalingRuleCriteria
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-26T20:09:54.213Z")



public class AutoscalingRuleCriteria {
  @SerializedName("name")
  private String name = null;

  @SerializedName("nsMonitoringParamRef")
  private String nsMonitoringParamRef = null;

  /**
   * Gets or Sets scaleInRelationalOperation
   */
  @JsonAdapter(ScaleInRelationalOperationEnum.Adapter.class)
  public enum ScaleInRelationalOperationEnum {
    GE("GE"),
    
    LE("LE"),
    
    GT("GT"),
    
    LT("LT"),
    
    EQ("EQ");

    private String value;

    ScaleInRelationalOperationEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ScaleInRelationalOperationEnum fromValue(String text) {
      for (ScaleInRelationalOperationEnum b : ScaleInRelationalOperationEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<ScaleInRelationalOperationEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ScaleInRelationalOperationEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ScaleInRelationalOperationEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return ScaleInRelationalOperationEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("scaleInRelationalOperation")
  private ScaleInRelationalOperationEnum scaleInRelationalOperation = null;

  @SerializedName("scaleInThreshold")
  private Integer scaleInThreshold = null;

  /**
   * Gets or Sets scaleOutRelationalOperation
   */
  @JsonAdapter(ScaleOutRelationalOperationEnum.Adapter.class)
  public enum ScaleOutRelationalOperationEnum {
    GE("GE"),
    
    LE("LE"),
    
    GT("GT"),
    
    LT("LT"),
    
    EQ("EQ");

    private String value;

    ScaleOutRelationalOperationEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ScaleOutRelationalOperationEnum fromValue(String text) {
      for (ScaleOutRelationalOperationEnum b : ScaleOutRelationalOperationEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<ScaleOutRelationalOperationEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ScaleOutRelationalOperationEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ScaleOutRelationalOperationEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return ScaleOutRelationalOperationEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("scaleOutRelationalOperation")
  private ScaleOutRelationalOperationEnum scaleOutRelationalOperation = null;

  @SerializedName("scaleOutThreshold")
  private Integer scaleOutThreshold = null;

  public AutoscalingRuleCriteria name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public AutoscalingRuleCriteria nsMonitoringParamRef(String nsMonitoringParamRef) {
    this.nsMonitoringParamRef = nsMonitoringParamRef;
    return this;
  }

   /**
   * Get nsMonitoringParamRef
   * @return nsMonitoringParamRef
  **/
  @ApiModelProperty(value = "")
  public String getNsMonitoringParamRef() {
    return nsMonitoringParamRef;
  }

  public void setNsMonitoringParamRef(String nsMonitoringParamRef) {
    this.nsMonitoringParamRef = nsMonitoringParamRef;
  }

  public AutoscalingRuleCriteria scaleInRelationalOperation(ScaleInRelationalOperationEnum scaleInRelationalOperation) {
    this.scaleInRelationalOperation = scaleInRelationalOperation;
    return this;
  }

   /**
   * Get scaleInRelationalOperation
   * @return scaleInRelationalOperation
  **/
  @ApiModelProperty(value = "")
  public ScaleInRelationalOperationEnum getScaleInRelationalOperation() {
    return scaleInRelationalOperation;
  }

  public void setScaleInRelationalOperation(ScaleInRelationalOperationEnum scaleInRelationalOperation) {
    this.scaleInRelationalOperation = scaleInRelationalOperation;
  }

  public AutoscalingRuleCriteria scaleInThreshold(Integer scaleInThreshold) {
    this.scaleInThreshold = scaleInThreshold;
    return this;
  }

   /**
   * Get scaleInThreshold
   * @return scaleInThreshold
  **/
  @ApiModelProperty(value = "")
  public Integer getScaleInThreshold() {
    return scaleInThreshold;
  }

  public void setScaleInThreshold(Integer scaleInThreshold) {
    this.scaleInThreshold = scaleInThreshold;
  }

  public AutoscalingRuleCriteria scaleOutRelationalOperation(ScaleOutRelationalOperationEnum scaleOutRelationalOperation) {
    this.scaleOutRelationalOperation = scaleOutRelationalOperation;
    return this;
  }

   /**
   * Get scaleOutRelationalOperation
   * @return scaleOutRelationalOperation
  **/
  @ApiModelProperty(value = "")
  public ScaleOutRelationalOperationEnum getScaleOutRelationalOperation() {
    return scaleOutRelationalOperation;
  }

  public void setScaleOutRelationalOperation(ScaleOutRelationalOperationEnum scaleOutRelationalOperation) {
    this.scaleOutRelationalOperation = scaleOutRelationalOperation;
  }

  public AutoscalingRuleCriteria scaleOutThreshold(Integer scaleOutThreshold) {
    this.scaleOutThreshold = scaleOutThreshold;
    return this;
  }

   /**
   * Get scaleOutThreshold
   * @return scaleOutThreshold
  **/
  @ApiModelProperty(value = "")
  public Integer getScaleOutThreshold() {
    return scaleOutThreshold;
  }

  public void setScaleOutThreshold(Integer scaleOutThreshold) {
    this.scaleOutThreshold = scaleOutThreshold;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AutoscalingRuleCriteria autoscalingRuleCriteria = (AutoscalingRuleCriteria) o;
    return Objects.equals(this.name, autoscalingRuleCriteria.name) &&
        Objects.equals(this.nsMonitoringParamRef, autoscalingRuleCriteria.nsMonitoringParamRef) &&
        Objects.equals(this.scaleInRelationalOperation, autoscalingRuleCriteria.scaleInRelationalOperation) &&
        Objects.equals(this.scaleInThreshold, autoscalingRuleCriteria.scaleInThreshold) &&
        Objects.equals(this.scaleOutRelationalOperation, autoscalingRuleCriteria.scaleOutRelationalOperation) &&
        Objects.equals(this.scaleOutThreshold, autoscalingRuleCriteria.scaleOutThreshold);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, nsMonitoringParamRef, scaleInRelationalOperation, scaleInThreshold, scaleOutRelationalOperation, scaleOutThreshold);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AutoscalingRuleCriteria {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    nsMonitoringParamRef: ").append(toIndentedString(nsMonitoringParamRef)).append("\n");
    sb.append("    scaleInRelationalOperation: ").append(toIndentedString(scaleInRelationalOperation)).append("\n");
    sb.append("    scaleInThreshold: ").append(toIndentedString(scaleInThreshold)).append("\n");
    sb.append("    scaleOutRelationalOperation: ").append(toIndentedString(scaleOutRelationalOperation)).append("\n");
    sb.append("    scaleOutThreshold: ").append(toIndentedString(scaleOutThreshold)).append("\n");
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
