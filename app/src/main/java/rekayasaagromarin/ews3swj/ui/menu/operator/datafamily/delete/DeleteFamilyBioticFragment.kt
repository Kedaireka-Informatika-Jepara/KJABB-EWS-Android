package rekayasaagromarin.ews3swj.ui.menu.operator.datafamily.delete

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.ui.menu.operator.datafamily.DataFamilyFragment

class DeleteFamilyBioticFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val bundle = arguments
            val id = bundle?.getInt(DataFamilyFragment.EXTRA_ID)
            builder.setMessage(R.string.confirm_delete_family_biotic)
                .setPositiveButton(
                    R.string.delete
                ) { _, _ ->
                    (parentFragment as DataFamilyFragment).deleteFamilyBiotic(id!!)
                }
                .setNegativeButton(
                    R.string.cancel
                ) { _, _ -> }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}