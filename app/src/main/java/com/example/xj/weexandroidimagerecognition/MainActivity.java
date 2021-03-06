package com.example.xj.weexandroidimagerecognition;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.xj.weexandroidimagerecognition.https.WXHttpManager;
import com.example.xj.weexandroidimagerecognition.https.WXHttpTask;
import com.example.xj.weexandroidimagerecognition.https.WXRequestListener;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.RenderContainer;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.ui.component.NestedContainer;
import com.taobao.weex.utils.WXFileUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements IWXRenderListener, WXSDKInstance.NestedInstanceInterceptor  {

    private static final String TAG = "MainActivity";
    WXSDKInstance mInstance;
    private ViewGroup mContainer;
    private HashMap mConfigMap = new HashMap<String, Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContainer = (ViewGroup) findViewById(R.id.container);

        mInstance = new WXSDKInstance(this);
        // demo at http://dotwe.org/vue/4259b37aff50cdf6c890c6904f41940a
        loadWXfromService("http://dotwe.org/raw/dist/4259b37aff50cdf6c890c6904f41940a.bundle.wx");



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_camera) {
            Intent intent = new Intent(this, CameraPreviewActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadWXfromService(final String url) {

        if (mInstance != null) {
            mInstance.destroy();
        }

        RenderContainer renderContainer = new RenderContainer(this);
        mContainer.addView(renderContainer);

        mInstance = new WXSDKInstance(this);
        mInstance.setRenderContainer(renderContainer);
        mInstance.registerRenderListener(this);
        mInstance.setNestedInstanceInterceptor(this);
        mInstance.setBundleUrl(url);
        mInstance.setTrackComponent(true);

        WXHttpTask httpTask = new WXHttpTask();
        httpTask.url = url;
        httpTask.requestListener = new WXRequestListener() {

            @Override
            public void onSuccess(WXHttpTask task) {
                Log.i(TAG, "into--[http:onSuccess] url:" + url);
                try {
                    mConfigMap.put("bundleUrl", url);
                    mInstance.render(TAG, new String(task.response.data, "utf-8"), mConfigMap, null,
                            ScreenUtil.getDisplayWidth(MainActivity.this),
                            ScreenUtil.getDisplayHeight(MainActivity.this),
                            WXRenderStrategy.APPEND_ASYNC);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(WXHttpTask task) {
                Log.i(TAG, "into--[http:onError]");
                Toast.makeText(getApplicationContext(), "network error!", Toast.LENGTH_SHORT).show();
            }
        };

        WXHttpManager.getInstance().sendRequest(httpTask);
    }

    @Override
    public void onViewCreated(WXSDKInstance wxsdkInstance, View view) {
        if(view.getParent() == null) {
            mContainer.addView(view);
        }
        mContainer.requestLayout();
        Log.d("WARenderListener", "renderSuccess");
    }

    @Override
    public void onRenderSuccess(WXSDKInstance wxsdkInstance, int i, int i1) {

    }

    @Override
    public void onRefreshSuccess(WXSDKInstance wxsdkInstance, int i, int i1) {

    }

    @Override
    public void onException(WXSDKInstance wxsdkInstance, String s, String s1) {

    }

    @Override
    public void onCreateNestInstance(WXSDKInstance wxsdkInstance, NestedContainer nestedContainer) {

    }


}
