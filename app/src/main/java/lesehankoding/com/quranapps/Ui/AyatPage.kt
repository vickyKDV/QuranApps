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
import com.example.jean.jcplayer.model.JcAudio
import com.lesehankoding.rumahmadani.PlanerPage.wrapper_api.wrapper.Wrapper
import kotlinx.android.synthetic.main.activity_ayat.*
import kotlinx.android.synthetic.main.item_ayat.view.*
import lesehankoding.com.quranapps.Adapter.AdapterAyat
import lesehankoding.com.quranapps.Base.BaseActivity
import lesehankoding.com.quranapps.Model.*
import lesehankoding.com.quranapps.Model.ModelAyat.*
import lesehankoding.com.quranapps.R
import lesehankoding.com.quranapps.Utils.Utils
import lesehankoding.com.quranapps.databinding.ActivityAyatBinding
import java.util.*
import kotlin.collections.ArrayList


class AyatPage : BaseActivity()  {

    private lateinit var binding: ActivityAyatBinding
    override fun setLayout(): View {
        binding = ActivityAyatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        setupUI()
    }

    override fun onDestroyActivity() {

    }


    private fun getSurah(numberOfSurah: String){

        isConnectionReady(
            onReady = {
                Wrapper.getAyat(
                    numberOfSurah = numberOfSurah,
                    onLoading = {
                        showProgressAnim(label = "Load data...")
                    },
                    onSuccess = {
                        setupData(it)
                        hideProgressAnim()
                    },
                    onError = {code:Int,message:String ->
                        hideProgressAnim()
                    }
                )
            },
            onRefresh = {
                getSurah(numberOfSurah)
            }
        )

    }

    private fun setupUI(){
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

    private fun setupData(model:ModelAyatv3){
        model.apply {
            if (code == 200) {
                val list: ArrayList<VersesItem> = ArrayList()
                val jcAudios = ArrayList<JcAudio>()
                jcAudios.clear()
                var title: String?
                for (i in data!!.verses!!.indices) {
                    val item = data.verses!![i]!!.copy()
                    Log.d("rlBanner", "onResponse: ${item.toString()}");
                    list.add(item)
                    title =
                        "${data.verses[i]?.text?.transliteration?.en} - ${item.translation?.id}"
                    val audio = "https://cdn.islamic.network/quran/audio/64/ar.alafasy/${item.number?.inQuran}.mp3"
                    jcAudios.add(JcAudio.createFromURL(title, "${audio}"))
                }

                jcplayer.initPlaylist(jcAudios)
                setupAdapter(list = list)

            }
        }
    }

    private fun setupAdapter(list: ArrayList<VersesItem> = ArrayList()){
        val adapter = AdapterAyat(list)
        scrollable_content.adapter = adapter
        scrollable_content.layoutManager = LinearLayoutManager(this@AyatPage, RecyclerView.VERTICAL, false)
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
                    jcplayer.playAudio(jcplayer.myPlaylist!![position])
                }

                override fun onBookMark(
                    view: View,
                    position: Int,
                    dataItem: VersesItem
                ) {
                    Log.d(
                        "AYATPAGE",
                        "onClick: ${dataItem.number?.inSurah}"
                    );
                }

            })
        }
    }




}