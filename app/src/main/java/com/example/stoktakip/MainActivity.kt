package com.example.stoktakip

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var dbHelper: DatabaseHelper
    lateinit var listView: ListView
    lateinit var bosUyari: TextView
    var urunListesi = ArrayList<Urun>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)
        listView = findViewById(R.id.listeUrunler)
        bosUyari = findViewById(R.id.txtBosUyari)

        findViewById<Button>(R.id.btnEkleSayfa).setOnClickListener {
            startActivity(Intent(this, EkleActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        urunListesi = dbHelper.tumUrunleriGetir()
        if (urunListesi.isEmpty()) {
            bosUyari.visibility = View.VISIBLE
            listView.visibility = View.GONE
        } else {
            bosUyari.visibility = View.GONE
            listView.visibility = View.VISIBLE
            listView.adapter = UrunAdapter()
        }
    }

    inner class UrunAdapter : BaseAdapter() {
        override fun getCount(): Int = urunListesi.size
        override fun getItem(p0: Int): Any = urunListesi[p0]
        override fun getItemId(p0: Int): Long = urunListesi[p0].id.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = layoutInflater.inflate(R.layout.item_urun, null)
            val urun = urunListesi[position]

            val txtStok = view.findViewById<TextView>(R.id.txtListeStok)

            view.findViewById<TextView>(R.id.txtListeAd).text = urun.ad
            view.findViewById<TextView>(R.id.txtListeFiyat).text = "${urun.fiyat} TL"

            if (urun.adet < 5) {
                txtStok.text = "KRİTİK STOK: ${urun.adet}"
                txtStok.setTextColor(Color.RED)
            } else {
                txtStok.text = "Stok: ${urun.adet}"
                txtStok.setTextColor(Color.DKGRAY)
            }

            view.setOnClickListener {
                val intent = Intent(this@MainActivity, DetayActivity::class.java)
                intent.putExtra("id", urun.id)
                intent.putExtra("ad", urun.ad)
                intent.putExtra("adet", urun.adet)
                intent.putExtra("fiyat", urun.fiyat)
                startActivity(intent)
            }
            return view
        }
    }
}