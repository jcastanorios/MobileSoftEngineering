package com.vinylsmobile.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vinylsmobile.model.Collector

@Dao
interface CollectorsDao {
    @Query("SELECT * FROM collectors_table")
    suspend fun getCollectors():List<Collector>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(collectors: List<Collector>)
}