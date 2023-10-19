package com.softpie.karabiner.ui.cam

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.YuvImage
import android.location.Geocoder
import android.location.LocationManager
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
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
import com.google.android.gms.location.LocationServices
import com.softpie.karabiner.R
import com.softpie.karabiner.component.button.ButtonType
import com.softpie.karabiner.component.button.KarabinerButton
import com.softpie.karabiner.component.loading.LoadInFullScreen
import com.softpie.karabiner.component.select.KarabinerButtonSelectMenu
import com.softpie.karabiner.component.textfield.KarabinerTextField
import com.softpie.karabiner.component.theme.BoldBody
import com.softpie.karabiner.component.theme.BoldHeadline
import com.softpie.karabiner.component.theme.BoldTitle
import com.softpie.karabiner.component.theme.Headline
import com.softpie.karabiner.component.theme.KarabinerTheme
import com.softpie.karabiner.component.theme.Label
import com.softpie.karabiner.component.theme.Title
import com.softpie.karabiner.ui.root.MainSideEffect
import com.softpie.karabiner.utiles.TAG
import com.softpie.karabiner.utiles.collectAsSideEffect
import com.softpie.karabiner.utiles.getCategoryName
import com.softpie.karabiner.utiles.getCategoryNumber
import com.softpie.karabiner.utiles.shortToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import java.io.ByteArrayOutputStream
import java.security.AccessController.getContext
import java.util.Locale


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CamScreen(
    navController: NavController,
    camViewModel: CamViewModel = viewModel(),
    capture: Boolean,
    bottomNavVisible: (Boolean) -> Unit = {}
) {
    // 관리
    val camState = camViewModel.uiState.collectAsState().value

    // 카메라
    val context = LocalContext.current
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraController = remember { LifecycleCameraController(context)}
    val isCapture by rememberUpdatedState(newValue = capture)
    Log.d(TAG, "CamScreen: $isCapture")
//    LaunchedEffect(true) {
//        launcher.launch(Manifest.permission.READ_PHONE_NUMBERS)
//    }
    // 페이지 관리
    val nowPage = camState.nowPage //by remember { mutableIntStateOf(0) }
    var camImage by remember { mutableStateOf(context.getDrawable(R.drawable.ic_cam)!!.toBitmap()) }
    val textPage = camState.textPage

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var carNo by remember { mutableStateOf("") }
    var tag by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }


    // 위치
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val permissionGranted = permissions.values.reduce { aac, isPermissionGranted ->
            Log.d(TAG, "CamScreen: $aac $isPermissionGranted")
            aac == isPermissionGranted
        }

//        Log.d("TAG", "Signup: $it")
//        if (!it) { return@rememberLauncherForActivityResult }
        Log.d(TAG, "CamScreen: 여기 권환 미션받음")
        if (permissionGranted) {
            Log.d(TAG, "CamScreen: 여기 권환 미션받음2")
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return@rememberLauncherForActivityResult
            }
            fusedLocationClient.lastLocation.addOnSuccessListener {
                Log.d(TAG, "CamScreen: 요청 성공")
                try {
                    val geoCoder = Geocoder(context, Locale.getDefault())
                    val address = geoCoder.getFromLocation(it.latitude, it.longitude, 3)
                    if (address != null) {
//                        Log.d(TAG, "CamScreen: ${address[0].getAddressLine(0)}")
                        location = address[0].getAddressLine(0)
                    }
                    Log.d(TAG, "CamScreen: $address")
                } catch (e: Exception) {
                    context.shortToast("위치를 불러오는 것을 실패하였습니다.")
                }
            }
        } else {
            return@rememberLauncherForActivityResult
        }
    }
    LaunchedEffect(key1 = true) {
        camViewModel.init(context)
    }
    LaunchedEffect(key1 = camState.data.title != "") {
        with(camState.data) {
            Log.d(TAG, "CamScreen: $this")
            title = this.title
            content = this.content
            tag = this.tag
        }
    }
    camViewModel.sideEffect.collectAsSideEffect() {
        when(it) {
            is CamSideEffect.LoadFailed -> {
                context.shortToast("로딩에 실패하였습니다.")
            }
            is CamSideEffect.SuccessResultPost -> {
                context.shortToast("등록에 성공하였습니다.")
                navController.popBackStack()
            }
        }
    }
    LaunchedEffect(key1 = capture == true) {
        try {
            if (capture && cameraController.isImageCaptureEnabled) {
                Log.d(TAG, "CamScreen: qwewqeqwewqe 호출됨")
                val mainExecutor = ContextCompat.getMainExecutor(context)
                cameraController.takePicture(
                    mainExecutor,
                    @ExperimentalGetImage object : ImageCapture.OnImageCapturedCallback() {
                        override fun onCaptureSuccess(image: ImageProxy) {
                            Log.d("LOG", "onCaptureSuccess: ${image.height} ${image.width}")
                            super.onCaptureSuccess(image)
                            Log.d(TAG, "onCaptureSuccess: ${image.imageInfo.rotationDegrees}")
                            val bitmap = imageProxyToBitmap(image)!!
                            val rotateMatrix = Matrix()
                            rotateMatrix.postRotate(image.imageInfo.rotationDegrees.toFloat())
                            camImage = Bitmap.createBitmap(
                                bitmap, 0, 0,
                                bitmap.width, bitmap.height, rotateMatrix, false
                            )
                            //                                camViewModel.postImage(camImage)
                            //                                camViewModel.nextPage()
                            camViewModel.nextNowPage()
                            Log.d(TAG, "onCaptureSuccess: ${camState.textPage}")
                            Log.d(TAG, "onCaptureSuccess: $textPage")
                        }
                    })
            }
        } catch (e: java.lang.Exception) {
            Log.d(TAG, "CamScreen: 알파노")
        }
    }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 9.dp),
                shape = RoundedCornerShape(15.dp),
                color = KarabinerTheme.color.White
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(28.dp))
                    BoldTitle(text = "신고를 완료하시겠습니까?")
                    Spacer(modifier = Modifier.height(16.dp))
                    Label(
                        text = "신고가 완료되면 이메일로 안내됩니다.",
                        textColor = KarabinerTheme.color.Gray400
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .offset(y = 3.dp)
                    ) {
                        KarabinerButton(
                            modifier = Modifier.weight(1f),
                            text = "취소",
                            type = ButtonType.Gray,
                            shape = RoundedCornerShape(0.dp, 0.dp, 0.dp, 0.dp)
                        ) {
                            showDialog = false
                        }
                        KarabinerButton(
                            modifier = Modifier.weight(1f),
                            text = "신고하기",
                            karabinerable = true,
                            shape = RoundedCornerShape(0.dp, 0.dp, 0.dp, 0.dp)
                        ) {
                            camViewModel.postResult(
                                type = tag,
                                address = location,
                                title = title,
                                content = content,
                                carNo = carNo,
                                image = camImage
                            )
                        }
                    }

                }
            }
        }
    }
    // 입력 페이지 요소
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

        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text(text = "사진찰칵")},
                    icon = {},
                    onClick = {
//                        camImage = context.getDrawable(R.drawable.ic_cam)!!.toBitmap()
//                        camViewModel.nextNowPage()

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
        Scaffold(
            bottomBar = {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    KarabinerButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .padding(horizontal = 24.dp),
                        text = "신고하기"
                    ) {
                        if (title.isNullOrBlank()) {
                            context.shortToast("제목을 입력해주세요")
                            return@KarabinerButton
                        }
                        if (content.isNullOrBlank()) {
                            context.shortToast("신고 내용을 입력해주세요.")
                            return@KarabinerButton
                        }
                        if (tag == 14) {
                            context.shortToast("이외의 카테고리는 신고할 수 없습니다.")
                            return@KarabinerButton
                        }
                        if (tag in 6..13) {
                            if (carNo.isNullOrBlank()) {
                                context.shortToast("차량번호를 입력해주세요")
                                return@KarabinerButton
                            }
                        }
                        if (location.isNullOrBlank()) {
                            context.shortToast("제목을 입력해주세요")
                            return@KarabinerButton
                        }
                        showDialog = true
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(it)
                    .verticalScroll(rememberScrollState())
            ) {
                LaunchedEffect(key1 = true) {
                    launcher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
                }
                Spacer(modifier = Modifier.height(36.dp))
                Headline(text = "AI 자동 작성 완료,")
                Row {
                    Title(text = "신고내용", karabinerable = true)
                    Title(text = "을 확인해주세요")
                }
                Spacer(modifier = Modifier.height(36.dp))
                BoldBody(text = "제목")
                Spacer(modifier = Modifier.height(4.dp))
                KarabinerTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = title,
                    onValueChange = { title = it}
                )
                Spacer(modifier = Modifier.height(28.dp))
                BoldBody(text = "내용")
                Spacer(modifier = Modifier.height(4.dp))
                KarabinerTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = content,
                    onValueChange = { content = it},
                    singleLine = false
                )
                Spacer(modifier = Modifier.height(28.dp))
                BoldBody(text = "장소")
                Spacer(modifier = Modifier.height(4.dp))
                KarabinerTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = location,
                    onValueChange = { location = it}
                )
                Spacer(modifier = Modifier.height(28.dp))
                BoldBody(text = "카테고리")
                Spacer(modifier = Modifier.height(4.dp))
                KarabinerButtonSelectMenu(
                    modifier = Modifier.height(50.dp),
                    itemList = listOf("테스트", "소음", "테스트"),
                    hint = camState.data.tag.getCategoryName(),
                    onSelectItemListener = { item ->
                        tag = item.getCategoryNumber()
                    }
                )
                if (tag in 6..13) {
                    Spacer(modifier = Modifier.height(28.dp))
                    BoldBody(text = "차량번호")
                    Spacer(modifier = Modifier.height(4.dp))
                    KarabinerTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = carNo,
                        onValueChange = { carNo = it }
                    )
                }
                Spacer(modifier = Modifier.height(28.dp))
                BoldBody(text = "이미지")
                Spacer(modifier = Modifier.height(4.dp))
                Image(
                    painter = BitmapPainter(
                        image = camImage.asImageBitmap()
                    ),
                    contentDescription = "촬영된 이미지"
                )
                Spacer(modifier = Modifier.height(28.dp))

            }
        }
    }



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

