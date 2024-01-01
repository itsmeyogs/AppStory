package com.dicoding.appstory

import com.dicoding.appstory.data.remote.response.ListStoryItem

object DataDummy {

    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                "https://example.com/photo$i.jpg",
                "2023-11-05T12:34:56+$i",
                "story $i",
                "description $1",
                10.0 ,
                "id_+$i",
                10.0
            )
            items.add(story)
        }
        return items
    }
}