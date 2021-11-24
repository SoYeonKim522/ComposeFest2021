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

package com.codelabs.state.todo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.codelabs.state.ui.StateCodelabTheme

/**
 Week 2-2 : Building an interactive To-do app using unidirectional data flow in compose !!

  * TodoScreen 을 ViewModel 과 연결시켜줘야 하는데 (그래서 버튼 이벤트 등 동작)
    This composable will be a bridge between the state stored in our ViewModel and the TodoScreen composable that's already defined in the project.

 */

class TodoActivity : AppCompatActivity() {

    val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StateCodelabTheme {
                Surface {
                    //build the screen in compose (integrating TodoScreen into TodoActivity)
                    TodoActivityScreen(todoViewModel)
                }
            }
        }
    }
}

//TodoScreen 을 ViewModel 과 연결하는 부분
@Composable
private fun TodoActivityScreen(todoViewModel: TodoViewModel) {
    TodoScreen(
        items = todoViewModel.todoItems,
        onAddItem = todoViewModel::addItem,
        onRemoveItem = todoViewModel::removeItem,
        //추가 - todoScreen 이 업데이트 되었으니 필요함
        currentlyEditing = todoViewModel.currentEditItem,
        onStartEdit = todoViewModel::onEditItemSelected,
        onEditItemChange = todoViewModel::onEditItemChange,
        onEditDone = todoViewModel::onEditDone
    )
}