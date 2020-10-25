package ru.bmstu.iu9.rk1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(private val clickListener: (String) -> Unit) : RecyclerView.Adapter<ListAdapter.ElemViewHolder> () {
    var data = listOf<ExchangeDataItem> ()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElemViewHolder {
        return ElemViewHolder.from(parent)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ElemViewHolder, position: Int) {
        val item = data[position]
        holder.setDataAndListener(item.conversionType,  clickListener)
    }

    class ElemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)  {
        private val textViewRow: TextView = itemView.findViewById(R.id.name)
        private val imageViewRow : TextView = itemView.findViewById(R.id.last_name)


        fun setDataAndListener(item : String, clickListener: (String) -> Unit) {
            textViewRow.text = item
//            imageViewRow.text = lastName
            itemView.setOnClickListener{clickListener(item)}
        }

        companion object {
            fun from(parent: ViewGroup) : ElemViewHolder {

                val context = parent.context
                val layoutIdForListItem = R.layout.list_item
                val inflater = LayoutInflater.from(context)
                val shouldAttachToParentImmediately = false
                val view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately)
                return ElemViewHolder(view)
            }
        }
    }
}