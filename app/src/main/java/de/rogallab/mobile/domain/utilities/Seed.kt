package de.rogallab.mobile.domain.utilities

import de.rogallab.mobile.domain.model.Person

object Seed {

   val person1 = Person("Achim","Arndt","a.arndt@t-online.de","05826 / 1234 5678")
   val person2 = Person("Benno","Bauer","b.bauer@outlook.com","0581 / 1234 5678")
   val person3 = Person("Christine","Connrad","c.conrad@google.com")
   val person4 = Person("Dagmar","Deppe","d.deppe@freenet.de")
   val person5 = Person("Erika","Erdmann","e.erdmann@icloud.com")
   val person6 = Person("Fritz","Fischer","f.fischer@google.com")

   val people = listOf(person1, person2, person3, person4, person5, person6)

}