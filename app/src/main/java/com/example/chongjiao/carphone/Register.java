package com.example.chongjiao.carphone;

import android.app.Application;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

/**
 * Created by chongjiao on 17-5-5.
 */

public class Register extends AppCompatActivity {
    private Button Register_register = null;
    private EditText Register_Account = null;
    private EditText Register_password = null;
    private String path = "http://10.42.0.1/CarPhp/registerOrLogin.php";
    private Upload upload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Register_register = (Button)findViewById(R.id.register_register);
        Register_Account = (EditText)findViewById(R.id.register_Account);
        Register_password = (EditText)findViewById(R.id.register_password);

        upload = new Upload(path);
    }
    @Override
    protected void onResume(){
        super.onResume();
        Register_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String registerAccount = Register_Account.getText().toString();
                String registerPassword = Register_password.getText().toString();
                if(registerAccount == null||registerPassword == null){
                    //弹出警告框
                    new AlertDialog.Builder(Register.this).setTitle("标题").setMessage("请输入正确信息")
                            .setPositiveButton("确定",null).show();
                    return;
                }else {
                    JSONObject json = new JSONObject();
                    try {
                        json.put("type", 0);
                        json.put("Account", registerAccount);
                        json.put("Password", registerPassword);
                        upload.setJson(json);
                        upload.start();
                        try{
                            Thread.sleep(2000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        upload.flag = false;
                        JSONObject response = upload.response;
                        if (response == null) {
                            new AlertDialog.Builder(Register.this).setTitle("标题")
                                    .setMessage("注册失败请重新注册")
                                    .setPositiveButton("确定", null).show();
                            return;
                        } else {
                            if (response.getInt("state") == 0) {
                                new AlertDialog.Builder(Register.this).setTitle("标题")
                                        .setMessage("注册失败请重新注册")
                                        .setPositiveButton("确定", null).show();
                                return;
                            }
                            if (response.getInt("state") == 1) {
                                new AlertDialog.Builder(Register.this).setTitle("标题")
                                        .setMessage("注册成功")
                                        .setPositiveButton("确定", null).show();
                                Intent intent = new Intent(Register.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
