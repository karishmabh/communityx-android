package com.communityx.models.oauth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OAuthResponse {

    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("data")
    @Expose
    private List<OauthData> data = null;
    @SerializedName("error")
    @Expose
    private Error error;

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public List<OauthData> getData() {
        return data;
    }

    public void setData(List<OauthData> data) {
        this.data = data;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}