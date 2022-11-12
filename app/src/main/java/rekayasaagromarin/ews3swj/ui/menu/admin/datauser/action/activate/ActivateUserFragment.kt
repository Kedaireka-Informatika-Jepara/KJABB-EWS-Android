package rekayasaagromarin.ews3swj.ui.menu.admin.datauser.action.activate

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.ui.menu.admin.datauser.action.UserActionActivity

class ActivateUserFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val isActivate = (activity as UserActionActivity).isActivate()
            val stringActivate = if (isActivate == 0) "activate" else "deactivate"
            val builder = AlertDialog.Builder(it)

            Log.d("ACTIVATE", "onResponse: isActivate : $isActivate")

            builder.setMessage(getString(R.string.confirm_activate_user, stringActivate))
                .setPositiveButton(
                    if (isActivate == 0) R.string.activate else R.string.deactivate
                ) { _, _ ->
                    if (isActivate == 0)
                        (activity as UserActionActivity).activateUser()
                    else
                        (activity as UserActionActivity).deactivateUser()
                }
                .setNegativeButton(
                    R.string.cancel
                ) { _, _ -> }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}