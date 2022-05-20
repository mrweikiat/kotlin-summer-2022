package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import java.lang.reflect.Type

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener{

    private var mUserName: String? = null
    private var mCorrectAnswers: Int = 0

    private var mCurrentPosition: Int = 0
    private var mQuestionList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0

    private var progressBar: ProgressBar? = null
    private var tvProgress: TextView? = null
    private var tvQuestion: TextView? = null
    private var ivImage: ImageView? = null

    private var tvOptionOne: TextView? = null
    private var tvOptionTwo: TextView? = null
    private var tvOptionThree: TextView? = null
    private var tvOptionFour: TextView? = null

    private var btnSubmit: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        progressBar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tv_progress)
        tvQuestion = findViewById(R.id.tv_question)
        ivImage = findViewById(R.id.iv_image)
        tvOptionOne = findViewById(R.id.tv_option_one)
        tvOptionTwo = findViewById(R.id.tv_option_two)
        tvOptionThree = findViewById(R.id.tv_option_three)
        tvOptionFour = findViewById(R.id.tv_option_four)
        btnSubmit = findViewById(R.id.btn_submit)

        mQuestionList = Constants.getQuestions()

        setQuestion()

    }

    private fun setQuestion() {
        defaultOptionView()
        val question: Question = mQuestionList!![mCurrentPosition]
        progressBar?.progress = mCurrentPosition + 1
        tvProgress?.text = "${mCurrentPosition + 1}/5"
        tvQuestion?.text = question.question
        tvOptionOne?.text = question.optionOne
        tvOptionTwo?.text = question.optionTwo
        tvOptionThree?.text = question.optionThree
        tvOptionFour?.text = question.optionFour
        ivImage?.setImageResource(question.image)

        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)


        if (mCurrentPosition == mQuestionList!!.size) {
            // last entry
            btnSubmit?.text = "Finish"
        } else {
            btnSubmit?.text = "Submit"
        }
    }

    private fun defaultOptionView() {
        val options = ArrayList<TextView>()
        tvOptionOne?.let {
            options.add(0, it)
        }
        tvOptionTwo?.let{
            options.add(1, it)
        }
        tvOptionThree?.let{
            options.add(2, it)
        }
        tvOptionFour?.let{
            options.add(3, it)
        }

        for (option in options) {
            option.setTextColor(Color.parseColor("#FF000000"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.qns_background
            )
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#FF000000"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border
        )
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_option_one -> {
                tvOptionOne?.let {
                    selectedOptionView(it, 1)
                }
            }
            R.id.tv_option_two -> {
                tvOptionTwo?.let {
                    selectedOptionView(it, 2)
                }
            }
            R.id.tv_option_three -> {
                tvOptionThree?.let {
                    selectedOptionView(it, 3)
                }
            }
            R.id.tv_option_four -> {
                tvOptionFour?.let {
                    selectedOptionView(it, 4)
                }
            }
            R.id.btn_submit -> {
                if(mSelectedOptionPosition == 0) {
                    mCurrentPosition++

                    when{
                        mCurrentPosition < mQuestionList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this, Results::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionList?.size)
                            startActivity(intent)
                            finish()
                        }
                    }

                } else {
                    val question = mQuestionList?.get(mCurrentPosition)
                    if (question!!.answer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border)
                    } else {
                        mCorrectAnswers++
                    }
                    answerView(question.answer, R.drawable.correct_option_border)

                    if (mCurrentPosition == mQuestionList!!.size-1) {
                        btnSubmit?.text = "Finish"
                    } else {
                        btnSubmit?.text = "Go to next question"
                    }
                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawable: Int) {
        when(answer) {
            1 -> {
                tvOptionOne?.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawable
                )
            }
            2 -> {
                tvOptionTwo?.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawable
                )
            }
            3 -> {
                tvOptionThree?.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawable
                )
            }
            4 -> {
                tvOptionFour?.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawable
                )
            }
        }
    }
}