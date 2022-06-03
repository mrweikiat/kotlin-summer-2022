package com.example.workoutapp

object Constants {

    fun defaultExerciseList(): ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()

        // id name image isCompleted isSelected
        val squats = ExerciseModel(
            1,
            "Squats",
            R.drawable.squats,
            false,
            false
        )
        val pushUps = ExerciseModel(
            2,
            "Push ups",
            R.drawable.push_ups,
            false,
            false
        )

        val burpees = ExerciseModel(
            3,
            "Burpees",
            R.drawable.burpees_exercise,
            false,
            false
        )

        val sitUps = ExerciseModel(
            4,
            "Sit Ups",
            R.drawable.situps,
            false,
            false
        )

        exerciseList.add(squats)
        exerciseList.add(pushUps)
        exerciseList.add(burpees)
        exerciseList.add(sitUps)

        return exerciseList
    }




}