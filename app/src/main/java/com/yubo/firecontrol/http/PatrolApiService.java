package com.yubo.firecontrol.http;

import com.yubo.firecontrol.base.BasePostResponse;
import com.yubo.firecontrol.bean.UpImgResultBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lyf on 2018/9/3 11:48
 *
 * @author lyf
 * desc：巡线网络请求Service
 */
public interface PatrolApiService {

    /**
     * 登录接口
     *
     * @param map map
     * @return LoginResultBean LoginResultBean
     */
    @GET("core/platform/login")
    Observable<BasePostResponse<Object>> login(@QueryMap Map<String, String> map);

    /**
     * 意见反馈接口
     *
     * @param body body
     * @return BasePostResponse<Object> BasePostResponse<Object>
     */
    @POST("app/sysAppOpinion/addupdate")
    Observable<BasePostResponse<Object>> msgFeedback(//@Header("token") String token,
                                                     @Body Object body);

    /**
     * 上传文件
     */
    @Multipart
    @POST("file/sysFileInfo/HD/image/upload")
    Call<BasePostResponse<List<UpImgResultBean>>> uploadImg(@Header("qjtoken") String token,
                                                            @Part List<MultipartBody.Part> parts);
    /**
     * 上传文件
     */
    @Multipart
    @POST("file/sysFileInfo/{updateUrl1}/{updateUrl2}/upload")
    Call<BasePostResponse<List<UpImgResultBean>>> uploadImgOnlyOne(@Header("qjtoken") String token,
                                                                   @Path("updateUrl1") String updateUrl1,
                                                                   @Path("updateUrl2") String updateUrl2,
                                                                   @Part MultipartBody.Part part);

    /**
     * 上传文件
     */
    @Multipart
    @POST("{updateUrl}")
    Call<BasePostResponse<List<UpImgResultBean>>> uploadImgOnlyOne(@Header("qjtoken") String token,
                                                                   @Path("updateUrl") String updateUrl,
                                                                   @Part MultipartBody.Part part);
    /**
     * 上传文件
     */
    @Multipart
    @POST("file/sysFileInfo/{updateUrl1}/{updateUrl2}/upload")
    Call<BasePostResponse<List<UpImgResultBean>>> uploadFiles(@Header("qjtoken") String token,
                                                              @Path("updateUrl1") String updateUrl1,
                                                              @Path("updateUrl2") String updateUrl2,
                                                              @Part List<MultipartBody.Part> part);

    /**
     * 上传文件
     */
    @Multipart
    @POST("{updateUrl}")
    Call<BasePostResponse<List<UpImgResultBean>>> uploadFiles(@Header("qjtoken") String token,
                                                              @Path("updateUrl") String updateUrl,
                                                              @Part List<MultipartBody.Part> part);

    /**
     * 工单上报
     */
    @FormUrlEncoded
    @POST("patrol/patrolReportInfo/addupdate")
    Observable<BasePostResponse<Object>> addupdate(@FieldMap Map<String, String> map);

}
