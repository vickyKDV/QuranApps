package lesehankoding.com.quranapps.Interface

import android.view.View

interface SetOnClickListener<T> {
    fun onClick(view: View, position: Int, dataItem: T)
}