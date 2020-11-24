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
            showErrorMessage(builder.toString())
        }
    }

    private fun showErrorMessage(message: String) {
        findViewById<TextView>(R.id.message).text = message
    }

    private fun checkLicence() {
        /*README.md Checking for a valid licence */
        val licence = AVVLicence.Builder(this, demoConfig.licenceKey)
            .domain(demoConfig.licenceDomain)
            .bundle(demoConfig.bundle)
            .listener(object : AVVLicenceCheckListener {
                override fun onLicenceValidated(valid: Boolean) {
                    if (valid) {
                        PlayerActivity.start(this@MainActivity, demoConfig)
                        finish()
                    } else {
                        showErrorMessage("You do not own a valid licence, please check licenceKey, licenceDomain and bundle in /assets/demo_config.json")
                    }
                }
            })
            .build()

        AVVSettings.instance.checkLicence(licence)
    }

    private fun demoConfigIsValid(validate: List<String>) = validate.isEmpty()
}