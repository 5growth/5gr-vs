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
 * AddressData
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-26T20:09:54.213Z")



public class AddressData {
  /**
   * Gets or Sets addressType
   */
  @JsonAdapter(AddressTypeEnum.Adapter.class)
  public enum AddressTypeEnum {
    MAC_ADDRESS("MAC_ADDRESS"),
    
    IP_ADDRESS("IP_ADDRESS");

    private String value;

    AddressTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static AddressTypeEnum fromValue(String text) {
      for (AddressTypeEnum b : AddressTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<AddressTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final AddressTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public AddressTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return AddressTypeEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("addressType")
  private AddressTypeEnum addressType = null;

  @SerializedName("floatingIpActivated")
  private Boolean floatingIpActivated = null;

  @SerializedName("iPAddressAssignment")
  private Boolean iPAddressAssignment = null;

  /**
   * Gets or Sets iPAddressType
   */
  @JsonAdapter(IPAddressTypeEnum.Adapter.class)
  public enum IPAddressTypeEnum {
    IPV4("IPv4"),
    
    IPV6("IPv6");

    private String value;

    IPAddressTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static IPAddressTypeEnum fromValue(String text) {
      for (IPAddressTypeEnum b : IPAddressTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<IPAddressTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final IPAddressTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public IPAddressTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return IPAddressTypeEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("iPAddressType")
  private IPAddressTypeEnum iPAddressType = null;

  @SerializedName("management")
  private Boolean management = null;

  @SerializedName("numberOfIpAddress")
  private Integer numberOfIpAddress = null;

  public AddressData addressType(AddressTypeEnum addressType) {
    this.addressType = addressType;
    return this;
  }

   /**
   * Get addressType
   * @return addressType
  **/
  @ApiModelProperty(value = "")
  public AddressTypeEnum getAddressType() {
    return addressType;
  }

  public void setAddressType(AddressTypeEnum addressType) {
    this.addressType = addressType;
  }

  public AddressData floatingIpActivated(Boolean floatingIpActivated) {
    this.floatingIpActivated = floatingIpActivated;
    return this;
  }

   /**
   * Get floatingIpActivated
   * @return floatingIpActivated
  **/
  @ApiModelProperty(value = "")
  public Boolean isFloatingIpActivated() {
    return floatingIpActivated;
  }

  public void setFloatingIpActivated(Boolean floatingIpActivated) {
    this.floatingIpActivated = floatingIpActivated;
  }

  public AddressData iPAddressAssignment(Boolean iPAddressAssignment) {
    this.iPAddressAssignment = iPAddressAssignment;
    return this;
  }

   /**
   * Get iPAddressAssignment
   * @return iPAddressAssignment
  **/
  @ApiModelProperty(value = "")
  public Boolean isIPAddressAssignment() {
    return iPAddressAssignment;
  }

  public void setIPAddressAssignment(Boolean iPAddressAssignment) {
    this.iPAddressAssignment = iPAddressAssignment;
  }

  public AddressData iPAddressType(IPAddressTypeEnum iPAddressType) {
    this.iPAddressType = iPAddressType;
    return this;
  }

   /**
   * Get iPAddressType
   * @return iPAddressType
  **/
  @ApiModelProperty(value = "")
  public IPAddressTypeEnum getIPAddressType() {
    return iPAddressType;
  }

  public void setIPAddressType(IPAddressTypeEnum iPAddressType) {
    this.iPAddressType = iPAddressType;
  }

  public AddressData management(Boolean management) {
    this.management = management;
    return this;
  }

   /**
   * Get management
   * @return management
  **/
  @ApiModelProperty(value = "")
  public Boolean isManagement() {
    return management;
  }

  public void setManagement(Boolean management) {
    this.management = management;
  }

  public AddressData numberOfIpAddress(Integer numberOfIpAddress) {
    this.numberOfIpAddress = numberOfIpAddress;
    return this;
  }

   /**
   * Get numberOfIpAddress
   * @return numberOfIpAddress
  **/
  @ApiModelProperty(value = "")
  public Integer getNumberOfIpAddress() {
    return numberOfIpAddress;
  }

  public void setNumberOfIpAddress(Integer numberOfIpAddress) {
    this.numberOfIpAddress = numberOfIpAddress;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddressData addressData = (AddressData) o;
    return Objects.equals(this.addressType, addressData.addressType) &&
        Objects.equals(this.floatingIpActivated, addressData.floatingIpActivated) &&
        Objects.equals(this.iPAddressAssignment, addressData.iPAddressAssignment) &&
        Objects.equals(this.iPAddressType, addressData.iPAddressType) &&
        Objects.equals(this.management, addressData.management) &&
        Objects.equals(this.numberOfIpAddress, addressData.numberOfIpAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(addressType, floatingIpActivated, iPAddressAssignment, iPAddressType, management, numberOfIpAddress);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddressData {\n");
    
    sb.append("    addressType: ").append(toIndentedString(addressType)).append("\n");
    sb.append("    floatingIpActivated: ").append(toIndentedString(floatingIpActivated)).append("\n");
    sb.append("    iPAddressAssignment: ").append(toIndentedString(iPAddressAssignment)).append("\n");
    sb.append("    iPAddressType: ").append(toIndentedString(iPAddressType)).append("\n");
    sb.append("    management: ").append(toIndentedString(management)).append("\n");
    sb.append("    numberOfIpAddress: ").append(toIndentedString(numberOfIpAddress)).append("\n");
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

