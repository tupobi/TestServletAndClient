package com.example.administrator.testjson;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends Activity {
    private List<Manager> managers;
    private EditText etUserName, etUserPassword;
    private Button btnLogin;
//    public static void actionStart(Context context){
//        context.startActivity(new Intent(context, MainActivity.class));
//    }

//    private void PostDataToServletWithHttpURLConnection() {
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String strUrl = "http://192.168.43.42:8080/TestJson/servlet/GetDataFromClient";
//                URL url = null;
//                try {
//                    Log.d("------------->", "PostDataToServlet: 进入传值方法！！！");
//                    url = new URL(strUrl);
//                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                    //...........设置相关参数，暂时不写出来。。
//                    urlConnection.setRequestMethod("POST");
//                    urlConnection.setDoInput(true);
//                    urlConnection.setDoOutput(true);
//                    urlConnection.setUseCaches(false);
//                    urlConnection.setInstanceFollowRedirects(true);
//                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
////                    urlConnection.setRequestProperty("Charset", "utf-8");
////                    urlConnection.setReadTimeout(5000);
////                    urlConnection.setConnectTimeout(5000);
//
//                    urlConnection.connect();
//
//
//                    DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
//                    String data = "param1=" + URLEncoder.encode("哈哈哈哈！", "utf-8")+"&param2="+URLEncoder.encode("get到！！", "UTF-8");
//                    dataOutputStream.writeBytes(data);
////                    String content = "firstname=" + URLEncoder.encode("一个大肥人", "utf-8");
//                    dataOutputStream.flush();
//                    dataOutputStream.close();
////                    urlConnection.connect();
//
//
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(
//                            urlConnection.getInputStream()));
//                    String line;
//                    System.out.println("=============================");
//                    System.out.println("Contents of post request");
//                    System.out.println("=============================");
//                    while ((line = reader.readLine()) != null) {
//                        System.out.println(line);
//                    }
//                    System.out.println("=============================");
//                    System.out.println("Contents of post request ends");
//                    System.out.println("=============================");
//                    reader.close();
//                    urlConnection.disconnect();
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }

    public void PostDataToServletWithOkHttp(){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        //Post请求
        //    请求条件：platform=2&gifttype=2&compare=60841c5b7c69a1bbb3f06536ed685a48
        //    请求参数：page=1&code=news&pageSize=20&parentid=0&type=1
        RequestBody requestBodyPost = new FormBody.Builder()
                .add("uname", etUserName.getText().toString())
                .add("upwd", etUserPassword.getText().toString())
//                .add("pageSize", "20")
//                .add("parentid", "0")
//                .add("type", "1")
                .build();
        Request requestPost = new Request.Builder()
                .url("http://192.168.43.42:8080/TestServletWithJson/servlet/GetDataFromClient")
                .post(requestBodyPost)
                .build();
        client.newCall(requestPost).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "服务器异常，请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if("true".equals(result)){
                            AtyRecyclerView.actionStart(MainActivity.this);
                        }else{
                            Toast.makeText(MainActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                        }
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
        sendRequestWithOkHttp();
        initView();

//        PostDataToServletWithHttpURLConnection();
//        PostDataToServletWithOkHttp();
    }

    private void initView() {
        etUserName = (EditText) findViewById(R.id.et_userName);
        etUserPassword = (EditText) findViewById(R.id.et_userPassWord);
        btnLogin = (Button) findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostDataToServletWithOkHttp();
            }
        });
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    Log.d("------>", "run: ");
                    //引入OkHttpClient的库依赖。
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://192.168.43.42:8080/TestServletWithJson/servlet/GetManagerInfor")
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
//        Log.d("------>", "parse ");
        Gson gson = new Gson();
        managers = gson.fromJson(jsonData, new TypeToken<List<Manager>>() {
        }.getType());
//        Log.d("------------->", "parseJSONWithGSON: ");
//        for (Manager manager : managers) {
//            Log.d("--------------->", "uname:" + manager.getUname());
//            Log.d("--------------->", "upwd:" + manager.getUpwd());
//        }
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
