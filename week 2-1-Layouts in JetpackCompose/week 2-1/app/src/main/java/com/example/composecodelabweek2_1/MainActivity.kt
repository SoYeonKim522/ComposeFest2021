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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCodelabWeek21Theme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    PhotographerCard()
                }
            }
        }
    }
}

@Composable
fun PhotographerCard(modifier: Modifier = Modifier) {    //함수의 인자로 empty modifier 를 주면 -> more flexible (enable the caller to modify composables) (이해???)
    Row(modifier                            //이 함수의 root composable 의 인자로 넣기
        .padding(8.dp)                      //래플에 포함 안되는 패딩 범위
        .clip(RoundedCornerShape(4.dp))
        .background(MaterialTheme.colors.surface)
        .clickable(onClick = {  })          //modifier chaining 할 때 순서도 상관이 있음
        .padding(16.dp)                     //(clickable 설정 후 패딩 주면 패딩 범위까지 모두 래플. 그 반대 순서면 패딩 범위는 래플에 포함x)
    ) {
        Surface (
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {
            //이미지 위치

        }
        Column (
            modifier = Modifier                     //modifier 는 () 안에 위치하는 것임
                .padding(start = 8.dp)              //왼쪽에 패딩 주는 방법
                .align(Alignment.CenterVertically)  //center vertical!!
            ){
            Text(text = "Alfred Sisley", fontWeight = FontWeight.Bold)  //text는 따로 modifier x
            //LocalContentAlpha -> 자식의 투명도 조절 가능
            //CompositionLocalProvider 를 통해 데이터 전달해서 ContentAlpha.medium 에 접근
            CompositionLocalProvider(LocalContentAlpha provides  ContentAlpha.medium) { //medium 의 투명도 지정. (disabled는 더 옅어짐)
                Text(text = "3 minutes ago", style = MaterialTheme.typography.body2)
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PhotographerCardPreview() {
    ComposeCodelabWeek21Theme {
        PhotographerCard()
    }
}