package com.viona.mahasiswaapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.viona.mahasiswaapp.helper.MahasiswaDatabaseHelper
import com.viona.mahasiswaapp.model.Mahasiswa
import com.viona.mahasiswaapp.adapter.MahasiswaAdapter
import com.viona.mahasiswaapp.screen.AddEditActivity

class MainActivity : AppCompatActivity() {
    private lateinit var databaseHelper: MahasiswaDatabaseHelper
    private lateinit var mahasiswaAdapter: MahasiswaAdapter
    private lateinit var recyclerView: RecyclerView
    private var mahasiswaList = mutableListOf<Mahasiswa>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = MahasiswaDatabaseHelper(this)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        fabAdd.setOnClickListener {
            val intent = Intent(this, AddEditActivity::class.java)
            startActivity(intent)
        }

        loadMahasiswaData()
    }

    private fun loadMahasiswaData() {
        mahasiswaList = databaseHelper.getAllMahasiswa().toMutableList()
        mahasiswaAdapter = MahasiswaAdapter(this, mahasiswaList,
            onEditClick = { mahasiswa ->
                val intent = Intent(this, AddEditActivity::class.java)
                intent.putExtra("id", mahasiswa.id)
                intent.putExtra("nama", mahasiswa.nama)
                intent.putExtra("nim", mahasiswa.nim)
                intent.putExtra("jurusan", mahasiswa.jurusan)
                startActivity(intent)
            },
            onDeleteClick = { mahasiswa ->
                deleteMahasiswa(mahasiswa)
            }
        )
        recyclerView.adapter = mahasiswaAdapter
    }

    private fun deleteMahasiswa(mahasiswa: Mahasiswa) {
        databaseHelper.deleteMahasiswa(mahasiswa.id!!)
        Toast.makeText(this, "Mahasiswa berhasil dihapus!", Toast.LENGTH_SHORT).show()
        loadMahasiswaData()
    }



    override fun onResume() {
        super.onResume()
        loadMahasiswaData()
    }
}
