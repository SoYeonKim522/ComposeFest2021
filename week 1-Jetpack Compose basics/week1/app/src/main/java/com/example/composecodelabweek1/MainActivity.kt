package com.example.composecodelabweek1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composecodelabweek1.ui.theme.ComposeCodelabWeek1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {  //xml 대신 setContent 안에 레이아웃 설정
            ComposeCodelabWeek1Theme {  // 프로젝트명+Theme 으로 자동으로 설정됨
                // A surface container using the 'background' color from the theme
                MyApp()
            }
        }
    }
}

//화면 이동 구현 : onboarding screen -> greetings screen
@Composable
fun MyApp() {
    var shouldShowOnBoarding by remember { mutableStateOf(true) }  //hoist the state
    if (shouldShowOnBoarding){
        OnBoardingScreen(onContinueClicked = {shouldShowOnBoarding = false})  //콜백함수 -> 상태 변경시키기 -> 화면 전환
    } else {
        Greetings()
    }
}



@Composable
fun OnBoardingScreen(onContinueClicked: () -> Unit) {  // callback -> we can mutate the state from MyApp
//    var shouldShowOnBoarding by remember { mutableStateOf(true) }  //by -> shouldShowOnBoarding.value 에서 value 쓸 필요x
    //ㄴ여기에서 상태를 선언하는 것이 아니라 MyApp 에서 해줌 (hoist the state)
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Welcome to the Basics Codelab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked     //버튼 클릭하면 콜백함수 호출
            ) {
                Text(text = "Continue")
            }

        }
    }
}

@Composable
private fun Greetings(names: List<String> = listOf("World", "Compose")) {
    Column(modifier = Modifier.padding(vertical = 20.dp)) {  //전체 레이아웃의 위아래 패딩
        for (name in names){
            Greeting(name = name)
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {  //다른 이름으로 preview
    ComposeCodelabWeek1Theme {
        OnBoardingScreen(onContinueClicked = {})
    }
}

@Composable
fun Greeting(name: String) {
    val expanded = remember { mutableStateOf(false) }  // = 로 remember -> 항상 expanded.value 로 refer 해야함
    val extraPadding = if(expanded.value) 48.dp else 0.dp

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)  //각 아이템의 세로 가로 마진
    ) {  //Surface => background. 배경색을 primary로 바꾸면 Material이 폰트색 자동으로 onPrimary(흰색)로 바꿔줌
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding)) {
                Text(text = "Hello")
                Text(text = name)
            }
            //버튼
            OutlinedButton(
                onClick = { expanded.value = !expanded.value }
            ) {
                Text(if (expanded.value) "Show less" else "Show more")
            }
        }

    }
}


//하나의 파일에 다른 이름을 가진 여러 Preview 가능
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun DefaultPreview() {
    ComposeCodelabWeek1Theme {
//        Greeting("Android**")
//        MyApp()
//        OnBoardingScreen()
        Greetings()
    }
}
