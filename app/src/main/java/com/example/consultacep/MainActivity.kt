package com.example.consultacep

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.consultacep.databinding.ActivityMainBinding
import com.example.consultacep.domain.MyDataItem
import com.example.consultacep.repository.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnConsult.setOnClickListener {
            val codeCep = binding.codeCep.text.toString()
            if(codeCep.length > 8 || codeCep.length < 8){
                Toast.makeText(
                    this,
                    "Dados invalidos, por favor consulte e tente novamente!",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                getMyData()
            }
        }
    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://viacep.com.br/ws/")
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData(binding.codeCep.text.toString())

        retrofitData.enqueue(object : Callback<MyDataItem> {
            override fun onResponse(call: Call<MyDataItem>, response: Response<MyDataItem>) {

                if (response.isSuccessful) {
                    binding.textCep.text = "Cep: ${response.body()?.cep}"
                    binding.textLogadouro.text = "Logadouro: ${response.body()?.logradouro}"
                    binding.textBairro.text = "Bairro: ${response.body()?.bairro}"
                    binding.textLocalidade.text = "Localidade: ${response.body()?.localidade}"
                    binding.textUf.text = "UF: ${response.body()?.uf}"
                    binding.textDdd.text = "DDD: ${response.body()?.ddd}"
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Erro ao consultar o Cep: " + response.message(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<MyDataItem>, t: Throwable) {
                Log.d("MainActivity", "ERRO" + t.message)
            }
        })
    }
}