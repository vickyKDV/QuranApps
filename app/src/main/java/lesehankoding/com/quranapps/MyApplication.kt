package lesehankoding.com.quranapps

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.pixplicity.easyprefs.library.Prefs
import io.realm.Realm
import io.realm.RealmConfiguration


class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(this)
        AndroidNetworking.enableLogging(); // simply enable logging

        // Get a Realm instance for this thread
        Realm.init(applicationContext)
        val config = RealmConfiguration.Builder()
            .name("quranDb.realm")
            .schemaVersion(1)
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)


        Prefs.Builder()
            .setContext(this)
            .setMode(MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()

    }
}