import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeedairy.R

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mapFragment, MapFragment())
                .commit()
        }
    }
}



