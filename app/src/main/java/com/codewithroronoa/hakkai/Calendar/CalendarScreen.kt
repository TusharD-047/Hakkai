package com.codewithroronoa.hakkai.Calendar

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CalendarScreen(modifier: Modifier = Modifier) {
    Text(text = "Calendar",modifier= Modifier.fillMaxSize(), textAlign = TextAlign.Center)
}