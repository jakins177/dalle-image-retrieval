package com.sample.dalle_3_pic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sample.dalle_3_pic.ui.theme.Dalle3picTheme
import com.sample.dalle_3_pic.viewmodel.AIIG_ViewModel
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sample.dalle_3_pic.model.DataItem
import com.sample.dalle_3_pic.model.ResponseData
import androidx.compose.runtime.livedata.observeAsState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imageViewModel =  ViewModelProvider(this).get(AIIG_ViewModel::class.java)
        val model = "dall-e-3"
        val prompt = "beautiful mountains with a yellow sky"
        val n = 1
        val size = "1024x1024"

        setContent {
            val images by imageViewModel.images.observeAsState()

            LaunchedEffect(key1 = true) {
                imageViewModel.fetchImages(model, prompt, n, size)
            }

            Dalle3picTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    images?.let{ImageList(it)}


                }
            }
        }
    }
}


@Composable
fun ImageList(images: ResponseData){
    LazyColumn {
        items(images.data) { dataItem ->
            Text(text = dataItem.url,
                 modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Bold
            )

            ImageCard(
                photo = dataItem,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun ImageCard(photo: DataItem, modifier: Modifier = Modifier) {
    Card(
        modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(photo.url)
                .crossfade(true).build(),
            error = null,
            placeholder = null,
            contentDescription = "dalle-3 image",
            modifier = Modifier.fillMaxWidth()
        )

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Dalle3picTheme {
        Greeting("Android")
    }
}