import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeedairy.InfoAdapter
import com.example.coffeedairy.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class InfoFragment : Fragment() {

    private lateinit var infoAdapter: InfoAdapter
    private lateinit var allInfoList: List<Info>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 프래그먼트 레이아웃 설정
        val rootView = inflater.inflate(R.layout.activity_info, container, false)

        // 리사이클러뷰 설정
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.infoRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        infoAdapter = InfoAdapter(emptyList(), requireContext())
        recyclerView.adapter = infoAdapter

        // 더미 데이터 로드
        GlobalScope.launch {
            allInfoList = generateDummyData()
            requireActivity().runOnUiThread {
                infoAdapter.updateData(allInfoList)
            }
        }

        // 뷰 반환
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 검색 기능 구현
        val searchEditText = view.findViewById<EditText>(R.id.searchEditText)
//        val infoRecyclerView = view.findViewById<RecyclerView>(R.id.infoRecyclerView)

        // 어댑터 설정
        val infoAdapter = InfoAdapter(emptyList(), requireContext())
//        infoRecyclerView.adapter = infoAdapter

        // 검색 기능 추가
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                infoAdapter.filterResults(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private suspend fun generateDummyData(): List<Info> {
        // 더미 데이터를 직접 하드코딩
        val jsonString = """
        {
          "info1": {
            "id": 1,
            "title": "빈속에 커피, 어떤 문제가 생길까?",
            "content": "직장인의 활력소가 되어주는 모닝커피... (중략)",
            "overview": "매일 아침 커피로 하루를 시작하는 현대인들, 빈속 커피는 다양한 문제를 부릅니다!",
            "imagePath": "https://search.pstatic.net/.../1_%25282%2529.jpg&type=sc960_832",
            "source": "web"
          },
          "info2": {
            "id": 2,
            "title": "커피를 몇 잔까지 마셔도 된다고요?",
            "content": "안전한 카페인 섭취량은 하루 최대 커피 3-5잔... (중략)",
            "overview": "늘 마시는 커피, 하루 몇 잔까지 안전할까?",
            "imagePath": "https://cdn.kormedi.com/.../gettyimages-a11224594.jpg.webp",
            "source": "web"
          },
          "info3": {
            "id": 3,
            "title": "아메리카노와 콜드브루의 차이를 아시나요?",
            "content": "요즘 같이 무더위가 극성인 여름날 시원한 아이스 아메리카노 한 잔은... (중략)",
            "overview": "차이점을 알아봐요!(feat. 더치커피, 롱블랙)",
            "imagePath": "https://img1.daumcdn.net/.../img.jpg",
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

}

