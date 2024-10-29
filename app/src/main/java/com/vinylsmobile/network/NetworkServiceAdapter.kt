package com.vinylsmobile.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError

import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

import com.vinylsmobile.data.model.Album
import com.vinylsmobile.data.model.Comment
import com.vinylsmobile.data.model.Performer
import com.vinylsmobile.data.model.Track


class NetworkServiceAdapter private constructor(context: Context) {
    companion object {
        private const val BASE_URL = "https://backvynils-q6yc.onrender.com/"
        @Volatile private var instance: NetworkServiceAdapter? = null

        fun getInstance(context: Context): NetworkServiceAdapter =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also { instance = it }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun getAlbums(onComplete: (List<Album>) -> Unit, onError: (VolleyError) -> Unit) {
        requestQueue.add(getRequest("albums",
            { response ->
                val albums = parseAlbums(response)
                onComplete(albums)
            },
            { error -> onError(error) }
        ))
    }


    private fun getRequest(path: String, onResponse: (String) -> Unit, onError: (VolleyError) -> Unit): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL + path, onResponse, onError)
    }

    private fun parseAlbums(response: String): List<Album> {
        val respArray = JSONArray(response)
        val list = mutableListOf<Album>()
        for (i in 0 until respArray.length()) {
            val item = respArray.getJSONObject(i)
            list.add(
                Album(
                    id = item.getInt("id"),
                    name = item.getString("name"),
                    cover = item.getString("cover"),
                    releaseDate = item.getString("releaseDate"),
                    description = item.getString("description"),
                    genre = item.getString("genre"),
                    recordLabel = item.getString("recordLabel"),
                    //tracks = parseTracks(item.getJSONArray("tracks")),
                    //performers = parsePerformers(item.getJSONArray("performers")),
                    //comments = parseComments(item.getJSONArray("comments"))
                )
            )
        }
        return list
    }

    private fun parseTracks(jsonArray: JSONArray): List<Track> {
        val trackList = mutableListOf<Track>()
        for (i in 0 until jsonArray.length()) {
            val trackJson = jsonArray.getJSONObject(i)
            val track = Track(
                id = trackJson.getInt("id"),
                name = trackJson.getString("name"),
                duration = trackJson.getString("duration")
            )
            trackList.add(track)
        }
        return trackList
    }

    private fun parsePerformers(jsonArray: JSONArray): List<Performer> {
        val performerList = mutableListOf<Performer>()
        for (i in 0 until jsonArray.length()) {
            val performerJson = jsonArray.getJSONObject(i)
            val performer = Performer(
                id = performerJson.getInt("id"),
                name = performerJson.getString("name"),
                image = performerJson.getString("image"),
                description = performerJson.getString("description"),
                birthDate = performerJson.getString("birthDate")
            )
            performerList.add(performer)
        }
        return performerList
    }

    private fun parseComments(jsonArray: JSONArray): List<Comment> {
        val commentList = mutableListOf<Comment>()
        for (i in 0 until jsonArray.length()) {
            val commentJson = jsonArray.getJSONObject(i)
            val comment = Comment(
                id = commentJson.getInt("id"),
                description = commentJson.getString("description"),
                rating = commentJson.getInt("rating")
            )
            commentList.add(comment)
        }
        return commentList
    }

}