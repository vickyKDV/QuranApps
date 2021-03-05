package lesehankoding.com.quranapps.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_surah.view.*
import lesehankoding.com.quranapps.Holder.SurahViewHolder
import lesehankoding.com.quranapps.Interface.SetOnClickListener
import lesehankoding.com.quranapps.Model.DataItem
import lesehankoding.com.quranapps.R

class AdapterSurah:BaseRecyclerAdapter<DataItem,SurahViewHolder>() {

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
