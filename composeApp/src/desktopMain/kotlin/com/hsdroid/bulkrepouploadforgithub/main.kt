package com.hsdroid.bulkrepouploadforgithub

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.APP_NAME

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = APP_NAME,
        resizable = false
    ) {
        App()
    }
}