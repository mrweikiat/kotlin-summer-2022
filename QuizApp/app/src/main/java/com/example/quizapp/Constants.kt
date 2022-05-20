package com.example.quizapp

object Constants {

    const val USER_NAME: String = "user_name"
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = "correct_answers"

    fun getQuestions():ArrayList<Question>{
        // hard coded questions
        val questionList = ArrayList<Question>()

        val que1 = Question(
            //id, qns, image, option 1,2,3,4, ans
        1,"What country does this flag belong to?",
            R.drawable.china,
            "China",
            "Japan",
            "USA",
            "Russia",
            1
        )
        val que2 = Question(
            //id, qns, image, option 1,2,3,4, ans
            1,"What country does this flag belong to?",
            R.drawable.monaco,
            "France",
            "Monaco",
            "Egypt",
            "Great Britain",
            2
        )
        val que3 = Question(
            //id, qns, image, option 1,2,3,4, ans
            1,"What country does this flag belong to?",
            R.drawable.jamaica,
            "Singapore",
            "Belgium",
            "Jamaica",
            "Venezuela",
            3
        )
        val que4 = Question(
            //id, qns, image, option 1,2,3,4, ans
            1,"What country does this flag belong to?",
            R.drawable.argentina,
            "Brazil",
            "Argentina",
            "South Africa",
            "Russia",
            2
        )
        val que5 = Question(
            //id, qns, image, option 1,2,3,4, ans
            1,"What country does this flag belong to?",
            R.drawable.sweden,
            "Sweden",
            "Norway",
            "Australia",
            "USA",
            1
        )

        questionList.add(que1)
        questionList.add(que2)
        questionList.add(que3)
        questionList.add(que4)
        questionList.add(que5)

        return questionList
    }


}