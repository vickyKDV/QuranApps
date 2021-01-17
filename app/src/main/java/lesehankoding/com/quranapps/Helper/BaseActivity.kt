package lesehankoding.com.quranapps.Helper


import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import lesehankoding.com.quranapps.Adapter.AdapterSurah
import lesehankoding.com.quranapps.Interface.SetOnClickListener
import lesehankoding.com.quranapps.Model.DataItem
import lesehankoding.com.quranapps.NetworkRx.MvpView
import lesehankoding.com.quranapps.R
import lesehankoding.com.quranapps.Ui.AyatPage
import lesehankoding.com.quranapps.Utils.DividerItemDecoration
import lesehankoding.com.quranapps.Utils.Utils

abstract class BaseActivity : AppCompatActivity(), MvpView {

    private var mContext: Context
        get() = this
        set(value) = TODO()

    var toolbar: Toolbar? = null
        protected set

    private var mInflater: LayoutInflater? = null
    private var mActionBar: ActionBar? = null
    private var progressDialog:ProgressDialog? = null
//    private lateinit var kProgressHUD: KProgressHUD

    private val baseFragmentManager: FragmentManager
        get() = super.getSupportFragmentManager()

    protected abstract val resourceLayout: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT in 19..20) {
            Utils.setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            Utils.setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }
        super.onCreate(savedInstanceState)
        setContentView(resourceLayout)
        mInflater = LayoutInflater.from(mContext)
        progressDialog = ProgressDialog(mContext)
//        kProgressHUD = KProgressHUD(mContext)
        onViewReady(savedInstanceState)
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

    override fun onBackPressed() {
        if (baseFragmentManager.backStackEntryCount > 0) {
            baseFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    protected fun showToast(message: String?) {
        if (!message.isNullOrBlank()) Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
    }

    protected abstract fun onViewReady(savedInstanceState: Bundle?)

//    override fun showLoading(
//        isBackPressedCancelable: Boolean,
//        message: String?,
//        currentPage: Int?
//    ) {
//
//    }

    override fun showLoadingWithText(msg: String) {
//        Dlg.Builder(mContext)
//            .setTitle(resources.getString(R.string.info))
//            .setDescription(msg)
//            .setDlgPosition(Dlg.Position.BOTTOM)
//            .setTextAligment(Gravity.CENTER)
//            .setCancelable(false)
//            .setCornerRadius(14f)
//            .addButtonVert(
//                resources.getString(R.string.ya), Dlg.Style.STYLE1
//            ) { dialog, _ -> dialog.dismiss() }.show()
    }


    override fun showLoading(
        isBackPressedCancelable: Boolean,
        message: String?,
        currentPage: Int?
    ) {
        progressDialog?.setTitle(message)
        progressDialog?.setCancelable(isBackPressedCancelable)
        progressDialog?.show()

    }

    override fun hideLoading() {
        progressDialog?.dismiss()
    }

    override fun showConfirmationDialog(
        message: String,
        title: String?,
        confirmCallback: () -> Unit
    ) {
        val confirmDialog = AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton(android.R.string.yes) { _, _ -> confirmCallback() }
            .setNegativeButton(android.R.string.no) { _, _ -> }
            .create()

        if (!title.isNullOrBlank()) confirmDialog.setTitle(title)

        confirmDialog.show()
    }

    override fun showConfirmationSingleDialog(message: String, confirmCallback: () -> Unit) {
        val confirmDialog = AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton(android.R.string.yes) { _, _ -> confirmCallback() }
            .create()

        confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        confirmDialog.show()
    }

    override fun showConfirmationDialog(message: Int, title: Int?, confirmCallback: () -> Unit) {
        var stringTitle: String? = null
        val stringMessage = getString(message)

        title?.let { stringTitle = getString(it) }
        showConfirmationDialog(stringMessage, stringTitle, confirmCallback)
    }

    override fun showAlertDialog(message: String?, title: String?) {
        if (!message.isNullOrBlank()) {
            val confirmDialog = AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok) { d, _ -> d.dismiss() }
                .create()

            if (!title.isNullOrBlank()) confirmDialog.setTitle(title)

            confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            confirmDialog.show()
        }
    }

    override fun showAlertDialog(message: Int, title: Int?) {
        var stringTitle: String? = null
        val stringMessage = getString(message)

        title?.let { stringTitle = getString(it) }
        showAlertDialog(stringMessage, stringTitle)
    }


//    val list: ArrayList<DataItem> = ArrayList()
//    for (i in t.data!!.indices) {
//        val item = t.data[i]!!.copy()
//        list.add(item)
//    }
//    val adapter = AdapterSurah(list)
//    scrollable_content.adapter = adapter
//    val dividerItemDecoration = DividerItemDecoration(
//        this@SurahActivityRxJava,
//        DividerItemDecoration.VERTICAL,
//        false
//    )
//    scrollable_content.addItemDecoration(dividerItemDecoration)
//    scrollable_content.layoutManager = LinearLayoutManager(
//    this@SurahActivityRxJava,
//    RecyclerView.VERTICAL,
//    false
//    )
//
//    adapter.SetOnItemClickListener(object : SetOnClickListener<DataItem> {
//        override fun onClick(view: View, position: Int, dataItem: DataItem) {
//            val i = Intent(this@SurahActivityRxJava, AyatPage::class.java)
//            i.putExtra("data", list[position])
//            startActivity(i)
//        }
//
//    })

    //RecycleView
//
//    fun finishLoad(recycler: BaseRecyclerView?) {
//        recycler?.let {
//            it.refreshComplete()
//            it.loadMoreComplete()
//        }
//    }
//
//    fun clearRecyclerView(recyclerView: BaseRecyclerView?) {
//        recyclerView?.destroy()
//    }
//
//    /**
//     * Custom Toolbar
//     * */
//
//    protected fun setupToolbarCustom(title: String?, elevation: Int, include: View) {
//        if (rlToolbar != null) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                rlToolbar.elevation = elevation.toFloat()
//            }
//            imgBack.isSelected = true
//            tvTitle.text = title
//            imgBack.setOnClickListener { finish() }
//        }
//    }
//
//    protected fun setupToolbarCustom(title: String?, hideAppName: Boolean, elevation: Int) {
//
//        if (hideAppName) {
//            tvCustom.visibility = View.GONE
//        }
//        if (rlToolbar != null) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                rlToolbar.elevation = elevation.toFloat()
//            }
//            imgBack.isSelected = true
//            tvTitle.text = title
//            imgBack.setOnClickListener { finish() }
//        }
//    }
//
//    protected fun setupToolbarCustom(title: String?, elevation: Int) {
//        if (rlToolbar != null) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                rlToolbar.elevation = elevation.toFloat()
//            }
//            imgBack.isSelected = true
//            tvTitle.text = title
//            imgBack.setOnClickListener { finish() }
//        }
//    }
//
//    protected fun setupToolbarCustomVariant(
//        title: String?,
//        elevation: Int,
//        onClickListener: View.OnClickListener
//    ) {
//        if (rlToolbar != null) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                rlToolbar.elevation = elevation.toFloat()
//            }
//            imgBack.isSelected = true
//            tvTitle.text = title
//            imgBack.setOnClickListener { v -> onClickListener.onClick(v) }
//        }
//    }

//    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
//        if (ev.action == MotionEvent.ACTION_DOWN) {
//            val v: View = currentFocus!!
//            if (v is EditText) {
//                val outRect = Rect()
//                v.getGlobalVisibleRect(outRect)
//                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
//                    v.clearFocus()
//                    val imm = ContextCompat.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
//                }
//            }
//        }
//        return super.dispatchTouchEvent(ev)
//    }

}
