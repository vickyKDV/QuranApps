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
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_ayat.*
import kotlinx.android.synthetic.main.item_ayat.view.*
import lesehankoding.com.quranapps.Adapter.AdapterAyat
import lesehankoding.com.quranapps.Base.BaseActivity
import lesehankoding.com.quranapps.DB.Ayat
import lesehankoding.com.quranapps.DB.RealmHelper
import lesehankoding.com.quranapps.Model.*
import lesehankoding.com.quranapps.Model.ModelAyat.*
import lesehankoding.com.quranapps.Model.ModelSurah.DataItem
import lesehankoding.com.quranapps.R
import lesehankoding.com.quranapps.Utils.Utils
import lesehankoding.com.quranapps.databinding.ActivityAyatBinding
import java.util.*
import kotlin.collections.ArrayList


class AyatPage : BaseActivity()  {

    private lateinit var binding: ActivityAyatBinding
    var realmHelper = RealmHelper()
    var realm = Realm.getDefaultInstance()

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
        val listAyat : ArrayList<Ayat> = ArrayList()
        listAyat.addAll(realmHelper.getAyat(realm,numberOfSurah.toInt()))
        if(listAyat.isNotEmpty()) {
            Log.d("binding", "getSurah: Ayat is ready!!");
            setupAdapter(list = listAyat)
        }else {
            Log.d("binding", "getSurah: Ayat not ready!!");
            isConnectionReady(
                onReady = {
                    Wrapper.getAyat(
                        numberOfSurah = numberOfSurah.toString(),
                        onLoading = {
                            showProgressAnim(label = "Load data...")
                        },
                        onSuccess = {
                            setupData(it, numberOfSurah.toInt())
                            hideProgressAnim()
                        },
                        onError = { message: String ->
                            hideProgressAnim()
                            showDialogError("Opps...!!", message)
                        }
                    )
                },
                onRefresh = {
                    getSurah(numberOfSurah)
                }
            )
        }

    }

    private fun setupUI(){
//        val item: DataItem = intent?.getParcelableExtra("data")!!

//        putString("id", listItem[view.adapterPosition]?.id.toString())
//        putString("namaSurah", listItem[view.adapterPosition]?.surahNameID)
//        putString("arti", listItem[view.adapterPosition]?.surahArti.toString())
//        putString("diturunkan", listItem[view.adapterPosition]?.surahDiturunkan.toString())
//        putString("tafsir", listItem[view.adapterPosition]?.tafsirSurah.toString())
//        putString("title", listItem[view.adapterPosition]?.surahNameID.toString()+" - "+listItem[view.adapterPosition]?.jumlahSurah.toString())


        txtArti.text = intent.getStringExtra("arti")


        txtNamaSurahId.text = intent.getStringExtra("namaSurah")
        txtDiturunkan.text = if(intent.getStringExtra("diturunkan") == "Makkiyyah") "Diturunkan : Mekkah" else "Diturunkan : Madinah"
        txtTafsir.text =  intent.getStringExtra("tafsir")
        txtTitle.text = intent.getStringExtra("title")

        imbBack.setOnClickListener {
            onBackPressed()
        }

        getSurah(intent.getStringExtra("id")!!)
    }

    private fun setupData(model:ModelAyatv3,idSurah:Int){
        model.apply {
            if (code == 200) {
//                val list: ArrayList<VersesItem> = ArrayList()
                val jcAudios = ArrayList<JcAudio>()
                jcAudios.clear()
                var title: String?
                for (i in data!!.verses!!.indices) {
                    val item = data.verses!![i]!!.copy()
                    val ayat = Ayat()
                    ayat.numberInQuran = item.number?.inQuran
                    ayat.numberInSurah = item.number?.inSurah
                    ayat.idSurah = idSurah
                    ayat.jumlahAyat = model.data?.numberOfVerses
                    ayat.arab = item.text?.arab
                    ayat.latin = item.text?.transliteration?.en
                    ayat.terjemahan = item.translation?.id
                    ayat.tafsirAyat = item.tafsir?.id?.jsonMemberLong
                    ayat.arti = item.tafsir?.id?.jsonMemberShort
                    ayat.juz = item.meta?.juz
                    ayat.audio = "https://cdn.islamic.network/quran/audio/64/ar.alafasy/${item.number?.inQuran}.mp3"
                    realmHelper.addAyat(realm,ayat)

                    Log.d("rlBanner", "onResponse: ${item.toString()}");
//                    list.add(item)
                    title =
                        "${data.verses[i]?.text?.transliteration?.en} - ${item.translation?.id}"
                    val audio = "https://cdn.islamic.network/quran/audio/64/ar.alafasy/${item.number?.inQuran}.mp3"
                    jcAudios.add(JcAudio.createFromURL(title, "${audio}"))
                }

                jcplayer.initPlaylist(jcAudios)
                val listAyat : ArrayList<Ayat> = ArrayList()
                listAyat.addAll(realmHelper.getAyat(realm,idSurah))
                setupAdapter(list = listAyat)

            }
        }
    }

    private fun setupAdapter(list: ArrayList<Ayat> = ArrayList()){
        val adapter = AdapterAyat(list)
        scrollable_content.adapter = adapter
        scrollable_content.layoutManager = LinearLayoutManager(this@AyatPage, RecyclerView.VERTICAL, false)
        adapter.apply {
            adapter.SetOnItemClickListener(object :
                AdapterAyat.SetOnClickListener<Ayat> {
                override fun onShare(
                    view: View,
                    position: Int,
                    dataItem: Ayat
                ) {
                    Log.d("rlBanner", "onClick: Share");
                }

                override fun onPlay(
                    view: View,
                    position: Int,
                    dataItem: Ayat
                ) {
                    jcplayer.playAudio(jcplayer.myPlaylist!![position])
                }

                override fun onBookMark(
                    view: View,
                    position: Int,
                    dataItem: Ayat
                ) {
                    Log.d(
                        "AYATPAGE",
                        "onClick: ${dataItem.numberInQuran}"
                    );
                }

            })
        }
    }




}