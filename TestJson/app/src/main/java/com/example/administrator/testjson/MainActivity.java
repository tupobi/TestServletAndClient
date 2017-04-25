package com.example.administrator.testjson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private List<Manager> managers;
    private TextView tv;

    private void PostDataToServletWithHttpURLConnection() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String strUrl = "http://192.168.3.8:8080/TestJson/servlet/GetDataFromClient";
                URL url = null;
                try {
                    Log.d("------------->", "PostDataToServlet: 进入传值方法！！！");
                    url = new URL(strUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    //...........设置相关参数，暂时不写出来。。
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                    urlConnection.setUseCaches(false);
                    urlConnection.setInstanceFollowRedirects(true);
                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                    urlConnection.setRequestProperty("Charset", "utf-8");
//                    urlConnection.setReadTimeout(5000);
//                    urlConnection.setConnectTimeout(5000);

                    urlConnection.connect();


                    DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
                    String data = "param1=" + URLEncoder.encode("哈哈哈哈！", "utf-8")+"&param2="+URLEncoder.encode("get到！！", "UTF-8");
                    dataOutputStream.writeBytes(data);
//                    String content = "firstname=" + URLEncoder.encode("一个大肥人", "utf-8");
                    dataOutputStream.flush();
                    dataOutputStream.close();
//                    urlConnection.connect();


                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream()));
                    String line;
                    System.out.println("=============================");
                    System.out.println("Contents of post request");
                    System.out.println("=============================");
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                    System.out.println("=============================");
                    System.out.println("Contents of post request ends");
                    System.out.println("=============================");
                    reader.close();
                    urlConnection.disconnect();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void PostDataToServletWithOkHttp(){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        //Post请求
        //    请求条件：platform=2&gifttype=2&compare=60841c5b7c69a1bbb3f06536ed685a48
        //    请求参数：page=1&code=news&pageSize=20&parentid=0&type=1
        RequestBody requestBodyPost = new FormBody.Builder()
                .add("param1", "1")
                .add("param2", "news")
                .add("pageSize", "20")
                .add("parentid", "0")
                .add("type", "1")
                .build();
        Request requestPost = new Request.Builder()
                .url("http://192.168.3.8:8080/TestJson/servlet/GetDataFromClient")
                .post(requestBodyPost)
                .build();
        client.newCall(requestPost).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(string);
                    }
                });
            }
        });
    }

//    public void postFileToServletWithOkHttp(){
//        //Post请求上传文件
//        File file = new File(Environment.getExternalStorageDirectory(), "dd.mp4");
//        if (!file.exists()) {
//            Toast.makeText(MainActivity.this, "文件不存在", Toast.LENGTH_SHORT).show();
//        } else {
//            RequestBody fileBody = RequestBody.create(MediaType.parse(TYPE), file);
//            RequestBody requestBody = new MultipartBody.Builder().addFormDataPart("filename", file.getName(), fileBody).build();
//
//            Request requestPostFile = new Request.Builder()
//                    .url("http://10.11.64.50/upload/UploadServlet")
//                    .post(requestBody)
//                    .build();
//            client.newCall(requestPostFile).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//
//                }
//
//                @Override
//                public void onResponse(Call call, final Response response) throws IOException {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            tvShow.setText(response.toString());
//                        }
//                    });
//                }
//            });
//        }
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        sendRequestWithOkHttp();
//        PostDataToServletWithHttpURLConnection();
        PostDataToServletWithOkHttp();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //引入OkHttpClient的库依赖。
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://192.168.3.8:8080/TestJson/servlet/GetManagerInfor")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithGSON(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }


//                try {
//                    String result = HttpUtil.httpGet("http://169.254.236.50:8080/TestJson/servlet/GetManagerInfor");
//                    Log.d("json:::", result);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }


            }
        }).start();
    }

    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        managers = gson.fromJson(jsonData, new TypeToken<List<Manager>>() {
        }.getType());
        Log.d("------------->", "parseJSONWithGSON: ");
        for (Manager manager : managers) {
            Log.d("--------------->", "uname:" + manager.getUname());
            Log.d("--------------->", "upwd:" + manager.getUpwd());
        }
    }

//    private void parseJSONWithGSON(String jsonData){
//        try {
//            JSONArray jsonArray = new JSONArray(jsonData);
//            for (int i=0; i<jsonArray.length(); i++){
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                Log.d("--------------->", "uname:" + jsonObject.getString("uname"));
//            Log.d("--------------->", "upwd:" + jsonObject.getString("upwd"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
