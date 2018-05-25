package com.vving.app.okhttpdemo.util;


import android.util.Log;

import com.vving.app.okhttpdemo.progress.ProgressHelper;
import com.vving.app.okhttpdemo.progress.ProgressListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestHandler {

    private static final String TAG = "RequestHandler";
    private static volatile RequestHandler requestHandler = null;
    private Request request;
    private Response response;
    private Call call;
    private OkHttpClient okHttpClient;

    //线程阻塞方式请求的get，post状态码
    private final static int GET_INSTANCE = 0;
    private final static int POST_INSTANCE = 1;

    //默认超时时间
    private final static int VALUE_DEFAULT_TIME_OUT = 20 * 1000;

    /**
     * 参数类型
     * "text", 文本
     * "image", 图片
     * "audio",音频
     * "video"，视频
     * "object",其他
     */
    private static final MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/x-markdown; charset=utf-8");
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final MediaType MEDIA_TYPE_AUDIO = MediaType.parse("audio/mp3");
    private static final MediaType MEDIA_TYPE_VIDEO = MediaType.parse("video/mp4");
    private static final MediaType MEDIA_TYPE_OBJECT = MediaType.parse("application/octet-stream");
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    //给每个网络请求的标记
    public static final String TAG_DATA = "tag_data";
    public static final String TAG_IMAGE = "tag_image";
    public static final String TAG_FILE = "tag_file";


    private RequestHandler() {
        //okHttpClient = new OkHttpClient();
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(VALUE_DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(VALUE_DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(VALUE_DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS)
                .build();
    }

    public static synchronized RequestHandler getInstance() {
        if (requestHandler == null) {
            synchronized (RequestHandler.class) {
                if (requestHandler == null) {
                    requestHandler = new RequestHandler();
                }
            }
        }
        return requestHandler;
    }

    /**
     * 队列请求（线程阻塞）的结果处理方法
     *
     * @param call
     */
    public void handleResponse(Call call) {
        try {
            //判断是否是成功的
            response = call.execute();
            if (response.isSuccessful()) {
                //成功
                //String jsonStr=response.body().string();//字符串，无编码
                String jsonStr = new String(response.body().bytes(), "UTF-8");//二进制字节数组
                //InputStream inputStream=response.body().byteStream();//输入流
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    //解析数据处理
                    //发送数据处理
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                //失败
                response.code();//失败码
                response.message();//失败信息
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步请求结果回调处理：
     *
     * @param call
     */
    public void handleResponseEnqueue(Call call) {
        //需要注意的是异步回调还是在子线程中，所以如果要更新ui，则记得handler
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //失败
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //响应,回调不在UI线程
                if (response.cacheResponse() != null) {
                    String s = response.cacheResponse().toString();
                    Log.i(TAG, "onResponse: cache=" + s);
                } else {
                    response.body().string();
                    String s = response.networkResponse().toString();
                    Log.i(TAG, "onResponse: network=" + s);
                }
                try {
                    if (response.isSuccessful()) {
                        //String jsonStr=response.body().string();//字符串，无编码
                        String jsonStr = null;//二进制字节数组

                        jsonStr = new String(response.body().bytes(), "UTF-8");

                        //InputStream inputStream=response.body().byteStream();//输入流
                        try {
                            JSONObject jsonObject = new JSONObject(jsonStr);
                            //解析数据处理
                            //发送数据处理
                            /*
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                            */
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                call.cancel();
            }
        });
    }

    /**
     * 线程阻塞的单例发送get请求,线程阻塞的方式需要在非ui线程中.
     * 异步请求结果回调处理.
     *
     * @param url
     * @param useNewThread
     */
    public void requestGet(String url, boolean useNewThread) {
        //创建一个请求
        request = new Request.Builder()
                .tag(TAG_DATA)
                .url(url)
                .method("GET", null) //Get方式可省略
                .build();
        call = okHttpClient.newCall(request);
        if (useNewThread) {
            handleResponseEnqueue(call);
        } else {
            handleResponse(call);
        }
    }

    /**
     * 线程阻塞的post请求,线程阻塞的方式需要在非ui线程中,可以附带文件
     * 异步的post请求，传文件
     *
     * @param url
     * @param params
     * @param files
     * @param useNewThread
     */
    public void requestPost(String url, Map<String, String> params, List<File> files, boolean useNewThread) {

        RequestBody body;
        /*
        //上传文件
        File file = new File("/sdcard/demo.txt");
        body = RequestBody.create(MEDIA_TYPE_TEXT, file);

        //提交json数据
        body = RequestBody.create(MEDIA_TYPE_JSON, "json_content");
        //提交键值对
        body = new FormBody.Builder()
                .add("key1", "value1")
                .add("key2", "value2")
                .build();
        */

        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);
        //遍历map中所有参数到builder
        if (params != null) {
            for (String key : params.keySet()) {
                multipartBodyBuilder.addFormDataPart(key, params.get(key));
            }
        }
        //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
        if (files != null && files.size() != 0) {
            for (File f : files) {
                multipartBodyBuilder.addFormDataPart("upload", f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
            }
        }
        body = multipartBodyBuilder.build();

        String tag = files == null ? TAG_DATA : TAG_FILE;
        request = new Request.Builder()
                .url(url)
                .tag(tag)
                .post(body)
                .build();
        call = okHttpClient.newCall(request);
        if (useNewThread) {
            handleResponseEnqueue(call);
        } else {
            handleResponse(call);
        }
    }

    /**
     * 上传多张图片及参数
     *
     * @param reqUrl  URL地址
     * @param params  参数
     * @param pic_key 上传图片的关键字
     * @param files   图片路径
     */
    public Observable<String> sendMultipart(final String reqUrl, final Map<String, String> params,
                                            final String pic_key, final List<File> files) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> observableEmitter) throws Exception {
                MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
                multipartBodyBuilder.setType(MultipartBody.FORM);
                //遍历map中所有参数到builder
                if (params != null) {
                    for (String key : params.keySet()) {
                        multipartBodyBuilder.addFormDataPart(key, params.get(key));
                    }
                }
                //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
                if (files != null) {
                    for (File file : files) {
                        multipartBodyBuilder.addFormDataPart(pic_key, file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
                    }
                }
                //构建请求体
                RequestBody requestBody = multipartBodyBuilder.build();

                Request.Builder requestBuilder = new Request.Builder();
                requestBuilder.url(reqUrl);// 添加URL地址
                requestBuilder.post(requestBody);
                Request request = requestBuilder.build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        observableEmitter.onError(e);
                        observableEmitter.onComplete();
                        call.cancel();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String str = response.body().string();
                        observableEmitter.onNext(str);
                        observableEmitter.onComplete();
                        call.cancel();
                    }
                });
            }
        });
    }


    /**
     * 设置超时
     *
     * @param value
     */
    /*public void setTimeOut(int value) {
        if (okHttpClient == null) return;
        okHttpClient.setConnectTimeout(value, TimeUnit.MILLISECONDS);//连接超时
        okHttpClient.setReadTimeout(value, TimeUnit.MILLISECONDS);//读取超时
        okHttpClient.setWriteTimeout(value, TimeUnit.MILLISECONDS);//写入超时
    }*/

    /**
     * 取消一个当前正在处理的Call
     * 使用Call.cancel()可以立即停止掉一个正在执行的call。
     * 注意：如果一个线程正在写请求或者读响应，将会引发IOException。
     */
    public void cancelByCall() {
        if (call != null) {
            try {
                call.cancel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 清除所有网络请求
     * 你可以通过tags来同时取消多个请求。
     * 当你构建一请求时，使用RequestBuilder.tag(tag)来分配一个标签
     * 之后你就可以用OkHttpClient.cancel(tag)来取消所有带有这个tag的call
     *
     * @param isCancelAllRequests
     */
    public void cancelAllRequests(boolean isCancelAllRequests) {
        if (okHttpClient != null) {
            try {
                if (isCancelAllRequests) {
                    okHttpClient.cancel(TAG_DATA);
                    okHttpClient.cancel(TAG_IMAGE);
                    okHttpClient.cancel(TAG_FILE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 非ui线程回调文件下载进度
     * 这个是非ui线程回调，不可直接操作UI
     */
    final ProgressListener progressResponseListener = new ProgressListener() {
        @Override
        public void onProgress(long bytesRead, long contentLength, boolean done) {
            //长度未知的情况下回返回-1
            if (contentLength != -1 && (100 * bytesRead) / contentLength >= 0) {
                String donePercent = (100 * bytesRead) / contentLength + "%";//上传进度
            }
        }
    };

    /**
     * 下载文件
     *
     * @param url
     */
    public void downloadFile(String url) {
        request = new Request.Builder()
                .url(url)
                .build();
        //包装Response使其支持进度回调
        ProgressHelper.addProgressResponseListener(okHttpClient, progressResponseListener)
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        String message;
                        if (!NetWorkUtil.isNetworkConnected(UIAppliaction.getInstance())) {
                            //判断是否是因为没有网络的原因
                            message = "网络不给给力";
                        } else {
                            message = e.getMessage();
                        }
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        String message = "";
                        String url = response.request().url().getPath();
                        //获得url地址的文件后缀名
                        String filename = url.substring(message.indexOf("."), message.length());
                        InputStream inputStream = response.body().byteStream();//输入流
                        //通过流保存文件的工具类
                        if (FileUtil.insertSDCardFromInput("文件绝对路径", filename, inputStream))
                            message = "下载完成";
                        else
                            message = "文件保存失败";
                    }
                });
    }
}

