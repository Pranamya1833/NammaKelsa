package com.example.nammakelsa

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WorkerAdapter(private var workerList: MutableList<Worker>) :
    RecyclerView.Adapter<WorkerAdapter.WorkerViewHolder>() {

    class WorkerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvSkill: TextView = itemView.findViewById(R.id.tvSkill)
        val tvDailyRate: TextView = itemView.findViewById(R.id.tvDailyRate)
        val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)
        val tvAvailability: TextView = itemView.findViewById(R.id.tvAvailability)
        val btnCall: Button = itemView.findViewById(R.id.btnCall)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.worker_card, parent, false)
        return WorkerViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkerViewHolder, position: Int) {
        val worker = workerList[position]
        holder.tvName.text = worker.name
        holder.tvSkill.text = worker.skill
        holder.tvDailyRate.text = "₹${worker.daily_rate}/day"
        holder.tvLocation.text = worker.location

        if (worker.is_available) {
            holder.tvAvailability.text = "✓ Available"
            holder.tvAvailability.setBackgroundColor(
                android.graphics.Color.parseColor("#4CAF50"))
            holder.tvAvailability.setTextColor(
                android.graphics.Color.WHITE)
        } else {
            holder.tvAvailability.text = "✗ Unavailable"
            holder.tvAvailability.setBackgroundColor(
                android.graphics.Color.parseColor("#F44336"))
            holder.tvAvailability.setTextColor(
                android.graphics.Color.WHITE)
        }

        holder.btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:${worker.phone}"))
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = workerList.size

    fun updateList(newList: MutableList<Worker>) {
        workerList = newList
        notifyDataSetChanged()
    }
}