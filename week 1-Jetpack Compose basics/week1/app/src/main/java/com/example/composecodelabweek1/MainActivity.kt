package com.example.composecodelabweek1

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composecodelabweek1.ui.theme.ComposeCodelabWeek1Theme


//******** What's new : Lazy Column, rememberSavable, animation
//if activity restarted -> all state is lost
//remember : only as long as the composable is kept in the Composition
//rememberSavable : save each state surviving configuration changes (such as rotations) and process death.
//
//animate~AsState 로 만들어진 애니메이션
//it is interruptible
//if the target value changes, animate~AsState restarts the animation and points to new value


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {  //xml 대신 setContent 안에 레이아웃 설정
            ComposeCodelabWeek1Theme {  // 프로젝트명+Theme 으로 테마 이름이 자동으로 설정되고
                //이 안에 있는 함수는 해당 테마의 UI가 적용됨 (Theme.kt에서 확인 가능)
                MyApp()
            }
        }
    }
}


//화면 이동 구현 : onboarding screen -> greetings screen
@Composable
fun MyApp() {
    var shouldShowOnBoarding by rememberSaveable { mutableStateOf(true) }  //***rememberSavable 로 대체 -> 화면음 rotate 되어도 onboarding screen 으로 돌아오지 않음

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
//set the size and fill list with the value contained in its lambda
private fun Greetings(names: List<String> = List(100) {"$it"}) {   //it => list index
    LazyColumn(modifier = Modifier.padding(vertical = 20.dp)) {
        items(items = names) { name ->    //items import needed
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
    var expanded by remember { mutableStateOf(false) }  //by 키워드로 remember (property delegate) -> 뒤에 value 붙일 필요 x

    val extraPadding by animateDpAsState(       //expanded 되었을 때만 생기도록 만든 extraPadding 변수에 by로 animate 추가
        if(expanded) 48.dp else 0.dp,
        //세부 애니메이션 설정
        animationSpec = spring(   //spring spec 은 duration 등 시 설정은 안됨
            dampingRatio = Spring.DampingRatioMediumBouncy,  //바운스 효과
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)  //각 아이템의 세로 가로 마진
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))) {  //애니메이션 설정 후 padding 최소값 0 으로 지정!! (이거 안하면 앱 크래시)
                Text(text = "Hello")
                Text(text = name,
                    style = MaterialTheme.typography.h5.copy(   //copy -> you can modify a predefined style
                        fontWeight = FontWeight.ExtraBold
                    ))
            }
            //버튼
            OutlinedButton(
                onClick = { expanded = !expanded }
            ) {
                Text(if (expanded) "Show less" else "Show more")
            }
        }

    }
}

@Preview(  //add annotation to the same function -> 프리뷰가 추가됨!
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320, heightDp = 720)
@Composable
fun DefaultPreview() {
    ComposeCodelabWeek1Theme {
//        Greeting("Android**")
//        MyApp()
//        OnBoardingScreen()
        Greetings()
    }
}
