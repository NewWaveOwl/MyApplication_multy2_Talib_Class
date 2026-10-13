package com.example.myapplication_multy2.model

/** Immutable application data, independent of Compose and navigation. */
data class Person(val name: String, val city: String = "")

/** Sample data used by the existing About and Contact navigation examples. */
object SamplePeople {
    val about = Person("Franz")
    val contact = Person("Julie", "Paris")
}
