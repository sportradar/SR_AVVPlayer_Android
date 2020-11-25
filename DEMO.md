
## How to use the AVVPlayer Demo Application

1. Clone this repository
2. Open the project in Android Studio
3. Go to /assets folder and open "demo_config.json"


```
{
  "licenceKey": "", //required
  "licenceDomain": "", //required
  "bundle": "", //required
  "streamUrl": "https://cdn.theoplayer.com/video/elephants-dream/playlist.m3u8", //Direct streamUrl you want to play
  "configUrl": "", //If you'd like to play a video from an OTT videoConfig
  "autoplay": false, //true or false whether you want the video to start right away or not
  "authorizationToken": "" //only required if you use "configUrl" and the content needs an authorizationToken
}
```

NOTE: use either "streamUrl" or "configUrl" and leave the other one empty ("")

4. Run the application
