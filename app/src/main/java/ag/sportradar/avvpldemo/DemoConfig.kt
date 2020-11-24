package ag.sportradar.avvpldemo

import android.content.Context
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream

class DemoConfig private constructor(
    val licenceKey: String,
    val streamUrl: String,
    val configUrl: String,
    val autoplay: Boolean,
    val authToken: String
) {
    companion object {
        fun fromAssets(context: Context): DemoConfig {
            return Gson().fromJson(getJsonString(context), DemoConfig::class.java)
        }

        private fun getJsonString(context: Context): String {
            val inputStream: InputStream = context.assets.open("demo_config.json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            return String(buffer, Charsets.UTF_8)
        }
    }
}