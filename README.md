AVVPlayer-MARVIN-Android
========================
# Links

* [Changelog](/CHANGELOG.md)
* [How to use the Demo Application](/DEMO.md)

#  Developer Guide
## Basic Integration
### Adding AVVPL to your project

**1. Add the maven repository to your project level build.gradle**
```
allprojects {
    repositories {
        ...
        maven { url "https://avvpl-staging.sportradar.com/dist/android/latest/" }
        maven { url 'http://bitmovin.bintray.com/maven' }
        ...
    }
}
```
KNOWN ISSUE: Bitmovin Analytics integration demands adding the bitmovin maven repository. Fixing this issue is in progress.

**2. Add the avvpl dependency to your app module build.gradle**
```
implementation("ag.sportradar.android:avvplayermarvin:X.X.X")
```

The newest Version can be found [here](/CHANGELOG.md)

### Checking for a valid licence
```kotlin
    val licence = AVVLicence.Builder(this, "[your_licence_key]")
                .domain("[www.yourlicencedomain.com]") //if required
                .bundle("[com.your.appid]") //optional: it will pick the applictiationId per default 
                .listener(object : AVVLicenceCheckListener {
                    override fun onLicenceValidated(valid: Boolean) {
                        // player should not be built before licence is validated.
                    }
                })
                .build()

    AVVSettings.instance.checkLicence(licence)
```

### Creating the AVVPlayer instance
**1. Instantiate the Player**
```kotlin
val player = AVVPlayerBuilder(context) //your Activity's context
            .setPlayerContainer(playerContainer) //the viewgroup that contains the player
            .build()
```

**2. Start the video passing a video configuration to the player**
```kotlin
player.setup(AVVConfigUrl("[URL to the OTT videoconfig]"))
```
or
```kotlin
player.setup(AVVStreamUrl("https://cdn.theoplayer.com/video/elephants-dream/playlist.m3u8"))
```
or
```kotlin
player.setup(AVVConfig.Builder(0)
                    .streamUrl("https://cdn.theoplayer.com/video/elephants-dream/playlist.m3u8")
                    .build())
```

### Handle lifecycle events

The AVVPlayer instance needs to react to certain lifecycle events of the application, such as onPause(), onResume() and onDestroy().
```kotlin
class MainActivity : AppCompatActivity() {
    override fun onPause() {
        player.onActivityPause()
        super.onPause()
    }
 
    override fun onResume() {
        player.onActivityResume()
        super.onResume()
    }
 
    override fun onDestroy() {
        player.onActivityDestroy()
        super.onDestroy()
    }
}
```

POSSIBLE ISSUE:
```kotlinplayer.onActivityDestroy()``` calls Exoplayers ```stop()``` and ```release()``` method. 
As ```release()``` is known for blocking the UI Thread until all resources are released,some 
devices (especially lowend devices) might appear to freeze for 1-3 seconds. If you are experiencing these issues try calling
```kotlin
    override fun onDestroy() {
        player.onActivityDestroy(killPlaybackThread = false)
        super.onDestroy()
    }
```
instead

### Handle orientation change
In order for the AVVPlayer to continue playback through orientation changes you need to enable configChanges in the Activity that holds the player.

**1. Add the following line to your activity in the AndroidManifest.xml**
```xml
<activity 
    android:name=".YourPlayerActivity"
    android:configChanges="orientation|screenSize|screenLayout">
    ... 
</activity>
```

**2. Override onConfigurationChanged in your Activity/Fragment that holds the player**
```kotlin
override fun onConfigurationChanged(newConfig: Configuration) {
        player.onConfigurationChanged(newConfig)
        super.onConfigurationChanged(newConfig)
    }
```
------
## Changing the player configuration (if required)

The player fetches its configuration from the URL that is passed in the setUp() call. The configuration determines the looks and the behavior of the player, however if you wish to change any of this behavior or need to add necessary information to the player configuration you can do so by implementing AVVConfigAdaptationCallback and adding it to the player.setup() call
```kotlin
player.setup(config, object : AVVConfigAdaptationCallback() {})
```

* **Adding request headers to streamaccess**
```kotlin
player.setUp(config, object : AVVConfigAdaptationCallback() {
            override fun adaptConfig(config: AVVConfig) {
                //...
                config.streamUrlProviderInfo.requestData = AVVPostRequestData(mapOf(Pair("authorization", "your auth token")))
                //...
            }
})
```

* **Changing autoplay behavior**
```kotlin
player.setUp(config, object : AVVConfigAdaptationCallback() {
             
            override fun adaptConfig(config: AVVConfig) {
                //...
                config.streamMetaData.autoPlay = true
                //...
            }
             
        })
```
* **Add heartbeat**
```kotlin
player.setUp(config, object : AVVConfigAdaptationCallback() {
             
            override fun adaptConfig(config: AVVConfig) {
                //...
                config.heartbeat = AVVHeartbeat.Builder()
                    .enabled(true)
                    .time(30) //seconds
                    .ticket("[your heartbeat ticket]")
                    .validationPath("https://yourvalidation.com/validation")
                    .build()
                    
                 //...
            }
             
        })
```

------

## Customizing UI

### Customize Error Overlay
```kotlin
class MyErrorOverlay : AVVErrorOverlayDelegate {
    override fun onCreateErrorView(parent: ViewGroup, error: AVVError): View {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.my_error_layout, parent, false)
 
        // display the error data contained in the AVVError as you like.
        return view
    }
}
```
```kotlin
val player = AVVPlayerBuilder(activity)
            .setPlayerContainer(playerContainer)
            .setCustomErrorOverlay(MyErrorOverlay())
            .build()
```

### Customize Control Overlay
```kotlin
class MyCustomControls : AVVControlOverlayDelegate {
    override fun onCreateErrorView(parent: ViewGroup, error: AVVError): View {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.layout_videoplayer_controls, parent, false)
 
         controlBinding.bindPlayPauseButton(
            view.findViewById(R.id.playPauseButton),
            R.drawable.ic_avv_play,
            R.drawable.ic_avv_pause, config.skin
        )

        controlBinding.bindFullscreenButton(
            view.findViewById(R.id.fullscreenButton),
            R.drawable.ic_avv_fullscreen_enter,
            R.drawable.ic_avv_fullscreen_exit
        )

        controlBinding.bindLiveIndicatorView(
            view.findViewById(R.id.liveindicator),
            R.drawable.ic_avv_live_indicator,
            config.skin
        )
 
        // for more bindings check MyCustomControls.kt in DemoApplication
        return view
    }
}
```
```kotlin
val player = AVVPlayerBuilder(activity)
            .setPlayerContainer(playerContainer)
            .setControlOverlay(MyCustomControls())
            .build()
```

------

## Chromecast
### Create CastOptionsProvider
```kotlin
class DemoCastOptionsProvider: AVVCastOptionsProvider() {
    override fun getReceiverApplicationId(context: Context): String {
        return "YOUR_CAST_RECEIVER_ID"
    }
}
```

### Add the CastOptionsProvider to your AndroidManifest.xml
```xml
    <application>
        <meta-data
            android:name="com.google.android.gms.cast.framework.OPTIONS_PROVIDER_CLASS_NAME"
            android:value="path.to.your.AVVCastOptionsProvider"
            tools:replace="android:value" />
 
    </application>
```

### Initiate CastContext
```kotlin
    class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
        //...
        CastContext.getSharedInstance(this)
        //...
    }
    }
```

### (Optional) Add a MiniController to your layout
```xml
    <fragment
        android:id="@+id/miniControllerFragment"
        class="ag.sportradar.avvplayer.player.chromecast.widgets.AVVDefaultCastMiniController"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```