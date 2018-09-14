package com.makotogo.learn.kotlin.example4

var publicProperty: String = "publicPackageProperty"
internal var internalProperty: String = "internalPackageProperty"
private var privateProperty: String = "privatePackageProperty"

private class PrivateClass(val name: String)

internal class InternalClass(val name: String)

//protected val protectedProperty: String = "protectedProperty" // NOT ALLOWED

//protected class ProtectedClass(property: String) // NOT ALLOWED

open class Parent(val name: String) {
    private val privateClass = PrivateClass("$name.privateClass")
    internal val internalClass = InternalClass("$name.internalClass")
    protected val protectedString = "$name.protectedString"

    protected fun disclosePrivate() {
        println("${privateClass.name}")
    }
}

private class Child(name: String) : Parent(name) {
    fun disclose() {
        println("$protectedString")
        disclosePrivate()
    }
}

fun main(args: Array<String>) {
    println("$publicProperty")
    println("$internalProperty")
    println("$privateProperty")

    val parent = Parent("Parent")
    println("${parent.name}")
    println("${parent.internalClass.name}")

    val child = Child("Child")
    println("${child.name}")
    println("${child.internalClass.name}")
    child.disclose()
}

