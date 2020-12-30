package lesehankoding.com.quranapps.Networking
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener
import lesehankoding.com.quranapps.Model.*
import okhttp3.Response

class CallbackList {

    fun getSurah(list:ArrayList<DataItem>): Boolean{
        var isSuccess = false
        AndroidNetworking.get("https://api.quran.sutanlab.id/surah")
            .build()
            .getAsOkHttpResponseAndObject(ModelSurah::class.java,
                object : OkHttpResponseAndParsedRequestListener<ModelSurah> {
                    override fun onResponse(okHttpResponse: Response?, response: ModelSurah?) {
                        Log.d("javaClass", "onResponse: ${response?.message}");
                        for (i in response?.data!!.indices) {
                            val item = DataItem(
                                number = response.data[i]?.number,
                                sequence = response.data[i]?.sequence,
                                numberOfVerses = response.data[i]?.numberOfVerses,

                                revelation = Revelation(
                                    en = response.data[i]?.revelation?.en,
                                    id = response.data[i]?.revelation?.id,
                                    arab = response.data[i]?.revelation?.arab
                                ),
                                name = Name(
                                    translation = Translation(
                                        en = response.data[i]?.name?.translation?.en,
                                        id = response.data[i]?.name?.translation?.id
                                    ),

                                    jsonMemberShort = response.data[i]?.name?.jsonMemberShort,
                                    jsonMemberLong = response.data[i]?.name?.jsonMemberLong,
                                    transliteration = Transliteration(
                                        en = response.data[i]?.name?.transliteration?.en,
                                        id = response.data[i]?.name?.transliteration?.id,
                                    )
                                ),
                                tafsir = Tafsir(
                                    id = response.data[i]?.tafsir?.id
                                )
                            )
                            list.add(item)

                        }
                        isSuccess = true
                    }

                    override fun onError(anError: ANError?) {
                        isSuccess = false
                    }

                })


        return isSuccess
    }
}