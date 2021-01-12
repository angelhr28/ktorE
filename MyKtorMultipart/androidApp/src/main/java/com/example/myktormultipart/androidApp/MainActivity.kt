package com.example.myktormultipart.androidApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.myktormultipart.shared.Greeting
import android.widget.TextView
import com.example.myktormultipart.shared.MyApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        val bt: Button = findViewById(R.id.btn_service)
        tv.text = greet()

        bt.setOnClickListener {
            GlobalScope.launch(Dispatchers.Default) {
                try {
                    MyApi().postServiceMultipart()
                } catch (e:Exception){
                    Log.e("TAG", "ERROR SERVICE: $e " )
                }
            }
        }
    }
}
