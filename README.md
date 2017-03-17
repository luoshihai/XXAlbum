这个库是严振杰大神写的 我只是根据我自己的项目来修改了一部分东西
放到github上方便使用
### [他的github](https://github.com/yanzhenjie/)

### [他的博客](http://blog.csdn.net/yanzhenjie1003)

## 开始使用

### 添加依赖

1.在project目录的build.gradle的allprojects节点添加
```java maven { url "https://jitpack.io" }```
如下:

    allprojects {
        repositories {
            jcenter()
            maven { url "https://jitpack.io" }
        }
    }

2.在自己Modul的build.gradle中添加``` 'compile 'com.github.luoshihai:XXAlbum:V1.0.0''```
如下:

	dependencies {
	        compile compile 'com.github.luoshihai:XXAlbum:V1.0.0'
	}

### 第一步
在AndroidManifest.xml中application节点添加如下代码:

	<activity
            android:name="com.yanzhenjie.album.AlbumActivity"
            android:label="图库"
            android:configChanges="orientation|keyboardHidden|screenSize"
            android:theme="@style/Theme.AppCompat.Light.NoActionBar"
            android:windowSoftInputMode="stateAlwaysHidden|stateHidden" />
android:label="" 可以根据自己需求来写  会显示到图片选择Activity上

### 第二步

	 Album.startAlbum(MainActivity.this, ACTIVITY_REQUEST_SELECT_PHOTO //这个ACTIVITY_REQUEST_SELECT_PHOTO会在取数据的时候使用
                        , 9      // 指定选择数量。
                        , ContextCompat.getColor(MainActivity.this, R.color.colorPrimary)        // 指定Toolbar的颜色。
                        , ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));  // 指定状态栏的颜色。
启动图片选择Activity 这个方法有几个重载方法 可以根据需求来使用

### 第三步  接收用户选择的图片路径

		@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_REQUEST_SELECT_PHOTO) { // ACTIVITY_REQUEST_SELECT_PHOTO就是startAlbum的第二个参数
            if (resultCode == RESULT_OK) { // 判断是否成功。
                // 拿到用户选择的图片路径List：
                List<String> pathList = Album.parseResult(data);
                Toast.makeText(this, "pathList.size():" + pathList.size(), Toast.LENGTH_SHORT).show();
                mAdapter.notifydataChanged(pathList);
            } else if (resultCode == RESULT_CANCELED) { // 用户取消选择。
                // 根据需要提示用户取消了选择。
            }
        }
    }

OK就这么简单