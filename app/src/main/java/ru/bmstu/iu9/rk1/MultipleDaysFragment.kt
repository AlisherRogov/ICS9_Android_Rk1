package ru.bmstu.iu9.rk1

import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Response

class MultipleDaysFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.multiple_days_fragment, container, false)
        viewManager = LinearLayoutManager(context)
        viewAdapter = ListAdapter()

        val api = retrofit.create(ExampleApiService::class.java)
        api.getDailyData(BITCOIN, MainActivity.getCurrentCurrency(), MainActivity.getDaysNumber())
            .enqueue(object : retrofit2.Callback<ExchangeResponse> {
                override fun onResponse(
                    call: Call<ExchangeResponse>,
                    response: Response<ExchangeResponse>
                ) {
                    viewAdapter.data = response.body()?.Data?.Data!!
                    recyclerView = view.findViewById<RecyclerView>(R.id.my_recycler_view).apply {
                        setHasFixedSize(true)
                        layoutManager = viewManager
                        adapter = viewAdapter
                    }
                }

                override fun onFailure(call: Call<ExchangeResponse>, t: Throwable) {
                    Log.d(TAG, FAIL_MSG)
                }

            })
        return view
    }

    companion object {
        private const val BITCOIN = "BTC"
        private const val TAG = "dani"
        private const val FAIL_MSG = "Fail"
        fun newInstance() = MultipleDaysFragment()
    }

}