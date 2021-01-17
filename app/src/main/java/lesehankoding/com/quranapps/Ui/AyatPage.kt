package lesehankoding.com.quranapps.Ui

import android.app.ProgressDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener
import com.example.jean.jcplayer.model.JcAudio
import kotlinx.android.synthetic.main.activity_ayat.*
import kotlinx.android.synthetic.main.item_ayat.view.*
import lesehankoding.com.quranapps.Adapter.AdapterAyat
import lesehankoding.com.quranapps.Model.*
import lesehankoding.com.quranapps.Model.ModelAyat_v2.*
import lesehankoding.com.quranapps.Model.ModelAyat_v2.Number
import lesehankoding.com.quranapps.R
import lesehankoding.com.quranapps.Utils.Utils
import okhttp3.Response
import java.util.*
import kotlin.collections.ArrayList


class AyatPage : AppCompatActivity()  {

//    var itemPlayer : ArrayList<HXMusicItem> = ArrayList()
//    var hxMusic = HXMusic.instance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT in 19..20) {
            Utils.setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            Utils.setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }
        setContentView(R.layout.activity_ayat)
        val item: DataItem = intent?.getParcelableExtra("data")!!

        Log.d("NetworkAcitivty", "onCreate: ${item.tafsir}")
        Log.d("NetworkAcitivty", "onCreate: " + item.number)
        Log.d("NetworkAcitivty", "onCreate: " + item.name!!.jsonMemberLong)

        txtArti.text = item.name.translation?.id
        txtNamaSurahId.text = item.name.transliteration?.id
        txtDiturunkan.text = if(item.revelation?.id == "Makkiyyah") "Diturunkan : Mekkah" else "Diturunkan : Madinah"
        txtTafsir.text = item.tafsir?.id
        txtTitle.text = "${item.name.transliteration?.id} - ${item.numberOfVerses} Ayat"

        imbBack.setOnClickListener {
            onBackPressed()
        }


    getSurah(item.number.toString())
    }




    private fun getSurah(number: String){
        var progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading")
        progressDialog.show()
        AndroidNetworking.get("https://api.quran.sutanlab.id/surah/$number")
            .build()
            .getAsOkHttpResponseAndObject(
                ModelAyatv3::class.java,
                    object : OkHttpResponseAndParsedRequestListener<ModelAyatv3> {
                        override fun onResponse(okHttpResponse: Response?, response: ModelAyatv3?) {
                            if (okHttpResponse!!.isSuccessful) {
                                if (response?.code == 200) {
                                    val list: ArrayList<VersesItem> = ArrayList()
//                                    val playList: ArrayList<JcAudio> = ArrayList()
                                    val jcAudios = ArrayList<JcAudio>()
                                    jcAudios.clear()
                                    var title: String?
                                    for (i in response.data!!.verses!!.indices) {
                                        val item = response.data.verses!![i]!!.copy()
                                        Log.d("rlBanner", "onResponse: ${item.toString()}");
                                        list.add(item)
                                        title =
                                                "${response.data.verses[i]?.text?.transliteration?.en} - ${item.translation?.id}"
                                        val audio = "https://cdn.islamic.network/quran/audio/64/ar.alafasy/${item.number?.inQuran}.mp3"
//                                                "https://cdn.islamic.network/quran/audio/64/ar.alafasy/${item.number?.inSurah}.mp3"
                                        jcAudios.add(JcAudio.createFromURL(title, "${audio}")
                                        )
                                    }

                                    jcplayer.initPlaylist(jcAudios)


                                    val adapter = AdapterAyat(list)
                                    scrollable_content.adapter = adapter
                                    scrollable_content.layoutManager = LinearLayoutManager(
                                            this@AyatPage,
                                            RecyclerView.VERTICAL,
                                            false
                                    )

                                    adapter.apply {
                                        adapter.SetOnItemClickListener(object :
                                                AdapterAyat.SetOnClickListener<VersesItem> {
                                            override fun onShare(
                                                    view: View,
                                                    position: Int,
                                                    dataItem: VersesItem
                                            ) {
                                                Log.d("rlBanner", "onClick: Share");
                                            }

                                            override fun onPlay(
                                                    view: View,
                                                    position: Int,
                                                    dataItem: VersesItem
                                            ) {
//                                                Log.d(
//                                                        "rlBanner",
//                                                        "onClick: ${dataItem.audio?.primary}"
//                                                );

//                                            jcplayer.createNotification(R.drawable.ic_quran)
                                                jcplayer.playAudio(jcplayer.myPlaylist!![position])
//                                                Utils.PlayQuran(this@AyatPage, dataItem.audio?.primary!!)
//                                                HXMusic.music().play(this@AyatPage)

                                            }

                                            override fun onBookMark(
                                                    view: View,
                                                    position: Int,
                                                    dataItem: VersesItem
                                            ) {
                                                Log.d(
                                                        "rlBanner",
                                                        "onClick: ${dataItem.number?.inSurah}"
                                                );
                                            }

                                        })
                                    }


                                }
                                progressDialog.dismiss()
                            } else {
                                progressDialog.dismiss()
                            }
                        }

                        override fun onError(anError: ANError?) {
                            progressDialog.dismiss()
                            Log.d("rlBanner", "onError: ${anError?.errorBody}")
                            Log.d("rlBanner", "onError: ${anError?.errorDetail}")
                            Log.d("rlBanner", "onError: ${anError?.errorCode}")
                        }

                    })
    }
}