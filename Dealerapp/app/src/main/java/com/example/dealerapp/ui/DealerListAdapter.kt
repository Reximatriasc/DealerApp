package com.example.dealerapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dealerapp.R
import com.example.dealerapp.model.Dealer


class DealerListAdapter(

    private val onItemClickListener: (Dealer) -> Unit
): ListAdapter<Dealer, DealerListAdapter.DealerViewHolder>(WORDS_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealerViewHolder {
        return DealerViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: DealerViewHolder, position: Int) {
        val Dealer = getItem(position)
        holder.bind(Dealer)
        holder.itemView.setOnClickListener {
            onItemClickListener(Dealer)
        }
    }
    class DealerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val NamaTextView: TextView = itemView.findViewById(R.id.NamaDealerTextView)
        private val AlamatTextView: TextView = itemView.findViewById(R.id.AlamatTextView)
        private val TelpTextView: TextView = itemView.findViewById(R.id.TelpTextView)

        fun bind(Dealer: Dealer?) {
            NamaTextView.text = Dealer?.Nama
            AlamatTextView.text = Dealer?.Alamat
            TelpTextView.text = Dealer?.Telp
        }

        companion object {
            fun create(parent: ViewGroup): DealerListAdapter.DealerViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_dealer, parent, false)
                return DealerViewHolder(view)
            }
        }

    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Dealer>() {
            override fun areItemsTheSame(oldItem: Dealer, newItem: Dealer): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Dealer, newItem: Dealer): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}