package com.viona.mahasiswaapp.screen

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.viona.mahasiswaapp.R
import com.viona.mahasiswaapp.helper.MahasiswaDatabaseHelper
import android.widget.Toast
import com.viona.mahasiswaapp.model.Mahasiswa


class UpdateMahasiswaActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUpdateDataBinding
    private lateinit var db : MahasiswaDatabaseHelper
    private var dataId : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MahasiswaDatabaseHelper(this)

        dataId = intent.getIntExtra("data_id",-1)

        if (dataId == -1){
            finish()
            return
        }

        val data = db.getAllMahasiswa(dataId)
        binding.etEditNama.setText(data.namaMahasiswa)
        binding.etEditNim.setText(data.nim)
        binding.etEditJurusan.setText(data.jurusan)

        binding.imgEdit.setOnClickListener{
            val newName = binding.etEditNama.text.toString()
            val newNim = binding.etEditNim.text.toString()
            val newJurusan = binding.etEditJurusan.text.toString()

            val updateData = Mahasiswa(dataId, newName, newNim, newJurusan)
            db.updateMahasiswa(updateData)
            finish()
            Toast.makeText(this,"Change Save", Toast.LENGTH_SHORT).show()
        }

    }
}