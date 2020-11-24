package ag.sportradar.avvpldemo

import ag.sportradar.avvplayer.player.AVVPlayer
import ag.sportradar.avvplayer.player.licencing.AVVLicenceCheckListener
import ag.sportradar.avvplayer.player.settings.AVVSettings
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    lateinit var player: AVVPlayer
    lateinit var demoConfig: DemoConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        demoConfig = DemoConfig.fromAssets(this)

        val errors = demoConfig.validate()

        if (demoConfigIsValid(errors)) {
            /*README.md Checking for a valid licence */
            AVVSettings.instance.initLicence(this, demoConfig.licenceKey,
                object : AVVLicenceCheckListener {
                    override fun onLicenceValidated(valid: Boolean) {
                        PlayerActivity.start(this@MainActivity, demoConfig)
                        finish()
                    }
                })
        } else {
            val builder = StringBuilder()
                .append("Please update /assets/demo_config.json\n")
            errors.forEach {
                builder.append("$it\n")
            }
            findViewById<TextView>(R.id.message).text = builder.toString()
        }
    }

    private fun demoConfigIsValid(validate: List<String>) = validate.isEmpty()
}