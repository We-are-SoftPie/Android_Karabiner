package com.softpie.karabiner.component.theme

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.softpie.karabiner.R
import com.softpie.karabiner.component.modifier.karaClickable

private val pretendard = FontFamily(
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

@OptIn(ExperimentalTextApi::class)
object KarabinerTypography {

    @Stable
    val title = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
    )

    @Stable
    val headline = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
    )

    @Stable
    val body = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
    )

    @Stable
    val label = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
    )

    @Stable
    val caption = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun Title(
    modifier: Modifier = Modifier,
    text: String,
    karabinerable: Boolean = false,
    textColor: Color = LocalContentColor.current,
    textAlign: TextAlign = TextAlign.Start,
    textDecoration: TextDecoration? = null,
    textOverflow: TextOverflow = TextOverflow.Clip,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    rippleColor: Color = Color.Unspecified,
    rippleEnable: Boolean = false,
    bounded: Boolean = true,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onClick: (() -> Unit)? = null,
) {
    val style = if (karabinerable) KarabinerTypography.title.copy(brush = gradient)
    else KarabinerTypography.title
    Text(
        modifier = modifier.karaClickable(
            onClick = onClick,
            interactionSource = interactionSource,
            rippleColor = rippleColor,
            rippleEnable = rippleEnable,
            bounded = bounded
        ),
        text = text,
        style = style,
        color = textColor,
        textAlign = textAlign,
        textDecoration = textDecoration,
        overflow = textOverflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun Headline(
    modifier: Modifier = Modifier,
    text: String,
    karabinerable: Boolean = false,
    textColor: Color = LocalContentColor.current,
    textAlign: TextAlign = TextAlign.Start,
    textDecoration: TextDecoration? = null,
    textOverflow: TextOverflow = TextOverflow.Clip,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    rippleColor: Color = Color.Unspecified,
    rippleEnable: Boolean = false,
    bounded: Boolean = true,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onClick: (() -> Unit)? = null,
) {
    val style = if (karabinerable) KarabinerTypography.headline.copy(brush = gradient)
    else KarabinerTypography.headline
    Text(
        modifier = modifier.karaClickable(
            onClick = onClick,
            interactionSource = interactionSource,
            rippleColor = rippleColor,
            rippleEnable = rippleEnable,
            bounded = bounded
        ),
        text = text,
        style = style,
        color = textColor,
        textAlign = textAlign,
        textDecoration = textDecoration,
        overflow = textOverflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun Body(
    modifier: Modifier = Modifier,
    text: String,
    karabinerable: Boolean = false,
    textColor: Color = LocalContentColor.current,
    textAlign: TextAlign = TextAlign.Start,
    textDecoration: TextDecoration? = null,
    textOverflow: TextOverflow = TextOverflow.Clip,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    rippleColor: Color = Color.Unspecified,
    rippleEnable: Boolean = false,
    bounded: Boolean = true,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onClick: (() -> Unit)? = null,
) {
    val style = if (karabinerable) KarabinerTypography.body.copy(brush = gradient)
    else KarabinerTypography.body
    Text(
        modifier = modifier.karaClickable(
            onClick = onClick,
            interactionSource = interactionSource,
            rippleColor = rippleColor,
            rippleEnable = rippleEnable,
            bounded = bounded
        ),
        text = text,
        style = style,
        color = textColor,
        textAlign = textAlign,
        textDecoration = textDecoration,
        overflow = textOverflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun Label(
    modifier: Modifier = Modifier,
    text: String,
    karabinerable: Boolean = false,
    textColor: Color = LocalContentColor.current,
    textAlign: TextAlign = TextAlign.Start,
    textDecoration: TextDecoration? = null,
    textOverflow: TextOverflow = TextOverflow.Clip,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    rippleColor: Color = Color.Unspecified,
    rippleEnable: Boolean = false,
    bounded: Boolean = true,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onClick: (() -> Unit)? = null,
) {
    val style = if (karabinerable) KarabinerTypography.label.copy(brush = gradient)
    else KarabinerTypography.label
    Text(
        modifier = modifier.karaClickable(
            onClick = onClick,
            interactionSource = interactionSource,
            rippleColor = rippleColor,
            rippleEnable = rippleEnable,
            bounded = bounded
        ),
        text = text,
        style = style,
        color = textColor,
        textAlign = textAlign,
        textDecoration = textDecoration,
        overflow = textOverflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun Caption(
    modifier: Modifier = Modifier,
    text: String,
    karabinerable: Boolean = false,
    textColor: Color = LocalContentColor.current,
    textAlign: TextAlign = TextAlign.Start,
    textDecoration: TextDecoration? = null,
    textOverflow: TextOverflow = TextOverflow.Clip,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    rippleColor: Color = Color.Unspecified,
    rippleEnable: Boolean = false,
    bounded: Boolean = true,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onClick: (() -> Unit)? = null,
) {
    val style = if (karabinerable) KarabinerTypography.caption.copy(brush = gradient)
    else KarabinerTypography.caption
    Text(
        modifier = modifier.karaClickable(
            onClick = onClick,
            interactionSource = interactionSource,
            rippleColor = rippleColor,
            rippleEnable = rippleEnable,
            bounded = bounded
        ),
        text = text,
        style = style,
        color = textColor,
        textAlign = textAlign,
        textDecoration = textDecoration,
        overflow = textOverflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun BoldBody(
    modifier: Modifier = Modifier,
    text: String,
    karabinerable: Boolean = false,
    textColor: Color = LocalContentColor.current,
    textAlign: TextAlign = TextAlign.Start,
    textDecoration: TextDecoration? = null,
    textOverflow: TextOverflow = TextOverflow.Clip,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    rippleColor: Color = Color.Unspecified,
    rippleEnable: Boolean = false,
    bounded: Boolean = true,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onClick: (() -> Unit)? = null,
) {
    val style = if (karabinerable) KarabinerTypography.body.copy(
        brush = gradient,
        fontWeight = FontWeight.Bold
    )
    else KarabinerTypography.body.copy(
        fontWeight = FontWeight.Bold
    )
    Text(
        modifier = modifier.karaClickable(
            onClick = onClick,
            interactionSource = interactionSource,
            rippleColor = rippleColor,
            rippleEnable = rippleEnable,
            bounded = bounded
        ),
        text = text,
        style = style,
        color = textColor,
        textAlign = textAlign,
        textDecoration = textDecoration,
        overflow = textOverflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
    )
}


@OptIn(ExperimentalTextApi::class)
@Composable
fun BoldHeadline(
    modifier: Modifier = Modifier,
    text: String,
    karabinerable: Boolean = false,
    textColor: Color = LocalContentColor.current,
    textAlign: TextAlign = TextAlign.Start,
    textDecoration: TextDecoration? = null,
    textOverflow: TextOverflow = TextOverflow.Clip,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    rippleColor: Color = Color.Unspecified,
    rippleEnable: Boolean = false,
    bounded: Boolean = true,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onClick: (() -> Unit)? = null,
) {
    val style = if (karabinerable) KarabinerTypography.headline.copy(
        brush = gradient,
        fontWeight = FontWeight.Bold
    )
    else KarabinerTypography.headline.copy(
        fontWeight = FontWeight.Bold
    )
    Text(
        modifier = modifier.karaClickable(
            onClick = onClick,
            interactionSource = interactionSource,
            rippleColor = rippleColor,
            rippleEnable = rippleEnable,
            bounded = bounded
        ),
        text = text,
        style = style,
        color = textColor,
        textAlign = textAlign,
        textDecoration = textDecoration,
        overflow = textOverflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun BoldTitle(
    modifier: Modifier = Modifier,
    text: String,
    karabinerable: Boolean = false,
    textColor: Color = LocalContentColor.current,
    textAlign: TextAlign = TextAlign.Start,
    textDecoration: TextDecoration? = null,
    textOverflow: TextOverflow = TextOverflow.Clip,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    rippleColor: Color = Color.Unspecified,
    rippleEnable: Boolean = false,
    bounded: Boolean = true,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onClick: (() -> Unit)? = null,
) {
    val style = if (karabinerable) KarabinerTypography.title.copy(
        brush = gradient,
        fontWeight = FontWeight.Bold
    )
    else KarabinerTypography.title.copy(
        fontWeight = FontWeight.Bold
    )
    Text(
        modifier = modifier.karaClickable(
            onClick = onClick,
            interactionSource = interactionSource,
            rippleColor = rippleColor,
            rippleEnable = rippleEnable,
            bounded = bounded
        ),
        text = text,
        style = style,
        color = textColor,
        textAlign = textAlign,
        textDecoration = textDecoration,
        overflow = textOverflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun BoldLabel(
    modifier: Modifier = Modifier,
    text: String,
    karabinerable: Boolean = false,
    textColor: Color = LocalContentColor.current,
    textAlign: TextAlign = TextAlign.Start,
    textDecoration: TextDecoration? = null,
    textOverflow: TextOverflow = TextOverflow.Clip,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    rippleColor: Color = Color.Unspecified,
    rippleEnable: Boolean = false,
    bounded: Boolean = true,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onClick: (() -> Unit)? = null,
) {
    val style = if (karabinerable) KarabinerTypography.label.copy(brush = gradient, fontWeight = FontWeight.Bold)
    else KarabinerTypography.label.copy(fontWeight = FontWeight.Bold)
    Text(
        modifier = modifier.karaClickable(
            onClick = onClick,
            interactionSource = interactionSource,
            rippleColor = rippleColor,
            rippleEnable = rippleEnable,
            bounded = bounded
        ),
        text = text,
        style = style,
        color = textColor,
        textAlign = textAlign,
        textDecoration = textDecoration,
        overflow = textOverflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
    )
}


val LocalTypography = staticCompositionLocalOf { KarabinerTypography }