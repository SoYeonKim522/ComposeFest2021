/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//**Compose implementation of a To-do screen that you will build during this codelab.

package com.codelabs.state.todo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelabs.state.util.generateRandomTodoItem
import kotlin.random.Random

/**
 * Stateless component that is responsible for the entire todo screen.
 * (A stateless composable is a composable that cannot directly change any state)
 * editable list 가능 by using a technique called state hoisting.
 * State hoisting is the pattern of moving state up to make a component stateless.
 *
 * @param items (state) list of [TodoItem] to display
 * @param onAddItem (event) request an item be added
 * @param onRemoveItem (event) request an item be removed
 *
 * UI 가 바뀌는 과정
 * : user requests an item be added or removed -> TodoScreen calls onAddItem or onRemoveItem -> the caller of TodoScreen update state
 *   state 가 바뀌면 TodoScreen 가 다시 호출  with the new items and it can display them on screen

 * TodoScreen 을 ViewModel 과 연결시켜줘야 함 (그래서 버튼 이벤트 등 동작) (연결 매개체 : TodoActivityScreen)
 */


@Composable
fun TodoScreen(
    items: List<TodoItem>,
    onAddItem: (TodoItem) -> Unit,
    onRemoveItem: (TodoItem) -> Unit
) {
    Column {
        // add TodoItemInputBackground and TodoItem at the top of TodoScreen
        TodoItemInputBackground(elevate = true, modifier = Modifier.fillMaxWidth()) {
            TodoItemInput(onItemComplete = onAddItem)
            LazyColumn(  //to-do screen 이 호출될 때마다 lazy column 이 recompose
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(top = 8.dp)
            ) {
                items(items = items) {
                    TodoRow(
                        todo = it,
                        onItemClicked = { onRemoveItem(it) },
                        modifier = Modifier.fillParentMaxWidth()
                    )
                }
            }

            // For quick testing, a random item generator button
            Button(
                onClick = { onAddItem(generateRandomTodoItem()) },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            ) {
                Text("Add random item")
            }
        }
    }
}

/**
 * add 버튼을 클릭하면 실제로 to-do item 이 추가되게 하기
 * we need to move the state from the child composable(TodoInputTextField) to the parent (TodoItemInput).
 *   => state hoisting (lift state from a composable to make it stateless)
 *      It is the main pattern to build unidirectional data flow designs
 */

//EditText(=text field) 추가
//This is child composable of TodoItemInput
//@Composable
//fun TodoInputTextField(text: String, onTextChange: (String) -> Unit, modifier: Modifier) { //add a value(text) and onValueChange(onTextChange) parameter
////    val (text, setText) = remember {  // -> to remember itself
////        mutableStateOf("")
////    }
////    TodoInputText(text, setText, modifier)
//    TodoInputText(text, onTextChange, modifier)
//}


//This is parent composable of TodoInputTextField
@Composable
fun TodoItemInput(onItemComplete: (TodoItem) -> Unit) {  //이벤트(onItemComplete)를 인자로 함.
    // onItemComplete is an event will fire when an item (TodoItem) is completed by the user

    val (text, setText) = remember { mutableStateOf("") }  //원래 TodoInputTextField 에 있던 state 인데 부모 안으로 이동
    val (icon, setIcon) = remember { mutableStateOf(TodoIcon.Default)}  //icon 관련 state
    val iconsVisible = text.isNotBlank()   //text 에 따라 달라지는 요소 (TodoItemInput 의 state 는 아님)
    val submit = {
        onItemComplete(TodoItem(text, icon))
        setIcon(TodoIcon.Default)
        setText("")
    }

    Column {
        Row(Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            TodoInputText(
                text = text,
                onTextChange = setText,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                    onImeAction = submit   //pass the submit callback to TodoInputText
            )
            TodoEditButton(
                onClick = submit,    //pass the submit callback to TodoEditButton
                //세줄지움
//                onItemComplete(TodoItem(text))   // send onItemComplete event up
//                setText("")        // clear the internal text
//                setIcon(TodoIcon.Default)  //-> use ICON state in the onclick listener
//                ,
                text = "Add",
                modifier = Modifier.align(Alignment.CenterVertically),
                enabled = text.isNotBlank()    // enable if text is not blank
            )
        }
        if (iconsVisible) {
            AnimatedIconRow(icon, setIcon, Modifier.padding(top = 8.dp))
        } else {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * Stateless composable that displays a full-width [TodoItem].
 *
 * @param todo item to show
 * @param onItemClicked (event) notify caller that the row was clicked
 * @param modifier modifier for this element
 */
@Composable
fun TodoRow(
    todo: TodoItem,
    onItemClicked: (TodoItem) -> Unit,
    modifier: Modifier = Modifier,
    iconAlpha : Float = remember(todo.id) { randomTint() }  //인자 위치로 이동 -> 이걸 적용할지 안할지 바로 컨트롤 가능
) {
    Row(
        modifier
            .clickable { onItemClicked(todo) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(todo.task)     //data.kt에 있는 텍스트
//        val iconAlpha = randomTint()  //-> 아이콘 tint 가 클릭할 때마다 바뀜
//        val iconAlpha : Float = remember(to do.id) { randomTint() }  //to make TodoRow to store the previous value
        Icon(
            imageVector = todo.icon.imageVector,   //data.kt에 있는 아이콘
            tint = LocalContentColor.current.copy(alpha = iconAlpha),
            contentDescription = stringResource(id = todo.icon.contentDescription)
        )
    }
}

private fun randomTint(): Float {
    return Random.nextFloat().coerceIn(0.3f, 0.9f)
}

@Preview
@Composable
fun PreviewTodoItemInput() = TodoItemInput(onItemComplete = { })

@Preview
@Composable
fun PreviewTodoScreen() {
    val items = listOf(
        TodoItem("Learn compose", TodoIcon.Event),
        TodoItem("Take the codelab"),
        TodoItem("Apply state", TodoIcon.Done),
        TodoItem("Build dynamic UIs", TodoIcon.Square)
    )
    TodoScreen(items, {}, {})
}

@Preview
@Composable
fun PreviewTodoRow() {
    val todo = remember { generateRandomTodoItem() }
    TodoRow(todo = todo, onItemClicked = {}, modifier = Modifier.fillMaxWidth())
}
