package com.example.chongjiao.carphone;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    private Button BtnLogin = null;
    private Button BtnRegisterr = null;
    private EditText EdTxAccount = null;
    private EditText EdTxPassword = null;
    private String SessionId;
    private Upload upload;
    private static String path = "http://10.42.0.1/CarPhp/registerOrLogin.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        BtnLogin = (Button)findViewById(R.id.sign_in_button);
        BtnRegisterr = (Button)findViewById(R.id.register_button);
        EdTxAccount = (EditText)findViewById(R.id.Account);
        EdTxPassword = (EditText) findViewById(R.id.password);
        upload = new Upload(path);
    }
    @Override
    protected void onResume(){
        super.onResume();
        setOnClick();
    }
    private void setOnClick(){
        BtnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String Account = String.valueOf(EdTxAccount.getText());
                String Password = String.valueOf(EdTxPassword.getText());
                if(Account == null || Password == null){
                    new AlertDialog.Builder(LoginActivity.this).setTitle("标题")
                            .setMessage("请输入正确账号和密码")
                            .setPositiveButton("确定",null).show();
                    return;
                }else{
                    JSONObject jsonObject = new JSONObject();
                    try{
                        jsonObject.put("type",1);
                        jsonObject.put("Account",Account);
                        jsonObject.put("Password",Password);
                        upload.setJson(jsonObject);
                        upload.start();
                        try{
                            Thread.sleep(2000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        upload.flag = false;
                        JSONObject response = upload.response;
                        if(response == null){
                            new AlertDialog.Builder(LoginActivity.this).setTitle("标题")
                                    .setMessage("登录失败")
                                    .setPositiveButton("确定",null).show();
                            return;
                        }else{
                            if(response.getInt("state")==0) {
                                new AlertDialog.Builder(LoginActivity.this).setTitle("标题")
                                        .setMessage("登录失败")
                                        .setPositiveButton("确定", null).show();
                                return;
                            }
                            if(response.getInt("state") == 1){
                                new AlertDialog.Builder(LoginActivity.this).setTitle("标题")
                                        .setMessage("登录成功")
                                        .setPositiveButton("确定",null).show();
                                SessionId = response.getString("SessionID");
                                Log.v("SesssionId",SessionId);
                                Intent intent = new Intent(LoginActivity.this,MainCar.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("SessionID",SessionId);
                                bundle.putString("userName",response.getString("name"));
                                bundle.putString("userGender",response.getString("gender"));
                                bundle.putString("userAccount",response.getString("account"));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        BtnRegisterr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,Register.class);
                startActivity(intent);
            }
        });
    }


}

