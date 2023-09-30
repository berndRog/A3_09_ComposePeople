package de.rogallab.mobile.domain.model

import java.util.*

data class Person(
   var firstName: String,
   var lastName: String,
   var email: String? = null,
   var phone:String? = null,
   var imagePath: String? = "",
   var id: UUID = UUID.randomUUID()
)
