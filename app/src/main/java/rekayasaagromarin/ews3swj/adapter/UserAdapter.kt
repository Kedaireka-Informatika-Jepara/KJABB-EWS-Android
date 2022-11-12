package rekayasaagromarin.ews3swj.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ItemUserBinding
import rekayasaagromarin.ews3swj.model.User

class UserAdapter : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    private val users = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setUser(user: ArrayList<User>) {
        users.clear()
        users.addAll(user)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserAdapter.ListViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserBinding.bind(itemView)
        fun bind(user: User) {
            with(binding) {
                tvItemName.text = user.name
                tvItemMembership.text = user.role
                if (user.isActive == 0) {
                    tvItemStatus.setText(R.string.not_active)
                    tvItemStatus.setBackgroundResource(R.drawable.bg_not_active)
                } else {
                    tvItemStatus.setText(R.string.active)
                    tvItemStatus.setBackgroundResource(R.drawable.bg_active)
                }
            }
            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: User)
    }
}