package com.makotogo.learn.kotlin.equality

import com.makotogo.learn.kotlin.model.Person

val doug = Person("Davis", "Douglas")
val dougClone = Person("Davis", "Douglas")
val anotherPerson = doug

const val twoHundredInt = 200
const val threeHundredInt = 300

const val twoHundredString = "200"
const val threeHundredString = "300"

fun equalsNumber() {
    println("********** equalsNumber() **********")
    println("twoHundredInt = \"$twoHundredInt\", hashCode = ${twoHundredInt.hashCode()}")
    println("threeHundredInt = \"$threeHundredInt\", hashCode = ${threeHundredInt.hashCode()}")
    println("twoHundredInt+100 = ${twoHundredInt+100}, hashCode = ${(twoHundredInt+100).hashCode()}")
    println("$twoHundredInt.equals($twoHundredInt): ${twoHundredInt.equals(twoHundredInt)}")
    println("$twoHundredInt.equals($threeHundredInt): ${twoHundredInt.equals(threeHundredInt)}")
    println("$threeHundredInt.equals($twoHundredInt+100): ${threeHundredInt.equals(twoHundredInt+100)}")
}

fun equalsNumericString() {
    println("********** equalsNumericString() **********")
    println("twoHundredString = \"$twoHundredString\", hashCode = ${twoHundredString.hashCode()}")
    println("threeHundredString = \"$threeHundredString\", hashCode = ${threeHundredString.hashCode()}")
    println("200.toString() = \"${200.toString()}\", hashCode = ${200.toString().hashCode()}")
    println("twoHundredString.equals(twoHundredString): ${twoHundredString.equals(twoHundredString)}")
    println("twoHundredString.equals(threeHundredString): ${twoHundredString.equals(threeHundredString)}")
    println("twoHundredString.equals(200.toString()): ${twoHundredString.equals(200.toString())}")
}

fun equalsPerson() {
    println("********** equalsPerson() **********")
    println("Doug: $doug, hashCode = ${doug.hashCode()}")
    println("Doug's clone: $dougClone, hashCode = ${dougClone.hashCode()}")
    println("doug.equals(dougClone): ${doug.equals(dougClone)}")
    println("doug.equals(anotherPerson): ${doug.equals(anotherPerson)}")

}

fun structuralEqualsNumber() {
    println("********** structuralEqualsNumber() **********")
    println("twoHundredInt = \"$twoHundredInt\", hashCode = ${twoHundredInt.hashCode()}")
    println("threeHundredInt = \"$threeHundredInt\", hashCode = ${threeHundredInt.hashCode()}")
    println("twoHundredInt+100 = ${twoHundredInt+100}, hashCode = ${(twoHundredInt+100).hashCode()}")
    println("twoHundredInt == twoHundredInt: ${twoHundredInt == twoHundredInt}")
    println("twoHundredInt == threeHundredInt: ${twoHundredInt == threeHundredInt}")
    println("threeHundredInt == (twoHundredInt+100): ${threeHundredInt == (twoHundredInt+100)}")

}

fun structuralEqualsNumericString() {
    println("********** structuralEqualsNumericString() **********")
    println("twoHundredString = \"$twoHundredString\", hashCode = ${twoHundredString.hashCode()}")
    println("threeHundredString = \"$threeHundredString\", hashCode = ${threeHundredString.hashCode()}")
    println("200.toString() = \"${200.toString()}\", hashCode = ${200.toString().hashCode()}")
    println("twoHundredString == twoHundredString: ${twoHundredString == twoHundredString}")
    println("twoHundredString == threeHundredString: ${twoHundredString == threeHundredString}")
    println("twoHundredString == 200.toString(): ${twoHundredString == 200.toString()}")
}

fun structuralEqualsPerson() {
    println("********** structuralEqualsPerson() **********")
    println("doug: $doug, hashCode = ${doug.hashCode()}")
    println("dougClone: $dougClone, hashCode = ${dougClone.hashCode()}")
    println("anotherPerson: $anotherPerson, hashCode = ${anotherPerson.hashCode()}")
    println("doug == dougClone: ${doug == dougClone}")
    println("doug == anotherPerson: ${doug == anotherPerson}")
}

fun referentialEqualsNumber() {
    println("********** referentialEqualsNumber() **********")
    println("twoHundredInt = \"$twoHundredInt\", hashCode = ${twoHundredInt.hashCode()}")
    println("threeHundredInt = \"$threeHundredInt\", hashCode = ${threeHundredInt.hashCode()}")
    println("twoHundredInt+100 = ${twoHundredInt+100}, hashCode = ${(twoHundredInt+100).hashCode()}")
    println("twoHundredInt === twoHundredInt: ${twoHundredInt === twoHundredInt}")
    println("twoHundredInt === threeHundredInt: ${twoHundredInt === threeHundredInt}")
    println("threeHundredInt === (twoHundredInt+100): ${threeHundredInt === (twoHundredInt+100)}")
}

fun referentialEqualsNumericString() {
    println("********** referentialEqualsNumericString() **********")
    println("twoHundredString = \"$twoHundredString\", hashCode = ${twoHundredString.hashCode()}")
    println("threeHundredString = \"$threeHundredString\", hashCode = ${threeHundredString.hashCode()}")
    println("200.toString() = \"${200.toString()}\", hashCode = ${200.toString().hashCode()}")
    println("twoHundredString === twoHundredString: ${twoHundredString === twoHundredString}")
    println("twoHundredString === threeHundredString: ${twoHundredString === threeHundredString}")
    println("twoHundredString === 200.toString(): ${twoHundredString === 200.toString()}")
}

fun referentialEqualsPerson() {
    println("********** referentialEqualsPerson() **********")
    println("Doug: $doug, hashCode = ${doug.hashCode()}")
    println("Doug's clone: $dougClone, hashCode = ${dougClone.hashCode()}")
    println("anotherPerson: $anotherPerson, hashCode = ${anotherPerson.hashCode()}")
    println("doug === dougClone: ${doug === dougClone}")
    println("doug === anotherPerson: ${doug === anotherPerson}")
}

fun main(args: Array<String>) {
    // .equals()
    equalsNumber()
    equalsNumericString()
    equalsPerson()
    // ==
    structuralEqualsNumber()
    structuralEqualsNumericString()
    structuralEqualsPerson()
    // ===
    referentialEqualsNumber()
    referentialEqualsNumericString()
    referentialEqualsPerson()
}

