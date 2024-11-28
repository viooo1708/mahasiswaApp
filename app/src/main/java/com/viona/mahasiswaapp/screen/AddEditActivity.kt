package com.viona.mahasiswaapp.screen

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.viona.mahasiswaapp.R
import com.viona.mahasiswaapp.helper.MahasiswaDatabaseHelper
import com.viona.mahasiswaapp.model.Mahasiswa

class AddEditActivity : AppCompatActivity() {

    private lateinit var databaseHelper: MahasiswaDatabaseHelper
    private lateinit var etNama: EditText
    private lateinit var etNim: EditText
    private lateinit var etJurusan: EditText
    private var mahasiswaId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        databaseHelper = MahasiswaDatabaseHelper(this)

        etNama = findViewById(R.id.etNama)
        etNim = findViewById(R.id.etNim)
        etJurusan = findViewById(R.id.etJurusan)
        val btnSave = findViewById<Button>(R.id.btnSave)

        mahasiswaId = intent.getIntExtra("id", -1)
        if (mahasiswaId != -1) {
            etNama.setText(intent.getStringExtra("nama"))
            etNim.setText(intent.getStringExtra("nim"))
            etJurusan.setText(intent.getStringExtra("jurusan"))
        }

        btnSave.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        val nama = etNama.text.toString().trim()
        val nim = etNim.text.toString().trim()
        val jurusan = etJurusan.text.toString().trim()

        if (nama.isEmpty() || nim.isEmpty() || jurusan.isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        val mahasiswa = Mahasiswa(
            id = mahasiswaId,
            nama = nama,
            nim = nim,
            jurusan = jurusan
        )

        if (mahasiswaId == -1) {
            databaseHelper.insertMahasiswa(mahasiswa)
            Toast.makeText(this, "Mahasiswa berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
        } else {
            databaseHelper.updateMahasiswa(mahasiswa)
            Toast.makeText(this, "Mahasiswa berhasil diperbarui!", Toast.LENGTH_SHORT).show()
        }

        finish()
    }
}