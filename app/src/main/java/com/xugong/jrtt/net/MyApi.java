package com.xugong.jrtt.net;

import com.xugong.jrtt.bean.ResponseData;

import retrofit2.Call;
import retrofit2.http.GET;

//注解 与 方法
public interface MyApi {
    @GET("home.json")
    public Call<ResponseData> getType();
}
