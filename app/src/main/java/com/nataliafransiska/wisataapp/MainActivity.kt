@file:OptIn(ExperimentalMaterial3Api::class)

package com.nataliafransiska.wisataapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nataliafransiska.wisataapp.ui.theme.WisataappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WisataappTheme {
                // Memulai aplikasi Tempat Wisata
                // Memanggil komponen MainActivity di sini, dalam konteks @Composable
                MainActivityContent()
            }
        }
    }
}

@Composable
fun WisataItem(title: String, description: String, imageResId: Int, buttonText: String) {
    var isDetailDialogVisible by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                // Tampilkan dialog detail saat item diklik
                isDetailDialogVisible = true
            },
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Gambar tempat wisata
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Informasi tempat wisata
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Tombol "Lihat Detail" dengan teks yang berbeda
            Button(
                onClick = {
                    // Tampilkan dialog detail saat tombol diklik
                    isDetailDialogVisible = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End)
            ) {
                Text(text = buttonText)
            }
        }
    }

    if (isDetailDialogVisible) {
        AlertDialog(
            onDismissRequest = {
                isDetailDialogVisible = false
            },
            title = {
                Text(text = title)
            },
            text = {
                Text(text = description)
                // menambahkan informasi detail di sini.
            },
            confirmButton = {
                Button(
                    onClick = {
                        isDetailDialogVisible = false
                    }
                ) {
                    Text(text = "Tutup")
                }
            }
        )
    }
}

// Fungsi yang mengembalikan ID gambar yang berbeda berdasarkan indeks item
fun getGambarBerbeda(index: Int): Int {
    return when (index % 3) {
        0 -> R.drawable.candiprambanan
        1 -> R.drawable.tebingbreksi
        2 -> R.drawable.tugujogja
        3 -> R.drawable.malioboro
        else -> R.drawable.candiprambanan
    }
}

// Fungsi yang mengembalikan deskripsi yang berbeda berdasarkan indeks item
fun getDeskripsi(index: Int): String {
    return when (index % 3) {
        0 -> "JawaTengah Indonesia"
        1 -> "Yogyakarta Indonesia"
        2 -> "Yogyakarta Indonesia"
        3 -> "Yogyakarta Indonesia"
        else -> "Deskripsi singkat tentang tempat wisata default."
    }
}

@Composable
fun MainActivityContent() {
    // State untuk nilai pencarian
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Toolbar dengan menggunakan TextField
        TopAppBar(
            title = {
                GoogleSearchBar(
                    searchText = searchText,
                    onSearchTextChanged = {
                        searchText = it
                    }
                )
            },
            actions = {
                // Tambahkan ikon atau fungsi tambahan di sini jika diperlukan
            },
            modifier = Modifier.background(Color.White) // Untuk mengatur Background color
        )

        // Daftar tempat wisata dalam Lazy Column
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(10) { index ->
                val title = when (index) {
                    0 -> "Candi Prambanan"
                    1 -> "Tebing Breksi"
                    2 -> "Tugu Jogja"
                    3 -> "Judul Tempat Wisata 3"
                    4 -> "Judul Tempat Wisata 4"
                    5 -> "Judul Tempat Wisata 5"
                    6 -> "Judul Tempat Wisata 6"
                    7 -> "Judul Tempat Wisata 7"
                    8 -> "Judul Tempat Wisata 8"
                    9 -> "Judul Tempat Wisata 9"
                    else -> "Judul Tempat Wisata Default"
                }
                val description = getDeskripsi(index) // Dapatkan deskripsi sesuai dengan indeks
                val imageResId = getGambarBerbeda(index) // Dapatkan ID gambar sesuai dengan indeks
                val buttonText = "Lihat Detail $index" // Teks tombol yang berbeda

                // Item tempat wisata
                WisataItem(
                    title = title,
                    description = description,
                    imageResId = imageResId,
                    buttonText = buttonText // Tambahkan parameter buttonText
                )
            }
        }
    }
}

@Composable
fun GoogleSearchBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit
) {
    val searchIcon = Icons.Default.Search

    BasicTextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                // Handle pencarian di sini
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(24.dp))
            .padding(8.dp)
    ) {
        Icon(
            imageVector = searchIcon,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(8.dp),
            tint = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WisataAppPreview() {
    WisataappTheme {
        // Menampilkan pratinjau aplikasi Tempat Wisata
        MainActivityContent()
    }
}










