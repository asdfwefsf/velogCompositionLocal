package com.company.velogcompositionlocal

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.velogcompositionlocal.ui.theme.VelogCompositionLocalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VelogCompositionLocalTheme {
                ExampleScreen()
            }
        }


    }
}

val compositionLocalTest1 = staticCompositionLocalOf { 33 }
var compositionLocalTest2 = compositionLocalOf { "dd" }

@Composable
fun Haha1() {


    CompositionLocalProvider(compositionLocalTest1 provides 333) {
        Haha2()
    }
    CompositionLocalProvider(compositionLocalTest2 provides "HAHA") {
        Haha2()
    }
}

@Composable
fun Haha2() {
    val haha1 = compositionLocalTest1
    var haha2 = compositionLocalTest2
    Log.d("haha1" , haha1.current.toString())
    Log.d("haha2" , haha2.current)

}


@Composable
fun StateHoistingExCode() {
    // 상태를 최상위 레벨로 호이스팅
    var counterState by remember { mutableStateOf(0) }

    // 상태를 하위 컴포저블로 전달
    Counter(
        count = counterState,
        updateCount = { newCount ->
            counterState = newCount
        }
    )
}

@Composable
fun Counter(count: Int, updateCount: (Int) -> Unit) {
    Button(onClick = { updateCount(count + 1) }) {
        Text("Clicked $count times")
    }
}





// ViewModel 클래스
class ExampleViewModel : ViewModel() {
    // LiveData를 사용하여 UI 상태 관리
    private val _state = MutableLiveData<ExampleState>()
    val state: LiveData<ExampleState> = _state

    // 비즈니스 로직을 실행하는 함수
    fun performBusinessLogic() {
        // 비즈니스 로직 결과에 따라 상태 업데이트
        _state.value = ExampleState(result = "New Result")
    }
}

// UI 상태를 나타내는 데이터 클래스
data class ExampleState(val result: String)

// Composable 함수에서 ViewModel 사용
@Composable
fun ExampleScreen(viewModel: ExampleViewModel = ExampleViewModel()) {
    val state by viewModel.state.observeAsState()

    // UI 업데이트
    Column{
        Text(text = state?.result ?: "")

        Button(onClick = { viewModel.performBusinessLogic() }) {
            Text("Perform Business Logic")
        }
    }
}
