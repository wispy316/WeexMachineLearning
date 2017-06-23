# WeexMachineLearning

Weex modules for machine learning based on [TensorFlow](https://www.tensorflow.org/)  in Android. Thanks for  [AndroidTensorFlowMachineLearningExample](https://github.com/MindorksOpenSource/AndroidTensorFlowMachineLearningExample) .

# Requirements
* Android Studio
* Android 14+

# Demo
1. Git clone this repo and import into Android Studio, then click run
2. Download this [APK](doc/app-debug.apk) and install
3. The demo VUE js-bundle is [here](http://dotwe.org/vue/4259b37aff50cdf6c890c6904f41940a)

# Modules

## imageRecognition

```javascript
let imageRecognition = weex.requireModule('imageRecognition')
imageRecognition.predictWithImage(this.$refs.image.ref, (results)=>{
  this.results = results;  // contains key result, data
})
```

![Screenshot1](https://github.com/wispy316/WeexMachineLearning/blob/master/doc/Screenshot1.png?raw=true)

![Screenshot2](https://github.com/wispy316/WeexMachineLearning/blob/master/doc/Screenshot2.png?raw=true)

Look, it is a little more precise than me. I thought screenshot2  was a leopard.

