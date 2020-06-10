package com.xugong.jrtt.net;

import com.xugong.jrtt.bean.MoreData;
import com.xugong.jrtt.bean.NewListData;
import com.xugong.jrtt.bean.PicData;
import com.xugong.jrtt.bean.ResponseData;
import com.xugong.jrtt.bean.VideoData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

//注解 与 方法
public interface MyApi {
    @GET("home.json")
    public Call<ResponseData> getType();

//    @GET("10007/list_1.json")
//    public Call<NewListData> getNewList();

    @GET
    public Call<NewListData> getNewList(@Url String loadFirstUrl);//"10007/list_1.json"
    @GET
    Call<MoreData>getMoreData(@Url String loadMoreUrl);//10007/list_2.json

    //http://192.168.1.102:8080/jrtt/
    @GET("photos/photos_1.json")
    Call<PicData> getPicData();

    @GET("video.json")
    Call<VideoData> getVideoData();
}
