package lesehankoding.com.quranapps.Ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.error.ANError;

import lesehankoding.com.quranapps.Model.ModelSurah;
import lesehankoding.com.quranapps.Networking.ApiService;
import lesehankoding.com.quranapps.Networking.CallbackApi;
import lesehankoding.com.quranapps.R;
import okhttp3.Response;

public class DetailPage extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surah_v2);
        createNetwork();
    }

    private void createNetwork() {
        ApiService apiService = new ApiService(ModelSurah.class, new CallbackApi() {
            @Override
            public void onResponse(Response okHttpResponse, Object response) {
                super.onResponse(okHttpResponse, response);
            }

            @Override
            public void onError(ANError anError) {
                super.onError(anError);
            }
        });


        apiService.getSurah("");

    }
}
