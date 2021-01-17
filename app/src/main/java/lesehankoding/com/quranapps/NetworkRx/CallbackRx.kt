package lesehankoding.com.quranapps.NetworkRx

import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable


interface CallbackRx<T> : Observer<T> {


    override fun onSubscribe(@NonNull d: Disposable)
//    {
////        showLoading(false,"Mohon menunggu",1)
//        Log.d("CallbackRx", "onSubscribe: " + d.isDisposed)
//    }

    override fun onNext(@NonNull t: T)

    override fun onError(@NonNull e: Throwable)
//    {
////        hideLoading()
//        Log.d("CallbackRx", "onError: ${e.message}");
//    }

    override fun onComplete()
//    {
////        hideLoading()
//        Log.d("CallbackRx", "onComplete");
//    }
}