package lesehankoding.com.quranapps.Adapter

import android.view.ViewGroup
import lesehankoding.com.quranapps.DB.Surah
import lesehankoding.com.quranapps.Holder.JadwalShalatViewHolder
import lesehankoding.com.quranapps.Holder.SurahViewHolder
import lesehankoding.com.quranapps.Model.ModelSurah.DataItem
import lesehankoding.com.quranapps.Model.ModelWaktuShalat.DatetimeItem
import lesehankoding.com.quranapps.Model.ModelWaktuShalat.Times
import lesehankoding.com.quranapps.R

class AdapterJadwalShalat:BaseRecyclerAdapter<DatetimeItem,JadwalShalatViewHolder>() {

    private lateinit var actionListener: JadwalShalatViewHolder.OnActionListener

    fun setOnActionListener(listener: JadwalShalatViewHolder.OnActionListener) {
        actionListener = listener
    }

    override fun getItemResourceLayout(): Int = R.layout.item_waktushalat

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JadwalShalatViewHolder {
        return JadwalShalatViewHolder(getView(parent)).also {
            it.setOnActionListener(actionListener)
        }
    }

}
