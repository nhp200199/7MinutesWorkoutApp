package eu.tutorials.a7_minutesworkoutapp

data class ExerciseModel(
    var id: Int,
    var name: String,
    var imageRes: Int,
    var isSelected: Boolean,
    var isCompleted: Boolean
) {

}