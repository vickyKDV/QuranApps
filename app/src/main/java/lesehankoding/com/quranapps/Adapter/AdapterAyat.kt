package lesehankoding.com.quranapps.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_ayat.view.*
import lesehankoding.com.quranapps.DB.Ayat
import lesehankoding.com.quranapps.Model.ModelAyat.VersesItem
import lesehankoding.com.quranapps.R

class AdapterAyat(private val list: ArrayList<Ayat>) : RecyclerView.Adapter<AdapterAyat.MyHolder>() {

    private var onClick: SetOnClickListener<Ayat>? = null

    interface SetOnClickListener<T> {
        fun onShare(view: View, position: Int, dataItem: T)
        fun onPlay(view: View, position: Int, dataItem: T)
        fun onBookMark(view: View, position: Int, dataItem: T)
    }

    fun SetOnItemClickListener(onclick: SetOnClickListener<Ayat>) {
        this.onClick = onclick
    }


    inner class MyHolder(itemview:View) : RecyclerView.ViewHolder(itemview) {

        fun bind(item: Ayat){
            with(itemView){
                txtAyatAr.text = item.arab
                txtAyatId.text = item.latin
                txtNumber.text = item.numberInSurah.toString()
                txtArti.text = "${item.arti} \n ${item.tafsirAyat}"

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