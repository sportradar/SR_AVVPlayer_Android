package ag.sportradar.avvpldemo

import ag.sportradar.avvplayer.config.*
import ag.sportradar.avvplayer.player.AVVPlayer
import ag.sportradar.avvplayer.player.AVVPlayerBuilder
import ag.sportradar.avvplayer.task.http.AVVPostRequestData
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PlayerActivity : AppCompatActivity() {

    lateinit var player: AVVPlayer
    lateinit var demoConfig: DemoConfig

    companion object {

        const val extraDemoConfig: String = "democonfig"

        fun start(context: Context, demoConfig: DemoConfig) {
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra(extraDemoConfig, demoConfig)
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
        return intent.extras?.get(extraDemoConfig) as DemoConfig
    }


    private fun getConfigAdaptation(): AVVConfigAdaptationCallback {
        /*Changing the player configuration */
        return object : AVVConfigAdaptationCallback() {

            override fun adaptConfig(config: AVVConfig) {
                /*Adding request headers to streamaccess*/
                if (demoConfig.authorizationToken.isNotEmpty()) {
                    config.streamUrlProviderInfo.requestData =
                        AVVPostRequestData(
                            mapOf(
                                Pair(
                                    "authorization",
                                    demoConfig.authorizationToken
                                )
                            )
                        )
                }

                /*Changing autoplay behavior*/
                config.streamMetaData.autoPlay = demoConfig.autoplay
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