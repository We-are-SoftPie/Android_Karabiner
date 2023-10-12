package com.softpie.karabiner.ui.theme

import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.softpie.karabiner.R

val pretendard = FontFamily(
    Font(R.font.pretendard_thin, FontWeight.Thin),
    Font(R.font.pretendard_extra_light, FontWeight.ExtraLight),
    Font(R.font.pretendard_light, FontWeight.Light),
    Font(R.font.pretendard_regular, FontWeight.Normal),
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_semi_bold, FontWeight.SemiBold),
    Font(R.font.pretendard_bold, FontWeight.Bold),
    Font(R.font.pretendard_extra_bold, FontWeight.ExtraBold),
    Font(R.font.pretendard_black, FontWeight.Black),
)
// Set of Material typography styles to start with

object KarabinerTypography {

    @Stable
    val body1 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp
    )
    @Stable
    val body2 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    )
    @Stable
    val body3 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp
    )
    @Stable
    val title1 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    )
    @Stable
    val title2 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp
    )
    @Stable
    val title3 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    )

    @Stable
    val head1 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        lineHeight = 34.sp,
        letterSpacing = 0.sp
    )

    @Stable
    val head2 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    )

    @Stable
    val head3 = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.sp
    )

}
@Composable
fun Body1(
    text: String,
    textColor: Color = KarabinerTheme.color.Black,
    modifier: Modifier = Modifier,
    maxLines: Int = 15,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        color = textColor,
        modifier = modifier,
        style = KarabinerTypography.body1,
        maxLines = maxLines,
        overflow = overflow
    )
}
@Composable
fun Body2(
    text: String,
    textColor: Color = KarabinerTheme.color.Black,
    modifier: Modifier = Modifier,
    maxLines: Int = 15,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        color = textColor,
        modifier = modifier,
        style = KarabinerTypography.body2,
        maxLines = maxLines,
        overflow = overflow
    )
}
@Composable
fun Body3(
    text: String,
    textColor: Color = KarabinerTheme.color.Black,
    modifier: Modifier = Modifier,
    maxLines: Int = 15,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        color = textColor,
        modifier = modifier,
        style = KarabinerTypography.body3,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun Title1(
    text: String,
    textColor: Color = KarabinerTheme.color.Black,
    modifier: Modifier = Modifier,
    maxLines: Int = 15,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        color = textColor,
        modifier = modifier,
        style = KarabinerTypography.title1,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun Title2(
    text: String,
    textColor: Color = KarabinerTheme.color.Black,
    modifier: Modifier = Modifier,
    maxLines: Int = 15,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        color = textColor,
        modifier = modifier,
        style = KarabinerTypography.title2,
        maxLines = maxLines,
        overflow = overflow
    )
}
@Composable
fun Title3(
    text: String,
    textColor: Color = KarabinerTheme.color.Black,
    modifier: Modifier = Modifier,
    maxLines: Int = 15,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        color = textColor,
        modifier = modifier,
        style = KarabinerTypography.title3,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun Head1(
    text: String,
    textColor: Color = KarabinerTheme.color.Black,
    modifier: Modifier = Modifier,
    maxLines: Int = 15,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        color = textColor,
        modifier = modifier,
        style = KarabinerTypography.head1,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun Head2(
    text: String,
    textColor: Color = KarabinerTheme.color.Black,
    modifier: Modifier = Modifier,
    maxLines: Int = 15,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        color = textColor,
        modifier = modifier,
        style = KarabinerTypography.head2,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun Head3(
    text: String,
    textColor: Color = KarabinerTheme.color.Black,
    modifier: Modifier = Modifier,
    maxLines: Int = 15,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        color = textColor,
        modifier = modifier,
        style = KarabinerTypography.head3,
        maxLines = maxLines,
        overflow = overflow
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun Gradient1(
    text: String,
    textColor: Color = KarabinerTheme.color.Black,
    modifier: Modifier = Modifier,
    maxLines: Int = 15,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        color = textColor,
        modifier = modifier,
        style = KarabinerTypography.head2.copy(brush = gradient),
        maxLines = maxLines,
        overflow = overflow
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun Gradient2(
    text: String,
    textColor: Color = KarabinerTheme.color.Black,
    modifier: Modifier = Modifier,
    maxLines: Int = 15,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        color = textColor,
        modifier = modifier,
        style = KarabinerTypography.title3.copy(brush = gradient),
        maxLines = maxLines,
        overflow = overflow
    )
}


val LocalTypography = staticCompositionLocalOf { KarabinerTypography }