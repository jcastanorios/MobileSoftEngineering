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
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(
    entities = [Collector::class, Album::class, Performer::class],
    version = 2,
    exportSchema = false
)
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
            context.deleteDatabase("vinyls_database")
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