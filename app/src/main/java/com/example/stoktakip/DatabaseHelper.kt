package com.example.stoktakip

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "StokTakip.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE urunler (id INTEGER PRIMARY KEY AUTOINCREMENT, ad TEXT, adet INTEGER, fiyat REAL)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS urunler")
        onCreate(db)
    }

    fun urunEkle(ad: String, adet: Int, fiyat: Double): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("ad", ad)
        values.put("adet", adet)
        values.put("fiyat", fiyat)
        return db.insert("urunler", null, values) != -1L
    }

    fun urunGuncelle(id: Int, ad: String, adet: Int, fiyat: Double): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("ad", ad)
        values.put("adet", adet)
        values.put("fiyat", fiyat)
        return db.update("urunler", values, "id=?", arrayOf(id.toString())) > 0
    }

    fun urunSil(id: Int): Boolean {
        val db = this.writableDatabase
        return db.delete("urunler", "id=?", arrayOf(id.toString())) > 0
    }

    fun tumUrunleriGetir(): ArrayList<Urun> {
        val liste = ArrayList<Urun>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM urunler ORDER BY id DESC", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val ad = cursor.getString(1)
                val adet = cursor.getInt(2)
                val fiyat = cursor.getDouble(3)
                liste.add(Urun(id, ad, adet, fiyat))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return liste
    }
}