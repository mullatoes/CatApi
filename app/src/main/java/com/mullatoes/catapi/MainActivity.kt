package com.mullatoes.catapi

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.Coil
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mullatoes.catapi.model.CatImage
import com.mullatoes.catapi.ui.theme.CatApiTheme
import com.mullatoes.catapi.utils.ApiKeyUtil
import com.mullatoes.catapi.viewmodel.CatViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: CatViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatApiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val apiKey = BuildConfig.CAT_API_KEY
                    val limit = 10

                    val catImages = viewModel.catImages
                    catImages.value?.let { CatImageList(catImages = it) }

                    viewModel.fetchCatImages(apiKey, limit)

                }
            }
        }
    }
}

@Composable
fun CatImageList(catImages: List<CatImage>) {
    LazyColumn {
        items(catImages){image ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image.url)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(CircleShape)
            )
        }
    }
}



@Composable
fun CenterColumn() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LaunchSnackBar()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LaunchSnackBar() {

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) {
        Button(onClick = {
            coroutineScope.launch {
                val result = snackBarHostState.showSnackbar(
                    message = "This is a snackBar",
                    actionLabel = "Undo",
                    duration = SnackbarDuration.Short
                )

                when(result){
                    SnackbarResult.Dismissed -> println("Dismissed")
                    SnackbarResult.ActionPerformed -> println("Action performed")
                }
            }
        }) {
            Text(text = "Click Me")
        }
    }
}
