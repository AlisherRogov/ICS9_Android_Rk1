package ru.bmstu.iu9.rk1

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import retrofit2.Call
import retrofit2.Response
import ru.bmstu.iu9.rk1.databinding.OneDayCurrencyFragmentBinding
import java.text.SimpleDateFormat
import java.util.*

class OneDayFragment : Fragment() {
    private lateinit var binding: OneDayCurrencyFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.one_day_currency_fragment, container, false)
        val api = retrofit.create(ExampleApiService::class.java)
        val time = requireArguments().getString(ARGUMENT_KEY)

        if (time != null) {
            api.getHourlyData(BITCOIN, MainActivity.getCurrentCurrency(), 1, time)
                .enqueue(object : retrofit2.Callback<ExchangeResponse> {
                    override fun onResponse(
                        call: Call<ExchangeResponse>,
                        response: Response<ExchangeResponse>
                    ) {
                        binding.date.text = getDateTime(response.body()?.Data?.Data!![1].time)
                        binding.toDollar.text = response.body()?.Data?.Data!![1].close.toString()
                        binding.low.text = "Lowest = ${response.body()?.Data?.Data!![1].low}"
                        binding.high.text = "Highest = ${response.body()?.Data?.Data!![1].high}"
                    }

                    override fun onFailure(call: Call<ExchangeResponse>, t: Throwable) {
                        Log.d(TAG, FAIL_MSG)
                    }
                })
        }
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDateTime(s: String): String? {
        return try {
            val sdf = SimpleDateFormat(DATE_FORMAT)
            val netDate = Date(s.toLong() * MS)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }

    companion object {
        private const val DATE_FORMAT = "dd/MM hh:mm a"
        private const val MS = 1000
        private const val ARGUMENT_KEY = "time"
        private const val BITCOIN = "BTC"
        private const val TAG = "dani"
        private const val FAIL_MSG = "Fail"
        fun newInstance() = MultipleDaysFragment()
    }
}