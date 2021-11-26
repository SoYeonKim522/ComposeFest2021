package com.codelab.theming.ui.start.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


//나만의 커스텀 테마 composable 만들기
//어디에서든 가져와 사용 가능

@Composable
fun JetnewsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),  //dark theme 적용 위해 추가한 인자 (두번째줄에 놓으면 에러;;)
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,   //커스텀 테마에 커스텀 색상 적용하기
        typography = JetnewsTypography,  //Typography.kt 생성 후 커스텀 테마에 글꼴 적용하기
        shapes = JetnewsShapes,  //Shape.kt 생성 후 커스텀 테마에 도형 적용하기
        content = content
    )
}

//Color.kt 생성 후, 그 파일에 넣은 색상들 가져오기
private val LightColors = lightColors(
    primary = Red700,
    primaryVariant = Red900,
    onPrimary = Color.White,
    secondary = Red700,
    secondaryVariant = Red900,
    onSecondary = Color.White,
    error = Red800
)

//Color.kt 에 넣은 dark theme 용 색상들 가져오기
private val DarkColors = darkColors(
    primary = Red300,
    primaryVariant = Red700,
    onPrimary = Color.Black,
    secondary = Red300,
    onSecondary = Color.Black,
    error = Red200
)