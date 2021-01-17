package lesehankoding.com.quranapps.Networking;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;

import okhttp3.Response;

public class CallbackApi<T> implements OkHttpResponseAndParsedRequestListener<T> {
    @Override
    public void onResponse(Response okHttpResponse, T response) {
        if(okHttpResponse.isSuccessful()){

        }
    }

    public void onError(ANError anError) {

    }

//    @Override
//    public void onResponse(Response okHttpResponse, T response) {
//        if(okHttpResponse.isSuccessful()){
//
//        }
//    }
//    @Override
//    public void onError(ANError anError) {
//        new CallbackError(anError, view -> {
//            Log.d("NetworkAcitivty", "onError: clicked!!");
//        });
//    }

}
