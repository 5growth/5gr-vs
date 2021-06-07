package io.swagger.eveportal.client.model;

import java.util.Objects;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModelProperty;

public class SliceProfile {

    @SerializedName("radioAccessTechnology")
    private RadioAccessTechnology radioAccessTechnology;

    @SerializedName("latency")
    private String latency;


    @SerializedName("uplinkThroughput")
    private String uplinkThroughput;

    @SerializedName("downlinkThroughput")
    private String downlinkThroughput;


    public SliceProfile(){}


    public SliceProfile(RadioAccessTechnology radioAccessTechnology, String latency, String uplinkThroughput, String downlinkThroughput) {
        this.radioAccessTechnology = radioAccessTechnology;
        this.latency = latency;
        this.uplinkThroughput = uplinkThroughput;
        this.downlinkThroughput = downlinkThroughput;
    }

    public RadioAccessTechnology getRadioAccessTechnology() {
        return radioAccessTechnology;
    }

    public String getLatency() {
        return latency;
    }

    public String getUplinkThroughput() {
        return uplinkThroughput;
    }

    public String getDownlinkThroughput() {
        return downlinkThroughput;
    }
}