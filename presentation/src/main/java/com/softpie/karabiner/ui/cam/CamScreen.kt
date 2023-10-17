package com.softpie.karabiner.ui.cam

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.YuvImage
import android.media.Image
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.softpie.karabiner.R
import com.softpie.karabiner.component.loading.LoadInFullScreen
import com.softpie.karabiner.component.textfield.KarabinerTextField
import com.softpie.karabiner.component.theme.BoldBody
import com.softpie.karabiner.component.theme.BoldHeadline
import com.softpie.karabiner.component.theme.Headline
import com.softpie.karabiner.component.theme.Title
import com.softpie.karabiner.utiles.TAG
import com.softpie.karabiner.utiles.shortToast
import kotlinx.coroutines.delay
import java.io.ByteArrayOutputStream


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CamScreen(
    navController: NavController,
    camViewModel: CamViewModel = viewModel()
) {
    // 카메라
    val context = LocalContext.current
    val camState = camViewModel.uiState.collectAsState().value
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    // 페이지 관리
    var nowPage = camState.nowPage //by remember { mutableIntStateOf(0) }
    var camImage by remember { mutableStateOf(context.getDrawable(R.drawable.ic_cam)!!.toBitmap()) }
    var textPage = camState.textPage

    //
    AnimatedVisibility(
        visible = permissionState.status.isGranted.not() && nowPage == 0,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        LoadInFullScreen()
        val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
            Log.d("TAG", "Cam: $it")
            if (it.not())
                context.shortToast("카메라 권환에 동의하지 않으면 사용할 수 없습니다. ")
        }
        LaunchedEffect(true) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    AnimatedVisibility(
        visible = permissionState.status.isGranted && nowPage == 0,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        val lifecycleOwner = LocalLifecycleOwner.current
        val cameraController = remember { LifecycleCameraController(context)}
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text(text = "사진찰칵")},
                    icon = {},
                    onClick = {
//                        camImage = context.getDrawable(R.drawable.ic_cam)!!.toBitmap()
//                        camViewModel.nextPage()

                        val mainExecutor = ContextCompat.getMainExecutor(context)
                        cameraController.takePicture(mainExecutor, @ExperimentalGetImage object: ImageCapture.OnImageCapturedCallback() {
                            override fun onCaptureSuccess(image: ImageProxy) {
                                Log.d("LOG", "onCaptureSuccess: ${image.height} ${image.width}")
                                super.onCaptureSuccess(image)
                                Log.d(TAG, "onCaptureSuccess: ${image.imageInfo.rotationDegrees}")
                                val bitmap = imageProxyToBitmap(image)!!
                                val rotateMatrix = Matrix()
                                rotateMatrix.postRotate(image.imageInfo.rotationDegrees.toFloat())
                                camImage = Bitmap.createBitmap(bitmap, 0, 0,
                                bitmap.width, bitmap.height, rotateMatrix, false)
//                                camViewModel.postImage(camImage)
//                                camViewModel.nextPage()
                                camViewModel.nextNowPage()
                                Log.d(TAG, "onCaptureSuccess: ${camState.textPage}")
                                Log.d(TAG, "onCaptureSuccess: $textPage")
                            }
                        })
                    }
                )
            }
        ) {
            AndroidView(
                modifier = Modifier.padding(it),
                factory = { context ->
                PreviewView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                    setBackgroundColor(Color.BLACK)
                    scaleType = PreviewView.ScaleType.FILL_START
                }.also { previewView ->
                    previewView.controller = cameraController
                    cameraController.bindToLifecycle(lifecycleOwner)
                }
            })
        }
    }
    AnimatedVisibility(
        visible = permissionState.status.isGranted && nowPage == 1,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
//        val rotateMatrix = Matrix()
//        rotateMatrix.postRotate(90f)
////        camImage = Bitmap.createBitmap(camImage, 0, 0,
////            camImage.width, camImage.height, rotateMatrix, false)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            BoldBody(
                modifier = Modifier.padding(end = 24.dp),
                text = "확인하기",
                onClick = {
                    Log.d(TAG, "CamScreen: rrrr")
                    camViewModel.postImage(camImage)
                    camViewModel.nextNowPage()
                }
            )
        }
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = BitmapPainter(camImage.asImageBitmap()),
            contentDescription = "촬영된 사진"
        )
    }
    AnimatedVisibility(
        visible = permissionState.status.isGranted && nowPage == 2,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        val lottieAnime by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
        Column(
            modifier = Modifier.padding(top = 280.dp)
        ) {
            LottieAnimation(
                modifier = Modifier
                    .padding(horizontal = 80.dp)
                    .aspectRatio(1f)
                    .fillMaxSize(),
                composition = lottieAnime,
                iterations = LottieConstants.IterateForever
            )
            Spacer(modifier = Modifier.height(24.dp))
            LaunchedEffect(true) {
                Log.d(TAG, "CamScreen: 지금호출합니다")
                delay(3000L)
                camViewModel.nextTextPage()
                delay(400L)
                camViewModel.nextTextPage()
                delay(3000L)
                camViewModel.nextTextPage()
                delay(400L)
                camViewModel.nextTextPage()
            }
            Box {
                this@Column.AnimatedVisibility(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    visible = permissionState.status.isGranted && textPage == 0,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column {
                        BoldHeadline(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = "이미지를 서버에"
                        )
                        Row(
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            BoldHeadline(
                                text = "업로드",
                                karabinerable = true
                            )
                            BoldHeadline(
                                text = "하고 있어요"
                            )
                        }
                    }
                }
                this@Column.AnimatedVisibility(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    visible = permissionState.status.isGranted && textPage == 2,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column {
                        BoldHeadline(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = "이미지를 토대로 카테고리를"
                        )
                        Row(
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            BoldHeadline(
                                text = "분류",
                                karabinerable = true
                            )
                            BoldHeadline(
                                text = "하고 있어요"
                            )
                        }
                    }
                }
                this@Column.AnimatedVisibility(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    visible = permissionState.status.isGranted && textPage == 4,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column {
                        BoldHeadline(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = "내용을 생성하고 있어요"
                        )

                    }
                }

            }
        }
    }
    AnimatedVisibility(
        visible = permissionState.status.isGranted && nowPage == 3,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
//            Spacer(modifier = Modifier.height(36.dp))
//            Headline(text = "AI 자동 작성 완료,")
//            Row {
//                Title(text = "신고내용", karabinerable = true)
//                Title(text = "을 확인해주세요")
//            }
//            Spacer(modifier = Modifier.height(36.dp))
//            BoldBody(text = "제목")
//            Spacer(modifier = Modifier.height(4.dp))
//            KarabinerTextField(
//                value = ,
//                onValueChange =
//            )
        }
    }



}

private fun toBitmap(image: Image): Bitmap? {
    val planes = image.planes
    val yBuffer = planes[0].buffer
    val uBuffer = planes[1].buffer
    val vBuffer = planes[2].buffer
    val ySize = yBuffer.remaining()
    val uSize = uBuffer.remaining()
    val vSize = vBuffer.remaining()
    val nv21 = ByteArray(ySize + uSize + vSize)
    //U and V are swapped
    yBuffer[nv21, 0, ySize]
    vBuffer[nv21, ySize, vSize]
    uBuffer[nv21, ySize + vSize, uSize]
    val yuvImage = YuvImage(nv21, ImageFormat.NV21, image.width, image.height, null)
    val out = ByteArrayOutputStream()
    yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 75, out)
    val imageBytes = out.toByteArray()
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}

fun imageProxyToBitmap(imageProxy: ImageProxy): Bitmap? {
    //https://developer.android.com/reference/android/media/Image.html#getFormat()
    //https://developer.android.com/reference/android/graphics/ImageFormat#JPEG
    //https://developer.android.com/reference/android/graphics/ImageFormat#YUV_420_888
    if (imageProxy.format == ImageFormat.JPEG) {
        val buffer = imageProxy.planes[0].buffer
        buffer.rewind()
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

        return bitmap
    }
    else if (imageProxy.format == ImageFormat.YUV_420_888) {
        val yBuffer = imageProxy.planes[0].buffer // Y
        val uBuffer = imageProxy.planes[1].buffer // U
        val vBuffer = imageProxy.planes[2].buffer // V

        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()

        val nv21 = ByteArray(ySize + uSize + vSize)

        yBuffer.get(nv21, 0, ySize)
        vBuffer.get(nv21, ySize, vSize)
        uBuffer.get(nv21, ySize + vSize, uSize)

        val yuvImage = YuvImage(nv21, ImageFormat.NV21, imageProxy.width, imageProxy.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 100, out)
        val imageBytes = out.toByteArray()
        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        return bitmap
    }
    return null
}