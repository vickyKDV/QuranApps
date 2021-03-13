package lesehankoding.com.quranapps.Holder

import android.annotation.SuppressLint
import android.view.View
import kotlinx.android.synthetic.main.item_surah.view.*
import lesehankoding.com.quranapps.DB.Surah
import lesehankoding.com.quranapps.Model.ModelSurah.DataItem


class SurahViewHolder(itemView: View) : BaseItemViewHolder<Surah>(itemView) {

    private lateinit var actionListener: OnActionListener
    private var dataItem:DataItem? = null

    @SuppressLint("SetTextI18n")
    override fun bind(item: Surah?){
            itemView.txtSurahId.text = "${item?.surahNameID}"
            itemView.txtSurahArab.text = "${item?.surahNameAR}"
            itemView.txtDiturunkan.text = "Diturunkan : ${item?.surahDiturunkan}"
            itemView.txtNumber.text = item?.id.toString()
            itemView.setOnClickListener { actionListener.onItemClick(this) }


        }

    fun getData(): DataItem {
        return dataItem!!
    }

    fun setOnActionListener(listener: OnActionListener) {
        actionListener = listener
    }

    interface OnActionListener {
        fun onItemClick(view: SurahViewHolder)
    }
}