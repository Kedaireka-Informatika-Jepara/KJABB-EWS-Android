package rekayasaagromarin.ews3swj.ui.menu.main.tutorial

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.FragmentTutorialBinding

class TutorialFragment : Fragment() {

    private val tutorialViewModel: TutorialViewModel by viewModels()
    private var _binding: FragmentTutorialBinding? = null
    private val binding get() = _binding!!

    private lateinit var listTutorial: ArrayList<String>
    private var videoUrl: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTutorialBinding.inflate(inflater, container, false)

        initListTutorial()

        return binding.root
    }

    private fun initListTutorial() {
        tutorialViewModel.setListTutorial()
        listTutorial = tutorialViewModel.getListTutorial()
        tutorialViewModel.setListUrl()

        val spinnerArrayAdapter = context?.let {
            ArrayAdapter(
                it,
                R.layout.item_text,
                listTutorial
            )
        }

        (binding.tilTutorial.editText as? AutoCompleteTextView)?.setAdapter(
            spinnerArrayAdapter
        )

        binding.dropdownTutorial.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                showLoading(true)
                videoUrl = tutorialViewModel.getUrl(position)
                setUpVideo()
            }
    }

    private fun setUpVideo() {
        val uri: Uri = Uri.parse(videoUrl)
        binding.videoTutorial.setVideoURI(uri)

        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(binding.videoTutorial)
        mediaController.setMediaPlayer(binding.videoTutorial)

        binding.videoTutorial.setMediaController(mediaController)
        binding.videoTutorial.start()

        binding.videoTutorial.setOnPreparedListener {
            showLoading(false)
        }

        binding.videoTutorial.setOnCompletionListener {
            binding.videoTutorial.start()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                loadingTutorial.visibility = View.VISIBLE
                cardVideoTutorial.visibility = View.INVISIBLE
            } else {
                loadingTutorial.visibility = View.INVISIBLE
                cardVideoTutorial.visibility = View.VISIBLE
            }
        }
    }
}