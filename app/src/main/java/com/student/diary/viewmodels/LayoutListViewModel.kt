package com.student.diary.viewmodels

import android.app.Application
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.student.diary.R


/**
 * ViewModel for providing a list of layouts.
 *
 * @param application The application context.
 * @param state Saved state handle for ViewModel persistence.
 */
class LayoutListViewModel(application: Application, private val state: SavedStateHandle) :
    AndroidViewModel(application) {

    /**
     * Retrieves a list of available layouts.
     *
     * @return List of Layout objects.
     */
    fun getLayoutList(): List<Layout> {
        return Layout.values().toList()
    }

    /**
     * Enum class defining different layout types with associated icon and text resources.
     *
     * @property iconId Drawable resource ID for the layout icon.
     * @property textId String resource ID for the layout text.
     */
    enum class Layout(
        @DrawableRes val iconId: Int,
        @StringRes val textId: Int,
    ) {
        CONTACT(R.drawable.contact, R.string.contact),
        COURSES(R.drawable.course, R.string.courses),
        EVENTS(R.drawable.events, R.string.events),
        NEWS(R.drawable.news, R.string.news)
    }

}