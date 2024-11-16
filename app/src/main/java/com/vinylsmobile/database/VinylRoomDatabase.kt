package com.vinylsmobile.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vinylsmobile.database.dao.AlbumsDao
import com.vinylsmobile.database.dao.CollectorsDao
import com.vinylsmobile.database.dao.PerformersDao
import com.vinylsmobile.model.Album
import com.vinylsmobile.model.Collector
import com.vinylsmobile.model.Performer
import com.vinylsmobile.model.PerformerTypeConverter

@Database(entities = [Collector::class, Album::class, Performer::class], version = 1, exportSchema = false)
@TypeConverters(PerformerTypeConverter::class)
abstract class VinylRoomDatabase : RoomDatabase() {
    abstract fun collectorsDao(): CollectorsDao
    abstract fun albumsDao(): AlbumsDao
    abstract fun performersDao(): PerformersDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: VinylRoomDatabase? = null

        fun getDatabase(context: Context): VinylRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VinylRoomDatabase::class.java,
                    "vinyls_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}