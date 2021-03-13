package lesehankoding.com.quranapps.DB

import io.realm.Realm
import io.realm.RealmResults
import lesehankoding.com.quranapps.Model.ModelAyat.ModelAyatv3
import lesehankoding.com.quranapps.Model.ModelSurah.ModelSurah


interface RealmHelperInterface {
    fun addSurah(realm: Realm, surah: Surah): Boolean
    fun addAyat(realm: Realm, ayat: Ayat): Boolean
    fun getSurah(realm: Realm): RealmResults<Surah>
    fun getCountSurah(realm: Realm): Boolean
    fun getAyat(realm: Realm, number: Int): RealmResults<Ayat>
}