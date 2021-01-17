package lesehankoding.com.quranapps.Ui

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_surah_v2.*
import lesehankoding.com.quranapps.Adapter.AdapterSurah
import lesehankoding.com.quranapps.Helper.BaseActivity
import lesehankoding.com.quranapps.Holder.SurahViewHolder
import lesehankoding.com.quranapps.Interface.SetOnClickListener
import lesehankoding.com.quranapps.Model.DataItem
import lesehankoding.com.quranapps.Model.ModelSurah
import lesehankoding.com.quranapps.R
import lesehankoding.com.quranapps.Utils.DividerItemDecoration
import lesehankoding.com.quranapps.Utils.Utils


class SurahActivityRxJava : BaseActivity(){
    override val resourceLayout: Int = R.layout.activity_surah_v2
    private lateinit var adapterSurah: AdapterSurah

    override fun onViewReady(savedInstanceState: Bundle?) {
        getData()
    }


    fun getSurah(uRl: String): Observable<ModelSurah> {
        return Rx2AndroidNetworking.get(uRl)
            .build()
            .getObjectObservable(ModelSurah::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    private fun getData() {
        showLoading(false,"Loading",1)
        getSurah("https://api.quran.sutanlab.id/surah")
            .subscribe(object :
                Observer<ModelSurah?> {
                override fun onSubscribe(d: Disposable) {

                    Log.d("rlBanner", "onSubscribe: ${d.isDisposed}");
                }

                override fun onNext(t: ModelSurah) {
                    val list: ArrayList<DataItem> = ArrayList()
                    for (i in t.data!!.indices) {
                        val item = t.data[i]!!.copy()
                        list.add(item)
                    }
//                    val adapter = AdapterSurah(list)
//                    adapter = AdapterSurah().also { it.setOnActionListener(this@SurahActivityRxJava) }
//                    adapter = AdapterSurah().also {
//                        it.setOnActionListener(this@SurahActivityRxJava)
//                    }
                    val dividerItemDecoration = DividerItemDecoration(
                        this@SurahActivityRxJava,
                        DividerItemDecoration.VERTICAL,
                        false
                    )

                    //Initials Adapter
                    adapterSurah = AdapterSurah()
                    //Add List to adapter
                    adapterSurah.addAll(list)


                    //Setup recyclerview
                    scrollable_content.apply {
                        adapter = adapterSurah
                        addItemDecoration(dividerItemDecoration)
                        layoutManager = LinearLayoutManager(
                            this@SurahActivityRxJava,
                            RecyclerView.VERTICAL,
                            false
                        )
                    }

                    //Setup OnClickItem
                    adapterSurah.setOnActionListener(object :SurahViewHolder.OnActionListener{
                        override fun onItemClick(view: SurahViewHolder) {
                            val i = Intent(this@SurahActivityRxJava, AyatPage::class.java)
                            i.putExtra("data", list[ view.adapterPosition])
                            startActivity(i)
                        }

                    })


//                    adapter.SetOnItemClickListener(object : SetOnClickListener<DataItem> {
//                        override fun onClick(view: View, position: Int, dataItem: DataItem) {
//                            val i = Intent(this@SurahActivityRxJava, AyatPage::class.java)
//                            i.putExtra("data", list[position])
//                            startActivity(i)
//                        }
//
//                    })
                }

                override fun onError(e: Throwable) {
                    hideLoading()
                    Log.d("rlBanner", "onError: ${e.message}");
                }

                override fun onComplete() {
                    hideLoading()
                    Log.d("rlBanner", "onComplete");
                }

            })
    }





}
