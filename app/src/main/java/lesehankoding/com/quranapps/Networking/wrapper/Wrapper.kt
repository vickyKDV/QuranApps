package com.lesehankoding.rumahmadani.PlanerPage.wrapper_api.wrapper

import com.androidnetworking.error.ANError
import com.lesehankoding.rumahmadani.PlanerPage.wrapper_api.repository.Repository
import com.lesehankoding.rumahmadani.PlanerPage.wrapper_api.utils.ErrorHanlder
import com.lesehankoding.rumahmadani.PlanerPage.wrapper_api.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import lesehankoding.com.quranapps.Model.ModelAyat.ModelAyatv3
import lesehankoding.com.quranapps.Model.ModelSurah.ModelSurah
import lesehankoding.com.quranapps.Model.ModelWaktuShalat.ModelWaktuShalat

object Wrapper {

    private val schedulerProvider: SchedulerProvider by lazy {
        SchedulerProvider()
    }

    private val repository: Repository by lazy {
        Repository()
    }

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }



    fun getSurah(
        onLoading: (() -> Unit),
        onSuccess: ((ModelSurah) -> Unit),
        onError: ((String) -> Unit)
    ){
        onLoading()
        compositeDisposable.add(
            repository.getSurah()
                .compose(schedulerProvider.ioToMainFlowableScheduler())
                .subscribe (
                    { resultData -> onSuccess(resultData) },
                    {throwable -> ErrorHanlder.errorHandle(throwable as ANError,onError)}
                )
        )


    }

    fun getAyat(
        numberOfSurah:String,
        onLoading: (() -> Unit),
        onSuccess: ((ModelAyatv3) -> Unit),
        onError: (( String) -> Unit)
    ){
        onLoading()
        compositeDisposable.add(
            repository.getAyat(numberOfSurah = numberOfSurah)
                .compose(schedulerProvider.ioToMainFlowableScheduler())
                .subscribe (
                    { resultData -> onSuccess(resultData) },
                    {throwable -> ErrorHanlder.errorHandle(throwable as ANError, onError)}
                )
        )


    }

    fun getWaktuShalat(
        latitude:String,
        longitude:String,
        onLoading: (() -> Unit),
        onSuccess: ((ModelWaktuShalat) -> Unit),
        onError: (( String) -> Unit)
    ){
        onLoading()
        compositeDisposable.add(
            repository.getWaktuShalat(latitude, longitude)
                .compose(schedulerProvider.ioToMainFlowableScheduler())
                .subscribe (
                    { resultData -> onSuccess(resultData) },
                    {throwable -> ErrorHanlder.errorHandle(throwable as ANError, onError)}
                )
        )


    }

   

}