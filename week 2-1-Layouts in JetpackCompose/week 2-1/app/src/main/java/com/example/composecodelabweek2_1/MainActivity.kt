package com.example.composecodelabweek2_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.composecodelabweek2_1.ui.theme.ComposeCodelabWeek21Theme
import kotlinx.coroutines.launch

//WEEK 2-1 : LAYOUTS CODE LAB
//WHAT'S NEW : List
    //일반 Column 으로 구현한 리스트 : 모든 아이템을 렌더링함
    //보이는 화면 구간에 있는 것만 렌더링 시키는 리스트가 lazy list : lazy column 으로 구현

//CoroutineScope



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCodelabWeek21Theme {
                ScrollingList()
            }
        }
    }
}

@Composable
fun ScrollingList(){
    val listSize = 100

    //scrolling position 저장
    val scrollState = rememberLazyListState()

    //스크롤을 외부에서 컨트롤 해보기 : 버튼 클릭하면 특정위치로 스크롤
    //coroutine scope (where our animated scroll will be executed) 저장
    val coroutineScope = rememberCoroutineScope()

    //버튼 두 개 추가
    Column {
        Row {
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(0) // position 0으로 스크롤해라
                }
            }) {
                Text("Scroll to the top")
            }

            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(listSize - 1)
                }
            }) {
                Text("Scroll to the end")
            }
        }
        //리스트 부분
        LazyColumn(state = scrollState) {
            items(listSize) {
                ImageListItem(index = it)
            }
        }
    }

}

//리스트 내 각 아이템
@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberImagePainter(
            data = "https://developer.android.com/images/brand/Android_Robot.png"
            ),
            contentDescription = "Android Logo",
            modifier = Modifier
                .size(50.dp)
                .padding(start = 20.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text("Item #$index", style = MaterialTheme.typography.subtitle1)
    }
}


@Preview
@Composable
fun LayoutsCodelabPreview() {
    ComposeCodelabWeek21Theme {
        ScrollingList()
    }
}