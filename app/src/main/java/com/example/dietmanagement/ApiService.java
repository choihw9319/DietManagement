package com.example.dietmanagement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.HTTP;
import retrofit2.http.Query;

public interface ApiService {

    @HTTP(method = "DELETE", path = "/deleteUser", hasBody = true)
    Call<Void> deleteUser(@Body UserIdRequest request);
}

class UserIdRequest {
    private String userid;

    public UserIdRequest(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
