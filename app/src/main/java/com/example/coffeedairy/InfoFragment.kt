import android.content.Context
import android.os.Bundle
import android.util.Log
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
            "title": "빈속에 커피, 어떤 문제가 생길까?",
            "content": "직장인의 활력소가 되어주는 모닝커피
아침마다 심한 피로감과 무기력증으로 출근이 힘든 직장인들의 정신을 깨우고 활력을 불어넣어주는 모닝커피. 그러나 빈속에 커피를 마시는 습관이 있다면 오히려 건강에 악영향을 줄 수 있기 때문에 이 습관에 제동을 걸도록 해야 합니다.\n\n빈속에 커피를 마시다가 속이 쓰린 증상을 느끼시는 분들이 계십니다. 이는 커피에 포함된 카페인이 소화를 돕는 위산을 과다 분비시켜 위벽을 자극하면서 유발되는 증상으로 심한 질병으로 이어질 수 있어 주의해야 합니다.\n",
            "overview": "매일 아침 커피로 하루를 시작하는 현대인들, 빈속 커피는 다양한 문제를 부릅니다!",
            "imagePath": "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzExMjJfMTcw%2FMDAxNzAwNjQwMTk5OTUx.GYKT5s6kP_QiZBC11fdo866lQWEPeMPIru5qHthI2e8g._tMy_GNRE2ljbWw9SoKoX_z8aCBDfQGdrYkPzqY82Rog.JPEG.dryuim7575%2F1_%25282%2529.jpg&type=sc960_832",
            "source": "web"
          },
          "info2": {
            "id": 2,
            "title": "커피를 몇 잔까지 마셔도 된다고요?",
            "content": "안전한 카페인 섭취량은 하루 최대 커피 3-5잔\n\n안전하게 섭취할 수 있는 카페인 최대 권장량은 임신 상태나 나이, 심장 건강 상태 등 여러 가지 요인에 따라 달라진다. 미국 식품의약국(FDA)은 대부분의 건강한 성인을 기준으로 하루 카페인 섭취량을 최대 400mg(커피 약 3~5잔)으로 제한해야 한다고 말한다. 미국산부인과학회는 임산부의 카페인 섭취량을 최대 200mg으로 할 것을 권고하고 있지만, 일부 전문가들은 임산부의 경우 카페인 섭취를 피하는 게 좋다고 말한다. ",
            "overview": "늘 마시는 커피, 하루 몇 잔까지 안전할까?",
            "imagePath": "https://cdn.kormedi.com/wp-content/uploads/2023/05/gettyimages-a11224594.jpg.webp",
            "source": "web"
          },
          "info3": {
            "id": 3,
            "title": "아메리카노와 콜드브루의 차이를 아시나요?",
            "content": "요즘 같이 무더위가 극성인 여름날 시원한 아이스 아메리카노 한 잔은 잠시나마 더위를 잊게 해줍니다. 더불어 콜드브루도 여름이면 인기 많은 메뉴입니다. 겉으로 보기엔 비슷해보이는 아이스 아메리카노와 콜드브루. 도대체 어떤 차이가 있길래 다른 메뉴로 구분하고 있는걸까요? 이번엔 콜드브루와 아이스 아메리카노의 차이에 대해 한번 알아보도록 하겠습니다. 콜드 브루(Cold Brew)는 이름 그대로 차가운 물에 추출하는 방식을 말합니다. 찬물에는 커피가 빠르게 우러나오지 않기 때문에 점적식은 8시간 이상, 침출식은 12~24시간 이상의 긴 시간동안 커피를 추출해냅니다.  ",
            "overview": "차이점을 알아봐요!(feat. 더치커피, 롱블랙)",
            "imagePath": "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdGwVT1%2FbtrIH6YXpgV%2FVYlRe9vMJ3IRXHfJ4aKpWk%2Fimg.jpg",
            "source": "web"
          },
          "info4": {
            "id": 4,
            "title": "커피 맛있게 만드는 방법! 궁금하시죠?",
            "content": "【맛있는 커피를 끓이려면】\n
섭씨 100° 이상의 물을 사용하면 커피 속 카페인이 변질되어 좋지 않은 쓴맛을 남기게 되고, 70° 이하의 물에서는 탄닌의 떫은맛을 남기게 된다. 끓이는 물의 온도는 끓기 직전, 즉 85~96°에서 불을 끄고, 일단 추출된 커피는 잔에 담을 때 대략 온도 60° 안팎이 가장 알맞다. 요즘 유행하는 원두커피 조리기에서 나오는 물이 펄펄 끓어 나오지 않는다고 불평했다면 다시 한번 생각해 보자.",
            "overview": "집에서 커피를 먹고 싶은데.. 맛이 없다면 클릭하세요!",
            "imagePath": "https://mblogthumb-phinf.pstatic.net/MjAyMjA3MTlfMTQg/MDAxNjU4MTk1MDg1NDg0.-9_LoHnk2yAivZYVvYeOD0GnRGHecebj2eIsWCzoZnsg.WAqxnnKn-mJMLCJ89MhaMn5WB7VFASM4phY19fIHhCEg.JPEG.eybs/brita_upgradeyourcoffee_coffee_barista_crema_landscape.jpg?type=w800",
            "source": "web"
          },
          "info5": {
            "id": 5,
            "title": "커피의 각성효과.. 과학적 원리는?",
            "content": "커피는 각성 효과가 있다. 각성 효과란 깨어 있는 상태, 즉 정신을 차릴 수 있게 해주는 효과를 뜻한다. 커피가 이런 효과를 갖는 것은 커피 속에 들어있는 카페인이 교감 신경 전달 과정에서 각성 작용(생리활성)과 밀접한 연관이 있는 c-AMP가 분해되는 과정을 막기 때문이다. 카페인은 c-AMP라는 물질의 분해를 일으키는 효소의 작용을 억제함으로써 각성 효과를 나타내고 일시적으로 집중이 잘 되며 피로감을 덜 느끼게 한다. 하지만 과량 섭취하면 불면증, 흥분, 손떨림, 신경과민 등의 증상이 나타날 수 있다. 그리고 카페인을 다량 섭취하면 심장의 교감 신경의 수용체가 흥분한 것처럼 심장이 두근거리고 고혈압이 유발될 수 있다. 민감한 사람의 경우에는 커피 한 잔에도 심장의 두근거림을 경험할 수 있다.",
            "overview": "커피를 많이 마시면 잠이 안 오는 이유!",
            "imagePath": "https://s3.ap-northeast-2.amazonaws.com/img.kormedi.com/news/article/__icsFiles/artimage/2017/07/28/c_km601/nap540_1.jpg",
            "source": "web"
          },
          "info6": {
            "id": 6,
            "title": "산미 나는 커피 vs 고소한 커피, 몸에 더 좋은 건?",
            "content": "최근 들어 산미 있는 원두를 활용하는 카페들이 늘어났다. 주문 전 산미 있는 커피를 원하는지, 고소한 커피를 원하는지 물어본다. 취향에 따라 선호하는 커피가 다를 수 있는데, 항산화·항염증 효과를 보고 싶다면 산미 있는 커피를 택하는 게 낫다.

어떤 원두에서 산미가 느껴지는 걸까? 원두 로스팅(커피콩을 볶는 것) 시간이 짧을수록 산미가 잘 느껴진다. 커피 원두에 들어있는 클로로겐산이 덜 파괴되기 때문이다. 클로로겐산은 열을 받으면 분해돼 로스팅을 오래 할수록 많이 파괴된다.",
            "overview": "커피의 산미란? 함께 알아보자!",
            "imagePath": "https://health.chosun.com/site/data/img_dir/2022/11/10/2022111001961_0.jpg",
            "source": "web"
          },
          "info7": {
            "id": 7,
            "title": "'디카페인' 커피… 마음 놓고 마셔도 될까?",
            "content": "카페에서 흔히 파는 메뉴 중 하나가 '디카페인 커피'다. 디카페인의 '디(de-)'는 영어에서 분리‧제거의 뜻을 나타내는 접두사로, 디카페인 커피는 카페인을 분리시킨 커피라는 뜻이다. 그런데 이 디카페인 커피는 어떻게 만들어지는 것일까? 카페인이 빠져서 몸에 이로울까?\n\n디카페인 커피는 카페인 양이 적어 건강에 이롭다고 생각할 수 있지만, 특정 질환 위험을 높인다는 연구 결과들이 있어 주의해야 한다. 그 예로는 콜레스테롤 수치를 높인다는 보고가 있다.",
            "overview": "차이점을 알아봐요!(feat. 더치커피, 롱블랙)",
            "imagePath": "https://health.chosun.com/site/data/img_dir/2022/07/25/2022072501681_0.jpg",
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

