package ru.minzdrav.therapist.feature.documents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination

interface DocumentsNavigator

@Destination
@Composable
fun DocumentsScreen(

) {
    val viewModel: DocumentsViewModel = hiltViewModel()
    DocumentsScreen(true)
}

@Composable
private fun DocumentsScreen(a: Boolean) {
    Column(Modifier.fillMaxSize()) {

    }
}