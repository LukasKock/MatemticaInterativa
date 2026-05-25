package com.lk.matemticainterativa.ui.components.vectors

fun scaleVectorKeepStart(
    vector: VectorPoints,
    zoom: Float
): VectorPoints {

    return vector.copy(
        endPoint =
            vector.startPoint +
                    (vector.endPoint - vector.startPoint) * zoom
    )
}