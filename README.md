# WeexMachineLearning

Weex modules for machine learning based on [TensorFlow](https://www.tensorflow.org/)  in Android. Thanks for  [AndroidTensorFlowMachineLearningExample](https://github.com/MindorksOpenSource/AndroidTensorFlowMachineLearningExample) .

# Requirements
* Android Studio
* Android 14+

# Demo
1. Git clone this repo and import into Android Studio, then click run
2. Download this [APK](doc/app-debug.apk) and install

# Modules

## imageRecognition

```javascript
let imageRecognition = weex.requireModule('imageRecognition')
imageRecognition.predictWithImage(this.$refs.image.ref, (results)=>{
  this.results = results;  // contains key result, data
})
```

[Screenshot1](./doc/Screenshot1.png)

[Screenshot2](./doc/Screenshot2.png)

