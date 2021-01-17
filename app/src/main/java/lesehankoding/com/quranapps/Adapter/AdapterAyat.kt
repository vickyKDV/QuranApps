package lesehankoding.com.quranapps.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_ayat.view.*
import lesehankoding.com.quranapps.Model.ModelAyat_v2.VersesItem
import lesehankoding.com.quranapps.R

class AdapterAyat(private val list: ArrayList<VersesItem>) : RecyclerView.Adapter<AdapterAyat.MyHolder>() {

    private var onClick: SetOnClickListener<VersesItem>? = null

    interface SetOnClickListener<T> {
        fun onShare(view: View, position: Int, dataItem: T)
        fun onPlay(view: View, position: Int, dataItem: T)
        fun onBookMark(view: View, position: Int, dataItem: T)
    }

    fun SetOnItemClickListener(onclick: SetOnClickListener<VersesItem>) {
        this.onClick = onclick
    }


    inner class MyHolder(itemview:View) : RecyclerView.ViewHolder(itemview) {

        fun bind(item: VersesItem){
            with(itemView){
                txtAyatAr.text = item.text?.arab
                txtAyatId.text = item.text?.transliteration?.en
                txtNumber.text = item.number?.inSurah.toString()
                txtArti.text = item.translation?.id

                imgShare.setOnClickListener { onClick!!.onShare(imgShare, adapterPosition, list[adapterPosition])  }
                imgPlay.setOnClickListener { onClick!!.onPlay(imgPlay, adapterPosition, list[adapterPosition])  }
                imgBookmark.setOnClickListener { onClick!!.onBookMark(imgBookmark, adapterPosition, list[adapterPosition])  }

//                setOnClickListener { v: View? -> }
            }




        }

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_ayat,parent,false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}