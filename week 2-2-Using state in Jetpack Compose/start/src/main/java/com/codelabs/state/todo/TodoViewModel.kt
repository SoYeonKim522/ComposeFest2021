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

//** A ViewModel that you will integrate with Compose to build the To-do screen.
// You will connect it to Compose and extend it to add more features as you complete this codelab.

package com.codelabs.state.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//We want to use this ViewModel to hoist the state from TodoScreen(Activity)

class TodoViewModel : ViewModel() {

    // state: todoItems
    var todoItems = mutableStateListOf<TodoItem>()  //bc mutableList is observable
        private set   //restricting to a private setter only visible inside the ViewModel

    // event: addItem
    fun addItem(item: TodoItem) {
        todoItems.add(item)
    }

    // event: removeItem
    fun removeItem(item: TodoItem) {
        todoItems.remove(item)
        onEditDone() // shouldn't keep the editor open when removing items
    }


    //<--editor 관련 state 추가하기-->
    // private state
    private var currentEditPosition by mutableStateOf(-1)

    // state: currentEditItem - 이게 바뀌면 recomposition
    val currentEditItem: TodoItem?
        get() = todoItems.getOrNull(currentEditPosition)

    // event: onEditItemSelected
    fun onEditItemSelected(item: TodoItem) {
        currentEditPosition = todoItems.indexOf(item)
    }

    // event: onEditDone
    fun onEditDone() {
        currentEditPosition = -1
    }

    // event: onEditItemChange - update the list at currentEditPosition
    fun onEditItemChange(item: TodoItem) {
        val currentItem = requireNotNull(currentEditItem)
        require(currentItem.id == item.id) {
            "You can only change an item with the same id as currentEditItem"
        }
        todoItems[currentEditPosition] = item
    }

}
