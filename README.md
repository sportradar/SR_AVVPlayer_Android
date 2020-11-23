AVVPlayer-MARVIN-Android
===================
#  Developer Guide
------
## Basic Integration
------
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
### Checking for a valid licence

```
AVVSettings.instance.initLicence(this,"your_licence_key",
        object : AVVLicenceCheckListener{
            override fun onLicenceValidated(valid: Boolean) {}
        });
```

### Creating the AVVPlayer instance

**1. Instantiate the Player**

```
val player = AVVPlayerBuilder(context) //your Activity's context
            .setPlayerContainer(playerContainer) //the viewgroup that contains the player
            .build()
```

**2. Start the video passing a Video Configuration to the player**

```
player.setup(AVVConfigUrl("https://www.badmintoneurope.tv/api/v2/content/92179/player-setting"))
```

### Handle lifecycle events

The AVVPlayer instance needs to react to certain lifecycle events of the application, such as onPause(), onResume() and onDestroy().
```
class MainActivity : AppCompatActivity() {
...
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
...
}
```

### Handle orientation change

In order for the AVVPlayer to continue playback through orientation changes you need to enable configChanges in the Activity that holds the player.

**1. Add the following line to your activtiy in the AndroidManifest.xml**

```
<activity
           ..
       android:configChanges="orientation|screenSize|screenLayout"
           ...
</activity>
```

**2. Override onConfigurationChanged in your Activtiy/Fragment that holds the player**

```
override fun onConfigurationChanged(newConfig: Configuration) {
        player.onConfigurationChanged(newConfig)
        super.onConfigurationChanged(newConfig)
    }
```

### Changing the player configuration

TODO:  
-adding request header to streamaccess
-changing autoplay behavior
-add heartbeat

## Customizing UI
------
### Customize Error Overlay
```
class MyErrorOverlay : AVVErrorOverlayDelegate {
    override fun onCreateErrorView(parent: ViewGroup, error: AVVError): View {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.my_error_layout, parent, false)
 
        // display the error data contained in the AVVError as you like.
        return view
    }
}
```
```
val player = AVVPlayerBuilder(activity)
            .setPlayerContainer(playerContainer)
            .setCustomErrorOverlay(MyErrorOverlay())
            .build()
```


## Chromecast
------
### Create CastOptionsProvider
```
class DemoCastOptionsProvider: AVVCastOptionsProvider() {
    override fun getReceiverApplicationId(context: Context): String {
        return "YOUR_CAST_RECEIVER_ID"
    }
}
```

### Add the CastOptionsProvider to your AndroidManifest.xml
```
    <application>
        ...
        <meta-data
            android:name="com.google.android.gms.cast.framework.OPTIONS_PROVIDER_CLASS_NAME"
            android:value="ag.sportradar.avvplayerdemo.DemoCastOptionsProvider"
            tools:replace="android:value" />
 
    </application>
```

### Initiate CastContext
```
    class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
        ...
        CastContext.getSharedInstance(this)
        ...
    }
```

### (Optional) Add a MiniController to your layout
```
    <fragment
        android:id="@+id/miniControllerFragment"
        class="ag.sportradar.avvplayer.player.chromecast.widgets.AVVDefaultCastMiniController"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```

#  Changelog
------

TODO:

