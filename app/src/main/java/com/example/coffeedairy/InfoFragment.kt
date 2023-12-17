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
아침마다 심한 피로감과 무기력증으로 출근이 힘든 직장인들의 정신을 깨우고 활력을 불어넣어주는 모닝커피. 그러나 빈속에 커피를 마시는 습관이 있다면 오히려 건강에 악영향을 줄 수 있기 때문에 이 습관에 제동을 걸도록 해야 합니다. 

빈속에 커피가 유발하는 문제, 카페인에 의한 '속 쓰림'
빈속에 커피를 마시다가 속이 쓰린 증상을 느끼시는 분들이 계십니다. 이는 커피에 포함된 카페인이 소화를 돕는 위산을 과다 분비시켜 위벽을 자극하면서 유발되는 증상으로 심한 경우 만성 소화불량, 위염, 위궤양, 역류성식도염 등 다양한 위장질환으로 이어질 수 있어 주의해야 합니다. 

빈속에 커피가 유발하는 문제, '코르티솔 호르몬' 과다 분비
빈속에 커피를 마시면 스트레스가 더 누적되는 결과를 초래할 수도 있습니다. 이 역시 카페인이 원인으로, 카페인에 의해 각성 상태가 오래 지속되면 코르티솔 호르몬이 과다 분비되어 스트레스가 더 쉽게 쌓일 수 있고 두근거림, 두통 등을 호소할 수 있습니다. 

기상 후 모닝커피 보다는 '물' 섭취하기
모닝커피가 나쁜 것은 아니지만 아침에는 빈속일 가능성이 높기 때문에 커피보단 물을 섭취하시기 바랍니다. 물은 수면을 취하는 동안 부족해진 체내 수분 함유량을 보충해줄 뿐 아니라 소화를 도우며 몸에 활력을 불어넣어주는 효과가 있습니다.",
            "overview": "매일 아침 커피로 하루를 시작하는 현대인들, 빈속 커피는 다양한 문제를 부릅니다!",
            "imagePath": "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzExMjJfMTcw%2FMDAxNzAwNjQwMTk5OTUx.GYKT5s6kP_QiZBC11fdo866lQWEPeMPIru5qHthI2e8g._tMy_GNRE2ljbWw9SoKoX_z8aCBDfQGdrYkPzqY82Rog.JPEG.dryuim7575%2F1_%25282%2529.jpg&type=sc960_832",
            "source": "web"
          },
          "info2": {
            "id": 2,
            "title": "커피를 몇 잔까지 마셔도 된다고요?",
            "content": "안전한 카페인 섭취량은 하루 최대 커피 3-5잔

안전하게 섭취할 수 있는 카페인 최대 권장량은 임신 상태나 나이, 심장 건강 상태 등 여러 가지 요인에 따라 달라진다. 미국 식품의약국(FDA)은 대부분의 건강한 성인을 기준으로 하루 카페인 섭취량을 최대 400mg(커피 약 3~5잔)으로 제한해야 한다고 말한다. 미국산부인과학회는 임산부의 카페인 섭취량을 최대 200mg으로 할 것을 권고하고 있지만, 일부 전문가들은 임산부의 경우 카페인 섭취를 피하는 게 좋다고 말한다.

카페인을 너무 많이 섭취한다면

카페인 400mg이 많은 양처럼 보일 수 있겠지만, 하루에도 커피를 여러 잔 마시거나 에너지드링크를 즐겨 마신다면 이 수치를 넘기기는 생각보다 쉽다.

한 보고서에 의하면, 에너지드링크를 과다 섭취한 사람들이 발작이나 심박수 증가를 경험했다고 보고했지만 이것이 카페인 때문인지, 음료에 들어있는 다른 성분 때문인지는 분명하지 않다. 또한, 매일 400mg 이상의 카페인을 섭취한 여성들은 방광 기능이 불안정해질 위험이 증가한다는 연구 결과도 있다.

그 밖에 카페인을 너무 많이 섭취할 경우 불면, 불안, 초조, 짜증, 두통, 설사 등의 증상이 나타날 수 있다. 카페인이 소화관을 자극해 배탈을 경험할 수도 있다.

음료에 들어 있는 카페인 함량

사람들이 즐겨 마시는 음료에 들어있는 대략의 카페인 함량은 다음과 같다.

△ 커피 – 추출 방식과 원두 종류에 따라 한 잔(약 240ml)에 들어있는 카페인의 양은 70mg에서 140mg까지 다양 △ 에스프레소 – 30ml 기준 약 64mg △ 디카페인 커피 – 한 잔 기준 약 2mg △ 홍차 – 한 잔 기준 47~50mg (차 종류와 우려내는 시간에 따라 다름) △ 녹차 – 한 잔 기준 29~50mg (차 종류와 우려내는 시간에 따라 다름) △ 콜라 – 350ml 한 잔 기준 약 34mg △ 다이어트 콜라 – 350ml 한 잔 기준 약 46mg △ 펩시 – 350ml 한 잔 기준 약 38mg △ 마운틴듀 – 350ml한 잔 기준 약 91mg

카페인 갑자기 줄인다면

카페인 섭취를 갑자기 줄일 경우 금단현상이 나타날 수 있다. 가장 흔하게 나타나는 증상으로는 두통, 피로, 짜증, 불안, 떨림, 집중력 저하 등이 있다.

기운이 없고 집중력이 떨어질 때 카페인을 찾기 쉽지만 평소 운동을 꾸준히 하고, 햇빛을 충분히 쬐고, 적절한 양질의 수면을 하고, 균형 잡힌 식사를 한다면 카페인의 도움 없이도 활력을 유지할 수 있을 것이다.",
            "overview": "늘 마시는 커피, 하루 몇 잔까지 안전할까?",
            "imagePath": "https://cdn.kormedi.com/wp-content/uploads/2023/05/gettyimages-a11224594.jpg.webp",
            "source": "web"
          },
          "info3": {
            "id": 3,
            "title": "아메리카노와 콜드브루의 차이를 아시나요?",
            "content": "요즘 같이 무더위가 극성인 여름날 시원한 아이스 아메리카노 한 잔은 잠시나마 더위를 잊게 해줍니다. 더불어 콜드브루도 여름이면 인기 많은 메뉴입니다. 겉으로 보기엔 비슷해보이는 아이스 아메리카노와 콜드브루. 도대체 어떤 차이가 있길래 다른 메뉴로 구분하고 있는걸까요? 이번엔 콜드브루와 아이스 아메리카노의 차이에 대해 한번 알아보도록 하겠습니다. 콜드 브루(Cold Brew)는 이름 그대로 차가운 물에 추출하는 방식을 말합니다. 찬물에는 커피가 빠르게 우러나오지 않기 때문에 점적식은 8시간 이상, 침출식은 12~24시간 이상의 긴 시간동안 커피를 추출해냅니다. 오랜 시간이 걸리지만 한번 만들어놓으면 보관기간이 길고 시간이 지날수록 풍미가 숙성되는 장점이 있습니다. 아이스 아메리카노(Ice Americano) 역시 이름 그대로 아메리카노에 얼음을 넣은 커피입니다. 아메리카노는 이탈리아어로 'Caffè Americano' 뜻은 'American coffee'인데 미국식 커피가 유럽식 커피에 비해 옅은 농도이기 때문에 미국식 커피 스타일을 표현하는 단어가 됐다고 합니다.
 사실 미국인들은 아메리카노보다 카페라떼를 더 즐긴다고는 하지만 여하튼 이 아메리카노는 에스프레소에 물을 희석시켜 만든 커피이고 에스프레소는 곱게 간 원두에 고온 고압의 물을 투과시켜 추출한 커피입니다. 정리하자면 곱게 간 원두에 고온 고압의 물을 투과시켜 추출한 에스프레소를 얼음과 물로 희석시킨 커피가 아이스 아메리카노인 것입니다. 이처럼 추출 방식이 찬물에 오랜 시간 담가뒀다가 추출하는 방식과 고온 고압의 물을 짧은 시간에 투과시켜 추출하는 방식으로 전혀 다르기 때문에 여러가지 차이도 가지고 있습니다.
  콜드 브루는 위에서 설명한 바와 같이 차가운 물에 장시간 원두를 우려내 추출한 원액을 베이스로 하는 커피이기 때문에 아메리카노보다는 가격이 비싼 편입니다. 카페인 함량도 아메리카노보다 콜드 브루가 더 많은 편입니다. 카페인은 물에 접촉하는 시간이 길어질수록 성분이 높아지는 특성을 가지고 있어 추출 시간이 긴 콜드 브루의 카페인 양이 더 많다고 합니다. 콜드 브루의 평균 카페인 함량은 아메리카노의 평균 카페인 함량보다 1.5배정도 많습니다.
  여름이 되면 더욱더 소비량이 많아지는 아이스 아메리카노와 콜드브루. 커피가 몸에 좋은 항산화 물질도 가지고 있다고 하더라도 카페인 중독을 생각한다면 과다 섭취는 좋지 않을 것 같습니다. 건강한 몸을 지켜내는 선에서 다양하게 커피를 즐기시는 여러분이 되길 바라겠습니다.
  ",
            "overview": "차이점을 알아봐요!(feat. 더치커피, 롱블랙)",
            "imagePath": "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdGwVT1%2FbtrIH6YXpgV%2FVYlRe9vMJ3IRXHfJ4aKpWk%2Fimg.jpg",
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

