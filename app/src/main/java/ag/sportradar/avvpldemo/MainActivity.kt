package ag.sportradar.avvpldemo

import ag.sportradar.avvplayer.config.AVVConfig
import ag.sportradar.avvplayer.config.AVVConfigUrl
import ag.sportradar.avvplayer.player.AVVPlayer
import ag.sportradar.avvplayer.player.AVVPlayerBuilder
import ag.sportradar.avvplayer.player.licencing.AVVLicenceCheckListener
import ag.sportradar.avvplayer.player.settings.AVVSettings
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    lateinit var player: AVVPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*README.md Checking for a valid licence */
        AVVSettings.instance.initLicence(this, "your_licence_key",
            object : AVVLicenceCheckListener {
                override fun onLicenceValidated(valid: Boolean) {}
            });

        /*README.md Instantiate the Player */
        player = AVVPlayerBuilder(this)
            .setPlayerContainer(findViewById(R.id.playerContainer))
            .build()

        /*README.md Start the video passing a Video Configuration to the player */
        player.setup(AVVConfigUrl("https://www.badmintoneurope.tv/api/v2/content/92179/player-setting"))
    }

    //region README.md Handle lifecycle events
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
    //endregion

    //region README.md Handle orientation change
    override fun onConfigurationChanged(newConfig: Configuration) {
        player.onConfigurationChanged(newConfig)
        super.onConfigurationChanged(newConfig)
    }
    //endregion
}