package com.example.myapplication.ui.screens.entrydetail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.data.models.Entry
import com.example.myapplication.data.models.EntryList
import com.example.myapplication.utilities.BLANK_ENTRY
import com.example.myapplication.utilities.Resource

@Composable
fun EntryDetailScreen(
    navController: NavController,
    viewModel: EntryDetailViewModel = hiltViewModel(),
    entryId: Int
) {
    val entryDetails = produceState<Resource<EntryList>>(initialValue = Resource.Loading()) {
        value = viewModel.fetchEntryData(entryId)
    }.value

    Scaffold(
        content = { padding ->
            Surface(
                modifier = Modifier
                    .padding(padding)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(R.drawable.bgportrait),
                    contentDescription = "background image",
                    contentScale = ContentScale.FillBounds
                )
                EntryDetails(
                    entryDetails = entryDetails,
                    onNavigateBackToCompendium = {
                        navController.popBackStack()
                    }
                )
            }
        }
    )
}

@Composable
fun EntryDetails(
    entryDetails: Resource<EntryList>,
    onNavigateBackToCompendium: () -> Unit
) {
    val entryData = entryDetails.data?.data ?: BLANK_ENTRY

    EntryDetailsCard(
        entryData = entryData,
        onNavigateBackToCompendium = onNavigateBackToCompendium
    )
}

@Composable
fun EntryDetailsCard(
    entryData: Entry,
    onNavigateBackToCompendium: () -> Unit,
    isDarkTheme: Boolean = isSystemInDarkTheme()
) {
    val maxWidthFloat = 0.75F
    val roundedEdgeValue = 8.dp
    val verticalSpacerDp = 10.dp
    val lightOrDarkBGColor = if (isDarkTheme) {
        Color.DarkGray
    } else {
        Color.White
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 85.dp)
            .background(Color.Transparent)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth(maxWidthFloat)
                .background(
                    MaterialTheme.colors.primary,
                    RoundedCornerShape(
                        topStart = roundedEdgeValue,
                        topEnd = roundedEdgeValue
                    )
                )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_back_48px),
                contentDescription = "navigate back arrow",
                modifier = Modifier
                    .clickable {
                        onNavigateBackToCompendium()
                    }
                    .size(35.dp)
            )
            Text(
                text = entryData.name,
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                maxLines = 2,
                modifier = Modifier
                    .defaultMinSize(minHeight = 60.dp)
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
                    .padding(horizontal = 5.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth(maxWidthFloat)
                .background(lightOrDarkBGColor)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.monster_48px),
                contentDescription = "skull icon",
                modifier = Modifier
                    .size(35.dp)
            )
            AsyncImage(
                model = entryData.image,
                contentDescription = "Picture of ${entryData.name}",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            bottomStart = roundedEdgeValue,
                            bottomEnd = roundedEdgeValue
                        )
                    )
                    .width(125.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.monster_48px),
                contentDescription = "skull icon",
                modifier = Modifier
                    .size(35.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth(maxWidthFloat)
                .fillMaxHeight(0.45F)
                .background(
                    lightOrDarkBGColor,
                    RoundedCornerShape(
                        bottomStart = roundedEdgeValue,
                        bottomEnd = roundedEdgeValue
                    )
                )
                .padding(horizontal = 15.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.padding(vertical = verticalSpacerDp))
                Text(
                    text = entryData.description,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.padding(vertical = verticalSpacerDp))
                Text(
                    text = "@ Common Locations @",
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp
                )
                StringListFormatter(stringList = entryData.common_locations)
                Spacer(modifier = Modifier.padding(vertical = verticalSpacerDp))
                Text(
                    text = "@ Loot @",
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                )
                StringListFormatter(stringList = entryData.drops)
                Spacer(modifier = Modifier.padding(vertical = verticalSpacerDp))
            }
        }
    }
}

@Composable
fun StringListFormatter(stringList: List<Any>) {
    Text(
        buildAnnotatedString {
            when (stringList) {
                emptyList<Any>() -> Text(text = "None")
                else -> stringList.forEach {
                    withStyle(style = ParagraphStyle()) {
                        append("\t\t")
                        append(it.toString())
                    }
                }
            }
        }
    )
}