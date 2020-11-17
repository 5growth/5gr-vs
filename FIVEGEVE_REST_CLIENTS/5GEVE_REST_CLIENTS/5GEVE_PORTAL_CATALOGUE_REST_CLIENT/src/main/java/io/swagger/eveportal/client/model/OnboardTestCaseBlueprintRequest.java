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

/**
 * OnboardTestCaseBlueprintRequest
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-10-26T20:09:54.213Z")



public class OnboardTestCaseBlueprintRequest {
  @SerializedName("testCaseBlueprint")
  private TestCaseBlueprint testCaseBlueprint = null;

  public OnboardTestCaseBlueprintRequest testCaseBlueprint(TestCaseBlueprint testCaseBlueprint) {
    this.testCaseBlueprint = testCaseBlueprint;
    return this;
  }

   /**
   * Get testCaseBlueprint
   * @return testCaseBlueprint
  **/
  @ApiModelProperty(value = "")
  public TestCaseBlueprint getTestCaseBlueprint() {
    return testCaseBlueprint;
  }

  public void setTestCaseBlueprint(TestCaseBlueprint testCaseBlueprint) {
    this.testCaseBlueprint = testCaseBlueprint;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OnboardTestCaseBlueprintRequest onboardTestCaseBlueprintRequest = (OnboardTestCaseBlueprintRequest) o;
    return Objects.equals(this.testCaseBlueprint, onboardTestCaseBlueprintRequest.testCaseBlueprint);
  }

  @Override
  public int hashCode() {
    return Objects.hash(testCaseBlueprint);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OnboardTestCaseBlueprintRequest {\n");
    
    sb.append("    testCaseBlueprint: ").append(toIndentedString(testCaseBlueprint)).append("\n");
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

