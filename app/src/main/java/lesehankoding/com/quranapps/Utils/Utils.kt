package lesehankoding.com.quranapps.Utils

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
        val win: Window = activity.window
        val winParams: WindowManager.LayoutParams = win.getAttributes()
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.setAttributes(winParams)
    }

    fun setWhiteNavigationBar(bottomSheetDialog: BottomSheetDialog) {
        val window = bottomSheetDialog.window
        if (window != null) {
            val metrics = DisplayMetrics()
            window.windowManager.defaultDisplay.getMetrics(metrics)
            val dimDrawable = GradientDrawable()
            // ...customize your dim effect here
            val navigationBarDrawable = GradientDrawable()
            navigationBarDrawable.shape = GradientDrawable.RECTANGLE
            navigationBarDrawable.setColor(Color.WHITE)
            val layers = arrayOf<Drawable>(dimDrawable, navigationBarDrawable)
            val windowBackground = LayerDrawable(layers)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                windowBackground.setLayerInsetTop(1, metrics.heightPixels)
            }
            window.setBackgroundDrawable(windowBackground)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getYearAndMonth(): String? {
        val df: DateFormat = SimpleDateFormat("yyyy-MM")
        return df.format(Calendar.getInstance().time)
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateToday(): String? {
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        return df.format(Calendar.getInstance().time)
    }

    fun hari(): String? {
        val today = Date()
        val df = SimpleDateFormat("H")
        val formattedDate = df.format(today)
        Log.d("Format Date", formattedDate)
        val jam = formattedDate.toInt()
        var kondisiHari = ""
        kondisiHari = if (jam >= 0 && jam <= 12) {
            "Selamat pagi"
        } else if (jam > 12 && jam <= 15) {
            "Selamat siang"
        } else if (jam > 12 && jam <= 19) {
            "Selamat sore"
        } else if (jam > 19 && jam <= 23) {
            "Selamat malam"
        } else {
            "Selamat pagi"
        }
        Log.d("Utils", "jam: $jam")
        return kondisiHari
    }

}