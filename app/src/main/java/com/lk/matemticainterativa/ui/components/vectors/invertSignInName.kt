package com.lk.matemticainterativa.ui.components.vectors

fun invertSignInName(name: String): String{
    return if(!name.contains("-")){
        "-${name}"
    } else {
        name.substring(1)
    }

}
fun onlyNameNoSign(name: String): String {
    return if(name.contains("-")){
        name.substring(1)
    } else {
        name
    }
}