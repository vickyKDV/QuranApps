package lesehankoding.com.quranapps.Holder

import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_ayat.view.*
import lesehankoding.com.quranapps.Model.ModelAyat_v2.VersesItem

class AyatViewHolder(itemView:View) : BaseItemViewHolder<VersesItem>(itemView) {

    private var onClick: AyatViewHolder.SetOnClickListener<VersesItem>? = null

    private var dataItem: VersesItem? = null
    override fun bind(data: VersesItem?) {
        with(itemView){
            txtAyatAr.text = dataItem?.text?.arab
            txtAyatId.text = dataItem?.text?.transliteration?.en
            txtNumber.text = dataItem?.number?.inSurah.toString()
            txtArti.text = dataItem?.translation?.id


            // TODO: 1/18/21  SET ONCLICK IMPLEMEN TATION

            imgShare.setOnClickListener { onClick!!.onShare(imgShare, adapterPosition, dataItem!!)  }
            imgPlay.setOnClickListener { onClick!!.onPlay(imgPlay, adapterPosition, dataItem!!)  }
            imgBookmark.setOnClickListener { onClick!!.onBookMark(imgBookmark, adapterPosition, dataItem!!)  }
        }
    }


    interface SetOnClickListener<T> {
        fun onShare(view: View, position: Int, dataItem: T)
        fun onPlay(view: View, position: Int, dataItem: T)
        fun onBookMark(view: View, position: Int, dataItem: T)
    }



}