package com.softpie.karabiner.ui.log

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.softpie.karabiner.local.room.DeclarationDao
import com.softpie.karabiner.local.room.DeclarationEntity
import com.softpie.karabiner.local.room.KarabinerDatabase
import com.softpie.karabiner.local.room.toModels
import com.softpie.karabiner.local.sharedpreferences.KarabinerSharedPreferences
import com.softpie.karabiner.utiles.launchIO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import java.time.LocalDateTime

class LogViewModel: ViewModel() {
    private lateinit var declarationDao: DeclarationDao
    private lateinit var karabinerSharedPreferences: KarabinerSharedPreferences

    private val _uiState = MutableStateFlow(LogState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<LogSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun init(context: Context) {
        declarationDao = KarabinerDatabase.getInstance(context)?.declarationDao()!!
        karabinerSharedPreferences = KarabinerSharedPreferences(context)
    }

    fun load() = launchIO {
        val declarationLog =  declarationDao.getMembers().toModels()
        val userName = karabinerSharedPreferences.myName
        _uiState.value = uiState.value.copy(
            name = userName,
            data = declarationLog,
            loading = false
        )
    }

    fun dummy(image: Bitmap) = launchIO {
        declarationDao.insertMember(
            DeclarationEntity(
                image = image,
                title = "테스트",
                description = "테스트입니다.",
                category = 0,
                declarationId = "SPP-1234-1232131",
                location = "",
                date = LocalDateTime.now()
            )
        )
    }

}
