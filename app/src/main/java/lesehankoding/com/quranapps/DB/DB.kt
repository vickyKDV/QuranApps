package lesehankoding.com.quranapps.DB

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Surah (
    @PrimaryKey
    var id: Int? = null,
    var surahNameID:String? = null,
    var surahNameAR:String? = null,
    var surahArti:String? = null,
    var jumlahSurah:Int? = null,
    var tafsirSurah:String? = null,
    var surahDiturunkan:String? = null,

    ):RealmObject()

open class Ayat (

    @PrimaryKey
    var numberInQuran: Int? = null,

    var numberInSurah: Int? = null,
    var idSurah:Int? = null,
    var jumlahAyat:Int? = null,
    var tafsirAyat:String? = null,
    var arti:String? = null,
    var arab:String? = null,
    var latin:String? = null,
    var terjemahan:String? = null,
    var juz:Int? = null,
    var audio:String? = null,


    ):RealmObject()