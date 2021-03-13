package lesehankoding.com.quranapps.Base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import com.androidnetworking.error.ANError
import com.google.android.material.bottomsheet.BottomSheetDialog
import lesehankoding.com.easyprogressloading.EasyProgressAnim
import lesehankoding.com.quranapps.R
import lesehankoding.com.quranapps.Utils.Utils.setWhiteNavigationBar
import lesehankoding.com.quranapps.databinding.BottomDlgNointernetBinding
import org.json.JSONObject


/**
 * Base activity
 *
 * @constructor Create empty Base activity
 */
abstract class BaseActivity : AppCompatActivity() {

    private val TAG = BaseActivity::class.java.simpleName

    private var hud:EasyProgressAnim? = null
    private var toolBar: Toolbar? = null
    private var mInflater: LayoutInflater? = null
    private var mActionBar: ActionBar? = null


    private val baseFragmentManager: FragmentManager
        get() = super.getSupportFragmentManager()


    /**
     * Set layout
     *
     * @return
     */
    abstract fun setLayout() : View

    /**
     * On create activity
     *
     * @param savedInstanceState
     */
    abstract fun onCreateActivity(savedInstanceState: Bundle?)

    /**
     * On destroy activity
     *
     */
    abstract fun onDestroyActivity()

    /**
     * Hide navigation
     *
     * @return
     */
    fun hideNavigation():Int{
            return (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayout())
        hud = EasyProgressAnim(this)
        overridePendingTransition(R.anim.enter_from_bottom, R.anim.non)
        mInflater = LayoutInflater.from(this)
        onCreateActivity(savedInstanceState)
//        if(isInternetAvailable()) onMethodeFunction() else showDialogErrorInternet(
//            onRefresh = {
//                isInternetCheckAgain()
//            }
//        )
    }

    /**
     * Open
     *
     * @param T
     * @param isFinished
     */
    inline fun <reified T : Activity> Context.openActivity(
        isFinished: Boolean = false,
        extras: Bundle.() -> Unit = { }
    ) {
        val intent = Intent(this, T::class.java)
        intent.putExtras(Bundle().apply(extras))
        startActivity(intent)
        if(isFinished) finish()
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setTitle(title: Int) {
        super.setTitle(title)
        if (mActionBar != null)
            mActionBar!!.title = getString(title)
    }




    override fun onDestroy() {
        hideProgressAnim()
        hideKeyboard()
        super.onDestroy()
        onDestroyActivity()
    }

    /**
     * Show toast
     *
     * @param message
     * @param short
     */
    fun Context.showToast(message: String, short: Int = 0) {
        Toast.makeText(this, message, short).show()
    }

    override fun onBackPressed() {
        if (baseFragmentManager.backStackEntryCount > 0) {
            baseFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
            pendingTransition()
        }
    }


    override fun finish() {
        super.finish()
        pendingTransition()
    }



    private fun pendingTransition() {
        overridePendingTransition(R.anim.non, R.anim.exit_from_bottom)
    }

    /**
     * Hide keyboard
     *
     */
    fun hideKeyboard() {
        try {
            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        } catch (e: Exception) {
            Log.d(TAG, "Could not hide keyboard, window unreachable. " + e.toString())
        }
    }










    /**
     * Show progress anim
     *
     * @param label
     * @param message
     * @param cancelable
     */
    fun showProgressAnim(
        label: String = "Mohon tunggu",
        message: String = "",
        cancelable: Boolean = false
    ){
        if(!isFinishing){
            if(hud?.isShowing == false){
                hud!!.setLabel(label)
                    .setDeskripsi(message)
                    .setCancellable(cancelable)
                    .setFileName("loading2.json")
                    .setDimAmount(0.5f)
                    .setCornerRadius(14f)
                    .show()
            }
        }
    }

    /**
     * Hide progress anim
     *
     */
    fun hideProgressAnim(){
        if (hud?.isShowing == true) {
            hud?.dismiss()
        }
    }



    /**
     * Handle delay
     *
     * @param delay
     * @param process
     * @receiver
     */
    fun HandleDelay(delay: Long = 3000, process: () -> Unit) {
        Handler().postDelayed({
            process()
        }, delay)
        }


    /**
     * Show dialog error internet
     *
     * @param onRefresh
     */
    private fun showDialogErrorInternet(
        refreshClick: (() -> Unit)? = null
    ){
        val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val binding : BottomDlgNointernetBinding = BottomDlgNointernetBinding.inflate(layoutInflater)
        binding.btnViewSetting.setOnClickListener {
            val intent = Intent(Settings.ACTION_SETTINGS)
            startActivity(intent)
        }
        binding.btnRefresh.setOnClickListener {
            bottomSheetDialog.dismiss()
            refreshClick?.invoke()
        }
        bottomSheetDialog.setContentView(binding.root)
        bottomSheetDialog.setCancelable(false)
        bottomSheetDialog.setCanceledOnTouchOutside(false)
        bottomSheetDialog.show()
        setWhiteNavigationBar(bottomSheetDialog)
    }

    /**
     * Is connection ready
     *
     * @param onReady
     * @param onRefresh
     * @receiver
     */
    fun isConnectionReady(
        onReady: () -> Unit,
        onRefresh: (() -> Unit)? = null
    ){
        if(isInternetAvailable()){
            Log.d("TAG", "isConnectionReady: Inet Ready!!")
            onReady()
        }else{
            Log.d("TAG", "isConnectionReady: Inet Failed!!")
            hideProgressAnim()
            showDialogErrorInternet {
                onRefresh?.invoke()
            }
        }
    }

    /**
     * Is internet available
     *
     * @return
     */
    private fun isInternetAvailable(): Boolean {
        var result = false
        val connManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connManager.activeNetwork ?: return false
            val actNw =
                connManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connManager.run {
                connManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }

    fun showDialogError(
        titleError : String,
        deskripsiError : String,
        refreshClick: (() -> Unit)? = null
    ){
        val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val binding : BottomDlgNointernetBinding = BottomDlgNointernetBinding.inflate(layoutInflater)
        binding.apply {
            btnRefresh.setOnClickListener {
                bottomSheetDialog.dismiss()
                refreshClick?.invoke()
            }

            imgResource.setImageResource(R.drawable.no_inet)
            txtTitle.text = titleError
            txtDeskripsi.text = deskripsiError
            btnViewSetting.visibility = View.GONE
            btnRefresh.text = "Ulangi"
        }
        bottomSheetDialog.setContentView(binding.root)
        bottomSheetDialog.setCancelable(false)
        bottomSheetDialog.setCanceledOnTouchOutside(false)
        bottomSheetDialog.show()
        setWhiteNavigationBar(bottomSheetDialog)
    }

    fun showError(
        anError: Throwable,
        refreshClick: (() -> Unit)? = null
    ) {
        hideProgressAnim()
        when (anError) {
            is ANError -> {
                    if (anError.errorCode != 0) {
                        showToast(anError.errorDetail)
                    } else {
                        showDialogError(anError.errorDetail.toString(),anError.errorBody.toString()
                        ) {
                            refreshClick?.invoke()
                        }

                    }
            }else -> {
                if (anError is Exception) {
                    showToast(anError.message.toString())
                }
            }

        }
    }






}



