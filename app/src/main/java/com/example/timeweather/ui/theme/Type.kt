package com.example.timeweather.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.timeweather.R

val TitilliumWeb = FontFamily(
    Font(R.font.titillium_web_bold, FontWeight.Bold),
    Font(R.font.titillium_web_semi_bold, FontWeight.SemiBold),
    Font(R.font.titillium_web_regular)
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleMedium = TextStyle(
        fontFamily = TitilliumWeb,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = TitilliumWeb,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    displayMedium = TextStyle(
        fontFamily = TitilliumWeb,
        fontWeight = FontWeight.SemiBold,
        fontSize = 40.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = TitilliumWeb,
        fontWeight = FontWeight.SemiBold,
        fontSize = 25.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    bodySmall = TextStyle(
        fontFamily = TitilliumWeb,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    titleSmall= TextStyle(
        fontFamily = TitilliumWeb,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
)