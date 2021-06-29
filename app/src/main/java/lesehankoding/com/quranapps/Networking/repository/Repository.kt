package com.lesehankoding.rumahmadani.PlanerPage.wrapper_api.repository

import com.lesehankoding.rumahmadani.PlanerPage.wrapper_api.api.ApiService
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Flowable
import lesehankoding.com.quranapps.Model.ModelAyat.ModelAyatv3
import lesehankoding.com.quranapps.Model.ModelSurah.ModelSurah
import lesehankoding.com.quranapps.Model.ModelWaktuShalat.ModelWaktuShalat
import lesehankoding.com.quranapps.Networking.BaseURL

class Repository: ApiService {
    override fun getSurah(): Flowable<ModelSurah> {
        return Rx2AndroidNetworking.get(BaseURL.surahURL())
            .build()
            .getObjectFlowable(ModelSurah::class.java)
    }

    override fun getAyat(numberOfSurah: String): Flowable<ModelAyatv3> {
        return Rx2AndroidNetworking.get(BaseURL.ayatURL(numberOfSurah))
            .build()
            .getObjectFlowable(ModelAyatv3::class.java)
    }

    override fun getWaktuShalat(latitude: String, longitude: String): Flowable<ModelWaktuShalat> {
        return Rx2AndroidNetworking.get(BaseURL.waktuShalat(latitude, longitude))
            .build()
            .getObjectFlowable(ModelWaktuShalat::class.java)
    }

}