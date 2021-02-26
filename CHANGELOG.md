#  Changelog

### 0.11.4

#### New features
* Select a position a VOD should start at by using ```AVVPlaybackOptions``` like ```AVVConfig.playbackOptions.setStartPosition(position: Long, timeUnit: TimeUnit)```
* Bitrate track selection: 
  * DefaultControls are showing Tracks to change Bitrate (if ```AVVQuality``` is enabled in config).
  * New class ```AVVTracks``` contains information what language, subtitle and bitrate tracks are available.
  * ```AVVPlayerControlsObserver```: removes ```onLanguageOptionsAvailable(true|false)```.
  * ```AVVPlayerControlsObserver```: adds ```onTracksChanged([AVVTracks])```. Called whenever exoplayer changes tracks. 
* Adds watermark overlay.

#### API additions
* Adds ```AVVConfigAssetFile.kt``` to setup player with a videoconfig from an assetfile.
* Adds optional methods ```AVVPlayer().destroy(killPlaybackThread: Boolean)``` and ```AVVPlayer().onActivityDestroy(killPlaybackThread: Boolean)``` (see integration guide).
* Adds method ```AVVPlayer().failWith(errorState: StateError)``` to trigger error from outside.
* Adds ```AVVColor``` as a color representation class.
  * ```AVVColor``` not only understands hexCode colors but also rgba ("rgba(255, 255, 255, 0.3)") format.
  
#### API changes/ removals
* Moves autoplay field from ```AVVConfig.streamMetaData.autoPlay``` to ```AVVConfig.playbackOptions.autoPlay```.
* Renaming / adding callbacks to ```AVVPlayerControlsObserver```.
  * ```onPositionSeeked(positionMs: Long)``` removed.
  * ```onTimeBarUpdated(progressMs: Long, durationMs: Long)``` added.
  * ```onTimeBarScrubStart(progressMs: Long)``` added.
  * ```onTimeBarScrubStop(progressMs: Long)``` added.
  * ```onLanguageOptionsAvailable(true|false)``` removed.
  * ```onTracksChanged([AVVTracks])``` added.
* Deprecated following setup() methods in ```AVVPlayer```.
  * ```setup(videoConfigConvertible: AVVPlayerConfigConvertible, initialPlaybackPosition: Long)```
  * ```setup(videoConfigConvertible: AVVPlayerConfigConvertible,configAdaptationCallback: AVVConfigAdaptationCallback?, initialPlaybackPosition: Long)```
  * set the initial playposition with ```AVVPlaybackOptions.setStartPosition(position: Long, timeUnit: TimeUnit)``` instead.
* ```Skin``` class moved to  package ```ag.sportradar.avvplayer.player.skin```.
* Removes class ```AVVQualitySettings``` included in ```AVVPlayerSettings```.
  * Set bitrate limitations using ```AVVConfigAdaptationCallback``` overriding ```AVVQuality().maxBitrate```.
* Renames ```AVVExperimentalSettings``` to ```AVVRendererSettings``` in class ```AVVPlayerSettings```.
  
#### States
* Adds ```MediaSessionState.INITIALIZED``` which will be called one time to indicate that everything needed to start the video (e.g. DRM licence, etc) is initialized.

#### Overlays
* Changes look of subtitles (no background, black outline and configurable textcolor instead).
* Layout of all layers in fullscreen mode is reduced to 16:9 aspect ratio.
* Redesigns ```AVVDefaultErrorOverlay```.
  * makes class open and adds overridable functions to set error icon and error icon color.
* Refactors endscreen layout to fix layout issues for certain screensizes.
  
#### Analytics
* Changes in ```AVVAnalyticsDelegate```:
  * removes ```onCreateAnalytics(context: Context?, player: SimpleExoPlayer?, avvAnalytics: AVVAnalyticsData)```.
  * adds ```fun createAnalyticsOnConfigLoaded(context: Context?,player: SimpleExoPlayer?,avvAnalytics: AVVAnalyticsData,config: AVVConfig)```.
  * adds  ```fun createAnalyticsOnStreamUrlProvided(context: Context?,player: SimpleExoPlayer?,avvAnalytics: AVVAnalyticsData,config: AVVConfig)```.
  * removes ```onDetachAnalytics()```.
  * adds ```destroyAnalytics(player: SimpleExoplayer)```.
  
#### Bugfixes
* Fixes issue that application would crash if the player is released while config is loading
* Bugfixes for endscreen
  * ```AVVConfigAdaptationCallback``` is passed to videos selected from Endscreen
  * Fixes double call on ```MediaPlaybackState.ENDED``` that cause Endscreen countdown to start twice. 

#### Sdk Updates
* Updates exoplayer to v2.12.3
* Updates bitmovin analytics to v1.22.0
* Updates Google IMA sdk to version 3.22.0


### 0.11.3
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

### 0.11.2
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

### 0.11.0
* Fixes Bug that triggered restart of heartbeat when ``` onActivityResume() ``` is called.
* Via PlayerBuilder the VideoSurfaceType of exoplayer can be changed.
* Adds support for remote control using android tv.
* By overriding ``` AVVPlayerSettings.kt ``` and passing it to ``` AVVPlayerBuilder.setPlayerSettings() ``` you can change various Settings regarding Playbackquality, Buffersize, SurfaceType and MediaCodecOperationMode.
* By overriding ``` AVVPlayerSettings.kt ```  you can activate a flag to show a debugoverlay within your video frame that displays all kinds of video information.

### 0.10.3
* Fixes Bug that causes FeatureOverlay to not be shown.

### 0.10.2
* Heartbeat Manager validates successfully if response delivers status 200 and ``` {success:true} ```