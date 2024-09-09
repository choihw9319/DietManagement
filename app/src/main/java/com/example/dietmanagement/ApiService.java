package com.example.dietmanagement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;

public interface ApiService {
    @DELETE("api/user/delete")
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
