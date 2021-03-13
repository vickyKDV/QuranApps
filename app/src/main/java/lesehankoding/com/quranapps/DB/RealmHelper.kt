package lesehankoding.com.quranapps.DB

import android.util.Log
import io.realm.Realm
import io.realm.RealmResults
import lesehankoding.com.quranapps.Model.ModelAyat.ModelAyatv3
import lesehankoding.com.quranapps.Model.ModelSurah.ModelSurah


class RealmHelper():RealmHelperInterface {
    override fun addSurah(realm: Realm, surah: Surah): Boolean {
        return try {
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(surah)
            realm.commitTransaction()
            true
        } catch (e: Exception) {
            println(e)
            false
        }
    }

    override fun addAyat(realm: Realm, ayat: Ayat): Boolean {
        return try {
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(ayat)
            realm.commitTransaction()
            true
        } catch (e: Exception) {
            println(e)
            false
        }
    }

    override fun getSurah(realm: Realm): RealmResults<Surah> {
        return realm.where(Surah::class.java).findAll()
    }

    override fun getCountSurah(realm: Realm): Boolean {
        return try {
            if(realm.where(Surah::class.java).count() >= 114) {
                true
            }else{
                false
            }
        } catch (e: Exception) {
            println(e)
            false
        }
    }

    override fun getAyat(realm: Realm, number: Int): RealmResults<Ayat> {
        return realm.where(Ayat::class.java).equalTo("idSurah", number).findAll()!!
    }


}