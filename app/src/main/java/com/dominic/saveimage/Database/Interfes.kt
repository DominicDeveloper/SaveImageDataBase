package com.dominic.saveimage.Database

import com.dominic.saveimage.ImageUser

interface Interfes {
    fun insertImage(imageUser: ImageUser)
    fun getAllImage():List<ImageUser>
}