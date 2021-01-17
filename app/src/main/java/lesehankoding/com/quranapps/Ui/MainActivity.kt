package lesehankoding.com.quranapps.Ui

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.error.ANError
import kotlinx.android.synthetic.main.activity_surah_v2.*
import lesehankoding.com.quranapps.Adapter.AdapterSurah
import lesehankoding.com.quranapps.Interface.SetOnClickListener
import lesehankoding.com.quranapps.Model.*
import lesehankoding.com.quranapps.Networking.ApiService
import lesehankoding.com.quranapps.Networking.CallbackApi
import lesehankoding.com.quranapps.R
import lesehankoding.com.quranapps.Utils.DividerItemDecoration
import lesehankoding.com.quranapps.Utils.Utils.setWindowFlag
import okhttp3.Response


class MainActivity : AppCompatActivity() {

    var getSurah: ApiService<ModelSurah>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this@MainActivity,SurahActivity::class.java))
        finish()
        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }


//        setContentView(R.layout.activity_surah_v2)
//
//
//        getSurahV2()
//
//        Thread {
//            getSurah!!.getSurah("https://api.quran.sutanlab.id/surah")
//        }.start()




    }


    private fun getSurahV2(){
        var progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading")
        progressDialog.show()
        getSurah = ApiService(ModelSurah::class.java, object : CallbackApi<ModelSurah>() {
            override fun onResponse(okHttpResponse: Response?, response: ModelSurah?) {
                var list: ArrayList<DataItem> = ArrayList()
                for (i in response?.data!!.indices) {
                    val item = response.data[i]!!.copy()
                    list.add(item)
                }
                val adapter = AdapterSurah(list)
                scrollable_content.adapter = adapter
                val dividerItemDecoration = DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL,
                    false
                )
                scrollable_content.addItemDecoration(dividerItemDecoration)
                scrollable_content.layoutManager = LinearLayoutManager(
                    this@MainActivity,
                    RecyclerView.VERTICAL,
                    false
                )

                adapter.SetOnItemClickListener(object : SetOnClickListener<DataItem> {
                    override fun onClick(view: View, position: Int, dataItem: DataItem) {
                        val i = Intent(this@MainActivity, AyatPage::class.java)
                        i.putExtra("data",list[position])
                        startActivity(i)
                    }

                })
                progressDialog.dismiss()
            }

            override fun onError(anError: ANError?) {
                progressDialog.dismiss()
            }

        })

    }





}