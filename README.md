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