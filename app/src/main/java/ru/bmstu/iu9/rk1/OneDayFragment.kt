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
        val time = requireArguments().getString("time")

        if (time != null) {
            api.getHourlyData("BTC", MainActivity.getCurrentCurrency(), 1, time)
                .enqueue(object : retrofit2.Callback<ExchangeResponse> {
                    override fun onResponse(
                        call: Call<ExchangeResponse>,
                        response: Response<ExchangeResponse>
                    ) {
                        Log.d("daniele", response.body()?.Data?.Data!![1].time)

                        binding.date.text = getDateTime(response.body()?.Data?.Data!![1].time)
                        binding.toDollar.text = response.body()?.Data?.Data!![1].close.toString()
                        binding.low.text = "Lowest = ${response.body()?.Data?.Data!![1].low}"
                        binding.high.text = "Highest = ${response.body()?.Data?.Data!![1].high}"
                    }

                    override fun onFailure(call: Call<ExchangeResponse>, t: Throwable) {
                    }
                })
        }
        return binding.root
    }


    @SuppressLint("SimpleDateFormat")
    private fun getDateTime(s: String): String? {
        return try {
            val sdf = SimpleDateFormat("dd/MM hh:mm a")
            val netDate = Date(s.toLong() * 1000)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }

    companion object {
        fun newInstance() = MultipleDaysFragment()
    }
}