AVVPlayer-MARVIN-Android
===================
#  Developer Guide
------
## Basic Intergration
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
}```