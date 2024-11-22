package com.vinylsmobile.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vinylsmobile.database.dao.AlbumsDao
import com.vinylsmobile.database.dao.CollectorsDao
import com.vinylsmobile.database.dao.PerformersDao
import com.vinylsmobile.database.dao.CommentDao
import com.vinylsmobile.model.Album
import com.vinylsmobile.model.Collector
import com.vinylsmobile.model.Comment
import com.vinylsmobile.model.CommentConverter
import com.vinylsmobile.model.Performer
import com.vinylsmobile.model.PerformerTypeConverter
import com.squareup.leakcanary.core.BuildConfig

@Database(entities = [Collector::class, Album::class, Performer::class, Comment::class], version = 1, exportSchema = false)
@TypeConverters(PerformerTypeConverter::class, CommentConverter::class)
abstract class VinylRoomDatabase : RoomDatabase() {
    abstract fun collectorsDao(): CollectorsDao
    abstract fun albumsDao(): AlbumsDao
    abstract fun performersDao(): PerformersDao
    abstract fun commentDao(): CommentDao

    companion object {
        @Volatile
        private var INSTANCE: VinylRoomDatabase? = null

        fun getDatabase(context: Context): VinylRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VinylRoomDatabase::class.java,
                    "vinyls_database"
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }
}