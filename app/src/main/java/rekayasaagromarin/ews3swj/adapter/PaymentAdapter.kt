package rekayasaagromarin.ews3swj.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ItemPaymentBinding
import rekayasaagromarin.ews3swj.model.Payment

class PaymentAdapter : RecyclerView.Adapter<PaymentAdapter.ListViewHolder>() {

    private val payments = ArrayList<Payment>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setPayment(payment: ArrayList<Payment>) {
        payments.clear()
        payments.addAll(payment)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PaymentAdapter.ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_payment, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaymentAdapter.ListViewHolder, position: Int) {
        holder.bind(payments[position])
    }

    override fun getItemCount(): Int {
        return payments.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPaymentBinding.bind(itemView)
        fun bind(payment: Payment) {
            with(binding) {
                pyTvItemEmail.text = payment.userEmail

                if (payment.status == 0) {
                    pyTvItemStatus.setText(R.string.not_confirmed)
                    pyTvItemStatus.setBackgroundResource(R.drawable.bg_not_active)
                } else {
                    pyTvItemStatus.setText(R.string.confirmed)
                    pyTvItemStatus.setBackgroundResource(R.drawable.bg_active)
                }
            }

            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(payment)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(payment: Payment)
    }
}