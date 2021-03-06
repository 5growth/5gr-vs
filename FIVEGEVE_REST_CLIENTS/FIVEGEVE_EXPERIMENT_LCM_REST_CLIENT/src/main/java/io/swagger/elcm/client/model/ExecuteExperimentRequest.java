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

import java.util.HashMap;
import java.util.Map;

/**
 * ExecuteExperimentRequest
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-27T08:54:11.910Z")



public class ExecuteExperimentRequest {
  @SerializedName("executionName")
  private String executionName = null;

  @SerializedName("experimentId")
  private String experimentId = null;

  @SerializedName("testCaseDescriptorConfiguration")
  private Map<String, Map<String, String>> testCaseDescriptorConfiguration = null;

  public ExecuteExperimentRequest executionName(String executionName) {
    this.executionName = executionName;
    return this;
  }

   /**
   * Get executionName
   * @return executionName
  **/
  @ApiModelProperty(value = "")
  public String getExecutionName() {
    return executionName;
  }

  public void setExecutionName(String executionName) {
    this.executionName = executionName;
  }

  public ExecuteExperimentRequest experimentId(String experimentId) {
    this.experimentId = experimentId;
    return this;
  }

   /**
   * Get experimentId
   * @return experimentId
  **/
  @ApiModelProperty(value = "")
  public String getExperimentId() {
    return experimentId;
  }

  public void setExperimentId(String experimentId) {
    this.experimentId = experimentId;
  }

  public ExecuteExperimentRequest testCaseDescriptorConfiguration(Map<String, Map<String, String>> testCaseDescriptorConfiguration) {
    this.testCaseDescriptorConfiguration = testCaseDescriptorConfiguration;
    return this;
  }

  public ExecuteExperimentRequest putTestCaseDescriptorConfigurationItem(String key, Map<String, String> testCaseDescriptorConfigurationItem) {
    if (this.testCaseDescriptorConfiguration == null) {
      this.testCaseDescriptorConfiguration = new HashMap<String, Map<String, String>>();
    }
    this.testCaseDescriptorConfiguration.put(key, testCaseDescriptorConfigurationItem);
    return this;
  }

   /**
   * Get testCaseDescriptorConfiguration
   * @return testCaseDescriptorConfiguration
  **/
  @ApiModelProperty(value = "")
  public Map<String, Map<String, String>> getTestCaseDescriptorConfiguration() {
    return testCaseDescriptorConfiguration;
  }

  public void setTestCaseDescriptorConfiguration(Map<String, Map<String, String>> testCaseDescriptorConfiguration) {
    this.testCaseDescriptorConfiguration = testCaseDescriptorConfiguration;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExecuteExperimentRequest executeExperimentRequest = (ExecuteExperimentRequest) o;
    return Objects.equals(this.executionName, executeExperimentRequest.executionName) &&
        Objects.equals(this.experimentId, executeExperimentRequest.experimentId) &&
        Objects.equals(this.testCaseDescriptorConfiguration, executeExperimentRequest.testCaseDescriptorConfiguration);
  }

  @Override
  public int hashCode() {
    return Objects.hash(executionName, experimentId, testCaseDescriptorConfiguration);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExecuteExperimentRequest {\n");
    
    sb.append("    executionName: ").append(toIndentedString(executionName)).append("\n");
    sb.append("    experimentId: ").append(toIndentedString(experimentId)).append("\n");
    sb.append("    testCaseDescriptorConfiguration: ").append(toIndentedString(testCaseDescriptorConfiguration)).append("\n");
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

