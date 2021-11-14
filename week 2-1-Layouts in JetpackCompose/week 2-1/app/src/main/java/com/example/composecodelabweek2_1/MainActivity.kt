package com.example.composecodelabweek2_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composecodelabweek2_1.ui.theme.ComposeCodelabWeek21Theme

//WEEK 2-1 : LAYOUTS CODE LAB
//WHAT'S NEW : Scaffold API

    //it is the most high-level composable
    //provides slots for the most common top-level Material components such as TopAppBar, BottomAppBar, FloatingActionButton and Drawer.


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCodelabWeek21Theme {
                LayoutsCodelab()
            }
        }
    }
}

@Composable
fun LayoutsCodelab() {
    Scaffold(     //Scaffold has a slot for a top AppBar
        topBar = {
            // 이 안에서 자유롭게 we can fill the slot with any composable we want
            TopAppBar(  //app bar 넣기
                //TopAppBar composable has slots for a title, navigation icon, actions.)
                title = {
                    Text(text = "Layouts Codelab")
                },
                actions = {  //normally actions modify the state of your app
                    Row {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Filled.Facebook, contentDescription = null)  // 아이콘 넣기
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Filled.TravelExplore, contentDescription = null)
                        }

                    }
                }

            )

//            Text(  //TopAppBar 말고 topBar 안에 이렇게 아무거나 넣을 수도 있음
//                text = "LayoutsCodelab",
//                style = MaterialTheme.typography.h3
//            )

        }
    ) { innerPadding ->                  //람다가 innerPadding 을 인자로 받고
        BodyContent(modifier = Modifier
            .padding(innerPadding)       //이 innerPadding 을 root composable에 한번에 적용
            .padding(8.dp))              // + bodyContent 에 padding 추가로 적용
    }
}

//함수 쪼개기
//새로운 composable 함수를 만들 때 아래와 같이 default Modifier 을 modifier 인자로 갖는게 좋음 -> reusable composable
@Composable
fun BodyContent(modifier: Modifier = Modifier) {  //modifier 을 인자로 함
    Column(modifier = modifier) {
        Text(text = "Hi there!")
        Text(text = "I'm excited for going through the Layouts codelab")
    }
}


@Preview
@Composable
fun LayoutsCodelabPreview() {
    ComposeCodelabWeek21Theme {
        LayoutsCodelab()
    }
}