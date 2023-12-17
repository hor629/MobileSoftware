import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeedairy.Info
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

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.infoRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        infoAdapter = InfoAdapter(emptyList(), requireContext())
        recyclerView.adapter = infoAdapter

        // Dummy 데이터 로드
        GlobalScope.launch {
            val infoList = generateDummyData(requireContext())
            requireActivity().runOnUiThread {
                infoAdapter.updateData(infoList)
            }
        }

        return rootView
    }

    private suspend fun generateDummyData(context: Context): List<Info> {
//        val jsonString = loadJSONFromAsset(context, "dummyData.json")

        /*json 파일 연결이 잘 안 되어서 InfoFragment에 하드코딩 하는 형식으로 바꿨어*/
        val jsonString = """
        {
          "info1": {
            "id": 1,
            "title": "Coffee Shop A",
            "content": "Description of Coffee Shop A.",
            "overview": "Overview of Coffee Shop A.....",
            "imagePath": "path_to_image1.jpg",
            "source": "web"
          },
          "info2": {
            "id": 2,
            "title": "Coffee Shop B",
            "content": "Description of Coffee Shop B.",
            "overview": "Overview of Coffee Shop B.....",
            "imagePath": "path_to_image2.jpg",
            "source": "web"
          },
          "info3": {
            "id": 3,
            "title": "Coffee Shop C",
            "content": "Description of Coffee Shop C.",
            "overview": "Overview of Coffee Shop C.....",
            "imagePath": "path_to_image3.jpg",
            "source": "web"
          }
        }
    """.trimIndent()

        Log.d("JSON_DATA", jsonString)

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
        return try {
            val inputStream = context.resources.assets.open(fileName)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            val stringBuilder = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            inputStream.close()
            stringBuilder.toString()
        } catch (ex: Exception) {
            ex.printStackTrace()
            ""
        }
    }
}
