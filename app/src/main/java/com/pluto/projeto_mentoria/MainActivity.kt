package com.pluto.projeto_mentoria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.pluto.projeto_mentoria.service.MovieService
import com.pluto.projeto_mentoria.service.RetrofitClient
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pluto.projeto_mentoria.adapter.MovieAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclermovies)
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            getMovies()
        }
    }

    suspend fun getMovies() {
        val retrofitClient = RetrofitClient
            .getRetrofitInstance()

        val service = retrofitClient.create(MovieService::class.java)

        try {
            val response = service.getMovies("191acad5d46a8532f2f3a23f9853f652")
            if (response.isSuccessful) {
                response.body()?.results?.let { movies ->
                    recyclerView.adapter = MovieAdapter(movies)
                }
            } else {
                Toast.makeText(baseContext, "Erro: ${response.message()}", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(baseContext, e.message, Toast.LENGTH_SHORT).show()
        }
    }
}