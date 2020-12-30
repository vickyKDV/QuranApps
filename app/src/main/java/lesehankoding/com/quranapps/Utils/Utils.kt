package lesehankoding.com.quranapps.Utils

import android.app.Activity
import android.content.Context
import android.view.Window
import android.view.WindowManager
import com.huhx0015.hxaudio.audio.HXMusic
import com.huhx0015.hxaudio.model.HXMusicItem

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

    fun PlayQuran(item: HXMusicItem,context:Context) {
        HXMusic.logging(true)
        HXMusic.instance().initMusic(item,0,true,false,context)
//        HXMusic.music().load(url)
//            .looped(false)
//            .play(context)
    }
}