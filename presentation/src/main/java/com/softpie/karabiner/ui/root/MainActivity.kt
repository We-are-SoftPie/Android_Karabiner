package com.softpie.karabiner.ui.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.softpie.karabiner.component.button.ButtonType
import com.softpie.karabiner.component.button.KarabinerButton
import com.softpie.karabiner.component.theme.Body
import com.softpie.karabiner.component.theme.Caption
import com.softpie.karabiner.component.theme.Headline
import com.softpie.karabiner.component.theme.KarabinerTheme
import com.softpie.karabiner.component.theme.Label
import com.softpie.karabiner.component.theme.Title
import com.softpie.karabiner.ui.signup.Signup

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KarabinerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {

                        KarabinerButton(text = "신고하기") {

                        }
                        KarabinerButton(text = "신고하기", karabinerable = true) {

                        }
                        KarabinerButton(text = "신고하기", type = ButtonType.Gray) {

                        }
                        KarabinerButton(text = "신고하기", type = ButtonType.Gray, enabled = false) {

                        }
                        KarabinerButton(text = "신고하기", type = ButtonType.Transparent) {

                        }
                        Title(text = "어쩌라고", karabinerable = true)
                        Headline(text = "어쩌라고", karabinerable = true)
                        Body(text = "어쩌라고", karabinerable = true)
                        Label(text = "어쩌라고", karabinerable = true)
                        Caption(text = "어쩌라고", karabinerable = true)
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KarabinerTheme {

    }
}