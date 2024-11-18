package com.vinylsmobile

import android.app.Application
import com.vinylsmobile.database.VinylRoomDatabase

class VinylsApplication : Application() {
    val database by lazy { VinylRoomDatabase.getDatabase(this) }
}