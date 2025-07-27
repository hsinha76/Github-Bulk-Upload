package com.hsdroid.bulkrepouploadforgithub.ui.intro

import CustomBlue
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.APP_NAME
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.CONTINUE_TXT
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.SUB_TITLE
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun IntroScreen(onNextClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = androidx.compose.ui.graphics.Color.White).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(APP_NAME, style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            SUB_TITLE,
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            onNextClick()
        }, content = {
            Text(text = CONTINUE_TXT, fontSize = 12.sp)
        }, shape = CircleShape, colors = ButtonDefaults.buttonColors(containerColor = CustomBlue))
    }
}
