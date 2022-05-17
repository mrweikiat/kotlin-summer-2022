package com.example.kotlin_1

fun main() {
    // different types of variables
    // val, var
    // int byte short long
    // bool char string
    // string interpolation
    var dummy = "XDC123"
    println("The serial number is $dummy")

    // comparison
    val isEqual = 5==3
    println("isEqual is $isEqual")
    println("is 5 greater than 3?: ${5>3}")

    // post increment value++
    // pre increment ++value

    // when expression
    var season = 3
    println("What the season now?")
    when (season) {
        1 -> println("its spring")
        2 -> println("its summer")
        3 -> println("its autumn")
        4 -> println("its winter")
        else -> println("invald season!")
    }

    println("What is your 2.4km timing?")
    val timing: Double = 9.30
    println(timing)
    when (timing) {
        in 7.0..10.0 -> println("Your timing is gold!")
        in 10.0..11.0 -> println("Your timing is silver!")
        else -> println("You suck")
    }

    // do while
    var x = 1
    do {
        print("$x")
        x++
    }while (x<10)
    print("\n")
    for (i in 1 until 10) {
        print("$i")
    }

    print("\n")
    for (i in 1 until 20) {
        if (i%2 == 0) {
            continue
        }
        print("$i")
    }
    print("\n")
    print("\n")

    dummyFunction()

    // elvis operator
    var nullableName : String? = "Johnny"
    //nullableName = null
    val name = nullableName ?: "Guest"
    println("name is $name")
    println("name is ${nullableName!!.lowercase()}")
}

fun dummyFunction() {
    println("this is another function!")
    val a = 99
    val b = 1
    println(addUp(a,b))
}

fun addUp(a: Int, b: Int): Int {
    return a+b
}