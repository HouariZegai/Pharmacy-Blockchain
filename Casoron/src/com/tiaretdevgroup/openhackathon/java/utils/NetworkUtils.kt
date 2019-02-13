package utils

import com.mashape.unirest.http.Unirest
import com.tiaretdevgroup.openhackathon.java.utils.Constants
import org.json.JSONArray

/**
 * Class to make common network calls
 */
object NetworkUtils {


    const val NODES = "${Constants.HOST}/api/pharmacy"

    fun getAllNodes(): Array<String> {
        val data = Unirest.get(NODES).asJson()
        print(NODES)
        val array = JSONArray(data.body.toString())
        val node = mutableListOf<String>()
        for (i in 0 until (array.length())) {
            node.add(array.getString(i))
        }
        return node.toTypedArray()
    }
}