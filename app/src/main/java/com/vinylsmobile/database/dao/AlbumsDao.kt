package com.vinylsmobile.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vinylsmobile.model.Album

@Dao
interface AlbumsDao {
    @Query("SELECT * FROM albums_table order by name LIMIT :limit")
    suspend fun getAlbums(limit: Int): List<Album>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<Album>)

    @Query("DELETE FROM albums_table")
    suspend fun clearCache()

}