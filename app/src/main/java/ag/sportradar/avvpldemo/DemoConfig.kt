package ag.sportradar.avvpldemo

import android.content.Context
import com.google.gson.Gson
import java.io.InputStream
import java.io.Serializable

class DemoConfig constructor(
    val licenceKey: String,
    val licenceDomain: String,
    val bundle:String,
    val streamUrl: String,
    val configUrl: String,
    val autoplay: Boolean,
    val authorizationToken: String
) : Serializable {

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

    fun validate(): List<String> {
        val errors = mutableListOf<String>()
        if (licenceKey.isEmpty())
            errors.add("- Provide a licenceKey.")

        if (streamUrl.isEmpty() && configUrl.isEmpty())
            errors.add("- Provide either a streamUrl or a OTT video config url")

        return errors
    }
}