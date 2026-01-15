package com.example.stoktakip

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EkleActivity : AppCompatActivity() {
    var guncellenecekId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ekle)

        val db = DatabaseHelper(this)
        val edtAd = findViewById<EditText>(R.id.edtUrunAd)
        val edtStok = findViewById<EditText>(R.id.edtStok)
        val edtFiyat = findViewById<EditText>(R.id.edtFiyat)
        val btnKaydet = findViewById<Button>(R.id.btnKaydet)
        val btnGeri = findViewById<Button>(R.id.btnGeri) // Geri butonu tanımlandı

        btnGeri.setOnClickListener {
            finish()
        }

        guncellenecekId = intent.getIntExtra("id", 0)
        if (guncellenecekId != 0) {
            edtAd.setText(intent.getStringExtra("ad"))
            edtStok.setText(intent.getIntExtra("adet", 0).toString())
            edtFiyat.setText(intent.getDoubleExtra("fiyat", 0.0).toString())
            btnKaydet.text = "GÜNCELLE"
        }

        btnKaydet.setOnClickListener {
            val ad = edtAd.text.toString().trim()
            val stokStr = edtStok.text.toString().trim()
            val fiyatStr = edtFiyat.text.toString().trim()

            if (ad.isEmpty() || stokStr.isEmpty() || fiyatStr.isEmpty()) {
                Toast.makeText(this, "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show()
            } else {
                if (guncellenecekId == 0) {
                    db.urunEkle(ad, stokStr.toInt(), fiyatStr.toDouble())
                    Toast.makeText(this, "Ürün Başarıyla Eklendi", Toast.LENGTH_SHORT).show()
                } else {
                    db.urunGuncelle(guncellenecekId, ad, stokStr.toInt(), fiyatStr.toDouble())
                    Toast.makeText(this, "Ürün Güncellendi", Toast.LENGTH_SHORT).show()
                }
                finish()
            }
        }
    }
}