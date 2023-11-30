import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeedairy.InfoAdapter
import com.example.coffeedairy.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class InfoFragment : Fragment() {

    private lateinit var infoAdapter: InfoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.activity_info, container, false)

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.topLayout)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        infoAdapter = InfoAdapter(emptyList())
        recyclerView.adapter = infoAdapter

        // Dummy 데이터 로드
        GlobalScope.launch {
            val infoList = generateDummyData(requireContext())
            requireActivity().runOnUiThread {
               // infoAdapter.updateData(infoList)
            }
        }

        return rootView
    }

    private suspend fun generateDummyData(context: Context): List<Info> {
        val jsonString = loadJSONFromAsset(context, "dummyData.json")
        val jsonObject = JSONObject(jsonString)
        val infoList = mutableListOf<Info>()

        for (key in jsonObject.keys()) {
            val infoObject = jsonObject.getJSONObject(key)
            val info = Info(
                infoObject.getInt("id"),
                infoObject.getString("title"),
                infoObject.getString("content"),
                infoObject.getString("overview"),
                infoObject.getString("imagePath"),
                infoObject.getString("source")
            )
            infoList.add(info)
        }

        return infoList
    }

    private fun loadJSONFromAsset(context: Context, fileName: String): String {
        val json: String
        try {
            val inputStream = context.assets.open(fileName)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            val stringBuilder = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            inputStream.close()
            json = stringBuilder.toString()
        } catch (ex: Exception) {
            ex.printStackTrace()
            return ""
        }
        return json
    }
}

