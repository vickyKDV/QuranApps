package lesehankoding.com.quranapps.Holder

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.item_waktushalat.view.*
import lesehankoding.com.quranapps.Model.ModelWaktuShalat.DatetimeItem
import lesehankoding.com.quranapps.Model.ModelWaktuShalat.Times
import lesehankoding.com.quranapps.R
import lesehankoding.com.quranapps.Utils.Utils


class JadwalShalatViewHolder(itemView: View) : BaseItemViewHolder<DatetimeItem>(itemView) {

    private lateinit var actionListener: OnActionListener
    private var waktuShalat:DatetimeItem? = null

    @SuppressLint("SetTextI18n")
    override fun bind(item: DatetimeItem?){

            itemView.txtSubuh.text = item?.times?.fajr
            itemView.txtDzuhur.text = item?.times?.dhuhr
            itemView.txtAshar.text = item?.times?.asr
            itemView.txtMaghrib.text = item?.times?.maghrib
            itemView.txtIsya.text = item?.times?.isha
            itemView.txtImsak.text = item?.times?.imsak
            itemView.txtGeorgian.text = "${item?.date?.gregorian} M"
            itemView.txtHijriah.text = "${item?.date?.hijri} H"
            itemView.setOnClickListener { actionListener.onItemClick(this) }


//        if(item?.date?.gregorian.equals(Utils.getDateToday())){
//            Log.d("actionListener", "bind: ${Utils.getDateToday()}")
//            itemView.lnHeader.background = itemView.context.getDrawable(R.drawable.bg_gradient_red)
//        }else{
//            itemView.lnHeader.background = itemView.context.getDrawable(R.drawable.bg_gradient)
//        }

        }

    fun getData(): DatetimeItem {
        return waktuShalat!!
    }

    fun setOnActionListener(listener: OnActionListener) {
        actionListener = listener
    }

    interface OnActionListener {
        fun onItemClick(view: JadwalShalatViewHolder)
    }

}