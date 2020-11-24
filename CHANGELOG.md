#  Changelog

#### 0.11.2
* Bugfixes and improvements regarding Chromecast
* DVR Window is now maximum per default (before it was limited to 600sec)
* Updates Exoplayer version to 2.12.1
* Implements LicenceDomain check with licence key and bundle (see Developer Guide)
* ``` AVVDate.printDate() ``` needs a Locale as second parameter
* Updates targetSdk to 30
* User can implement his own ``` AVVAnalyticsDelegate ``` and use it via ```AVVPlayerBuilder.addAnalyticsHandler()```
* Timebar is not updates when user is scrubbing
* DefaultControls are staying visible while user is scrubbing timebar
* Implements Subtitle Support
  * Subtitles are read from hls file and additional subtitles (vtt files) can be added via PlayerConfig.

#### 0.11.0
* Fixes Bug that triggered restart of heartbeat when ``` onActivityResume() ``` is called.
* Via PlayerBuilder the VideoSurfaceType of exoplayer can be changed.
* Adds support for remote control using android tv.
* By overriding ``` AVVPlayerSettings.kt ``` and passing it to ``` AVVPlayerBuilder.setPlayerSettings() ``` you can change various Settings regarding Playbackquality, Buffersize, SurfaceType and MediaCodecOperationMode.
* By overriding ``` AVVPlayerSettings.kt ```  you can activate a flag to show a debugoverlay within your video frame that displays all kinds of video information.

#### 0.10.3
* Fixes Bug that causes FeatureOverlay to not be shown.

#### 0.10.2
* Heartbeat Manager validates successfully if response delivers status 200 and ``` {success:true} ```