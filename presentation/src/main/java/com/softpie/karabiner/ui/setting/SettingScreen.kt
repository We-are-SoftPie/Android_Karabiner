package com.softpie.karabiner.ui.setting

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.softpie.karabiner.R
import com.softpie.karabiner.component.theme.BoldBody
import com.softpie.karabiner.component.theme.BoldTitle
import com.softpie.karabiner.component.theme.KarabinerTheme
import com.softpie.karabiner.component.theme.Label
import com.softpie.karabiner.component.theme.Title
import com.softpie.karabiner.ui.root.NavGroup


@Composable
fun SettingScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val activity = context as Activity
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        BoldTitle(
            modifier = Modifier.padding(start = 24.dp),
            text = "설정"
        )
        Spacer(modifier = Modifier.height(60.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            SettingButton(
                title = "내 프로필",
                content = "사용자 프로필을 수정합니다."
            ) {
                navController.navigate(NavGroup.Setting.PROFILE.id)
            }
            Spacer(modifier = Modifier.height(20.dp))
            SettingButton(
                title = "개인정보 이용약관",
                content = "Karabiner의 개인정보 이용약관입니다"
            ) {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse("https://karabiner-privacy-site.vercel.app/")
                context.startActivity(i)
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun SettingButton(
    title: String,
    content: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = KarabinerTheme.color.Gray100),
        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 16.dp)
    ) {
        Box(

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart)
            ) {
                BoldBody(
                    text = title,
                    textColor = KarabinerTheme.color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Label(
                    text = content,
                    textColor = KarabinerTheme.color.Gray400
                )
            }
            Image(
                modifier = Modifier.align(Alignment.CenterEnd),
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = "오른쪽 화살표"
            )
        }
    }
}