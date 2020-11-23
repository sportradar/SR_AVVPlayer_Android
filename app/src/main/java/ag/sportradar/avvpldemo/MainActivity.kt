package ag.sportradar.avvpldemo

import ag.sportradar.avvplayer.config.AVVConfig
import ag.sportradar.avvplayer.config.AVVConfigUrl
import ag.sportradar.avvplayer.player.AVVPlayerBuilder
import ag.sportradar.avvplayer.player.licencing.AVVLicenceCheckListener
import ag.sportradar.avvplayer.player.settings.AVVSettings
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*README.md Checking for a valid licence */
        AVVSettings.instance.initLicence(this, "your_licence_key",
            object : AVVLicenceCheckListener {
                override fun onLicenceValidated(valid: Boolean) {}
            });

        /*README.md Instantiate the Player */
        val player = AVVPlayerBuilder(this)
            .build()

        /*README.md Start the video passing a Video Configuration to the player */
        player.setup(AVVConfigUrl("https://www.badmintoneurope.tv/api/v2/content/92179/player-setting"))
    }
}