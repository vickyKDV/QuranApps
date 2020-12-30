package lesehankoding.com.quranapps.Networking;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;

import lesehankoding.com.quranapps.Model.ModelSurah;
import okhttp3.Response;

public class ApiService<T>  {
    private Class dataClass;
    private CallbackApi<T> requestListener;

    public ApiService(Class dataClass, CallbackApi<T> requestListener) {
        this.dataClass = dataClass;
        this.requestListener = requestListener;
    }

    public Class getDataClass() {
        return dataClass;
    }


//    public void getRequest(String url) {
//        ANRequest request = AndroidNetworking.get(url)
//                .build();
//        request.getAsOkHttpResponseAndObject(getDataClass(),requestListener);
//
//    }

    public void getSurah(String url) {
        ANRequest request = AndroidNetworking.get(url)
                .build();
        request.getAsOkHttpResponseAndObject(getDataClass(), requestListener);

    }

    public void postRequest(String url) {
        ANRequest request = AndroidNetworking.get(url).build();
        request.getAsOkHttpResponseAndObject(getDataClass(), requestListener);
    }


}
