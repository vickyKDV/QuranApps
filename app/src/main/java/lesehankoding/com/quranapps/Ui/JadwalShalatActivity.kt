package lesehankoding.com.quranapps.Ui

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.lesehankoding.rumahmadani.PlanerPage.wrapper_api.wrapper.Wrapper
import lesehankoding.com.quranapps.Adapter.AdapterJadwalShalat
import lesehankoding.com.quranapps.Base.BaseActivity
import lesehankoding.com.quranapps.Holder.JadwalShalatViewHolder
import lesehankoding.com.quranapps.Model.ModelSurah.DataItem
import lesehankoding.com.quranapps.Model.ModelWaktuShalat.DatetimeItem
import lesehankoding.com.quranapps.Model.ModelWaktuShalat.ModelWaktuShalat
import lesehankoding.com.quranapps.Model.ModelWaktuShalat.Times
import lesehankoding.com.quranapps.R
import lesehankoding.com.quranapps.Utils.PermissionUtils
import lesehankoding.com.quranapps.Utils.Utils
import lesehankoding.com.quranapps.databinding.JadwalSholatActivityBinding
import java.util.*
import kotlin.collections.ArrayList


class JadwalShalatActivity : BaseActivity() {
    private val binding by lazy {
        JadwalSholatActivityBinding.inflate(layoutInflater)
    }
    private val LOCATION_PERMISSION_REQ_CODE = 1000;

    override fun setLayout(): View {
        return binding.root
    }

    override fun onCreateActivity(savedInstanceState: Bundle?) {

        binding.apply {
            cvHeader.visibility  = View.GONE
        }
        when {
            PermissionUtils.isAccessFineLocationGranted(this) -> {
                when {
                    PermissionUtils.isLocationEnabled(this) -> {
                        setUpLocationListener()
                    }
                    else -> {
                        PermissionUtils.showGPSNotEnabledDialog(this)
                    }
                }
            }
            else -> {
                PermissionUtils.requestAccessFineLocationPermission(
                    this,
                    LOCATION_PERMISSION_REQ_CODE
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun setUpLocationListener() {
        showProgressAnim(label = "Load data...")
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        // for getting the current location update after every 2 seconds with high accuracy
        val locationRequest = LocationRequest().setInterval(1000000).setFastestInterval(1000000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                @SuppressLint("SetTextI18n")
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    for (location in locationResult.locations) {
                        getWaktuShalat(location.latitude.toString(), location.longitude.toString())
                    }
                    // Few more things we can do here:
                    // For example: Update the location of user on server
                }
            },
            Looper.myLooper()
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQ_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    when {
                        PermissionUtils.isLocationEnabled(this) -> {
                            setUpLocationListener()
                        }
                        else -> {
                            PermissionUtils.showGPSNotEnabledDialog(this)
                        }
                    }
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.location_permission_not_granted),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onDestroyActivity() {
    }



    private fun getWaktuShalat(latitude: String, longitude: String){
        isConnectionReady(
            onReady = {
                Wrapper.getWaktuShalat(
                    latitude = latitude,
                    longitude = longitude,
                    onLoading = {

                    },
                    onSuccess = {
                        setupData(it)
                        hideProgressAnim()
                    },
                    onError = { message: String ->
                        hideProgressAnim()
                        showDialogError("Opps...!!", message)
                    }
                )
            },
            onRefresh = {
                getWaktuShalat(latitude, longitude)
            }
        )
    }

    @SuppressLint("SetTextI18n")
    private fun setupData(it: ModelWaktuShalat) {
        Log.d("binding", "setupJadwalShalat: ${it}");
        if(it.code == 200 && it.status.equals("OK")){

            val list: ArrayList<DatetimeItem> = ArrayList()
            list.clear()
            for (i in it.results?.datetime!!.indices) {
                val item = it.results.datetime[i]!!.copy()
                list.add(item)
                Log.d("binding", "setupData: $item");
//                binding.txtTanggal.text = it.results.datetime[i]!!.date.toString()

                Log.d("binding", "setupData: $i ${it.results.datetime.size-1}");
                if(i == it.results.datetime.size-1){
                    Log.d("binding", "setupData: $i ${it.results.datetime.size}");
                    Log.d("binding", "tanggal: ${Utils.getDateToday()}");
                    Log.d("binding", "tanggal: ${it.results.datetime[i]!!.date?.gregorian}");
                        binding.apply {
                            txtSubuh.text = it.results.datetime[i]!!.times?.fajr
                            txtDzuhur.text = it.results.datetime[i]!!.times?.dhuhr
                            txtAshar.text = it.results.datetime[i]!!.times?.asr
                            txtMaghrib.text = it.results.datetime[i]!!.times?.maghrib
                            txtIsya.text = it.results.datetime[i]!!.times?.isha
                            txtImsak.text = it.results.datetime[i]!!.times?.imsak
                            cvHeader.visibility  = View.VISIBLE
                        }

                }
            }


//            val dividerItemDecoration = DividerItemDecoration(
//                this@JadwalShalatActivity,
//                DividerItemDecoration.HORIZONTAL,
//                false
//            )

            val adapterJadwalShalat = AdapterJadwalShalat()
            adapterJadwalShalat.addAll(list.reversed())
            binding.scrollableContent.apply {
                layoutManager = GridLayoutManager(this@JadwalShalatActivity,2)
                adapter = adapterJadwalShalat
//                layoutManager = LinearLayoutManager(this@JadwalShalatActivity, RecyclerView.HORIZONTAL,false)
            }
            adapterJadwalShalat.setOnActionListener(object :
                JadwalShalatViewHolder.OnActionListener {
                override fun onItemClick(view: JadwalShalatViewHolder) {

                }
            })
        }else{
            Log.d("binding", "setupJadwalShalat: Jadwal shalat tidak ditemukan");
        }
    }



}
