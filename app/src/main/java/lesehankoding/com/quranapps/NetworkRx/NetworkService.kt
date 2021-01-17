package lesehankoding.com.quranapps.NetworkRx

import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import lesehankoding.com.quranapps.Networking.CallbackApi

class NetworkService<T>(private val objectClass: Class<T>){

    //    private val dataClass: Class<T>? = null

    fun NetworkService(dataClass: Class<T>) {

    }

    fun getDataClass(): Class<T> {
        return objectClass
    }
    fun getSurah(uRl: String, callbackRx: CallbackRx<T>): Observable<T> {
        return Rx2AndroidNetworking.get(uRl)
            .build()
            .getObjectObservable(objectClass)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    }

    fun getSurah(uRl: String): Observable<T> {
        return Rx2AndroidNetworking.get(uRl)
            .build()
            .getObjectObservable(objectClass)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    }
}
