package de.rogallab.mobile.domain.utilities

import java.util.*

fun UUID.as8(): String =
   this.toString().toString().substring(0..7)+"..."

val UuidEmpty: UUID =
   UUID.fromString("00000000-0000-0000-0000-000000000000")