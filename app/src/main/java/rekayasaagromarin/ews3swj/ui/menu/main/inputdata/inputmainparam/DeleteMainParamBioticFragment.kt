package rekayasaagromarin.ews3swj.ui.menu.main.inputdata.inputmainparam

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import rekayasaagromarin.ews3swj.R

class DeleteMainParamBioticFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val bundle = arguments
            val position = bundle?.getInt(InputMainParamBioticActivity.EXTRA_POSITION)
            builder.setMessage(R.string.confirm_delete_biotic_data)
                .setPositiveButton(
                    R.string.delete
                ) { _, _ ->
                    (activity as InputMainParamBioticActivity).deleteBiotic(position!!)
                }
                .setNegativeButton(
                    R.string.cancel
                ) { _, _ -> }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}