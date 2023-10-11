package de.rogallab.mobile.ui.people

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import de.rogallab.mobile.domain.model.Person
import de.rogallab.mobile.domain.utilities.Seed
import de.rogallab.mobile.domain.utilities.logDebug

import java.util.*

class PeopleViewModel() : ViewModel() {

   var id: UUID = UUID.randomUUID()

   // State = Observables (DataBinding)
   var firstName: String by mutableStateOf(value = "")
      private set

   fun onFirstNameChange(value: String) {
      firstName = value
   }

   var lastName: String by mutableStateOf(value = "")
      private set

   fun onLastNameChange(value: String) {
      lastName = value
   }

   var email: String? by mutableStateOf(value = null)
      private set

   fun onEmailChange(value: String) {
      email = value
   }

   var phone: String? by mutableStateOf(value = null)
      private set

   fun onPhoneChange(value: String) {
      phone = value
   }

   var imagePath: String? by mutableStateOf(value = null)
      private set

   fun onImagePathChange(value: String?) {
      imagePath = value
   }

   // State errorMessage
   var errorMessage: String? by mutableStateOf(value = null)
      private set

   fun onErrorMessage(value: String) {
      errorMessage = value
   }
   // error handling -> dismissed
   fun onErrorDismiss() {
      logDebug(tag, "onErrorReject()")
   }
   // error handling
   fun onErrorAction() {
      logDebug(tag, "onErrorAction()")
   }

   // mutabelList with observer
   val people: SnapshotStateList<Person> = mutableStateListOf()

   init {
//    errorMessage = "Test SnackBar: Fehlermeldung ..."

   }

   // lifecycle ViewModel
   override fun onCleared() {
      Log.d(tag, "onCleared()")
      super.onCleared()
   }

   fun initialize(seed:Seed): Boolean {
      logDebug(tag,"initialize people from seed")
      return people.addAll(seed.people)
   }

   fun readById(personId: UUID) {
      val person = people.first { it.id == personId }
      setStateFromPerson(person, personId)
      logDebug(tag, "readbyId() ${person.firstName} ${person.lastName}")
   }
   fun add() {
      val person = getPersonFromState()
      logDebug(tag, "add() ${person.firstName} ${person.lastName}")
      people.add(person)
   }
   fun update() {
      val updatedPerson = getPersonFromState()
      val person = people.first { it.id == updatedPerson.id }
      people.remove( person )
      people.add(person)
      logDebug(tag, "update() ${person.firstName} ${person.lastName}")
   }

   fun setStateFromPerson(
      person: Person?,
      personId:UUID = UUID.randomUUID()
   ) {
      id        = person?.id ?: personId
      firstName = person?.firstName ?: ""
      lastName  = person?.lastName ?: ""
      email     = person?.email
      phone     = person?.phone
      imagePath = person?.imagePath
   }

   fun getPersonFromState(): Person =
      Person(firstName, lastName, email, phone, imagePath)

   fun clearState() {
      id        = UUID.randomUUID()
      firstName = ""
      lastName  = ""
      email     = null
      phone     = null
      imagePath = null
   }

   companion object {
      private val tag: String = "ok>PeopleViewModel    ."
   }
}
