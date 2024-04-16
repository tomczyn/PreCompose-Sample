import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetLayout(
    modifier: Modifier = Modifier,
    show: Boolean,
    bottomSheetContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    Column(modifier = Modifier.fillMaxSize()) {
        content()
        if (show) {
            ModalBottomSheet(
                modifier = modifier,
                sheetState = sheetState,
                onDismissRequest = {},
                dragHandle = null,
                content = {
                    bottomSheetContent()
                }
            )
        }
    }
}
