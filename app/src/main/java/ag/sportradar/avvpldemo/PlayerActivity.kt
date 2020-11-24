package ag.sportradar.avvpldemo

import ag.sportradar.avvplayer.config.AVVConfig
import ag.sportradar.avvplayer.config.AVVConfigAdaptationCallback
import ag.sportradar.avvplayer.config.AVVConfigUrl
import ag.sportradar.avvplayer.config.AVVPlayerConfigConvertible
import ag.sportradar.avvplayer.player.AVVPlayer
import ag.sportradar.avvplayer.player.AVVPlayerBuilder
import ag.sportradar.avvplayer.player.licencing.AVVLicenceCheckListener
import ag.sportradar.avvplayer.player.settings.AVVSettings
import ag.sportradar.avvplayer.task.http.AVVPostRequestData
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import org.parceler.Parcels

class PlayerActivity : AppCompatActivity() {

    lateinit var player: AVVPlayer
    lateinit var demoConfig: DemoConfig

    companion object {

        const val extraDemoConfig: String = "democonfig"

        fun start(context: Context, demoConfig: DemoConfig) {
            val intent = Intent(context, PlayerActivity.javaClass)
            intent.putExtra(extraDemoConfig, Parcels.wrap(demoConfig))
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        demoConfig = unwrapDemoConfig()

        /*README.md Instantiate the Player */
        player = AVVPlayerBuilder(this)
            .setPlayerContainer(findViewById(R.id.playerContainer))
            .build()

        /*README.md Start the video passing a Video Configuration to the player */
        getVideoConfig()?.let { config -> player.setup(config, getConfigAdaptation()) }
    }

    private fun getVideoConfig(): AVVPlayerConfigConvertible? {
        return when {
            demoConfig.configUrl.isNotEmpty() -> {
                AVVConfigUrl(demoConfig.configUrl)
            }
            demoConfig.streamUrl.isNotEmpty() -> {
                AVVConfig.Builder(0).streamUrl(demoConfig.streamUrl).build()
            }
            else -> null
        }
    }

    private fun unwrapDemoConfig(): DemoConfig {
        val demoConfigParcelable = intent.extras?.get(extraDemoConfig) as Parcelable
        return Parcels.unwrap(demoConfigParcelable) as DemoConfig
    }


    private fun getConfigAdaptation(): AVVConfigAdaptationCallback {
        /*Changing the player configuration */
        return object : AVVConfigAdaptationCallback() {

            override fun adaptConfig(config: AVVConfig) {
                /*Adding request headers to streamaccess*/
                config.streamUrlProviderInfo.requestData =
                    AVVPostRequestData(mapOf(Pair("authorization", "your auth token")))

                /*Changing autoplay behavior*/
                config.streamMetaData.autoPlay = true
            }
        }
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