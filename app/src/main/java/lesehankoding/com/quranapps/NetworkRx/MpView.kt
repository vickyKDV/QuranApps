package lesehankoding.com.quranapps.NetworkRx

import androidx.annotation.StringRes

interface MvpView {

//    fun showLoading(isBackPressedCancelable: Boolean = true, message: String? = null, currentPage: Int? = 1)
    fun showLoading(isBackPressedCancelable: Boolean = true, message: String? = "Loading", currentPage: Int? = 1)

    fun showLoadingWithText(msg: String)

    fun hideLoading()

    fun showConfirmationDialog(message: String, title: String?, confirmCallback: () -> Unit)

    fun showConfirmationSingleDialog(message: String, confirmCallback: () -> Unit)

    fun showConfirmationDialog(@StringRes message: Int, @StringRes title: Int?, confirmCallback: () -> Unit)

    fun showAlertDialog(message: String?, title: String? = null)

    fun showAlertDialog(@StringRes message: Int, @StringRes title: Int? = null)

}