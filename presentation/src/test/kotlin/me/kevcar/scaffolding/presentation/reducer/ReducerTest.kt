package me.kevcar.scaffolding.presentation.reducer

import me.kevcar.scaffolding.core.entity.ImagePage
import me.kevcar.scaffolding.presentation.model.AppModel
import me.kevcar.scaffolding.presentation.model.Reducer
import org.junit.Test
import kotlin.test.assertEquals

class ReducerTest {

    @Test
    fun testAddPage() {
        val initialState = AppModel.State().copy(emptyList())
        val newPage = ImagePage("foo", 35, emptyList(), 2)
        val action = AppModel.Action.AddPage(newPage)
        val updatedState = Reducer.reduce(initialState, action)

        assertEquals(updatedState.pages.size, 1)
    }

    @Test
    fun testFirstPageOffset() {

        val initialState = AppModel.State().copy(emptyList())
        val newPage = ImagePage("foo", PAGE_SIZE, emptyList(), OFFSET)
        val action = AppModel.Action.AddPage(newPage)
        val updatedState = Reducer.reduce(initialState, action)

        assertEquals(updatedState.pages.first().nextPageOffset, PAGE_SIZE + OFFSET)
    }

    @Test
    fun testNextPageOffset() {
        val firstPage  = ImagePage("foo", PAGE_SIZE, emptyList(), 0)
        val initialState = AppModel.State().copy(listOf(firstPage))
        val newPage = ImagePage("foo", PAGE_SIZE, emptyList(), OFFSET)
        val action = AppModel.Action.AddPage(newPage)
        val updatedState = Reducer.reduce(initialState, action)

        assertEquals(updatedState.pages.last().nextPageOffset, PAGE_SIZE + OFFSET)
    }

    companion object {
        val PAGE_SIZE = 35
        val OFFSET = 2
    }
}
