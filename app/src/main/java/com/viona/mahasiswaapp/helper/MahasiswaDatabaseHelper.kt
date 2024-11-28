package com.viona.mahasiswaapp.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.viona.mahasiswaapp.model.Mahasiswa

class MahasiswaDatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "MahasiswaDB"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "mahasiswa"
        const val COL_ID = "id"
        const val COL_NAMA = "nama"
        const val COL_NIM = "nim"
        const val COL_JURUSAN = "jurusan"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NAMA TEXT,
                $COL_NIM TEXT,
                $COL_JURUSAN TEXT
            )
        """
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertMahasiswa(mahasiswa: Mahasiswa): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NAMA, mahasiswa.nama)
            put(COL_NIM, mahasiswa.nim)
            put(COL_JURUSAN, mahasiswa.jurusan)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun getAllMahasiswa(): List<Mahasiswa> {
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, "$COL_ID DESC")
        val mahasiswaList = mutableListOf<Mahasiswa>()

        if (cursor.moveToFirst()) {
            do {
                val mahasiswa = Mahasiswa(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                    nama = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAMA)),
                    nim = cursor.getString(cursor.getColumnIndexOrThrow(COL_NIM)),
                    jurusan = cursor.getString(cursor.getColumnIndexOrThrow(COL_JURUSAN))
                )
                mahasiswaList.add(mahasiswa)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return mahasiswaList
    }

    fun updateMahasiswa(mahasiswa: Mahasiswa): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NAMA, mahasiswa.nama)
            put(COL_NIM, mahasiswa.nim)
            put(COL_JURUSAN, mahasiswa.jurusan)
        }
        return db.update(TABLE_NAME, values, "$COL_ID = ?", arrayOf(mahasiswa.id.toString()))
    }

    fun deleteMahasiswa(id: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_NAME, "$COL_ID = ?", arrayOf(id.toString()))
    }
}
