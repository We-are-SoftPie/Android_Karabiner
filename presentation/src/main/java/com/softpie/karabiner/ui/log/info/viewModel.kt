package com.softpie.karabiner.ui.log.info

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.softpie.karabiner.local.room.DeclarationDao
import com.softpie.karabiner.local.room.DeclarationEntity
import com.softpie.karabiner.local.room.KarabinerDatabase
import com.softpie.karabiner.local.room.toModel
import com.softpie.karabiner.local.room.toModels
import com.softpie.karabiner.local.sharedpreferences.KarabinerSharedPreferences
import com.softpie.karabiner.ui.log.LogSideEffect
import com.softpie.karabiner.ui.log.LogState
import com.softpie.karabiner.utiles.launchIO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import java.time.LocalDateTime

class LogInfoViewModel: ViewModel() {
    private lateinit var declarationDao: DeclarationDao

    private val _uiState = MutableStateFlow(LogInfoState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<LogInfoSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun init(context: Context) {
        declarationDao = KarabinerDatabase.getInstance(context)?.declarationDao()!!
    }

    fun load(id: Int) = launchIO {
        val declarationLog =  declarationDao.getMember(id).toModel()
        _uiState.value = _uiState.value.copy(
            data = declarationLog,
            loading = false
        )
    }

}
