package com.vving.app.retrofit1;

import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by VV on 2017/8/17.
 */

public interface ApiManager {
    @GET("login/")
    Call<LoginResult> getData(@Query("name") String name, @Query("password") String pw);

    //用表单 @FieldMap
    @FormUrlEncoded
    @POST("/url")
    Call<T> postForm(
            @FieldMap Map<String , Object> maps);

    //直接用对象 @Body
    @POST("url")
    Call<T> PostBody(
            @Body Objects objects);

    //直接多参数 @QueryMap
    @PUT("/url")
    Call<T> queryMap(
            @QueryMap Map<String, String> maps);

    //上传文件 @Part
    @Multipart
    @POST("/url")
    Call<ResponseBody> uploadFlie(
            @Part("description") RequestBody description,
            @Part("files") MultipartBody.Part file);

    //多文件上传 @PartMap()
    @Multipart
    @POST("{url}")
    Call<T> uploadFiles(
            @Path("url") String url,
            @PartMap() Map<String, RequestBody> maps);
}
