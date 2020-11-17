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
import java.util.ArrayList;
import java.util.List;

/**
 * CtxBlueprint
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-26T20:09:54.213Z")



public class CtxBlueprint {
  @SerializedName("applicationMetrics")
  private List<ApplicationMetric> applicationMetrics = null;

  @SerializedName("atomicComponents")
  private List<VsComponent> atomicComponents = null;

  @SerializedName("blueprintId")
  private String blueprintId = null;

  /**
   * Gets or Sets compatibleSites
   */
  @JsonAdapter(CompatibleSitesEnum.Adapter.class)
  public enum CompatibleSitesEnum {
    ITALY_TURIN("ITALY_TURIN"),
    
    SPAIN_5TONIC("SPAIN_5TONIC"),
    
    FRANCE_PARIS("FRANCE_PARIS"),
    
    FRANCE_NICE("FRANCE_NICE"),
    
    FRANCE_RENNES("FRANCE_RENNES"),
    
    GREECE_ATHENS("GREECE_ATHENS");

    private String value;

    CompatibleSitesEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static CompatibleSitesEnum fromValue(String text) {
      for (CompatibleSitesEnum b : CompatibleSitesEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<CompatibleSitesEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final CompatibleSitesEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public CompatibleSitesEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return CompatibleSitesEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("compatibleSites")
  private List<CompatibleSitesEnum> compatibleSites = null;

  /**
   * Gets or Sets compositionStrategy
   */
  @JsonAdapter(CompositionStrategyEnum.Adapter.class)
  public enum CompositionStrategyEnum {
    PASS_THROUGH("PASS_THROUGH"),
    
    CONNECT("CONNECT");

    private String value;

    CompositionStrategyEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static CompositionStrategyEnum fromValue(String text) {
      for (CompositionStrategyEnum b : CompositionStrategyEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<CompositionStrategyEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final CompositionStrategyEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public CompositionStrategyEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return CompositionStrategyEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("compositionStrategy")
  private CompositionStrategyEnum compositionStrategy = null;

  @SerializedName("configurableParameters")
  private List<String> configurableParameters = null;

  @SerializedName("connectivityServices")
  private List<VsbLink> connectivityServices = null;

  @SerializedName("description")
  private String description = null;

  @SerializedName("endPoints")
  private List<VsbEndpoint> endPoints = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("parameters")
  private List<VsBlueprintParameter> parameters = null;

  @SerializedName("serviceSequence")
  private List<VsbForwardingPathHop> serviceSequence = null;

  @SerializedName("version")
  private String version = null;

  public CtxBlueprint applicationMetrics(List<ApplicationMetric> applicationMetrics) {
    this.applicationMetrics = applicationMetrics;
    return this;
  }

  public CtxBlueprint addApplicationMetricsItem(ApplicationMetric applicationMetricsItem) {
    if (this.applicationMetrics == null) {
      this.applicationMetrics = new ArrayList<ApplicationMetric>();
    }
    this.applicationMetrics.add(applicationMetricsItem);
    return this;
  }

   /**
   * Get applicationMetrics
   * @return applicationMetrics
  **/
  @ApiModelProperty(value = "")
  public List<ApplicationMetric> getApplicationMetrics() {
    return applicationMetrics;
  }

  public void setApplicationMetrics(List<ApplicationMetric> applicationMetrics) {
    this.applicationMetrics = applicationMetrics;
  }

  public CtxBlueprint atomicComponents(List<VsComponent> atomicComponents) {
    this.atomicComponents = atomicComponents;
    return this;
  }

  public CtxBlueprint addAtomicComponentsItem(VsComponent atomicComponentsItem) {
    if (this.atomicComponents == null) {
      this.atomicComponents = new ArrayList<VsComponent>();
    }
    this.atomicComponents.add(atomicComponentsItem);
    return this;
  }

   /**
   * Get atomicComponents
   * @return atomicComponents
  **/
  @ApiModelProperty(value = "")
  public List<VsComponent> getAtomicComponents() {
    return atomicComponents;
  }

  public void setAtomicComponents(List<VsComponent> atomicComponents) {
    this.atomicComponents = atomicComponents;
  }

  public CtxBlueprint blueprintId(String blueprintId) {
    this.blueprintId = blueprintId;
    return this;
  }

   /**
   * Get blueprintId
   * @return blueprintId
  **/
  @ApiModelProperty(value = "")
  public String getBlueprintId() {
    return blueprintId;
  }

  public void setBlueprintId(String blueprintId) {
    this.blueprintId = blueprintId;
  }

  public CtxBlueprint compatibleSites(List<CompatibleSitesEnum> compatibleSites) {
    this.compatibleSites = compatibleSites;
    return this;
  }

  public CtxBlueprint addCompatibleSitesItem(CompatibleSitesEnum compatibleSitesItem) {
    if (this.compatibleSites == null) {
      this.compatibleSites = new ArrayList<CompatibleSitesEnum>();
    }
    this.compatibleSites.add(compatibleSitesItem);
    return this;
  }

   /**
   * Get compatibleSites
   * @return compatibleSites
  **/
  @ApiModelProperty(value = "")
  public List<CompatibleSitesEnum> getCompatibleSites() {
    return compatibleSites;
  }

  public void setCompatibleSites(List<CompatibleSitesEnum> compatibleSites) {
    this.compatibleSites = compatibleSites;
  }

  public CtxBlueprint compositionStrategy(CompositionStrategyEnum compositionStrategy) {
    this.compositionStrategy = compositionStrategy;
    return this;
  }

   /**
   * Get compositionStrategy
   * @return compositionStrategy
  **/
  @ApiModelProperty(value = "")
  public CompositionStrategyEnum getCompositionStrategy() {
    return compositionStrategy;
  }

  public void setCompositionStrategy(CompositionStrategyEnum compositionStrategy) {
    this.compositionStrategy = compositionStrategy;
  }

  public CtxBlueprint configurableParameters(List<String> configurableParameters) {
    this.configurableParameters = configurableParameters;
    return this;
  }

  public CtxBlueprint addConfigurableParametersItem(String configurableParametersItem) {
    if (this.configurableParameters == null) {
      this.configurableParameters = new ArrayList<String>();
    }
    this.configurableParameters.add(configurableParametersItem);
    return this;
  }

   /**
   * Get configurableParameters
   * @return configurableParameters
  **/
  @ApiModelProperty(value = "")
  public List<String> getConfigurableParameters() {
    return configurableParameters;
  }

  public void setConfigurableParameters(List<String> configurableParameters) {
    this.configurableParameters = configurableParameters;
  }

  public CtxBlueprint connectivityServices(List<VsbLink> connectivityServices) {
    this.connectivityServices = connectivityServices;
    return this;
  }

  public CtxBlueprint addConnectivityServicesItem(VsbLink connectivityServicesItem) {
    if (this.connectivityServices == null) {
      this.connectivityServices = new ArrayList<VsbLink>();
    }
    this.connectivityServices.add(connectivityServicesItem);
    return this;
  }

   /**
   * Get connectivityServices
   * @return connectivityServices
  **/
  @ApiModelProperty(value = "")
  public List<VsbLink> getConnectivityServices() {
    return connectivityServices;
  }

  public void setConnectivityServices(List<VsbLink> connectivityServices) {
    this.connectivityServices = connectivityServices;
  }

  public CtxBlueprint description(String description) {
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

  public CtxBlueprint endPoints(List<VsbEndpoint> endPoints) {
    this.endPoints = endPoints;
    return this;
  }

  public CtxBlueprint addEndPointsItem(VsbEndpoint endPointsItem) {
    if (this.endPoints == null) {
      this.endPoints = new ArrayList<VsbEndpoint>();
    }
    this.endPoints.add(endPointsItem);
    return this;
  }

   /**
   * Get endPoints
   * @return endPoints
  **/
  @ApiModelProperty(value = "")
  public List<VsbEndpoint> getEndPoints() {
    return endPoints;
  }

  public void setEndPoints(List<VsbEndpoint> endPoints) {
    this.endPoints = endPoints;
  }

  public CtxBlueprint name(String name) {
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

  public CtxBlueprint parameters(List<VsBlueprintParameter> parameters) {
    this.parameters = parameters;
    return this;
  }

  public CtxBlueprint addParametersItem(VsBlueprintParameter parametersItem) {
    if (this.parameters == null) {
      this.parameters = new ArrayList<VsBlueprintParameter>();
    }
    this.parameters.add(parametersItem);
    return this;
  }

   /**
   * Get parameters
   * @return parameters
  **/
  @ApiModelProperty(value = "")
  public List<VsBlueprintParameter> getParameters() {
    return parameters;
  }

  public void setParameters(List<VsBlueprintParameter> parameters) {
    this.parameters = parameters;
  }

  public CtxBlueprint serviceSequence(List<VsbForwardingPathHop> serviceSequence) {
    this.serviceSequence = serviceSequence;
    return this;
  }

  public CtxBlueprint addServiceSequenceItem(VsbForwardingPathHop serviceSequenceItem) {
    if (this.serviceSequence == null) {
      this.serviceSequence = new ArrayList<VsbForwardingPathHop>();
    }
    this.serviceSequence.add(serviceSequenceItem);
    return this;
  }

   /**
   * Get serviceSequence
   * @return serviceSequence
  **/
  @ApiModelProperty(value = "")
  public List<VsbForwardingPathHop> getServiceSequence() {
    return serviceSequence;
  }

  public void setServiceSequence(List<VsbForwardingPathHop> serviceSequence) {
    this.serviceSequence = serviceSequence;
  }

  public CtxBlueprint version(String version) {
    this.version = version;
    return this;
  }

   /**
   * Get version
   * @return version
  **/
  @ApiModelProperty(value = "")
  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CtxBlueprint ctxBlueprint = (CtxBlueprint) o;
    return Objects.equals(this.applicationMetrics, ctxBlueprint.applicationMetrics) &&
        Objects.equals(this.atomicComponents, ctxBlueprint.atomicComponents) &&
        Objects.equals(this.blueprintId, ctxBlueprint.blueprintId) &&
        Objects.equals(this.compatibleSites, ctxBlueprint.compatibleSites) &&
        Objects.equals(this.compositionStrategy, ctxBlueprint.compositionStrategy) &&
        Objects.equals(this.configurableParameters, ctxBlueprint.configurableParameters) &&
        Objects.equals(this.connectivityServices, ctxBlueprint.connectivityServices) &&
        Objects.equals(this.description, ctxBlueprint.description) &&
        Objects.equals(this.endPoints, ctxBlueprint.endPoints) &&
        Objects.equals(this.name, ctxBlueprint.name) &&
        Objects.equals(this.parameters, ctxBlueprint.parameters) &&
        Objects.equals(this.serviceSequence, ctxBlueprint.serviceSequence) &&
        Objects.equals(this.version, ctxBlueprint.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(applicationMetrics, atomicComponents, blueprintId, compatibleSites, compositionStrategy, configurableParameters, connectivityServices, description, endPoints, name, parameters, serviceSequence, version);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CtxBlueprint {\n");
    
    sb.append("    applicationMetrics: ").append(toIndentedString(applicationMetrics)).append("\n");
    sb.append("    atomicComponents: ").append(toIndentedString(atomicComponents)).append("\n");
    sb.append("    blueprintId: ").append(toIndentedString(blueprintId)).append("\n");
    sb.append("    compatibleSites: ").append(toIndentedString(compatibleSites)).append("\n");
    sb.append("    compositionStrategy: ").append(toIndentedString(compositionStrategy)).append("\n");
    sb.append("    configurableParameters: ").append(toIndentedString(configurableParameters)).append("\n");
    sb.append("    connectivityServices: ").append(toIndentedString(connectivityServices)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    endPoints: ").append(toIndentedString(endPoints)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    parameters: ").append(toIndentedString(parameters)).append("\n");
    sb.append("    serviceSequence: ").append(toIndentedString(serviceSequence)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
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

