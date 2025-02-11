package com.app.ticbook;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiError {
    @SerializedName("non_field_errors")
    private List<String> nonFieldErrors;

    public List<String> getNonFieldErrors() {
        return nonFieldErrors;
    }
}
