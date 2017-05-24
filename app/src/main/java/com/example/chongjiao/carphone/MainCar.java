package com.example.chongjiao.carphone;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Page.CarFragment;
import Page.CarListFragment;
import Page.CarRegisterFragment;
import Page.MeFragment;

/**
 * Created by chongjiao on 17-5-5.
 */

public class MainCar extends AppCompatActivity implements View.OnClickListener {
    private String path = "http://10.42.0.1/CarPhp/phonePhp.php";
    public static String SessionID;
    //初始化的数据
    public String userName = null;
    public String userGender = null;
    public String userAccount = null;
    public static List<HashMap<String,String >> data;
    public static boolean dataFlag = true;
    public Upload upload = null;

    private CarRegisterFragment mCarRegisterFragment;
    //private CarFragment mCarFragment;
    private MeFragment mMeFragment;
    private CarListFragment mCarListFragment;
    public CarFragment mCarFragment;

    private List<Fragment> mFragmentList;
    public ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;
    private BottomLayout mCarRegisterLayout;
    private BottomLayout mMeLayout;
    private BottomLayout mCarListLayout;
/* 页面数据传输部分　*/
    //数据传输部分,表示某个设备的型号等信息
    public static String car_name = null;
    public static String car_type = null;

    public static String car_data = null;

    //表示注册设备的信息
    public String car_register_name = null;
    public String car_register_type = null;
    public static JSONObject js;

    /**
     * 表示心跳包传输的类型，由每个fragement来确定发送什么信息
     * type = 0;默认值，一般连接心跳包
     * type = 1;请求类型数据
     * type = 2；发送类型数据
     */
    public static int type = 1;
    //心跳包部分
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_main);
        upload = new Upload(path);
        Intent intent  = getIntent();
        Bundle bundle = intent.getExtras();
        SessionID = bundle.getString("SessionID");
        userName = bundle.getString("userName");
        userGender = bundle.getString("userGender");
        userAccount = bundle.getString("userAccount");
        initId();
        initFragment();
        initList();
        setPagerAdapter();
        setViewPagerListener();
        mMeLayout.setSelectState();
    }

    @Override
    protected void onResume(){
        super.onResume();
        init();
        checkUserName();
    }

    private void checkUserName(){
        if(!userName.equals("null")&&!userGender.equals("null")){
            try{
                Thread.sleep(2000);
            }catch (Exception e){
                e.printStackTrace();
            }
            mMeFragment.setMe(userName,userGender,userAccount);
        }else{
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.user_info_register, (ViewGroup)findViewById(R.id.register_user_info));
            final EditText rename = (EditText)view.findViewById(R.id.register_name);
            final EditText regender = (EditText)view.findViewById(R.id.register_gender);
            new AlertDialog.Builder(this).setTitle("请注册个人信息").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = rename.getText().toString();
                    String gender = regender.getText().toString();
                    JSONObject jsonObject = new JSONObject();
                    MainCar.type = 2;
                    try {
                        Log.v("nameText",name);
                        jsonObject.put("type", MainCar.type);
                        jsonObject.put("SessionId",SessionID);
                        jsonObject.put("Name",name);
                        jsonObject.put("Gender",gender);
                        upload.setJson(jsonObject);
                        try{
                            Thread.sleep(2000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        JSONObject js = upload.response;
                        if(js != null){
                            userName = name;
                            userGender = gender;
                            mMeFragment.setMe(name,gender,userAccount);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }).show();
        }
    }
    private void setViewPagerListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setBottomState(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化Fragement类
     */
    private void initFragment() {
        mCarListFragment = new CarListFragment();
        mMeFragment = new MeFragment();
        mCarRegisterFragment = new CarRegisterFragment();
        mCarFragment = new CarFragment();
    }
    private void setPagerAdapter() {
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

    private void initList() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(mMeFragment);
        mFragmentList.add(mCarRegisterFragment);
        mFragmentList.add(mCarListFragment);
        mFragmentList.add(mCarFragment);
        //增加新添设备的页面
    }
    /**
     * 初始化底部标题栏
     */
    private void initId() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mMeLayout = (BottomLayout) findViewById(R.id.me_layout);
        mMeLayout.setNormalIcon(R.drawable.me_selector);
        mMeLayout.setIconText("我");
        mCarRegisterLayout = (BottomLayout) findViewById(R.id.car_register_layout);
        mCarRegisterLayout.setNormalIcon(R.drawable.car_register_selector);
        mCarRegisterLayout.setIconText("设备注册");

        mCarListLayout = (BottomLayout)findViewById(R.id.list_layout);
        mCarListLayout.setNormalIcon(R.drawable.car_list_selector);
        mCarListLayout.setIconText("设备列表");


        mCarListLayout.setOnClickListener(this);
        mMeLayout.setOnClickListener(this);
        mCarRegisterLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.me_layout:
                setBottomState(0);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.car_register_layout:
                setBottomState(1);
                mViewPager.setCurrentItem(1);
                break;
            case R.id.list_layout:
                setBottomState(2);
                mViewPager.setCurrentItem(2);
            default:
                break;
        }
    }
    private void setBottomState(int position) {
        switch (position) {
            case 0:
                mMeLayout.setSelectState();
                mCarRegisterLayout.setUnSelectState();
                mCarListLayout.setUnSelectState();
                break;
            case 1:
                mMeLayout.setUnSelectState();
                mCarRegisterLayout.setSelectState();
                mCarListLayout.setUnSelectState();
                break;
            case 2:
                mMeLayout.setUnSelectState();
                mCarRegisterLayout.setUnSelectState();
                mCarListLayout.setSelectState();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化心跳线程的初始数据
     */
    private void init(){
        try{
            js = new JSONObject();
            js.put("type",MainCar.type);
            js.put("SessionId",SessionID);
            upload.setJson(js);
            upload.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
