package com.example.myapplication.ui.screens.compendium

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.data.models.MonsterEntry

@Composable
fun CompendiumScreen(
    navController: NavController,
    viewModel: CompendiumViewModel = hiltViewModel(),
) {
    val monsterList by remember { viewModel.monstersList }
    val loading by remember { viewModel.loading }
    val errorMessage by remember { viewModel.errorMessage }

    Scaffold(
        content = { padding ->
            Surface(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(R.drawable.bgportrait),
                    contentDescription = "background image",
                    contentScale = ContentScale.FillBounds
                )
                MonsterLazyList(
                    monsterList = monsterList,
                    loading = loading,
                    errorMessage = errorMessage,
                    fetchMonsters = { viewModel.fetchMonsterData() },
                    onNavigateToDetails = {
                        navController.navigate(
                            "entry_detail_screen/$it"
                        )
                    }
                )
            }
        }
    )
}

@Composable
fun MonsterLazyList(
    monsterList: List<MonsterEntry>,
    loading: Boolean,
    errorMessage: String,
    fetchMonsters: () -> Unit,
    onNavigateToDetails: (entryId: Int?) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 50.dp),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.scale(0.80F)
    ) {
        items(monsterList.size) { entry ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally,
                modifier = Modifier
                    .wrapContentSize(Center)
                    .clickable {
                        onNavigateToDetails(monsterList[entry].id ?: 0)
                    }
            ) {
                val cardWidth = 150.dp
                val roundedEdgeValue = 8.dp
                AsyncImage(
                    model = monsterList[entry].image,
                    contentDescription = "Picture of ${monsterList[entry].name}",
                    modifier = Modifier
                        .size(cardWidth)
                        .clip(
                            RoundedCornerShape(
                                topStart = roundedEdgeValue,
                                topEnd = roundedEdgeValue
                            )
                        )
                )
                Text(
                    text = monsterList[entry].name ?: "",
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    modifier = Modifier
                        .defaultMinSize(minHeight = 60.dp)
                        .width(cardWidth)
                        .background(
                            MaterialTheme.colors.primary,
                            RoundedCornerShape(
                                bottomStart = roundedEdgeValue,
                                bottomEnd = roundedEdgeValue
                            )
                        )
                        .clip(
                            RoundedCornerShape(
                                bottomStart = roundedEdgeValue,
                                bottomEnd = roundedEdgeValue
                            )
                        )
                        .wrapContentHeight(Alignment.CenterVertically)
                )
                Spacer(
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                )
            }
        }
    }

    Box(
        contentAlignment = Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if(loading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
        if(errorMessage.isNotEmpty()) {
            RetrySection(error = errorMessage) {
                fetchMonsters()
            }
        }
    }
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(90.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}