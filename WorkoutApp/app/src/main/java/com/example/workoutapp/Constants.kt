package com.example.workoutapp

object Constants {

    fun defaultExerciseList(): ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()

        // id name image isCompleted isSelected
        // squats | push ups | burpees | sit ups |
        // lunges | jumping jacks | mountain climbers | back raisers
        // high jumps | planks

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

        val backExtensions = ExerciseModel(
            5,
            "Back Extension",
            R.drawable.back_extension,
            false,
            false
        )

        val highJump = ExerciseModel(
            6,
            "High Jump",
            R.drawable.high_jump,
            false,
            false
        )

        val jumpingJacks = ExerciseModel(
            7,
            "Jumping Jacks",
            R.drawable.jumping_jacks,
            false,
            false
        )

        val lunges = ExerciseModel(
            8,
            "Lunges",
            R.drawable.lunges,
            false,
            false
        )

        val mountainClimbers = ExerciseModel(
            9,
            "Mountain Climbers",
            R.drawable.mountain_climbers,
            false,
            false
        )

        val planks = ExerciseModel(
            10,
            "Planks",
            R.drawable.planks,
            false,
            false
        )

        exerciseList.add(squats)
        exerciseList.add(pushUps)
        exerciseList.add(burpees)
        exerciseList.add(sitUps)
        exerciseList.add(backExtensions)
        exerciseList.add(highJump)
        exerciseList.add(jumpingJacks)
        exerciseList.add(lunges)
        exerciseList.add(mountainClimbers)
        exerciseList.add(planks)

        return exerciseList
    }




}