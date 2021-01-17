package lesehankoding.com.quranapps.Helper


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import lesehankoding.com.quranapps.R

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    @LayoutRes open val contentView: Int = R.layout.activity_surah_v2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeButtonEnabled(true)
    }

//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        when (item?.itemId) {
//            android.R.id.home -> finish()
//        }
//        return super.onOptionsItemSelected(item)
//    }
}
