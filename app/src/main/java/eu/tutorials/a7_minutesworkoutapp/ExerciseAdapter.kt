package eu.tutorials.a7_minutesworkoutapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eu.tutorials.a7_minutesworkoutapp.databinding.ItemExerciseBinding

class ExerciseAdapter(private var exerciseModels: List<ExerciseModel>) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {
    inner class ExerciseViewHolder(binding: ItemExerciseBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvExercise = binding.tvExercise
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val inflate = ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exerciseModel = exerciseModels[position]
        holder.tvExercise.text = exerciseModel.id.toString()
        val backgroundResId = if (exerciseModel.isCompleted) {
            R.drawable.item_circle_color_accent
        }
        else if (exerciseModel.isSelected) {
            R.drawable.item_circular_color_accent_border
        }
        else {
            R.drawable.item_circle_color_gray
        }
        holder.tvExercise.setBackgroundResource(backgroundResId)
    }

    override fun getItemCount(): Int = exerciseModels.count()
}