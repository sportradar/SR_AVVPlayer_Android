#  Changelog

#### 0.11.4
##### -SNAPSHOT-b51
* Bitrate Tracks
  * DefaultControls are showing Tracks to change Bitrate (if AVVQuality is enabled in Config)
  * New class ```AVVTracks``` contains information what language, subtitle and bitrate tracks are available.
  * ```AVVPlayerControlsObserver```: removes ```onLanguageOptionsAvailable(true|false)``` 
  * ```AVVPlayerControlsObserver```: adds ```onTracksChanged([AVVTracks])```. Called whenever exoplayer changes tracks. 
* Adds ```AVVConfigAssetFile.kt``` to setup Player with a Config from an AssetFile.
##### -SNAPSHOT-b52
* Bugfixes for Endscreen
  * ```AVVConfigAdaptationCallback``` is passed to videos selected from Endscreen
  * Fixes double call on ```MediaPlaybackState.ENDED``` that cause Endscreen countdown to start twice. 
##### -SNAPSHOT-b53
* Renaming / Adding callbacks to ```AVVPlayerControlsObserver```
  * ```onPositionSeeked(positionMs: Long)``` removed
  * ```onTimeBarUpdated(progressMs: Long, durationMs: Long)``` added
  * ```onTimeBarScrubStart(progressMs: Long)``` added
  * ```onTimeBarScrubStop(progressMs: Long)``` added
##### -SNAPSHOT-b54
* Changes look of subtitles (no background, black outline and configurable textcolor instead)
* ```Skin``` class moved to  package ```ag.sportradar.avvplayer.player.skin```
* Adds ```AVVColor``` as a Color representation class
  * ```AVVColor``` not only understands hexCode colors but also rgba ("rgba(255, 255, 255, 0.3)") format.
##### -SNAPSHOT-b55
* Adds watermark support.
* Layout of all layers in fullscreen mode is reduced to 16:9 aspect ratio
* ```AVVColor``` converts all color codes to 8 digit hexcodes (this fixes an issue that 3 digit hexcolors could not be displayed)
##### -SNAPSHOT-b56
* Updates exoplayer to v2.12.3
* Updates bitmovin analytics to v1.22.0
##### -SNAPSHOT-b57
* Removes Class ```AVVQualitySettings``` included in ```AVVPlayerSettings```
  * Set bitrate limitations using ```AVVConfigAdaptationCallback``` overriding ```AVVQuality().maxBitrate```
* Updates Google IMA Sdk to version 3.22.0
* Select a custom startpoint in a Video using ```AVVMediaClipping``` in ```AVVConfig().clipping```
* Redesigns ```AVVDefaultErrorOverlay```
  * makes class open and adds overridable functions to set error icon and error icon color
* Adds method ```AVVPlayer().failWith(errorState: StateError)``` to trigger Error Overlay from outside
##### -SNAPSHOT-b58
* Adds ```MediaSessionState.INITIALIZED``` which will be called one time to indicate that everything needed to start the video (e.g. DRM licence, etc) is initialized.
* Changes in ```AVVAnalyticsDelegate```
  * renames ```onDetachAnalytics()``` to ```destroyAnalytics()```
  * removes ```onCreateAnalytics(context: Context?, player: SimpleExoPlayer?, avvAnalytics: AVVAnalyticsData)```
  * adds ```fun createAnalyticsOnConfigLoaded(context: Context?,player: SimpleExoPlayer?,avvAnalytics: AVVAnalyticsData,config: AVVConfig)```
  * adds ```fun createAnalyticsOnMediaSessionInitialized(context: Context?,player: SimpleExoPlayer?,avvAnalytics: AVVAnalyticsData,config: AVVConfig)```
##### -SNAPSHOT-b59
* Adds methods ```AVVPlayer().destroy(killPlaybackThread: Boolean)``` and ```AVVPlayer().onActivityDestroy(killPlaybackThread: Boolean)``` (see integration guide)
##### -SNAPSHOT-b60
* Fixes issue that when calling ```AVVPlayer().destroy(killPlaybackThread = true)``` audio would still start playing when the player is released too early.
* Fixes issue that application would crash if the player is released while Config is loading
* Refactors Endscreen layout to fix layout issues for certain screensizes
* Renames ```AVVExperimentalSettings``` to ```AVVRendererSettings``` in class ```AVVPlayerSettings```
##### -SNAPSHOT-b60
* Changes in ```AVVAnalyticsDelegate```
  * Adds exoplayer param to ```destroyAnalytics(player: SimpleExoplayer)```
  * removes ```fun createAnalyticsOnMediaSessionInitialized(context: Context?,player: SimpleExoPlayer?,avvAnalytics: AVVAnalyticsData,config: AVVConfig)```
  * adds  ```fun createAnalyticsOnStreamUrlProvided(context: Context?,player: SimpleExoPlayer?,avvAnalytics: AVVAnalyticsData,config: AVVConfig)```

#### 0.11.3
* Fixes broken default controllayout when Settings and Chromecast Icons are shown
* Removes seeking to certain position in video after chromecast is loaded
* Subtitles
  * Bugfixes and improvements regarding subtitle and audio selection.
  * Title for choosing Subtitles and Audiolanguage added in English and German
  * Subtitles are disabled by default, if not specified otherwise by config file
  * Adds support for SRT subtitles.
* ``` StreamUrlProviderInfo.providerClass ``` can be overwritten in ConfigAdaptation callback
* StreamUrlProvider returns a map of StreamUrls
* Player can be restored with a backup stream if a backup stream is returned by StreamUrlProvider
* AVVHttpRequest is not internal anymore
* Fixes issue that System UI was still hidden after fullscreenMode is left

#### 0.11.2
* Bugfixes and improvements regarding Chromecast
* DVR Window is now maximum per default (before it was limited to 600sec)
* Updates Exoplayer version to 2.12.1
* Implements LicenceDomain check with licence key and bundle (see Developer Guide)
* ``` AVVDate.printDate() ``` needs a Locale as second parameter
* Updates targetSdk to 30
* User can implement his own ``` AVVAnalyticsDelegate ``` and use it via ```AVVPlayerBuilder.addAnalyticsHandler()```
* Timebar does not update when user is scrubbing
* DefaultControls are staying visible while user is scrubbing timebar
* Implements Subtitle Support
  * Subtitles are read from hls file
  * Additional subtitles (vtt files) can be added via PlayerConfig.

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