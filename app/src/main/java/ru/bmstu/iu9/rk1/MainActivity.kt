package ru.bmstu.iu9.rk1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.Menu
import android.view.MenuItem
import android.view.ViewManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val api = retrofit.create(ExampleApiService::class.java)
        api.getDailyData("BTC", "USD", 10).enqueue(object : retrofit2.Callback<ExchangeResponse> {
            override fun onResponse(call: Call<ExchangeResponse>, response: Response<ExchangeResponse>) {
                d("danile", "onResponse:${response.body()?.Response}")
//                showData(response.body())
            }

            override fun onFailure(call: Call<ExchangeResponse>, t: Throwable) {
                d("daniele", "onfailuer")
            }

        })
    }

//    private fun showData(persons: List<Person>?) {
//        viewManager = LinearLayoutManager(this)
//        viewAdapter = ListAdapter { item -> itemClicked(item)}
//        if (persons != null) {
//            viewAdapter.data = persons
//        }
//
//        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
//            setHasFixedSize(true)
//            layoutManager = viewManager
//            adapter = viewAdapter
//        }
//    }

    private fun itemClicked(item : String) {
        Toast.makeText(this, "Clicked $item", Toast.LENGTH_LONG).show()
    }
}