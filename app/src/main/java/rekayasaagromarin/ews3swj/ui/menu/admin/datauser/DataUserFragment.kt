package rekayasaagromarin.ews3swj.ui.menu.admin.datauser

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import rekayasaagromarin.ews3swj.adapter.UserAdapter
import rekayasaagromarin.ews3swj.databinding.FragmentDataUserBinding
import rekayasaagromarin.ews3swj.model.User
import rekayasaagromarin.ews3swj.ui.menu.admin.datauser.action.add.AddUserActivity
import rekayasaagromarin.ews3swj.ui.menu.admin.datauser.action.UserActionActivity

class DataUserFragment : Fragment() {

    private val dataUserViewModel: DataUserViewModel by viewModels()
    private var _binding: FragmentDataUserBinding? = null
    private val binding get() = _binding!!
    private val userAdapter: UserAdapter by lazy { UserAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDataUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAddUser()
        setListUser()
        showListUser()
    }

    private fun initAddUser() {
        binding.actionAddUser.setOnClickListener {
            val intentAddUser = Intent(context, AddUserActivity::class.java)
            startActivity(intentAddUser)
        }
    }

    private fun setListUser() {
        with(dataUserViewModel) {
            setListUser()

            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

            message.observe(viewLifecycleOwner) {
                if (it != "" && it != null) {
                    showMessage(it)
                }
            }

            isError.observe(viewLifecycleOwner) {
                showError(it)
            }
        }
    }

    private fun showListUser() {
        dataUserViewModel.getListUser().observe(viewLifecycleOwner) { listUser ->
            listUser?.let {
                if (listUser.isNotEmpty()) {
                    userAdapter.setUser(listUser)
                }
            }
            binding.rvDataUser.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = userAdapter
            }
        }

        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                val intentUserAction = Intent(context, UserActionActivity::class.java)
                intentUserAction.putExtra(UserActionActivity.EXTRA_ID, user.userId)
                intentUserAction.putExtra(UserActionActivity.EXTRA_NAME, user.name)
                startActivity(intentUserAction)
            }

        })
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                loadingDataUser.visibility = View.VISIBLE
                rvDataUser.visibility = View.GONE
            } else {
                loadingDataUser.visibility = View.GONE
                rvDataUser.visibility = View.VISIBLE
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showError(isError: Boolean) {
        with(binding) {
            if (isError) {
                rvDataUser.visibility = View.GONE
                tvDataUserError.visibility = View.VISIBLE
                loadingDataUser.visibility = View.GONE
            } else {
                tvDataUserError.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        if (isUpdateItem) {
            setListUser()
            showListUser()
            isUpdateItem = false
        }
    }

    companion object {
        var isUpdateItem = false
    }
}