package com.lk.matemticainterativa.ui.components.vectors

fun setVectorScale(
    vector: VectorPoints,
    scale: Float,
    initialVector: VectorPoints
): VectorPoints {

    val originalLength = initialVector.length()
    val direction = vector.direction()


    return vector.copy(
        endPoint =
            vector.startPoint +
                    direction * (originalLength * scale)
    )
}