package lesehankoding.com.quranapps.Holder

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.item_surah.view.*

import lesehankoding.com.quranapps.Model.DataItem

class SurahViewHolder(itemView: View) : BaseItemViewHolder<DataItem>(itemView) {

    private lateinit var actionListener: OnActionListener
    private var dataItem:DataItem? = null

//    @SuppressLint("SetTextI18n")
//    override fun bind(data: DataItem?) {
//        Log.d("actionListener", "bind: ${data.toString()}");
//            this.dataItem = data
//            itemView.txtSurahId.text = "${dataItem?.name?.transliteration?.id}"
//            itemView.txtSurahArab.text = "${dataItem?.name?.jsonMemberShort}"
//            itemView.txtDiturunkan.text = "Diturunkan : ${data?.revelation?.id}"
//            itemView.txtNumber.text = dataItem?.number.toString()
//
//        itemView.setOnClickListener {
//            actionListener.onItemClick(this)
//        }
//    }

    @SuppressLint("SetTextI18n")
    override fun bind(item: DataItem?){
            itemView.txtSurahId.text = "${item?.name?.transliteration?.id}"
            itemView.txtSurahArab.text = "${item?.name?.jsonMemberShort}"
            itemView.txtDiturunkan.text = "Diturunkan : ${item?.revelation?.id}"
            itemView.txtNumber.text = item?.number.toString()

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