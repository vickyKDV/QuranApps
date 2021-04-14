package lesehankoding.com.quranapps.Ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lesehankoding.rumahmadani.PlanerPage.wrapper_api.wrapper.Wrapper
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_surah_v2.*
import lesehankoding.com.quranapps.Adapter.AdapterSurah
import lesehankoding.com.quranapps.Base.BaseActivity
import lesehankoding.com.quranapps.DB.RealmHelper
import lesehankoding.com.quranapps.DB.Surah
import lesehankoding.com.quranapps.Holder.SurahViewHolder
import lesehankoding.com.quranapps.Model.ModelSurah.DataItem
import lesehankoding.com.quranapps.Model.ModelSurah.ModelSurah
import lesehankoding.com.quranapps.Utils.DividerItemDecoration
import lesehankoding.com.quranapps.databinding.ActivitySurahV2Binding


class SurahActivity : BaseActivity(){
    private lateinit var binding: ActivitySurahV2Binding
    var realmHelper = RealmHelper()
    var realm = Realm.getDefaultInstance()


    override fun setLayout(): View {
        binding = ActivitySurahV2Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        getData()
    }

    override fun onDestroyActivity() {

    }

    private fun getData(){
        if(!realmHelper.getCountSurah(realm)) {
            Log.d("binding", "getData: Surah Not Ready");
            isConnectionReady(
                onReady = {
                    Wrapper.getSurah(
                        onLoading = {
                            showProgressAnim(label = "Load data...")
                        },
                        onSuccess = {
                            setupData(it)
                            hideProgressAnim()

                        },
                        onError = { message: String ->
                            hideProgressAnim()
                            showDialogError("Opps...!!", message)
                        }
                    )
                },
                onRefresh = {
                    getData()
                }
            )
        }else{
            Log.d("binding", "getData: Surah Is Ready");
            setupAdapter(realmHelper.getSurah(realm))
        }

    }

    private fun setupData(model: ModelSurah){
        val list: ArrayList<DataItem> = ArrayList()
        for (i in model.data?.indices!!) {
            val item = model.data[i]!!.copy()
            val surah = Surah()
            surah.id = item.number
            surah.jumlahSurah = item.numberOfVerses
            surah.surahNameID = item.name?.transliteration?.id
            surah.surahNameAR = item.name?.jsonMemberShort
            surah.surahArti = item.name?.translation?.id
            surah.tafsirSurah = item.tafsir?.id
            surah.surahDiturunkan = item.revelation?.id
            realmHelper.addSurah(realm, surah)

            list.add(item)
        }


        Log.d("binding", "setupData: ${realmHelper.getSurah(realm)}");
        setupAdapter(realmHelper.getSurah(realm))

    }

    private fun setupAdapter(listItem: RealmResults<Surah>) {
        val dividerItemDecoration = DividerItemDecoration(
            this@SurahActivity,
            DividerItemDecoration.VERTICAL,
            false
        )
        val adapterSurah = AdapterSurah()
        //Add List to adapter
        adapterSurah.addAll(listItem)
        scrollable_content.apply {
            adapter = adapterSurah
            addItemDecoration(dividerItemDecoration)
            layoutManager = LinearLayoutManager(
                this@SurahActivity,
                RecyclerView.VERTICAL,
                false
            )
        }
        adapterSurah.setOnActionListener(object : SurahViewHolder.OnActionListener {
            override fun onItemClick(view: SurahViewHolder) {
                openActivity<AyatPage> {
                    Log.d(
                        "binding",
                        "onItemClick: ${listItem[view.adapterPosition]?.id.toString()}"
                    );
                    putString("id", listItem[view.adapterPosition]?.id.toString())
                    putString("namaSurah", listItem[view.adapterPosition]?.surahNameID)
                    putString("arti", listItem[view.adapterPosition]?.surahArti.toString())
                    putString(
                        "diturunkan",
                        listItem[view.adapterPosition]?.surahDiturunkan.toString()
                    )
                    putString("tafsir", listItem[view.adapterPosition]?.tafsirSurah.toString())
                    putString(
                        "title",
                        listItem[view.adapterPosition]?.surahNameID.toString() + " - " + listItem[view.adapterPosition]?.jumlahSurah.toString() + " Ayat"
                    )
                }
            }
        })
    }




}
