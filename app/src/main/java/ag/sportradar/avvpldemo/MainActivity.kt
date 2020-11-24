package ag.sportradar.avvpldemo

import ag.sportradar.avvplayer.player.AVVPlayer
import ag.sportradar.avvplayer.player.licencing.AVVLicence
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
            checkLicence()
        } else {
            val builder = StringBuilder()
                .append("Please update /assets/demo_config.json\n")
            errors.forEach {
                builder.append("$it\n")
            }
            findViewById<TextView>(R.id.message).text = builder.toString()
        }
    }

    private fun checkLicence() {
        /*README.md Checking for a valid licence */
        val licence = AVVLicence.Builder(this, demoConfig.licenceKey)
            .domain(demoConfig.licenceDomain)
            .bundle(demoConfig.bundle)
            .listener(object : AVVLicenceCheckListener {
                override fun onLicenceValidated(valid: Boolean) {
                    PlayerActivity.start(this@MainActivity, demoConfig)
                    finish()
                }
            })
            .build()

        AVVSettings.instance.checkLicence(licence)
    }

    private fun demoConfigIsValid(validate: List<String>) = validate.isEmpty()
}