package rekayasaagromarin.ews3swj.ui.menu.main.inputhistory.result

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import rekayasaagromarin.ews3swj.databinding.ActivityResultHistoryBinding
import rekayasaagromarin.ews3swj.model.DataStation
import rekayasaagromarin.ews3swj.model.Result
import rekayasaagromarin.ews3swj.ui.menu.main.inputhistory.InputHistoryFragment
import rekayasaagromarin.ews3swj.ui.menu.main.inputhistory.result.edit.EditResultHistoryActivity
import java.text.DecimalFormat

class ResultHistoryActivity : AppCompatActivity() {

    private val resultHistoryViewModel: ResultHistoryViewModel by viewModels()
    private lateinit var binding: ActivityResultHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        setResultHistory()
        initResult()
        editResult()
    }

    private fun initToolbar() {
        val dataStation =
            intent.getParcelableExtra<DataStation>(InputHistoryFragment.EXTRA_STATION_ID)

        with(binding.toolbarDetailStation.viewToolbar) {
            title = dataStation?.stationId
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun editResult() {
        binding.btnEditDetailResultHistory.setOnClickListener {
            val dataStation =
                intent.getParcelableExtra<DataStation>(InputHistoryFragment.EXTRA_STATION_ID)
            val intentEditResult = Intent(this, EditResultHistoryActivity::class.java)
            intentEditResult.putExtra(InputHistoryFragment.EXTRA_STATION_ID, dataStation)
            startActivity(intentEditResult)
        }
    }

    private fun setResultHistory() {
        val dataStation =
            intent.getParcelableExtra<DataStation>(InputHistoryFragment.EXTRA_STATION_ID)

        with(resultHistoryViewModel) {
            if (dataStation != null) {
                setResultStation(dataStation.stationId)
            }

            isLoading.observe(this@ResultHistoryActivity) {
                showLoading(it)
            }

            message.observe(this@ResultHistoryActivity) {
                if (it != "") {
                    showMessage(it)
                }
            }

            isError.observe(this@ResultHistoryActivity) {
                showError(it)
            }
        }
    }

    private fun initResult() {
        resultHistoryViewModel.getResultStation().observe(this) { result ->
            if (result != Result()) {
                with(binding.viewDetailResultHistory) {
                    tvResultValue.text = convertDecimal(result.result)
                    tvResultStatus.text = result.status
                    tvResultConclusion.text = result.conclusion
                    tvResultRecommendation.text = result.recommendation
                }
            } else {
                finish()
                InputHistoryFragment.isUpdateItem = true
                Toast.makeText(this, "Data result tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                loadingResultHistory.root.visibility = View.VISIBLE
                layoutResultHistory.visibility = View.GONE
            } else {
                loadingResultHistory.root.visibility = View.GONE
                layoutResultHistory.visibility = View.VISIBLE
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showError(isError: Boolean) {
        with(binding) {
            if (isError) {
                layoutResultHistory.visibility = View.GONE
                tvResultHistoryError.visibility = View.VISIBLE
                loadingResultHistory.root.visibility = View.GONE
            } else {
                tvResultHistoryError.visibility = View.GONE
            }
        }
    }

    private fun convertDecimal(value: Double): String {
//        val dec = DecimalFormat("##.##")
//        val decFormat = dec.format(value)
        return String.format("%.2f", value)
    }

    override fun onResume() {
        super.onResume()

        if (isFinish) {
            InputHistoryFragment.isFinish = true
            isFinish = false
            finish()
        }
    }

    companion object {
        var isFinish = false
    }
}