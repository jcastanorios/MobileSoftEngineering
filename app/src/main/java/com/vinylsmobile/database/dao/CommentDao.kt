package com.vinylsmobile.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.vinylsmobile.model.Comment

@Dao
interface CommentDao {

    @Insert
    suspend fun insertComment(comment: Comment)

    @Query("SELECT * FROM comments WHERE albumId = :albumId")
    suspend fun getCommentsForAlbum(albumId: Int): List<Comment>
}
