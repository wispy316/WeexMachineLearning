package com.example.xj.weexandroidimagerecognition.recognition;

import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.example.xj.weexandroidimagerecognition.recognition.Classifier;
import com.example.xj.weexandroidimagerecognition.recognition.TensorFlowImageClassifier;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.annotation.Component;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.Destroyable;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXImage;
import com.taobao.weex.ui.view.WXImageView;
import com.taobao.weex.utils.ImageDrawable;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by xj on 2017/6/23.
 */

public class ImageRecognitionModule extends WXModule implements Destroyable {
    private final String CLIP_KEY = "WEEX_CLIP_KEY_MAIN";
    private static final String RESULT = "result";
    private static final String DATA = "data";
    private static final String RESULT_OK = "success";
    private static final String RESULT_FAILED = "failed";

    public ImageRecognitionModule() {
    }

    @JSMethod
    public void predictWithImage(String imageRef, JSCallback callback) {
        if(TextUtils.isEmpty(imageRef)) {
            return;
        }
        WXImageView image = findViewByRef(mWXSDKInstance, imageRef);
        if(null == image) {
            return; // can`t find image by ref
        }

        if(null == classifier) {
            init();
        }

        image.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(image.getDrawingCache());
        image.setDrawingCacheEnabled(false);

        Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);
        final List<Classifier.Recognition> results = classifier.recognizeImage(bitmapScaled);

        HashMap map = new HashMap(2);
        map.put("result", results.toString());
        map.put("data", results);
        if(null != callback) {
            callback.invoke(map);
        }

    }

    @Nullable
    private <T extends View> T findViewByRef(WXSDKInstance wxsdkInstance, String ref) {
        WXComponent<T> component = WXSDKManager.getInstance().getWXRenderManager().getWXComponent(wxsdkInstance.getInstanceId(), ref);
        if(null != component) {
            return (T) component.getRealView();
        }else {
            return null;
        }

    }

    private Executor executor = Executors.newSingleThreadExecutor();
    private Classifier classifier;
    private static final int INPUT_SIZE = 224;
    private static final int IMAGE_MEAN = 117;
    private static final float IMAGE_STD = 1;
    private static final String INPUT_NAME = "input";
    private static final String OUTPUT_NAME = "output";

    private static final String MODEL_FILE = "file:///android_asset/tensorflow_inception_graph.pb";
    private static final String LABEL_FILE = "file:///android_asset/imagenet_comp_graph_label_strings.txt";

    private void init() {
        try {
            classifier = TensorFlowImageClassifier.create(
                    mWXSDKInstance.getContext().getAssets(),
                    MODEL_FILE,
                    LABEL_FILE,
                    INPUT_SIZE,
                    IMAGE_MEAN,
                    IMAGE_STD,
                    INPUT_NAME,
                    OUTPUT_NAME);
        } catch (final Exception e) {
            throw new RuntimeException("Error initializing TensorFlow!", e);
        }
    }


    @Override
    public void destroy() {
        if(null != classifier) {
            classifier.close();
        }
    }
}
