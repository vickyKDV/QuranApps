package com.lesehankoding.rumahmadani.PlanerPage.wrapper_api.api

import io.reactivex.Flowable
import lesehankoding.com.quranapps.Model.ModelAyat.ModelAyatv3
import lesehankoding.com.quranapps.Model.ModelSurah

interface ApiService {

    fun getSurah(): Flowable<ModelSurah>
    fun getAyat(numberOfSurah: String): Flowable<ModelAyatv3>
}