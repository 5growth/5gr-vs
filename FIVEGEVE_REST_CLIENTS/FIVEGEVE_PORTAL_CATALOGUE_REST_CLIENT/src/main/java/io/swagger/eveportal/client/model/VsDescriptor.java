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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * VsDescriptor
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-26T20:09:54.213Z")



public class VsDescriptor {
  /**
   * Gets or Sets managementType
   */
  @JsonAdapter(ManagementTypeEnum.Adapter.class)
  public enum ManagementTypeEnum {
    PROVIDER_MANAGED("PROVIDER_MANAGED"),
    
    TENANT_MANAGED("TENANT_MANAGED");

    private String value;

    ManagementTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ManagementTypeEnum fromValue(String text) {
      for (ManagementTypeEnum b : ManagementTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<ManagementTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ManagementTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ManagementTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return ManagementTypeEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("managementType")
  private ManagementTypeEnum managementType = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("qosParameters")
  private Map<String, String> qosParameters = null;

  @SerializedName("serviceConstraints")
  private List<ServiceConstraints> serviceConstraints = null;

  @SerializedName("sla")
  private VsdSla sla = null;

  @SerializedName("sliceProfiles")
  private Map<String, SliceProfile> sliceProfiles = new HashMap<String, SliceProfile>();

  /**
   * Gets or Sets sst
   */
  @JsonAdapter(SstEnum.Adapter.class)
  public enum SstEnum {
    NONE("NONE"),
    
    EMBB("EMBB"),
    
    URLLC("URLLC"),
    
    M_IOT("M_IOT"),
    
    ENTERPRISE("ENTERPRISE"),
    
    NFV_IAAS("NFV_IAAS");

    private String value;

    SstEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static SstEnum fromValue(String text) {
      for (SstEnum b : SstEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<SstEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final SstEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public SstEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return SstEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("sst")
  private SstEnum sst = null;

  @SerializedName("version")
  private String version = null;

  @SerializedName("vsBlueprintId")
  private String vsBlueprintId = null;

  @SerializedName("vsDescriptorId")
  private String vsDescriptorId = null;

  public VsDescriptor managementType(ManagementTypeEnum managementType) {
    this.managementType = managementType;
    return this;
  }

   /**
   * Get managementType
   * @return managementType
  **/
  @ApiModelProperty(value = "")
  public ManagementTypeEnum getManagementType() {
    return managementType;
  }

  public void setManagementType(ManagementTypeEnum managementType) {
    this.managementType = managementType;
  }

  public VsDescriptor name(String name) {
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

  public VsDescriptor qosParameters(Map<String, String> qosParameters) {
    this.qosParameters = qosParameters;
    return this;
  }

  public VsDescriptor putQosParametersItem(String key, String qosParametersItem) {
    if (this.qosParameters == null) {
      this.qosParameters = new HashMap<String, String>();
    }
    this.qosParameters.put(key, qosParametersItem);
    return this;
  }

   /**
   * Get qosParameters
   * @return qosParameters
  **/
  @ApiModelProperty(value = "")
  public Map<String, String> getQosParameters() {
    return qosParameters;
  }

  public void setQosParameters(Map<String, String> qosParameters) {
    this.qosParameters = qosParameters;
  }

  public VsDescriptor serviceConstraints(List<ServiceConstraints> serviceConstraints) {
    this.serviceConstraints = serviceConstraints;
    return this;
  }

  public VsDescriptor addServiceConstraintsItem(ServiceConstraints serviceConstraintsItem) {
    if (this.serviceConstraints == null) {
      this.serviceConstraints = new ArrayList<ServiceConstraints>();
    }
    this.serviceConstraints.add(serviceConstraintsItem);
    return this;
  }

  public Map<String, SliceProfile> getSliceProfiles() {
    return sliceProfiles;
  }

  public void setSliceProfiles(Map<String, SliceProfile> sliceProfiles) {
    this.sliceProfiles = sliceProfiles;
  }

  /**
   * Get serviceConstraints
   * @return serviceConstraints
  **/
  @ApiModelProperty(value = "")
  public List<ServiceConstraints> getServiceConstraints() {
    return serviceConstraints;
  }

  public void setServiceConstraints(List<ServiceConstraints> serviceConstraints) {
    this.serviceConstraints = serviceConstraints;
  }

  public VsDescriptor sla(VsdSla sla) {
    this.sla = sla;
    return this;
  }

   /**
   * Get sla
   * @return sla
  **/
  @ApiModelProperty(value = "")
  public VsdSla getSla() {
    return sla;
  }

  public void setSla(VsdSla sla) {
    this.sla = sla;
  }

  public VsDescriptor sst(SstEnum sst) {
    this.sst = sst;
    return this;
  }

   /**
   * Get sst
   * @return sst
  **/
  @ApiModelProperty(value = "")
  public SstEnum getSst() {
    return sst;
  }

  public void setSst(SstEnum sst) {
    this.sst = sst;
  }

  public VsDescriptor version(String version) {
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

  public VsDescriptor vsBlueprintId(String vsBlueprintId) {
    this.vsBlueprintId = vsBlueprintId;
    return this;
  }

   /**
   * Get vsBlueprintId
   * @return vsBlueprintId
  **/
  @ApiModelProperty(value = "")
  public String getVsBlueprintId() {
    return vsBlueprintId;
  }

  public void setVsBlueprintId(String vsBlueprintId) {
    this.vsBlueprintId = vsBlueprintId;
  }

  public VsDescriptor vsDescriptorId(String vsDescriptorId) {
    this.vsDescriptorId = vsDescriptorId;
    return this;
  }

   /**
   * Get vsDescriptorId
   * @return vsDescriptorId
  **/
  @ApiModelProperty(value = "")
  public String getVsDescriptorId() {
    return vsDescriptorId;
  }

  public void setVsDescriptorId(String vsDescriptorId) {
    this.vsDescriptorId = vsDescriptorId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VsDescriptor vsDescriptor = (VsDescriptor) o;
    return Objects.equals(this.managementType, vsDescriptor.managementType) &&
        Objects.equals(this.name, vsDescriptor.name) &&
        Objects.equals(this.qosParameters, vsDescriptor.qosParameters) &&
        Objects.equals(this.serviceConstraints, vsDescriptor.serviceConstraints) &&
        Objects.equals(this.sla, vsDescriptor.sla) &&
        Objects.equals(this.sst, vsDescriptor.sst) &&
        Objects.equals(this.version, vsDescriptor.version) &&
        Objects.equals(this.vsBlueprintId, vsDescriptor.vsBlueprintId) &&
        Objects.equals(this.vsDescriptorId, vsDescriptor.vsDescriptorId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(managementType, name, qosParameters, serviceConstraints, sla, sst, version, vsBlueprintId, vsDescriptorId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VsDescriptor {\n");
    
    sb.append("    managementType: ").append(toIndentedString(managementType)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    qosParameters: ").append(toIndentedString(qosParameters)).append("\n");
    sb.append("    serviceConstraints: ").append(toIndentedString(serviceConstraints)).append("\n");
    sb.append("    sla: ").append(toIndentedString(sla)).append("\n");
    sb.append("    sst: ").append(toIndentedString(sst)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    vsBlueprintId: ").append(toIndentedString(vsBlueprintId)).append("\n");
    sb.append("    vsDescriptorId: ").append(toIndentedString(vsDescriptorId)).append("\n");
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

