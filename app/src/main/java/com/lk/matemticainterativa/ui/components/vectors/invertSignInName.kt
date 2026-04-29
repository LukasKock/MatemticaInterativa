package com.lk.matemticainterativa.ui.components.vectors

fun invertSignInName(name: String): String{
    if(!name.contains("-")){
        return "-${name}"
    } else {
        return name.substring(1)
    }

}