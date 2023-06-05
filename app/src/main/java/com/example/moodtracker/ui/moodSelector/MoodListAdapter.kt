package com.example.moodtracker.ui.moodSelector

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moodtracker.R
import com.example.moodtracker.data.entity.Mood

class MoodListAdapter(private val moodList: List<Mood>) : RecyclerView.Adapter<MoodListAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mood_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoodListAdapter.ViewHolder, position: Int) {
        val item = moodList[position]
        holder.tvMood.setText(item.moodType.toString())
    }

    override fun getItemCount(): Int {
        return moodList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvMood: TextView = itemView.findViewById(R.id.tv_mood)
    }
}