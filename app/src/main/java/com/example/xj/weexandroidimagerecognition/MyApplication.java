package com.example.xj.weexandroidimagerecognition;

import android.app.Application;

import com.example.xj.weexandroidimagerecognition.adapter.ImageAdapter;
import com.example.xj.weexandroidimagerecognition.adapter.JSExceptionAdapter;
import com.example.xj.weexandroidimagerecognition.recognition.ImageRecognitionModule;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.DefaultWXHttpAdapter;
import com.taobao.weex.common.WXException;

/**
 * Created by xj on 2017/6/21.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        WXSDKEngine.initialize(this,
                new InitConfig.Builder()
                        //.setImgAdapter(new FrescoImageAdapter())// use fresco adapter
                        .setImgAdapter(new ImageAdapter())
                        .setJSExceptionAdapter(new JSExceptionAdapter())
                        .setHttpAdapter(new DefaultWXHttpAdapter())
                        .build()
        );

        try {
            WXSDKEngine.registerModule("imageRecognition", ImageRecognitionModule.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
    }
}
