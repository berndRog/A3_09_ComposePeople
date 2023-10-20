@file:Suppress("RemoveCurlyBracesFromTemplate")

package de.rogallab.mobile.domain.utilities

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import de.rogallab.mobile.R
import de.rogallab.mobile.data.io.deleteFileOnInternalStorage
import de.rogallab.mobile.data.io.writeImageToInternalStorage
import de.rogallab.mobile.domain.model.Person

class Seed {
   private val _imagesUri = mutableListOf<String>()

   val person1 = Person("Achim","Arndt","a.arndt@t-online.de","05826 / 1234 5678")
   val person2 = Person("Benno","Bauer","b.bauer@outlook.com","0581 / 987 563")
   val person3 = Person("Christine","Connrad","c.conrad@google.com", "04131 / 234 567")
   val person4 = Person("Dagmar","Deppe","d.deppe@freenet.de", "05191 / 5678 90")
   val person5 = Person("Erika","Erdmann","e.erdmann@icloud.com", "05141 / 4567 89")
   val person6 = Person("Fritz","Fischer","f.fischer@google.com", "05317 / 5678 90")

   val people = listOf(person1, person2, person3, person4, person5, person6)

   fun initializeImages(
      context: Context,
      resources: Resources
   ) {
      // convert the drawables into image files
      val drawables = mutableListOf<Int>()
      drawables.add(0, R.drawable.man_1)
      drawables.add(1,R.drawable.man_2)
      drawables.add(2,R.drawable.man_3)
      drawables.add(3,R.drawable.man_4)
      drawables.add(4,R.drawable.man_5)
      drawables.add(5,R.drawable.woman_1)
      drawables.add(6,R.drawable.woman_2)
      drawables.add(7,R.drawable.woman_3)
      drawables.add(8,R.drawable.woman_4)
      drawables.add(9,R.drawable.woman_5)

      drawables.forEach {
         val bitmap = BitmapFactory.decodeResource(resources, it)
         bitmap?.let { bitmap ->
            writeImageToInternalStorage(context, bitmap)?.let { uriPath:String? ->
               logDebug("ok>SaveImage          .", "Uri $uriPath")
               uriPath?.let {
                  _imagesUri.add(uriPath)
               }
            }
         }
      }

      if(_imagesUri.size == 10) {
         person1.imagePath = _imagesUri[0]
         person2.imagePath = _imagesUri[1]
         person3.imagePath = _imagesUri[5]
         person4.imagePath = _imagesUri[6]
         person5.imagePath = _imagesUri[7]
         person6.imagePath = _imagesUri[3]
      }
   }

   fun disposeImages() {
      _imagesUri.forEach { uriPath ->
         logDebug("ok>disposeImages      .", "Uri $uriPath")
         deleteFileOnInternalStorage(uriPath)
      }
   }
}