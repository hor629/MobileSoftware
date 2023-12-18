import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.coffeedairy.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var geocoder: Geocoder
    private lateinit var button: Button
    private lateinit var editText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.activity_map, container, false)
        editText = rootView.findViewById(R.id.editText)
        button = rootView.findViewById(R.id.button)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        geocoder = Geocoder(requireContext())

        button.setOnClickListener {
            searchLocation()
        }

        return rootView
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // 맵을 사용할 준비가 되면 호출되는 콜백

        // 예시로 와플하우스의 좌표로 이동
        val cafe = LatLng(37.54446143052143, 126.96852827945)
        mMap.addMarker(MarkerOptions().position(cafe).title("와플하우스"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cafe, 15f))
    }

    private fun searchLocation() {
        val str = editText.text.toString()
        var addressList: List<android.location.Address>? = null
        try {
            // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
            addressList = geocoder.getFromLocationName(
                str, // 주소
                10 // 최대 검색 결과 개수
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // 검색 결과가 있으면 첫 번째 결과를 사용
        if (addressList != null && addressList.isNotEmpty()) {
            val location = addressList[0]
            val latitude = location.latitude
            val longitude = location.longitude

            // 좌표(위도, 경도) 생성
            val point = LatLng(latitude, longitude)

            // 마커 생성
            val markerOptions = MarkerOptions()
            markerOptions.title("Search Result")
            markerOptions.position(point)

            // 검색 결과를 지도에 표시
            mMap.clear() // 기존 마커 제거
            mMap.addMarker(markerOptions)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15f))
        }
    }
}
