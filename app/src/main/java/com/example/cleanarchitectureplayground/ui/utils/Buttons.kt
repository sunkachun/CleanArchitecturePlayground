package com.example.cleanarchitectureplayground.ui.utils

import com.example.cleanarchitectureplayground.R


data class ButtonItem(val id: Int, val title: String, val iconId: Int)

object Buttons {
    val buttons = listOf(
        ButtonItem(1, "To Do List", R.drawable.ic_dashboard_to_do),
        ButtonItem(2, "Weather", R.drawable.ic_dashboard_button_sunny),
    )
}