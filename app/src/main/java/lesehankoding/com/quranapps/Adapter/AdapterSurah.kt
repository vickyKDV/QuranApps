package lesehankoding.com.quranapps.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_surah.view.*
import lesehankoding.com.quranapps.Interface.SetOnClickListener
import lesehankoding.com.quranapps.Model.DataItem
import lesehankoding.com.quranapps.R

class AdapterSurah(private val list: ArrayList<DataItem>) : RecyclerView.Adapter<AdapterSurah.MyHolder>() {

    private var onClick: SetOnClickListener<DataItem>? = null

    fun SetOnItemClickListener(onclick: SetOnClickListener<DataItem>) {
        this.onClick = onclick
    }


    inner class MyHolder(itemview:View) : RecyclerView.ViewHolder(itemview) {

        fun bind(item: DataItem){
            itemView.txtSurahId.text = "${item.name?.transliteration?.id}"
            itemView.txtSurahArab.text = "${item.name?.jsonMemberShort}"
            itemView.txtDiturunkan.text = "Diturunkan : ${item.revelation?.id}"
            itemView.txtNumber.text = item.number.toString()

            itemView.setOnClickListener { v: View? -> onClick!!.onClick(itemView, adapterPosition, list[adapterPosition]) }


        }

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_surah,parent,false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}