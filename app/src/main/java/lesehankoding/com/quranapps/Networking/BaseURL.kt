package lesehankoding.com.quranapps.Networking

import lesehankoding.com.quranapps.Utils.Utils

object BaseURL {
    fun surahURL():String{
        return "https://api.quran.sutanlab.id/surah"
    }

    fun ayatURL(number:String):String{
        return "https://api.quran.sutanlab.id/surah/$number"
    }

    fun waktuShalat(latitude:String,longitude:String):String{
//        return "https://api.pray.zone/v2/times/today.json?latitude=$latitude&longitude=$longitude&elevation=333"
        return "https://api.pray.zone/v2/times/month.json?latitude=$latitude&longitude=$longitude&elevation=333&month="+Utils.getYearAndMonth()
    }

}