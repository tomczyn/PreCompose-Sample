import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    PreComposeApp {
        MaterialTheme {
            Column(modifier = Modifier.fillMaxSize()) {
                var showBottomSheet by remember { mutableStateOf(false) }
                val navigator = rememberNavigator()
                NavHost(
                    navigator = navigator,
                    navTransition = NavTransition(),
                    initialRoute = "/home",
                ) {
                    scene(route = "/home") {
                        HomeView(showSheet = { showBottomSheet = true })
                    }
                }
                if (showBottomSheet) {
                    BottomSheetView(hideBottomSheet = { showBottomSheet = false })
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun BottomSheetView(
    hideBottomSheet: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = hideBottomSheet,
        dragHandle = null,
        content = {
            BottomSheetContent {
                scope.launch { sheetState.hide() }
                    .invokeOnCompletion {
                        if (!sheetState.isVisible) hideBottomSheet()
                    }
            }
        }
    )
}

@Composable
private fun HomeView(showSheet: () -> Unit) {
    Column(
        Modifier.fillMaxSize().background(Color.Blue),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Main Title")
        Button(onClick = showSheet) {
            Text("Show bottom sheet")
        }
    }
}


@Composable
private fun BottomSheetContent(
    onHideBottomSheet: () -> Unit,
) {
    val navigator = rememberNavigator()
    NavHost(
        navigator = navigator,
        navTransition = NavTransition(),
        initialRoute = "/sheet",
    ) {
        scene(route = "/sheet") {
            Column(
                Modifier.fillMaxWidth().height(150.dp).background(Color.Red),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("BottomSheet Title")
                Button(onClick = onHideBottomSheet) {
                    Text("Hide bottom sheet")
                }
            }
        }
    }
}
