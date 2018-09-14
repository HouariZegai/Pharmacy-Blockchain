package utils

import com.mashape.unirest.http.Unirest
import org.json.JSONArray

/**
 * Class to make common network calls
 */
object NetworkUtils {

    const val BASE_URL = ""
    const val NODES = ""

    fun getAllNodes(): Array<String> {
        val data = Unirest.get("$BASE_URL/$NODES").asJson()
        val array = JSONArray(data.body.toString())
        val node = mutableListOf<String>()
        for (i in 0 until (array.length())) {
            node.add(array.getString(i))
        }
        return node.toTypedArray()
    }
}