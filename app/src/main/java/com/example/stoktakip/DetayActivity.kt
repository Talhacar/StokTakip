package com.example.stoktakip

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class DetayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detay)

        val db = DatabaseHelper(this)
        val id = intent.getIntExtra("id", 0)
        val ad = intent.getStringExtra("ad")
        val adet = intent.getIntExtra("adet", 0)
        val fiyat = intent.getDoubleExtra("fiyat", 0.0)

        findViewById<TextView>(R.id.viewUrunAd).text = ad
        findViewById<TextView>(R.id.viewStokDurum).text = "Stok: $adet Adet"
        findViewById<TextView>(R.id.viewToplamDeger).text = "Toplam: ${adet * fiyat} TL"

        // GERİ BUTONU İŞLEVİ
        findViewById<Button>(R.id.btnGeri).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnSil).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Silinsin mi?")
                .setMessage("Bu ürün kalıcı olarak silinecek.")
                .setPositiveButton("EVET") { _, _ ->
                    db.urunSil(id)
                    Toast.makeText(this, "Ürün Silindi", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .setNegativeButton("HAYIR", null)
                .show()
        }

        findViewById<Button>(R.id.btnDuzenle).setOnClickListener {
            val intent = Intent(this, EkleActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("ad", ad)
            intent.putExtra("adet", adet)
            intent.putExtra("fiyat", fiyat)
            startActivity(intent)
            finish()
        }
    }
}