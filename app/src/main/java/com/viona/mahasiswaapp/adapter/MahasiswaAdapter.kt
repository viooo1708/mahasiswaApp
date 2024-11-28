package com.viona.mahasiswaapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.viona.mahasiswaapp.R
import com.viona.mahasiswaapp.model.Mahasiswa

class MahasiswaAdapter(
    private val context: Context,
    private val mahasiswaList: List<Mahasiswa>,
    private val onEditClick: (Mahasiswa) -> Unit, // Listener untuk edit
    private val onDeleteClick: (Mahasiswa) -> Unit // Listener untuk delete
) : RecyclerView.Adapter<MahasiswaAdapter.MahasiswaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MahasiswaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_mahasiswa, parent, false)
        return MahasiswaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MahasiswaViewHolder, position: Int) {
        val mahasiswa = mahasiswaList[position]
        holder.tvNama.text = mahasiswa.nama
        holder.tvNim.text = mahasiswa.nim
        holder.tvJurusan.text = mahasiswa.jurusan

        holder.itemView.setOnClickListener {
            onEditClick(mahasiswa) // Panggil listener untuk edit
        }

        holder.btnDelete.setOnClickListener {
            onDeleteClick(mahasiswa) // Panggil listener untuk delete
        }
    }

    override fun getItemCount(): Int = mahasiswaList.size

    class MahasiswaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvNim: TextView = itemView.findViewById(R.id.tvNim)
        val tvJurusan: TextView = itemView.findViewById(R.id.tvJurusan)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete) // Tombol hapus
    }
}
