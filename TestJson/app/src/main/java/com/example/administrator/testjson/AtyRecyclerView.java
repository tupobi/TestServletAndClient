package com.example.administrator.testjson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/26.
 */

public class AtyRecyclerView extends Activity {
    private ArrayList<Manager> mManagers;
    private EditText etAddUserName, etAddUserPwd;
    private Button btnAdd;

    public static void actionStart(Context context){
        context.startActivity(new Intent(context, AtyRecyclerView.class));
    }

    private void initViewAndData(){
        mManagers = new ArrayList<>();
        initManager();
//        for (int i=0; i<mManagers.size(); i++){
//            Log.d("uname---->",  mManagers.get(i).getUname());
//            Log.d("upwd---->",  mManagers.get(i).getUpwd());
//        }
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter((ArrayList<Manager>) mManagers);
        recyclerView.setAdapter(recyclerViewAdapter);

        etAddUserName = (EditText) findViewById(R.id.et_addUserName);
        etAddUserPwd = (EditText) findViewById(R.id.et_addUserPwd);
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostDataToServletWithOkHttp();
                mManagers.clear();
//                recyclerView.setAdapter(recyclerViewAdapter);
                initManager();
                recyclerViewAdapter.dataChanged();

            }
        });
    }
    public void PostDataToServletWithOkHttp(){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        RequestBody requestBodyPost = new FormBody.Builder()
                .add("uname", etAddUserName.getText().toString())
                .add("upwd", etAddUserPwd.getText().toString())
                .build();
        Request requestPost = new Request.Builder()
                .url("http://192.168.43.42:8080/TestServletWithJson/servlet/AddManagerToDB")
                .post(requestBodyPost)
                .build();
        client.newCall(requestPost).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AtyRecyclerView.this, "服务器异常，应稍后再试", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(AtyRecyclerView.this, "插入成功！", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AtyRecyclerView.this, "插入失败，可能重复", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_recyclerview);
        initViewAndData();
    }

    private void initManager() {
        sendRequestWithOkHttp();
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
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
            }
        }).start();
    }

    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        List<Manager> temp = gson.fromJson(jsonData, new TypeToken<ArrayList<Manager>>() {
        }.getType());
        for (Manager manager: temp) {
//            Log.d("uname---->", manager.getUname());
//            Log.d("upwd", manager.getUpwd());
            mManagers.add(new Manager(manager.getUname(), manager.getUpwd()));
        }
    }


}
