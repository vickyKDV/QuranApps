package lesehankoding.com.quranapps.Ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jean.jcplayer.JcPlayerManagerListener
import com.example.jean.jcplayer.general.JcStatus
import com.example.jean.jcplayer.model.JcAudio
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lesehankoding.rumahmadani.PlanerPage.wrapper_api.wrapper.Wrapper
import com.pixplicity.easyprefs.library.Prefs
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_ayat.*
import kotlinx.android.synthetic.main.item_ayat.view.*
import lesehankoding.com.quranapps.Adapter.AdapterAyat
import lesehankoding.com.quranapps.Base.BaseActivity
import lesehankoding.com.quranapps.DB.Ayat
import lesehankoding.com.quranapps.DB.RealmHelper
import lesehankoding.com.quranapps.Model.*
import lesehankoding.com.quranapps.Model.ModelAyat.*
import lesehankoding.com.quranapps.R
import lesehankoding.com.quranapps.Utils.Constans
import lesehankoding.com.quranapps.Utils.Utils
import lesehankoding.com.quranapps.Utils.makeTextViewResizable
import lesehankoding.com.quranapps.databinding.ActivityAyatBinding
import lesehankoding.com.quranapps.databinding.BottomsheetTafsirAyatBinding
import lesehankoding.com.quranapps.databinding.BottomsheetTafsirBinding
import java.util.*
import kotlin.collections.ArrayList


class AyatPage : BaseActivity(), JcPlayerManagerListener {

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

    override fun onDestroy() {
        setResult(RESULT_OK)
        super.onDestroy()

    }

    override fun onBackPressed() {
        setResult(RESULT_OK)
        super.onBackPressed()
    }


    private fun getSurah(numberOfSurah: String){
        val listAyat : ArrayList<Ayat> = ArrayList()
        listAyat.addAll(realmHelper.getAyat(realm, numberOfSurah.toInt()))
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
        txtArti.text = intent.getStringExtra("arti")
        txtNamaSurahId.text = intent.getStringExtra("namaSurah")
        txtDiturunkan.text = if(intent.getStringExtra("diturunkan") == "Makkiyyah") "Diturunkan : Mekkah" else "Diturunkan : Madinah"
        txtTafsir.text =  intent.getStringExtra("tafsir")
        makeTextViewResizable(txtTafsir, 10, "selengkapnya") {

        }

        txtTafsir.setOnClickListener {
            dialogTafsir(
                intent.getStringExtra("namaSurah").toString(),
                intent.getStringExtra("arti").toString(),
                intent.getStringExtra("tafsir").toString()
            )
        }

        txtTitle.text = intent.getStringExtra("title")



        imbBack.setOnClickListener {
            onBackPressed()
        }

        getSurah(intent.getStringExtra("id")!!)

        jcplayer.jcPlayerManagerListener = this@AyatPage
    }


    private fun setupData(model: ModelAyatv3, idSurah: Int){
        model.apply {
            if (code == 200) {
//                val list: ArrayList<VersesItem> = ArrayList()
//                val jcAudios = ArrayList<JcAudio>()
//                jcAudios.clear()
//                var title: String?
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
                    realmHelper.addAyat(realm, ayat)
                    Log.d("rlBanner", "onResponse: $item")
//                    title = "${data.verses[i]?.text?.transliteration?.en} - ${item.translation?.id}"
//                    val audio = "https://cdn.islamic.network/quran/audio/64/ar.alafasy/${item.number?.inQuran}.mp3"
//                    jcAudios.add(JcAudio.createFromURL(title, "${audio}"))
                }
//                jcplayer.initPlaylist(jcAudios)
                val listAyat : ArrayList<Ayat> = ArrayList()
                listAyat.addAll(realmHelper.getAyat(realm, idSurah))
                setupAdapter(list = listAyat)



            }
        }
    }

    private fun setupAdapter(list: ArrayList<Ayat> = ArrayList()){
        val adapter = AdapterAyat(list)
        scrollable_content.adapter = adapter
        scrollable_content.layoutManager = LinearLayoutManager(
            this@AyatPage,
            RecyclerView.VERTICAL,
            false
        )

        val jcAudios = ArrayList<JcAudio>()
        jcAudios.clear()
        var title: String?

        for (i in list.indices){
            title = "${list[i].latin} - ${list[i].terjemahan}"
            val audio = "https://cdn.islamic.network/quran/audio/64/ar.alafasy/${list[i].numberInQuran}.mp3"
            jcAudios.add(JcAudio.createFromURL(title, "${audio}"))
        }

        jcplayer.initPlaylist(jcAudios)



        adapter.apply {
            adapter.SetOnItemClickListener(object :
                AdapterAyat.SetOnClickListener<Ayat> {
                override fun onShare(
                    view: View,
                    position: Int,
                    dataItem: Ayat
                ) {
                    Log.d("rlBanner", "onClick: Share"); }

                override fun onPlay(
                    view: View,
                    position: Int,
                    dataItem: Ayat
                ) {
                    jcplayer.playAudio(jcplayer.myPlaylist!![position])
                    jcplayer.createNotification(R.mipmap.ic_launcher_round)
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

                override fun onClickTafsir(view: View, position: Int, dataItem: Ayat) {
                    dialogTafsirAyat(
                        dataItem.arab.toString(),
                        dataItem.latin.toString(),
                        "Arti : \n${dataItem.arti} \n\n\nTafsir : \n${dataItem.tafsirAyat}"
                    )
                }

            })
        }
    }

    private fun dialogTafsirAyat(ayat: String, arti: String, tafsir: String) {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val binding: BottomsheetTafsirAyatBinding = BottomsheetTafsirAyatBinding.inflate(
            layoutInflater
        )

        binding.apply {
            txtTafsir.text = tafsir
            txtAyat.text = ayat
            txtArti.text = arti
        }

        bottomSheetDialog.behavior.skipCollapsed = false
        bottomSheetDialog.setContentView(binding.root)
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetDialog.behavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

        })
        bottomSheetDialog.show()
        Utils.setWhiteNavigationBar(bottomSheetDialog)
    }

    private fun dialogTafsir(title: String, subTitle: String, tafsir: String) {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val binding: BottomsheetTafsirBinding = BottomsheetTafsirBinding.inflate(
            layoutInflater
        )

        binding.apply {
            txtDeskripsi.text = tafsir
            txtTitle.text = title
            txtArti2.text = subTitle
        }

        bottomSheetDialog.setContentView(binding.root)
        bottomSheetDialog.show()
        Utils.setWhiteNavigationBar(bottomSheetDialog)
    }

    override fun onCompletedAudio() {

    }

    override fun onContinueAudio(status: JcStatus) {
    }

    override fun onJcpError(throwable: Throwable) {

    }

    override fun onPaused(status: JcStatus) {
    }

    override fun onPlaying(status: JcStatus) {
    }

    override fun onPreparedAudio(status: JcStatus) {
        Log.d("binding", "onPreparedAudio: ${status.jcAudio.copy()}")
        Log.d("binding", "Position: ${jcplayer.myPlaylist!![status.jcAudio.position!!]}")
        scrollable_content.scrollToPosition(status.jcAudio.position!!)
        Prefs.putString(
            Constans.PREF_LAST_AYAT_LISTEN,"Terakhir dibaca\nSurah ${binding.txtNamaSurahId.text}"
        )

        val number = status.jcAudio.position!!+1
        Prefs.putString(
            Constans.PREF_LAST_NUMBER_AYAT_LISTEN,"Ayat : $number"
        )
    }

    override fun onStopped(status: JcStatus) {
    }

    override fun onTimeChanged(status: JcStatus) {
    }


    fun makeMeBlink(view: View, duration: Int, offset: Int): View? {
        val anim: Animation = AlphaAnimation(0.0f, 1.0f)
        anim.duration = duration.toLong()
        anim.startOffset = offset.toLong()
        anim.repeatMode = Animation.REVERSE
        anim.repeatCount = Animation.INFINITE
        view.startAnimation(anim)
        return view
    }


}
