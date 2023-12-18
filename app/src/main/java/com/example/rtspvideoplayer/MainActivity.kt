package com.example.rtspvideoplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import com.example.rtspvideoplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener{
            val link = binding.editText.text.toString().trim()
            if(link.isNotEmpty() && link.startsWith("rtsp://")){
                val i = Intent(this@MainActivity, MainActivity2::class.java)
                i.putExtra("url", link)
                startActivity(i)
            }
            else if(link.isEmpty()){
                Toast.makeText(this,"Enter the link to Stream the playback", Toast.LENGTH_SHORT).show()
            }
            else{
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Enter Valid URL")
                    .setMessage("Please enter a valid rtsp:// Url")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        }
    }
}