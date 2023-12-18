import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.coffeedairy.MapFragment
import com.example.coffeedairy.R
import com.example.coffeedairy.RequestPermissionsUtil
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

class sMapActivity : AppCompatActivity() {

    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var placesClient: PlacesClient
    private lateinit var textViewLocation: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        textViewLocation = findViewById(R.id.textViewLocation)

        // 위치 권한 확인 및 요청
        checkAndRequestLocationPermissions()

        // 위치 정보 얻기
        getLocation()

        // Google Places API 초기화
        Places.initialize(applicationContext, "AIzaSyCsP0Bel3OHSwPu7NwSkFPrJQLSCMhNdMg")
        placesClient = Places.createClient(this)

        // 장소 자동완성 프래그먼트 추가
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // 장소가 선택되었을 때의 동작
                val selectedLocation = place.latLng
                textViewLocation.text = "Selected Place: ${place.name}, Latitude: ${selectedLocation?.latitude}, Longitude: ${selectedLocation?.longitude}"
                MapFragment.setMarkerAtLocation(selectedLocation)
            }

            override fun onError(status: Status) {
                // 오류가 발생했을 때의 동작
                Toast.makeText(this@MapActivity, "Error: ${status.statusMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    val currentLocation = LatLng(it.latitude, it.longitude)
                    MapFragment.setMarkerAtLocation(currentLocation)
                    textViewLocation.text = "Latitude: ${it.latitude}, Longitude: ${it.longitude}"
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to get location", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkAndRequestLocationPermissions() {
        val requestPermissionsUtil = RequestPermissionsUtil(this)
        requestPermissionsUtil.checkAndRequestLocationPermissions(this, REQUEST_LOCATION_PERMISSION)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            // 권한 요청 결과를 확인하고 위치 정보 가져오기 시도
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                getLocation()
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
