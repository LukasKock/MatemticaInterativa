package com.lk.matemticainterativa.ui.components.auxiliary

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.lk.matemticainterativa.MainActivity

@Composable
fun NextQuestionButton(visible: Boolean,
                       navController: NavController){
    if(!visible) return
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val route = navBackStackEntry?.destination?.route
    val activity = route?.substringBefore("/")
    val activityNumber = route?.substringAfter("/")


    Box(modifier = Modifier.fillMaxSize()){
        Button(
            modifier = Modifier.padding(60.dp).align(Alignment.BottomCenter),
            onClick = {
                if(activity == "triangles"){
                    if(activityNumber?.toIntOrNull() == MainActivity.numberOfTriangleActivities){
                        navController.navigate("${activity}/")
                    }
                    else {
                        navController.navigate("$activity/" + (activityNumber?.toIntOrNull()?.plus(1)))
                    }
                } else if(activity == "vectors"){
                    if(activityNumber?.toIntOrNull() == MainActivity.numberOfVectorActivities){
                        navController.navigate("${activity}/")
                    }
                    else {
                        navController.navigate("$activity/" + (activityNumber?.toIntOrNull()?.plus(1)))
                    }

                } else {
                    navController.navigate("main/")
                }

            }
        ){
            Text(
                text = if(activityNumber?.toIntOrNull() == MainActivity.numberOfTriangleActivities
                    || activityNumber?.toIntOrNull() == MainActivity.numberOfTriangleActivities){
                    "Finalizar"
                } else {"Próxima pergunta"}
            )
        }
    }
}