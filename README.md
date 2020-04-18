# AutoTouchManager

![ReleaseVer](https://img.shields.io/badge/Release-1.1.0-blueviolet)
![AndroidMinVer](https://img.shields.io/badge/API-24+-ff69b4)
![License](https://img.shields.io/badge/License-MIT-brightgreen)

👆🏽 'AutoTouchManager' is a library that automatically creates touch.

🛀🏻 If you use this library, It's very simple to create an auto touch event. It provides functionality very easily to those who want to create touch macros.

<br/>

## 📄 Table of Contents
1. [Quick Start](#quick-start)
	1. [Gradle](#gradle-setup)
3. [Simple Usage](#simple-usage)
4. [Types of TouchData](#type-constructor)
4. [Types of DoubleTabData](#type2-constructor)
2. [Example Source](#example-source)
4. [License](#free)

<br/>

<h2 id="quick-start">🏃 Quick Start 💨</h2>

Add the library to your Android project, then check out the examples below!

### Gradle Setup

```gradle-setup
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.HuveD:AutoTouchManager:1.0.0'
}
```

<br/>

<h2 id="simple-usage">⌨️ Simple Usage 🖲</h2>

#### Just setting and play. Very Simple!

```
// Create the Auto touch List.
// The events below occur sequentially.
ArrayList<TouchData> list = new ArrayList<>();
list.add(new TouchData(wantX, wantY));
list.add(new TouchData(wantXAfterTouch1, wantYAfterTouch1));

// Start the auto touch.
AutoTouchManager.getInstance().setTouchEventPreset(list);
AutoTouchManager.getInstance().play(this);
```

<br/>

#### (Optional) Attach the finish event listener

```
// Create a 'OnFinishListener'
AutoTouchManager.OnFinishListener onFinishListener = new AutoTouchManager.OnFinishListener() {
	@Override
	public void onFinish() {
		// This method will be invoked after the 'auto touch' finished.
	}
};

// Set the listener
AutoTouchManager.getInstance().setOnFinishListener(onFinishListener);
```

<br/>

#### (Optional) Attach the error event listener

```
// Create a 'OnErrorListener'
AutoTouchManager.OnErrorListener onErrorListener = new AutoTouchManager.OnErrorListener() {
	@Override
	public void onError(String message) {
		Log.d("Your Tag", "Error occurred: " + message);
	}
};

// Set the listener
AutoTouchManager.getInstance().setOnErrorListener(onErrorListener);
```

<br/>



<h2 id="type-constructor">👩🏻‍💻 Types of TouchData 👆🏻</h2>

##### 1. TouchData(float, float)  
##### 1st params  
- The x coordinate of screen anticipated occurring the touch.  
##### 2nd params  
- The y coordinate of screen anticipated occurring the touch.

<br>

##### 2. TouchData(float, float, int)      
##### 1st params  
- The x coordinate of screen anticipated occurring the touch.  
##### 2nd params  
- The y coordinate of screen anticipated occurring the touch.  
##### 3rd params  
- The delay time until the touch occurred. For example, if you set the time at 3000, the Auto touch will occur after 3 seconds.

<br>

##### 3. TouchData(float, float, Runnable)  
##### 1st params  
- The x coordinate of screen anticipated occurring the touch.  
##### 2nd params  
- The y coordinate of screen anticipated occurring the touch.  
##### 3rd params  
- This Runnable execute after 'Auto Touch' finished.

<br>

##### 4. TouchData(float, float, int, Runnable)    
##### 1st params  
- The x coordinate of screen anticipated occurring the touch.  
##### 2nd params  
- The y coordinate of screen anticipated occurring the touch.  
##### 3rd params  
- The delay time until the touch occurred. For example, if you set the time at 3000, the Auto touch will occur after 3 seconds.
##### 4th params  
- This Runnable execute after 'Auto Touch' finished.

<br/>

<h2 id="type2-constructor">👩🏻‍💻 Types of DoubleTabData 👆🏻</h2>

##### 1. DoubleTabData(float, float)
##### 1st params
- The x coordinate of screen anticipated occurring the touch.
##### 2nd params
- The y coordinate of screen anticipated occurring the touch.

<br>

##### 2. DoubleTabData(float, float, int)
##### 1st params
- The x coordinate of screen anticipated occurring the touch.
##### 2nd params
- The y coordinate of screen anticipated occurring the touch.
##### 3rd params
- The delay time until the touch occurred. For example, if you set the time at 3000, the Auto touch will occur after 3 seconds.

<br>

##### 3. DoubleTabData(float, float, Runnable)
##### 1st params
- The x coordinate of screen anticipated occurring the touch.
##### 2nd params
- The y coordinate of screen anticipated occurring the touch.
##### 3rd params
- This Runnable execute after 'Auto Touch' finished.

<br>

##### 4. DoubleTabData(float, float, int, Runnable)
##### 1st params
- The x coordinate of screen anticipated occurring the touch.
##### 2nd params
- The y coordinate of screen anticipated occurring the touch.
##### 3rd params
- The delay time until the touch occurred. For example, if you set the time at 3000, the Auto touch will occur after 3 seconds.
##### 4th params
- This Runnable execute after 'Auto Touch' finished.

<br/>

<h2 id="example-source">👉🏻 Example Source</h2>

If you want more details, look at the [source code](https://github.com/HuveD/AutoTouchManager/blob/master/AutoTouchManagerExample/src/main/java/kr/co/huve/AutoTouchExample/ExampleActivity.java).

The source code has an example of usage, like below.
- Customize permission text
- Simple auto touch usage
- Simple auto touch usage with delay
- Simple auto touch usage with runnable
- Simple auto touch usage with delay runnable
- Perform 'Home key' occur
- Perform 'Back key' occur

<br/>

<h1 id="free">🕵️‍♀️ License</h1>

MIT License

Copyright (c) 2020 JeongHyeon Yoo

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

<br/>

