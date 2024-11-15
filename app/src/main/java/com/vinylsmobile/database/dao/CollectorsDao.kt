package com.vinylsmobile.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.vinylsmobile.model.Collector

@Dao
interface CollectorsDao {
    @Query("SELECT * FROM collectors_table")
    fun getCollectors():List<Collector>
}