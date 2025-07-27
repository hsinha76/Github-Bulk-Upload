package com.hsdroid.bulkrepouploadforgithub.ui.common

import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.IMPORTANT
import javax.swing.JOptionPane

fun showInfoDialog(message: String) {
    JOptionPane.showMessageDialog(
        null,
        message,
        IMPORTANT,
        JOptionPane.INFORMATION_MESSAGE
    )
}
