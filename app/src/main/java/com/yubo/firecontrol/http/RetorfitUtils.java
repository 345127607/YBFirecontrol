package com.yubo.firecontrol.http;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.yubo.firecontrol.base.BasePostResponse;
import com.yubo.firecontrol.bean.UpImgResultBean;
import com.yubo.firecontrol.utils.Constant;
import com.yubo.firecontrol.utils.PreferencesUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class RetorfitUtils {

    /**
     * 将文件路径数组封装为{@link List<MultipartBody.Part>}
     *
     * @param key       对应请求正文中name的值。目前服务器给出的接口中，所有图片文件使用<br>
     *                  同一个name值，实际情况中有可能需要多个
     * @param filePaths 文件路径数组
     * @param imageType 文件类型
     */
    public static List<MultipartBody.Part> files2Parts(String key, File[] filePaths, MediaType imageType, RetrofitCallback callback) {
        List<MultipartBody.Part> parts = new ArrayList<>(filePaths.length);
        for (File file : filePaths) {
            // 根据类型及File对象创建RequestBody（okhttp的类）
            RequestBody requestBody = RequestBody.create(imageType, file);
            FileRequestBody body = new FileRequestBody(requestBody, callback);
            // 将RequestBody封装成MultipartBody.Part类型（同样是okhttp的）
            MultipartBody.Part part = MultipartBody.Part.
                    createFormData(key, file.getName(), body);
            // 添加进集合
            parts.add(part);
        }
        return parts;
    }

    /**
     * 将文件路径对象封装为{@link <MultipartBody.Part}
     *
     * @param key       对应请求正文中name的值。目前服务器给出的接口中，所有图片文件使用<br>
     *                  同一个name值，实际情况中有可能需要多个
     * @param filePath 文件路径数组
     * @param imageType 文件类型
     */
    public static MultipartBody.Part file2Part(String key, File filePath, MediaType imageType, RetrofitCallback callback) {
        // 根据类型及File对象创建RequestBody（okhttp的类）
        RequestBody requestBody = RequestBody.create(imageType, filePath);
        FileRequestBody body = new FileRequestBody(requestBody, callback);
        // 将RequestBody封装成MultipartBody.Part类型（同样是okhttp的）
        MultipartBody.Part part = MultipartBody.Part.
                createFormData(key, filePath.getName(), body);
        return part;
    }

    /**
     * 将路径对象封装为{@link <MultipartBody.Part}
     *
     * @param key       对应请求正文中name的值。目前服务器给出的接口中，所有图片文件使用<br>
     *                  同一个name值，实际情况中有可能需要多个
     * @param filePath 文件路径数组
     * @param imageType 文件类型
     */
    public static MultipartBody.Part file2Part(String key, String filePath, MediaType imageType, RetrofitCallback callback) {
        File file = FileUtils.getFileByPath(filePath);
        return file2Part(key, file, imageType, callback);
    }

    /**
     * 将路径对象封装为{@link <MultipartBody.Part}
     *
     * @param key       对应请求正文中name的值。目前服务器给出的接口中，所有图片文件使用<br>
     *                  同一个name值，实际情况中有可能需要多个
     * @param filePaths 文件路径数组
     * @param imageType 文件类型
     */
    public static List<MultipartBody.Part> files2Parts(String key, String filePaths, MediaType imageType, RetrofitCallback callback) {
        String[] filePath = filePaths.split(",");
        File[] files = new File[filePath.length];
        for (int i = 0;i < filePath.length;i ++) {
            if (!StringUtils.isTrimEmpty(filePath[i])) {
                files[i] = FileUtils.getFileByPath(filePath[i]);
            }
        }
        return files2Parts(key, files, imageType, callback);
    }

    /**
     * 直接添加文本类型的Part到的MultipartBody的Part集合中
     *
     * @param parts    Part集合
     * @param key      参数名（name属性）
     * @param value    文本内容
     * @param position 插入的位置
     */
    public static void addTextPart(List<MultipartBody.Part> parts,
                                   String key, String value, int position) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        MultipartBody.Part part = MultipartBody.Part.createFormData(key, null, requestBody);
        parts.add(position, part);
    }

    public static Map<String, RequestBody> addTextPart(
            String key, String value) {
        Map<String, RequestBody> map = new HashMap<>();
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        map.put(key, requestBody);
        return map;
    }

    /**
     * 文件上传（单个）
     * @param filePath filePath 文件地址
     * @param fileType fileType 文件类型
     * @param mOnListener mOnListener 回调监听
     */
    public static void updateFile(String filePath, int fileType, OnUpdateFileBackListener mOnListener, int flag) {
        RetrofitCallback<BasePostResponse<List<UpImgResultBean>>> callback = new RetrofitCallback<BasePostResponse<List<UpImgResultBean>>>() {
            @Override
            public void onSuccess(Call<BasePostResponse<List<UpImgResultBean>>> call, Response<BasePostResponse<List<UpImgResultBean>>> response) {
                LogUtils.d(response.body().getObj());
                mOnListener.onUpdateFileSuccess(response.body(), fileType, flag);
            }

            @Override
            public void onLoading(long total, long progress) {
                super.onLoading(total, progress);
                // LogUtils.d(progress * 100 / total + "---->" + total + "---" + progress);
            }

            @Override
            public void onFailure(Call<BasePostResponse<List<UpImgResultBean>>> call, Throwable t) {
                mOnListener.onFailure(t.getMessage());
                LogUtils.d(t.getMessage());
            }
        };
        MediaType parse = MediaType.parse("image/*");
        String updateUrl2 = "image";
        if (fileType == Constant.IMG_TYPE) {
            updateUrl2 = "image";
            parse = MediaType.parse("image/*");
        } else if (fileType == Constant.AUDIO_TYPE) {
            updateUrl2 = "voice";
            parse = MediaType.parse("audio/*");
        } else if (fileType == Constant.VIDEO_TYPE) {
            updateUrl2 = "video";
            parse = MediaType.parse("video/mp4");
        }
        MultipartBody.Part part = RetorfitUtils.file2Part("files", filePath, parse, callback);
        Call<BasePostResponse<List<UpImgResultBean>>> call = GithubService.createRetrofitService(PatrolApiService.class)
                .uploadImgOnlyOne(PreferencesUtil.getString(Constant.TOKEN), "PATROL", updateUrl2, part);
        call.enqueue(callback);
    }

    /**
     * 文件上传（单个）
     * @param filePath filePath 文件地址
     * @param fileType fileType 文件类型
     * @param mOnListener mOnListener 回调监听
     * @param key key 文件服务器key
     * @param updateUrl1 updateUrl 缺省值
     * @param updateUrl2 updateUrl 缺省值
     */
    public static void updateFile(String filePath, int fileType, OnUpdateFileBackListener mOnListener, String key, String updateUrl1, String updateUrl2, int flag) {
        RetrofitCallback<BasePostResponse<List<UpImgResultBean>>> callback = new RetrofitCallback<BasePostResponse<List<UpImgResultBean>>>() {
            @Override
            public void onSuccess(Call<BasePostResponse<List<UpImgResultBean>>> call, Response<BasePostResponse<List<UpImgResultBean>>> response) {
                LogUtils.d(response.body().getObj());
                mOnListener.onUpdateFileSuccess(response.body(), fileType, flag);
            }

            @Override
            public void onFailure(Call<BasePostResponse<List<UpImgResultBean>>> call, Throwable t) {
                mOnListener.onFailure(t.getMessage());
                LogUtils.d(t.getMessage());
            }
        };
        MediaType parse = MediaType.parse("image/*");
        if (fileType == Constant.IMG_TYPE) {
            parse = MediaType.parse("image/*");
        } else if (fileType == Constant.AUDIO_TYPE) {
            parse = MediaType.parse("audio/*");
        } else if (fileType == Constant.VIDEO_TYPE) {
            parse = MediaType.parse("video/mp4");
        }
        MultipartBody.Part part = RetorfitUtils.file2Part(key, filePath, parse, callback);
        Call<BasePostResponse<List<UpImgResultBean>>> call = GithubService.createRetrofitService(PatrolApiService.class)
                .uploadImgOnlyOne(PreferencesUtil.getString(Constant.TOKEN), updateUrl1, updateUrl2, part);
        call.enqueue(callback);
    }

    /**
     * 文件上传（批量）
     * @param filePaths filePaths 文件地址
     * @param fileType fileType 文件类型
     * @param mOnListener mOnListener 回调监听
     */
    public static void updateFiles(String filePaths, int fileType, OnUpdateFileBackListener mOnListener, int flag) {
        RetrofitCallback<BasePostResponse<List<UpImgResultBean>>> callback = new RetrofitCallback<BasePostResponse<List<UpImgResultBean>>>() {
            @Override
            public void onSuccess(Call<BasePostResponse<List<UpImgResultBean>>> call, Response<BasePostResponse<List<UpImgResultBean>>> response) {
                LogUtils.d(response.body().getObj());
                mOnListener.onUpdateFileSuccess(response.body(), fileType, flag);
            }

            @Override
            public void onLoading(long total, long progress) {
                super.onLoading(total, progress);
                // LogUtils.d(progress * 100 / total + "---->" + total + "---" + progress);
            }

            @Override
            public void onFailure(Call<BasePostResponse<List<UpImgResultBean>>> call, Throwable t) {
                mOnListener.onFailure(t.getMessage());
                LogUtils.d(t.getMessage());
            }
        };
        MediaType parse = MediaType.parse("image/*");
        String updateUrl2 = "image";
        if (fileType == Constant.IMG_TYPE) {
            updateUrl2 = "image";
            parse = MediaType.parse("image/*");
        } else if (fileType == Constant.AUDIO_TYPE) {
            updateUrl2 = "voice";
            parse = MediaType.parse("audio/*");
        } else if (fileType == Constant.VIDEO_TYPE) {
            updateUrl2 = "video";
            parse = MediaType.parse("video/mp4");
        }
        List<MultipartBody.Part> part = RetorfitUtils.files2Parts("files", filePaths, parse, callback);
        Call<BasePostResponse<List<UpImgResultBean>>> call = GithubService.createRetrofitService(PatrolApiService.class)
                .uploadFiles(PreferencesUtil.getString(Constant.TOKEN), "PATROL", updateUrl2, part);
        call.enqueue(callback);
    }

    /**
     * 文件上传（批量）
     * @param filePaths filePaths 文件地址
     * @param fileType fileType 文件类型
     * @param mOnListener mOnListener 回调监听
     * @param key key 文件服务器key
     * @param updateUrl1 updateUrl 缺省值
     * @param updateUrl2 updateUrl 缺省值
     */
    public static void updateFiles(String filePaths, int fileType, OnUpdateFileBackListener mOnListener, String key, String updateUrl1, String updateUrl2, int flag) {
        RetrofitCallback<BasePostResponse<List<UpImgResultBean>>> callback = new RetrofitCallback<BasePostResponse<List<UpImgResultBean>>>() {
            @Override
            public void onSuccess(Call<BasePostResponse<List<UpImgResultBean>>> call, Response<BasePostResponse<List<UpImgResultBean>>> response) {
                LogUtils.d(response.body().getObj());
                mOnListener.onUpdateFileSuccess(response.body(), fileType, flag);
            }

            @Override
            public void onFailure(Call<BasePostResponse<List<UpImgResultBean>>> call, Throwable t) {
                mOnListener.onFailure(t.getMessage());
                LogUtils.d(t.getMessage());
            }
        };
        MediaType parse = MediaType.parse("image/*");
        if (fileType == Constant.IMG_TYPE) {
            parse = MediaType.parse("image/*");
        } else if (fileType == Constant.AUDIO_TYPE) {
            parse = MediaType.parse("audio/*");
        } else if (fileType == Constant.VIDEO_TYPE) {
            parse = MediaType.parse("video/mp4");
        }
        List<MultipartBody.Part> part = RetorfitUtils.files2Parts(key, filePaths, parse, callback);
        Call<BasePostResponse<List<UpImgResultBean>>> call = GithubService.createRetrofitService(PatrolApiService.class)
                .uploadFiles(PreferencesUtil.getString(Constant.TOKEN), updateUrl1, updateUrl2, part);
        call.enqueue(callback);
    }

    /**
     * 文件上传（批量）
     * @param files files 文件
     * @param fileType fileType 文件类型
     * @param mOnListener mOnListener 回调监听
     */
    public static void updateFiles(File[] files, int fileType, OnUpdateFileBackListener mOnListener, int flag) {
        RetrofitCallback<BasePostResponse<List<UpImgResultBean>>> callback = new RetrofitCallback<BasePostResponse<List<UpImgResultBean>>>() {
            @Override
            public void onSuccess(Call<BasePostResponse<List<UpImgResultBean>>> call, Response<BasePostResponse<List<UpImgResultBean>>> response) {
                LogUtils.d(response.body().getObj());
                mOnListener.onUpdateFileSuccess(response.body(), fileType, flag);
            }

            @Override
            public void onLoading(long total, long progress) {
                super.onLoading(total, progress);
                // LogUtils.d(progress * 100 / total + "---->" + total + "---" + progress);
            }

            @Override
            public void onFailure(Call<BasePostResponse<List<UpImgResultBean>>> call, Throwable t) {
                mOnListener.onFailure(t.getMessage());
                LogUtils.d(t.getMessage());
            }
        };
        MediaType parse = MediaType.parse("image/*");
        String updateUrl2 = "image";
        if (fileType == Constant.IMG_TYPE) {
            updateUrl2 = "image";
            parse = MediaType.parse("image/*");
        } else if (fileType == Constant.AUDIO_TYPE) {
            updateUrl2 = "voice";
            parse = MediaType.parse("audio/*");
        } else if (fileType == Constant.VIDEO_TYPE) {
            updateUrl2 = "video";
            parse = MediaType.parse("video/mp4");
        }
        List<MultipartBody.Part> part = RetorfitUtils.files2Parts("files", files, parse, callback);
        Call<BasePostResponse<List<UpImgResultBean>>> call = GithubService.createRetrofitService(PatrolApiService.class)
                .uploadFiles(PreferencesUtil.getString(Constant.TOKEN), "PATROL", updateUrl2, part);
        call.enqueue(callback);
    }

    /**
     * 文件上传（批量）
     * @param files files 文件
     * @param fileType fileType 文件类型
     * @param mOnListener mOnListener 回调监听
     * @param key key 文件服务器key
     * @param updateUrl1 updateUrl 缺省值
     * @param updateUrl2 updateUrl 缺省值
     */
    public static void updateFiles(File[] files, int fileType, OnUpdateFileBackListener mOnListener, String key, String updateUrl1, String updateUrl2, int flag) {
        RetrofitCallback<BasePostResponse<List<UpImgResultBean>>> callback = new RetrofitCallback<BasePostResponse<List<UpImgResultBean>>>() {
            @Override
            public void onSuccess(Call<BasePostResponse<List<UpImgResultBean>>> call, Response<BasePostResponse<List<UpImgResultBean>>> response) {
                LogUtils.d(response.body().getObj());
                mOnListener.onUpdateFileSuccess(response.body(), fileType, flag);
            }

            @Override
            public void onFailure(Call<BasePostResponse<List<UpImgResultBean>>> call, Throwable t) {
                mOnListener.onFailure(t.getMessage());
                LogUtils.d(t.getMessage());
            }
        };
        MediaType parse = MediaType.parse("image/*");
        if (fileType == Constant.IMG_TYPE) {
            parse = MediaType.parse("image/*");
        } else if (fileType == Constant.AUDIO_TYPE) {
            parse = MediaType.parse("audio/*");
        } else if (fileType == Constant.VIDEO_TYPE) {
            parse = MediaType.parse("video/mp4");
        }
        List<MultipartBody.Part> part = RetorfitUtils.files2Parts(key, files, parse, callback);
        Call<BasePostResponse<List<UpImgResultBean>>> call = GithubService.createRetrofitService(PatrolApiService.class)
                .uploadFiles(PreferencesUtil.getString(Constant.TOKEN), updateUrl1, updateUrl2, part);
        call.enqueue(callback);
    }
}
