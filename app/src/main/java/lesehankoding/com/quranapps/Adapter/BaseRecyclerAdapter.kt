package lesehankoding.com.quranapps.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import lesehankoding.com.quranapps.Holder.BaseItemViewHolder
import java.util.ArrayList

abstract class BaseRecyclerAdapter<Data, Holder : BaseItemViewHolder<Data>> : RecyclerView.Adapter<Holder>() {

    private var mData: ArrayList<Data> = ArrayList()
    private var mItemClickListener: OnItemClickListener? = null
    private var mLongItemClickListener: OnLongItemClickListener? = null

    val data: List<Data> = mData

    init {
        mData = ArrayList()
    }

    protected fun getView(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(getItemResourceLayout(), parent, false)
    }

    protected abstract fun getItemResourceLayout(): Int

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (mData[position] != null) {
            holder.bind(mData[position])
        }
    }

    override fun getItemCount(): Int {
        return try {
            mData.size
        } catch (e: Exception) {
            0
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.mItemClickListener = itemClickListener
    }

    interface OnLongItemClickListener {
        fun onLongItemClick(view: View, position: Int)
    }

    fun setOnLongItemClickListener(longItemClickListener: OnLongItemClickListener) {
        this.mLongItemClickListener = longItemClickListener
    }

    fun add(item: Data) {
        mData.add(item)
        notifyItemInserted(mData.size - 1)
    }

    fun addAll(items: List<Data>) {
        add(items)
    }

    fun add(item: Data, position: Int) {
        mData.add(position, item)
        notifyItemInserted(position)
    }

    fun add(items: List<Data>) {
        val size = items.size
        for (i in 0 until size) {
            mData.add(items[i])
        }
        notifyDataSetChanged()
    }

    fun addOrUpdate(item: Data) {
        val i = mData.indexOf(item)
        if (i >= 0) {
            mData[i] = item
            notifyItemChanged(i)
        } else {
            add(item)
        }
    }

    fun addOrUpdate(items: List<Data>) {
        val size = items.size
        for (i in 0 until size) {
            val item = items[i]
            val x = mData.indexOf(item)
            if (x >= 0) {
                mData[x] = item
            } else {
                add(item)
            }
        }
        notifyDataSetChanged()
    }

    fun getData(): ArrayList<Data>? {
        return mData
    }

    fun getData(position: Int): Data {
        return mData[position]
    }

    fun remove(position: Int) {
        if (position >= 0 && position < mData.size) {
            mData.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun remove(item: Data) {
        val position = mData.indexOf(item)
        remove(position)
    }

    fun clear() {
        mData.clear()
        notifyDataSetChanged()
    }
}