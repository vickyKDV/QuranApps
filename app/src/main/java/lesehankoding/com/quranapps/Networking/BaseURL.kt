package lesehankoding.com.quranapps.Networking

object BaseURL {
    fun surahURL():String{
        return "https://api.quran.sutanlab.id/surah"
    }

    fun ayatURL(number:String):String{
        return "https://api.quran.sutanlab.id/surah/$number"
    }
}