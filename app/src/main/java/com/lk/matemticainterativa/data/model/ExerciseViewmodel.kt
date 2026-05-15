package com.lk.matemticainterativa.data.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ExercisesViewModel : ViewModel() {

    var topics by mutableStateOf(emptyList<Topic>())
        private set

    fun completeExercise(topicId: Int, exerciseId: Int) {
        topics = topics.map { topic ->
            if (topic.id == topicId) {
                topic.copy(
                    exercises = topic.exercises.map { ex ->
                        if (ex.id == exerciseId) ex.copy(isCompleted = true)
                        else ex
                    }
                )
            } else topic
        }
    }
}
