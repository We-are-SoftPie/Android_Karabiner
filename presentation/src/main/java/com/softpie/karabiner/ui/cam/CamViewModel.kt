package com.softpie.karabiner.ui.cam

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import com.softpie.karabiner.local.room.DeclarationDao
import com.softpie.karabiner.local.room.KarabinerDatabase
import com.softpie.karabiner.local.sharedpreferences.KarabinerSharedPreferences
import com.softpie.karabiner.remote.RetrofitBuilder
import com.softpie.karabiner.remote.mapper.toModel
import com.softpie.karabiner.utiles.TAG
import com.softpie.karabiner.utiles.launchIO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink


class CamViewModel: ViewModel() {

    private lateinit var declarationDao: DeclarationDao
    private lateinit var karabinerSharedPreferences: KarabinerSharedPreferences


    private val _uiState = MutableStateFlow(CamState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<CamSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun init(context: Context) {
        declarationDao = KarabinerDatabase.getInstance(context)?.declarationDao()!!
        karabinerSharedPreferences = KarabinerSharedPreferences(context)
    }

    fun nextNowPage() {
        _uiState.value = _uiState.value.copy(
            nowPage = _uiState.value.nowPage + 1
        )
    }

    fun nextTextPage() {
        _uiState.value = _uiState.value.copy(
            textPage = _uiState.value.textPage + 1
        )
    }

    fun postImage(image: Bitmap) {
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        image.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)
//        val requestBody =
//            byteArrayOutputStream.toByteArray()
//                .toRequestBody("image/jpg".toMediaTypeOrNull(), 0, content.size)
//        val uploadFile: Part = createFormData.createFormData("postImg", file.getName(), requestBody)
//        val request = RequestBody.create()
        Log.d(TAG, "postImage: qqqqq")
        val bitmapRequestBody = BitmapRequestBody(image)
        //multipart/form-data
        val bitmapMultipartBody: MultipartBody.Part = MultipartBody.Part.createFormData("image", "CamView.jpeg", bitmapRequestBody)
        launchIO {
            kotlin.runCatching {
                val service = RetrofitBuilder.getKarabinerService()
                service.postImage(bitmapMultipartBody).data.toModel()
            }.onSuccess {
                this.nextNowPage()
                Log.d(TAG, "postImage: $it")
                _uiState.value = _uiState.value.copy(
                    data = it,
                    textPage = _uiState.value.textPage + 1
                )

            }.onFailure {
                Log.d(TAG, "postImage: $it")
                _sideEffect.send(CamSideEffect.LoadFailed(it))
            }
        }

    }
}

class BitmapRequestBody(private val bitmap: Bitmap) : RequestBody() {
    override fun contentType(): MediaType? = "image/jpeg".toMediaType()

    override fun writeTo(sink: BufferedSink) {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, sink.outputStream())
    }
}