package com.stargazer.noteme.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@Composable
fun NoteItem(
    title: String?, //şimdi bu bir kart olacak not değil de daha çok notun ön izlemesi gibi o yüzden başlığı eğer varsa alabiliriz ama yoksa da sıkıntı yok Untitled yazılacağı için ? ekledim
    date: Long,
    imageUrl: String?, // yine title ile aynı sebepten koydum çünkü kullanıcı bi nota görsel eklemek zorunda değil e yoksa bu cardda zaten gözükemez
){
    Card(
        modifier = Modifier.size(130.dp,200.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ){
        Column{

            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.LightGray),
                contentAlignment = Alignment.Center)
            {
                if(!imageUrl.isNullOrEmpty()){
                    AsyncImage(
                        model = imageUrl, // Room'dan gelen Firebase linki
                        contentDescription = "Note Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }else{
                    Text(text = "$date", color = Color.Black,style = MaterialTheme.typography.titleMedium)
                }
            }

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
                contentAlignment = Alignment.Center)
            {
                if (!title.isNullOrEmpty()){

                    Text(text = "$title",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold
                    )

                }else{
                    Text("Untitled", color = Color.Gray,style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}
