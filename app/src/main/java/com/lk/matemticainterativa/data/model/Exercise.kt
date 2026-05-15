package com.lk.matemticainterativa.data.model

data class Exercise(
    val id: Int,
    val topicId: Int,
    val isCompleted: Boolean = false
)

data class Topic(
    val id: Int,
    val name: String,
    val exercises: List<Exercise>
)
