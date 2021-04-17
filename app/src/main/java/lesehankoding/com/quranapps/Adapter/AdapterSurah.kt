package lesehankoding.com.quranapps.Adapter

import android.view.ViewGroup
import lesehankoding.com.quranapps.DB.Surah
import lesehankoding.com.quranapps.Holder.SurahViewHolder
import lesehankoding.com.quranapps.Model.ModelSurah.DataItem
import lesehankoding.com.quranapps.R

class AdapterSurah:BaseRecyclerAdapter<Surah,SurahViewHolder>() {
    private lateinit var actionListener: SurahViewHolder.OnActionListener

    fun setOnActionListener(listener: SurahViewHolder.OnActionListener) {
        actionListener = listener
    }

    override fun getItemResourceLayout(): Int = R.layout.item_surah

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurahViewHolder {
        return SurahViewHolder(getView(parent)).also {
            it.setOnActionListener(actionListener)
        }
    }

}
