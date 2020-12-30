package lesehankoding.com.quranapps

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.huhx0015.hxaudio.audio.HXMusic

class MyApplication() : Application() {

    override fun onCreate() {
        super.onCreate()

        AndroidNetworking.initialize(this@MyApplication)
        AndroidNetworking.enableLogging(); // simply enable logging




    }
}