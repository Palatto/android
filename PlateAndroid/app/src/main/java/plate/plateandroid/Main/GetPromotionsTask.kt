package plate.plateandroid.Main

import android.os.AsyncTask
import java.net.URL

class GetPromotionsTask(loadList: LoadListInterface): AsyncTask<String, Void, String>() {
    val loadList: LoadListInterface = loadList

    override fun doInBackground(params: Array<String>): String {
        val result: String = URL(params[0]).readText()
        return result
    }

    override fun onPostExecute(result: String) {
        loadList.tryToLoad(result)
    }
}