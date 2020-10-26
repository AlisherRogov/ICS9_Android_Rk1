package ru.bmstu.iu9.rk1

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.ElemViewHolder>() {
    var data = listOf<ExchangeDataItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElemViewHolder {
        val holder = ElemViewHolder.from(parent)
        holder.itemView.setOnClickListener {
            val bundle = bundleOf("time" to holder.getDate())
            Navigation.findNavController(holder.itemView)
                .navigate(R.id.action_hostFragment_to_secondFragment, bundle)
        }
        return holder
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ElemViewHolder, position: Int) {
        val item = data[position]
        holder.setDataAndListener(item.time, item.close)
    }

    class ElemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateTextViewRow: TextView = itemView.findViewById(R.id.date)
        private val dollarTextViewRow: TextView = itemView.findViewById(R.id.toDollar)
        private var ts: String = ""


        fun setDataAndListener(item: String, sth: Float) {
            dateTextViewRow.text = getDateTime(item)
            dollarTextViewRow.text = sth.toString()
            ts = item
        }

        fun getDate() = ts

        @SuppressLint("SimpleDateFormat")
        private fun getDateTime(s: String): String? {
            return try {
                val sdf = SimpleDateFormat("dd/MM/yyyy")
                val netDate = Date(s.toLong() * 1000)
                sdf.format(netDate)
            } catch (e: Exception) {
                e.toString()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ElemViewHolder {

                val context = parent.context
                val layoutIdForListItem = R.layout.list_item
                val inflater = LayoutInflater.from(context)
                val shouldAttachToParentImmediately = false
                val view =
                    inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately)
                return ElemViewHolder(view)
            }
        }
    }
}