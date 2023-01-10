package rekayasaagromarin.ews3swj.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.model.Parameter

class ParameterAdapter : RecyclerView.Adapter<ParameterAdapter.ViewHolder>() {
    private lateinit var listParameter: ArrayList<Parameter>
    private var onItemClickEdit: OnItemClickCallback? = null
    private var onItemClickDelete: OnItemClickCallback? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tv_name)
        val tvDescription : TextView = view.findViewById(R.id.tv_description)
        val btnEdit: ImageButton = view.findViewById(R.id.btn_parameter_edit)
        val btnDelete: ImageButton = view.findViewById(R.id.btn_parameter_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_parameter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parameter = listParameter[position]

        holder.tvTitle.text = parameter.name
        holder.tvDescription.text = parameter.description

        holder.btnEdit.setOnClickListener {
            onItemClickEdit?.onItemClicked(parameter)
        }

        holder.btnDelete.setOnClickListener {
            onItemClickDelete?.onItemClicked(parameter)
        }
    }

    override fun getItemCount(): Int {
        return listParameter.size
    }

    fun setListParameter(listParameter: ArrayList<Parameter>) {
        this.listParameter = listParameter
    }

    fun setOnItemClickCallback(onItemClickEdit: OnItemClickCallback, onItemClickDelete: OnItemClickCallback) {
        this.onItemClickEdit = onItemClickEdit
        this.onItemClickDelete = onItemClickDelete
    }

    interface OnItemClickCallback {
        fun onItemClicked(parameter: Parameter)
    }
}