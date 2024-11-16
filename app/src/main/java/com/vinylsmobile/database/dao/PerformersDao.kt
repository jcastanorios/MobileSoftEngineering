package com.vinylsmobile.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vinylsmobile.model.Performer

@Dao
interface PerformersDao {
    @Query("SELECT * FROM performers_table order by name LIMIT :limit")
    suspend fun getPerformers(limit: Int?): List<Performer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(performers: List<Performer>)

    @Query("DELETE FROM performers_table")
    suspend fun clearCache()

}