package com.example.moodtracker.ui.moodSelector

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
        val context = holder.ivMoodStatus.context
        holder.moodBar.setBackground(context.getDrawable(getMoodStatusBackground(item.moodType)))
        holder.ivMoodStatus.setImageDrawable(context.getDrawable(getMoodStatusImage(item.moodType)))

        val layoutParams = holder.moodBar.layoutParams
        layoutParams.height = getMoodStatusBarSize(item.moodType)
    }

    override fun getItemCount(): Int {
        return moodList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val moodBar: View = itemView.findViewById(R.id.view_mood_bar)
        val ivMoodStatus: ImageView = itemView.findViewById(R.id.imv_mood_status)
    }

    private fun getMoodStatusImage(moodType: Int): Int {
        return when (moodType) {
            MoodSelectorDialog.HAPPY_MOOD -> R.drawable.ic_happy_mood
            MoodSelectorDialog.NEUTRAL_MOOD -> R.drawable.ic_neutral_mood
            MoodSelectorDialog.SAD_MOOD -> R.drawable.ic_sad_mood
            else -> R.drawable.ic_neutral_mood
        }
    }

    private fun getMoodStatusBackground(moodType: Int): Int {
        return when (moodType) {
            MoodSelectorDialog.HAPPY_MOOD -> R.drawable.bg_mood_happy_status_bar
            MoodSelectorDialog.NEUTRAL_MOOD -> R.drawable.bg_mood_neutral_status_bar
            MoodSelectorDialog.SAD_MOOD -> R.drawable.bg_mood_sad_status_bar
            else -> R.drawable.bg_mood_neutral_status_bar
        }
    }

    private fun getMoodStatusBarSize(moodType: Int): Int {
        return when (moodType) {
            MoodSelectorDialog.HAPPY_MOOD -> 500
            MoodSelectorDialog.NEUTRAL_MOOD -> 250
            MoodSelectorDialog.SAD_MOOD -> 100
            else -> 250
        }
    }
}