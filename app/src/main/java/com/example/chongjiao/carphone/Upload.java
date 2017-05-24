package com.example.chongjiao.carphone;

import android.content.pm.PackageInstaller;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by chongjiao on 17-5-5.
 */

public class Upload extends Thread {
    private String path ;
    public Upload(String path){
        this.path = path;
    }
    private static URL url = null;
    private static HttpURLConnection urlConnection = null;
    private static OutputStream os = null;
    private JSONObject json;
    public JSONObject response;
    public boolean flag = true;
    private int freshListCount = 0;
    public void run(){
        while(flag) {
            Log.v("sendJson",json.toString());
            response = DoPost(json);
            recall();
            try{
                Thread.sleep(2000);
            }catch (Exception e){
                e.printStackTrace();
            }
            if(freshListCount % 15 == 0)MainCar.dataFlag = true;
            freshListCount ++;
        }
    }

    /**
     * 对于服务器传回来的数据做出相应的处理
     */
    private void recall(){
        try{
            if(response.has("data")){
                MainCar.car_data = response.get("data").toString();
            }
            if(response.has("carList")){
                MainCar.data = new ArrayList<HashMap<String, String>>();
                JSONObject jsonObject = new JSONObject(response.getString("carList"));
                Log.v("dataList",response.getString("carList"));
                for(int i = 1; i <= jsonObject.length(); i++){
                    try {
                        JSONObject jsonTemp = new JSONObject(jsonObject.getString(String.valueOf(i)));
                        HashMap<String ,String> item = new HashMap<String,String>();
                        item.put("id", String.valueOf(i));
                        item.put("car_name",jsonTemp.getString("car_name"));
                        item.put("car_type",jsonTemp.getString("car_type"));
                        MainCar.data.add(item);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                MainCar.dataFlag = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(MainCar.dataFlag){
            try {
                MainCar.js.put("list", "1");
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            try{
                if(MainCar.js.has("list")) MainCar.js.remove("list");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        setJson(MainCar.js);
    }
    /**
     * json传递数据
     * type 为主要参数
     * type = 0 表示登录请求
     * type = 1 表示注册请求
     * type = 2 表示请求信息
     * type = 3 表示心跳包请求
     * type = 4 表示请求当前所有设备信息
     */
    public void setJson(JSONObject a){
        json = a;
    }
    public JSONObject DoPost(JSONObject json){
        try{
            url = new URL(path);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            os = urlConnection.getOutputStream();
            os.write(json.toString().getBytes());
            os.flush();
            if(urlConnection.getResponseCode() == 200){
                String str = null;
                InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader buffer = new BufferedReader(in);
                String line = null;
                while((line = buffer.readLine()) != null){
                    str += line;
                }
                Log.v("getData",str);
                String crappyPrefix = "null";
                if(str.startsWith(crappyPrefix)){
                    str = str.substring(crappyPrefix.length(),str.length());
                }
                JSONObject js = new JSONObject(str);
                return js;
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
