package lesehankoding.com.quranapps.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_ayat.*
import kotlinx.android.synthetic.main.item_ayat.view.*
import lesehankoding.com.quranapps.DB.Ayat
import lesehankoding.com.quranapps.Model.ModelAyat.VersesItem
import lesehankoding.com.quranapps.R
import lesehankoding.com.quranapps.Utils.makeTextViewResizable

class AdapterAyat(private val list: ArrayList<Ayat>) : RecyclerView.Adapter<AdapterAyat.MyHolder>() {

    private var onClick: SetOnClickListener<Ayat>? = null

    interface SetOnClickListener<T> {
        fun onShare(view: View, position: Int, dataItem: T)
        fun onPlay(view: View, position: Int, dataItem: T)
        fun onBookMark(view: View, position: Int, dataItem: T)
        fun onClickTafsir(view: View, position: Int, dataItem: T)
    }

    fun SetOnItemClickListener(onclick: SetOnClickListener<Ayat>) {
        this.onClick = onclick
    }


    inner class MyHolder(itemview:View) : RecyclerView.ViewHolder(itemview) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Ayat){
            with(itemView){
                txtAyatAr.text = item.arab
                txtAyatId.text = item.latin
                txtNumber.text = item.numberInSurah.toString()
                txtArti.text = "Arti : \n${item.arti} \n\n\nTafsir : \n${item.tafsirAyat}"

                imgShare.setOnClickListener { onClick!!.onShare(imgShare, adapterPosition, list[adapterPosition])  }
                imgPlay.setOnClickListener { onClick!!.onPlay(imgPlay, adapterPosition, list[adapterPosition])  }
                imgBookmark.setOnClickListener { onClick!!.onBookMark(imgBookmark, adapterPosition, list[adapterPosition])  }
                txtArti.setOnClickListener { onClick!!.onClickTafsir(imgBookmark, adapterPosition, list[adapterPosition])  }

//                makeTextViewResizable(txtArti, 10, "selengkapnya") {
//                }

//                setOnClickListener { v: View? -> }
            }




        }

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_ayat,parent,false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(list[position])
//        holder.itemView.txtAyatAr.text = list[position].arab
//        holder.itemView.txtAyatId.text = list[position].latin
//        holder.itemView.txtNumber.text = list[position].numberInSurah.toString()
//        holder.itemView.txtArti.text = "Arti : \n${list[position].arti} \n\n\nTafsir : \n${list[position].tafsirAyat}"
//
//        holder.itemView.imgShare.setOnClickListener { onClick!!.onShare(holder.itemView.imgShare, position, list[position])  }
//        holder.itemView.imgPlay.setOnClickListener { onClick!!.onPlay(holder.itemView.imgPlay, position, list[position])  }
//        holder.itemView.imgBookmark.setOnClickListener { onClick!!.onBookMark(holder.itemView.imgBookmark, position, list[position])  }
//        holder.itemView.txtArti.setOnClickListener { onClick!!.onClickTafsir(holder.itemView.imgBookmark, position, list[position])  }
//
//        makeTextViewResizable(holder.itemView.txtArti, 10, "selengkapnya") {
//        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}