package com.gwkj.qixiubaodian.obd.contants;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;

import com.gwkj.qixiubaodian.obd.Utils.BaseCacheUtil;
import com.gwkj.qixiubaodian.obd.activity.MyApplication;
import com.gwkj.qixiubaodian.obd.helper.OBDVarDataHelper;
import com.gwkj.qixiubaodian.obd.item.OBDSer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.gwkj.qixiubaodian.obd.Utils.BaseCacheUtil.getString;


/**
 * 对于网络操作的业务逻辑 采用单例模式
 */
public class NetInterfaceEngine {

    private static NetInterfaceEngine engine;

    private String device = "android";

    private Context context;
    public static Handler handler = new Handler(Looper.getMainLooper());
    private  OkHttpClient client;
    public static NetInterfaceEngine getEngine() {
        if (engine == null) {
            engine = new NetInterfaceEngine();
        }
        return engine;
    }

    private NetInterfaceEngine(){
        if(client==null) {
            client = new OkHttpClient().newBuilder().writeTimeout(30000, TimeUnit.SECONDS)
                    .connectTimeout(30000, TimeUnit.SECONDS)
                    .readTimeout(30000, TimeUnit.SECONDS).build();
        }

    }
    /**
     * post的方式请求
     *
     * @param
     * @param
     * @return
     */
    public static String sendByPost(String strUrl, String requestString) {
        String result = "";
        try {
            URL url = new URL(strUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

            ////设置连接属性
            httpConn.setDoOutput(true);//使用 URL 连接进行输出
            httpConn.setDoInput(true);//使用 URL 连接进行输入
            httpConn.setUseCaches(false);//忽略缓存
            httpConn.setRequestMethod("POST");//设置URL请求方法


            //设置请求属性
            //获得数据字节数据，请求数据流的编码，必须和下面服务器端处理请求流的编码一致
            byte[] requestStringBytes = requestString.getBytes();
            httpConn.setRequestProperty("Content-length", "" + requestStringBytes.length);
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            httpConn.setRequestProperty("Charset", "UTF-8");

            //建立输出流，并写入数据
            OutputStream outputStream = httpConn.getOutputStream();
            outputStream.write(requestStringBytes);
            outputStream.close();
            //获得响应状态
            int responseCode = httpConn.getResponseCode();
            if (200 == responseCode) {//连接成功
                result = dealResponseResult(httpConn.getInputStream());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    public String jsonData(JSONObject dataKey) {
        String data = "";
        try {

            JSONObject TokenKey = new JSONObject();
            TokenKey.put("loginid", MyApplication.getInstances().getLoginid());
            TokenKey.put("openid", MyApplication.getInstances().getOpenid());
            TokenKey.put("uid", MyApplication.getInstances().getUid());

            String verson= getString(MyApplication.getContext(),"app_version");
            JSONObject Authorization = new JSONObject();
            Authorization.put("client", "android");
            Authorization.put("version", verson);
            Authorization.put("data", dataKey);
            Authorization.put("token", TokenKey);
            data = String.valueOf(Authorization);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
    /*
      * Function  :   处理服务器的响应结果（将输入流转化成字符串）
      * Param     :   inputStream服务器的响应输入流
      */
    public static String dealResponseResult(InputStream inputStream) {
        String resultData = null;      //存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }
    public void commitPostPid(Context context, String url,String pid, final NetHelper helper) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        try {
            JSONArray jsonArray1 = new JSONArray();

            String openid=BaseCacheUtil.getString(context,"openid");
            builder.addFormDataPart("openid", openid);
            builder.addFormDataPart("pid", pid);
            builder.addFormDataPart("data", String.valueOf(jsonArray1));

            MultipartBody requestBody = builder.build();

            Request request = new Request.Builder().url(url)//地址
                    .post(requestBody)//添加请求体
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    System.out.println("上传失败:e.getLocalizedMessage() = " + e.getLocalizedMessage());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            helper.onFail(null, e.getLocalizedMessage());
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    ResponseBody body = response.body();//获取响应体
                    final String result = body.string();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            helper.onSuccess(result);
                        }
                    });
                }
            });
        }catch (Exception e){

        }
    }
    public void commitPostList(Context context, String url, List<OBDSer> list, final NetHelper helper) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        try {
            JSONArray jsonArray1 = new JSONArray();
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    JSONObject img = new JSONObject();
                    img.put("pid", list.get(i).getCmd());
                    img.put("data", list.get(i).getValue());
                    jsonArray1.put(img);
                }

            }
            String openid=BaseCacheUtil.getString(context,"openid");
            builder.addFormDataPart("openid", openid);
            builder.addFormDataPart("data", String.valueOf(jsonArray1));

            MultipartBody requestBody = builder.build();

            Request request = new Request.Builder().url(url)//地址
                    .post(requestBody)//添加请求体
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    System.out.println("上传失败:e.getLocalizedMessage() = " + e.getLocalizedMessage());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            helper.onFail(null, e.getLocalizedMessage());
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    ResponseBody body = response.body();//获取响应体
                    final String result = body.string();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            helper.onSuccess(result);
                        }
                    });
                }
            });
        }catch (Exception e){

        }
    }
    public void commitPostData(Context context,String url, final String data, String cmd,String type,String sendParam,final NetHelper helper) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        try {
            if(sendParam!=null&&!sendParam.isEmpty()){
                SQLiteDatabase db = OBDVarDataHelper.getInstance(context).getReadableDatabase();
                String[] namekey=sendParam.split(",");
                Cursor cursor = db.rawQuery("select * from varname ", null);
                while (cursor.moveToNext()) {
                    String name=cursor.getString(1);
                    String value=cursor.getString(2);
                    for (int i=0;i<namekey.length;i++) {
                        if(!namekey[i].isEmpty()&&namekey[i].equals(name)) {
                            builder.addFormDataPart(name, value);
                        }
                    }
                }
                cursor.close();
                db.close();
            }
            String openid=BaseCacheUtil.getString(context,"openid");
            builder.addFormDataPart("openid", openid);
            builder.addFormDataPart("data", data);
            builder.addFormDataPart("cmd", cmd);
            builder.addFormDataPart("type", type);

            MultipartBody requestBody = builder.build();

            Request request = new Request.Builder().url(url)//地址
                    .post(requestBody)//添加请求体
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    System.out.println("上传失败:e.getLocalizedMessage() = " + e.getLocalizedMessage());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            helper.onFail(null, e.getLocalizedMessage());
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    ResponseBody body = response.body();//获取响应体
                    final String result = body.string();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            helper.onSuccess(result);
                        }
                    });
                }
            });
        }catch (Exception e){

        }
    }

    public void commitPostPID(Context context,String url, final String pinpaiid, String data,String sendParam,final NetHelper helper) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        try {
            if(sendParam!=null&&!sendParam.isEmpty()){
                SQLiteDatabase db = OBDVarDataHelper.getInstance(context).getReadableDatabase();
                String[] namekey=sendParam.split(",");
                Cursor cursor = db.rawQuery("select * from varname ", null);
                while (cursor.moveToNext()) {
                    String name=cursor.getString(1);
                    String value=cursor.getString(2);
                    for (int i=0;i<namekey.length;i++) {
                        if(!namekey[i].isEmpty()&&namekey[i].equals(name)) {
                            builder.addFormDataPart(name, value);
                        }
                    }
                }
                cursor.close();
                db.close();
            }
            if(pinpaiid!=null&&!pinpaiid.isEmpty()){
                builder.addFormDataPart("pinpaiid", pinpaiid);
            }
            if(data!=null&&!data.isEmpty()) {
                builder.addFormDataPart("data", data);
            }

            MultipartBody requestBody = builder.build();

            Request request = new Request.Builder().url(url)//地址
                    .post(requestBody)//添加请求体
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    System.out.println("上传失败:e.getLocalizedMessage() = " + e.getLocalizedMessage());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            helper.onFail(null, e.getLocalizedMessage());
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    ResponseBody body = response.body();//获取响应体
                    final String result = body.string();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            helper.onSuccess(result);
                        }
                    });
                }
            });
        }catch (Exception e){

        }
    }
    public void OkHttpGet(String url, final NetHelper helper) {

        Request request = new Request.Builder().url(url).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("请求失败:e.getLocalizedMessage() = " + e.getLocalizedMessage());
                helper.onFail(null, e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                System.out.println("请求成功");
                final String message = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        helper.onSuccess(message);
                    }
                });

            }
        });
    }
    public void commitOKHttpJson(final String url, final String jsonstr, final NetHelper helper) {

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonstr);

        Request request = new Request.Builder()
                .url(url).post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                System.out.println("请求失败:e.getLocalizedMessage() = " + e.getLocalizedMessage());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        helper.onFail(null, e.getLocalizedMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                System.out.println("请求成功");
                final String message = response.body().string();
                if (message != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(message.contains("token-invalid")){

                                helper.onSuccess("");
                            }else {
                                if(helper!=null){
                                    helper.onSuccess(message);
                                }
                            }
                        }
                    });
                }

            }
        });

    }

}
