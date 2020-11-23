package ag.sportradar.avvpldemo

import ag.sportradar.avvplayer.player.licencing.AVVLicenceCheckListener
import ag.sportradar.avvplayer.player.settings.AVVSettings
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*README.md Checking for a valid licence */
        AVVSettings.instance.initLicence(this,"your_licence_key",
            object : AVVLicenceCheckListener {
                override fun onLicenceValidated(valid: Boolean) {}
            });
    }
}