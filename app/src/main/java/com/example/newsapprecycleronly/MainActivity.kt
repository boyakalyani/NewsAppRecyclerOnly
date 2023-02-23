package com.example.newsapprecycleronly

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity(), OnNewsClick {

    private lateinit var mAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // to set layout of our recyclerview as a linear layout
        var recycler = findViewById<RecyclerView>(R.id.recyclerView_id)
        recycler.layoutManager = LinearLayoutManager(this)

        mAdapter = NewsAdapter(this)
        recycler.adapter = mAdapter
        fetchNews()
    }

    //now we madw our new api calls in main activity
    private fun fetchNews(){
        //volley library to call api
        val queue = Volley.newRequestQueue(this)
            var url = "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json"//api url
//        var url = "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json"
//        var url="https://api.punkapi.com/v2/beers/1"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener {
                val NewsjsoArray = it.getJSONArray("message")
                val NewsArray = ArrayList<NewsDataClass>()
                for (i in 0 until NewsjsoArray.length()) {
                    val newsJsonObject = NewsjsoArray.getJSONObject(i)
                    val news = NewsDataClass(
                        newsJsonObject.getString("description"),
                        newsJsonObject.getString("price"),//author
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    NewsArray.add(news)
                    //now we update the data every time using adapter
                }
                mAdapter.updateData(NewsArray)
            },
            Response.ErrorListener {
                Log.d("kalyani", "vijitha")
            }
        )
        queue.add(jsonObjectRequest)
    }
    override fun onClicked(newsDataClass: NewsDataClass){
        //we will use chrome custome tabs to open full news when clicked
        val builder=CustomTabsIntent.Builder()
        val customTabsIntent=builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(newsDataClass.url))
//        startActivity(Intent(this,MainActivity::class.java))
    }
}