package lesehankoding.com.quranapps.Ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lesehankoding.rumahmadani.PlanerPage.wrapper_api.wrapper.Wrapper
import kotlinx.android.synthetic.main.activity_surah_v2.*
import lesehankoding.com.quranapps.Adapter.AdapterSurah
import lesehankoding.com.quranapps.Base.BaseActivity
import lesehankoding.com.quranapps.Holder.SurahViewHolder
import lesehankoding.com.quranapps.Model.DataItem
import lesehankoding.com.quranapps.Model.ModelSurah
import lesehankoding.com.quranapps.R
import lesehankoding.com.quranapps.Utils.DividerItemDecoration
import lesehankoding.com.quranapps.databinding.ActivitySurahV2Binding


class SurahActivity : BaseActivity(){
    private lateinit var binding: ActivitySurahV2Binding
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
                    onError = {code:Int,message:String ->
                        hideProgressAnim()
                    }
                )
            },
            onRefresh = {
                getData()
            }
        )

    }

    private fun setupData(model: ModelSurah){
        val list: ArrayList<DataItem> = ArrayList()
        for (i in model.data?.indices!!) {
            val item = model.data[i]!!.copy()
            list.add(item)
        }
        setupAdapter(list)

    }

    private fun setupAdapter(listItem: ArrayList<DataItem>  = ArrayList()) {
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
                    putParcelable("data", listItem[view.adapterPosition])
                }
            }
        })
    }




}
