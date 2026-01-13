package com.grumpyshoe.beertasticcmp.presentation.features.home.ui.targets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.grumpyshoe.beertasticcmp.presentation.common.resources.AppTheme
import com.grumpyshoe.beertasticcmp.presentation.common.resources.Res
import com.grumpyshoe.beertasticcmp.presentation.common.resources.home_loading_error
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun Error() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        contentAlignment = Alignment.Center,
    ) {
        Card {
            Box(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(Res.string.home_loading_error),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ErrorComponentPreview() {
    AppTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            Error()
        }
    }
}
