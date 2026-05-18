package com.mohamed.vtaper.ui.onboarding

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamed.vtaper.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(onComplete: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { 8 })
    val coroutineScope = rememberCoroutineScope()

    // State for all pages
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf(20) }
    var weight by remember { mutableStateOf(75f) }
    var isKg by remember { mutableStateOf(true) }
    var height by remember { mutableStateOf(175f) }
    var isCm by remember { mutableStateOf(true) }
    var bodyFat by remember { mutableStateOf(15) }
    val weakPoints = remember { mutableStateListOf<String>() }
    var experienceLevel by remember { mutableStateOf("Beginner") }
    var daysPerWeek by remember { mutableStateOf(4) }
    var preferredTime by remember { mutableStateOf("Flexible") }
    var environment by remember { mutableStateOf("Full Gym") }
    var goalAesthetic by remember { mutableStateOf("Classic") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgPrimary)
    ) {
        // Top Progress Dots
        Row(
            Modifier
                .height(60.dp)
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(8) { iteration ->
                val color = if (pagerState.currentPage == iteration) AccentBright else AccentSubtle
                val width = if (pagerState.currentPage == iteration) 24.dp else 8.dp
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .background(color, CircleShape)
                        .height(8.dp)
                        .width(width)
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp, bottom = 80.dp),
            userScrollEnabled = false // Force button usage for paged experience
        ) { page ->
            when (page) {
                0 -> WelcomePage()
                1 -> AboutYouPage(name, { name = it }, age, { age = it }, weight, { weight = it }, isKg, { isKg = it }, height, { height = it }, isCm, { isCm = it })
                2 -> StartingPointPage(bodyFat, { bodyFat = it }, weakPoints)
                3 -> ExperienceLevelPage(experienceLevel, { experienceLevel = it })
                4 -> SchedulePage(daysPerWeek, { daysPerWeek = it }, preferredTime, { preferredTime = it })
                5 -> TrainingEnvironmentPage(environment, { environment = it })
                6 -> GoalAestheticPage(goalAesthetic, { goalAesthetic = it })
                7 -> BlueprintPage(name, weight, height, bodyFat, onComplete)
            }
        }

        // Navigation Buttons at bottom
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            if (pagerState.currentPage > 0) {
                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Text("Back", color = TextSecondary)
                }
            }

            Button(
                onClick = {
                    if (pagerState.currentPage < 7) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        onComplete()
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .width(if (pagerState.currentPage == 0) 200.dp else 120.dp)
                    .height(50.dp)
                    .bounceClick(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(listOf(AccentPrimary, AccentBright)),
                            RoundedCornerShape(50.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        if (pagerState.currentPage == 0) "Let's Build Your Blueprint" else if (pagerState.currentPage == 7) "Enter App" else "Next",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun WelcomePage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Stylized V Logo
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(
                    Brush.verticalGradient(listOf(AccentBright, AccentPrimary)),
                    RoundedCornerShape(30.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text("V", fontSize = 70.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text("VTAPER", style = Typography.displayLarge, fontSize = 40.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Built different. Look different.", style = Typography.labelSmall, fontSize = 16.sp)
    }
}

@Composable
fun AboutYouPage(
    name: String, onNameChange: (String) -> Unit,
    age: Int, onAgeChange: (Int) -> Unit,
    weight: Float, onWeightChange: (Float) -> Unit,
    isKg: Boolean, onIsKgChange: (Boolean) -> Unit,
    height: Float, onHeightChange: (Float) -> Unit,
    isCm: Boolean, onIsCmChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("About You", style = Typography.headlineMedium)
        
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Name", color = TextSecondary) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                focusedBorderColor = AccentPrimary,
                unfocusedBorderColor = AccentSubtle,
                focusedContainerColor = BgElevated,
                unfocusedContainerColor = BgElevated
            )
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Age: $age", color = TextPrimary, modifier = Modifier.weight(1f))
            Slider(
                value = age.toFloat(),
                onValueChange = { onAgeChange(it.toInt()) },
                valueRange = 15f..60f,
                modifier = Modifier.weight(2f),
                colors = SliderDefaults.colors(thumbColor = AccentBright, activeTrackColor = AccentPrimary)
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Weight: ${weight.toInt()}${if (isKg) "kg" else "lbs"}", color = TextPrimary, modifier = Modifier.weight(1f))
            Slider(
                value = weight,
                onValueChange = onWeightChange,
                valueRange = 40f..150f,
                modifier = Modifier.weight(2f),
                colors = SliderDefaults.colors(thumbColor = AccentBright, activeTrackColor = AccentPrimary)
            )
            Spacer(modifier = Modifier.width(8.dp))
            UnitToggle(isKg, onIsKgChange, "kg", "lbs")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Height: ${height.toInt()}${if (isCm) "cm" else "ft"}", color = TextPrimary, modifier = Modifier.weight(1f))
            Slider(
                value = height,
                onValueChange = onHeightChange,
                valueRange = 140f..220f,
                modifier = Modifier.weight(2f),
                colors = SliderDefaults.colors(thumbColor = AccentBright, activeTrackColor = AccentPrimary)
            )
            Spacer(modifier = Modifier.width(8.dp))
            UnitToggle(isCm, onIsCmChange, "cm", "ft")
        }
    }
}

@Composable
fun UnitToggle(isSelected: Boolean, onSelectedChange: (Boolean) -> Unit, option1: String, option2: String) {
    Row(
        modifier = Modifier
            .background(BgElevated, RoundedCornerShape(50.dp))
            .padding(2.dp)
    ) {
        Box(
            modifier = Modifier
                .background(if (isSelected) AccentPrimary else Color.Transparent, RoundedCornerShape(50.dp))
                .clickable { onSelectedChange(true) }
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(option1, color = if (isSelected) Color.White else TextSecondary, fontSize = 12.sp)
        }
        Box(
            modifier = Modifier
                .background(!isSelected) AccentPrimary else Color.Transparent, RoundedCornerShape(50.dp))
                .clickable { onSelectedChange(false) }
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(option2, color = if (!isSelected) Color.White else TextSecondary, fontSize = 12.sp)
        }
    }
}

@Composable
fun StartingPointPage(bodyFat: Int, onBodyFatChange: (Int) -> Unit, weakPoints: MutableList<String>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Your Starting Point", style = Typography.headlineMedium)
        
        Text("Estimate Body Fat: $bodyFat%", color = TextPrimary)
        Slider(
            value = bodyFat.toFloat(),
            onValueChange = { onBodyFatChange(it.toInt()) },
            valueRange = 10f..35f,
            steps = 4,
            colors = SliderDefaults.colors(thumbColor = AccentBright, activeTrackColor = AccentPrimary)
        )

        Text("Select Weak Points (Multi-select):", color = TextPrimary)
        val points = listOf("Narrow shoulders", "Wide waist", "Small lats", "Flat chest", "Skinny arms", "No abs")
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(points) { point ->
                val isSelected = weakPoints.contains(point)
                Box(
                    modifier = Modifier
                        .background(if (isSelected) AccentSubtle else BgElevated, RoundedCornerShape(50.dp))
                        .border(1.dp, if (isSelected) AccentPrimary else Color.Transparent, RoundedCornerShape(50.dp))
                        .clickable {
                            if (isSelected) weakPoints.remove(point) else weakPoints.add(point)
                        }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(point, color = if (isSelected) AccentBright else TextSecondary, fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun ExperienceLevelPage(selected: String, onSelectedChange: (String) -> Unit) {
    val levels = listOf(
        "Beginner" to "less than 1 year, learning form",
        "Intermediate" to "1–3 years, consistent training",
        "Advanced" to "3+ years, periodized programming"
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Experience Level", style = Typography.headlineMedium)
        
        levels.forEach { (level, desc) ->
            val isSelected = selected == level
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, if (isSelected) AccentPrimary else AccentSubtle, RoundedCornerShape(20.dp))
                    .clickable { onSelectedChange(level) },
                colors = CardDefaults.cardColors(containerColor = if (isSelected) AccentSubtle else BgSurface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(level, style = Typography.headlineMedium, fontSize = 18.sp, color = if (isSelected) AccentBright else TextPrimary)
                    Text(desc, style = Typography.labelSmall)
                }
            }
        }
    }
}

@Composable
fun SchedulePage(days: Int, onDaysChange: (Int) -> Unit, time: String, onTimeChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("Schedule", style = Typography.headlineMedium)
        
        Text("Days per week:", color = TextPrimary)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(3, 4, 5, 6).forEach { day ->
                val isSelected = days == day
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(if (isSelected) AccentPrimary else BgElevated, CircleShape)
                        .clickable { onDaysChange(day) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(day.toString(), color = if (isSelected) Color.White else TextSecondary, fontWeight = FontWeight.Bold)
                }
            }
        }

        Text("Preferred workout time:", color = TextPrimary)
        val times = listOf("Morning", "Afternoon", "Evening", "Flexible")
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(times) { t ->
                val isSelected = time == t
                Box(
                    modifier = Modifier
                        .background(if (isSelected) AccentPrimary else BgElevated, RoundedCornerShape(50.dp))
                        .clickable { onTimeChange(t) }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(t, color = if (isSelected) Color.White else TextSecondary)
                }
            }
        }
    }
}

@Composable
fun TrainingEnvironmentPage(selected: String, onSelectedChange: (String) -> Unit) {
    val options = listOf(
        "Full Gym" to "barbells, cables, machines",
        "Home + Dumbbells" to "adjustable or fixed set",
        "Home Minimal" to "resistance bands + bodyweight",
        "Outdoor" to "bars, bench, bodyweight"
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Training Environment", style = Typography.headlineMedium)
        
        options.forEach { (name, desc) ->
            val isSelected = selected == name
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, if (isSelected) AccentPrimary else AccentSubtle, RoundedCornerShape(20.dp))
                    .clickable { onSelectedChange(name) },
                colors = CardDefaults.cardColors(containerColor = if (isSelected) AccentSubtle else BgSurface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(name, style = Typography.headlineMedium, fontSize = 18.sp, color = if (isSelected) AccentBright else TextPrimary)
                    Text(desc, style = Typography.labelSmall)
                }
            }
        }
    }
}

@Composable
fun GoalAestheticPage(selected: String, onSelectedChange: (String) -> Unit) {
    val options = listOf("Classic", "Men's Physique", "Athletic", "Frank Zane")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Goal Aesthetic", style = Typography.headlineMedium)
        
        options.forEach { option ->
            val isSelected = selected == option
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, if (isSelected) AccentPrimary else AccentSubtle, RoundedCornerShape(20.dp))
                    .clickable { onSelectedChange(option) },
                colors = CardDefaults.cardColors(containerColor = if (isSelected) AccentSubtle else BgSurface)
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    Text(option, style = Typography.headlineMedium, fontSize = 18.sp, color = if (isSelected) AccentBright else TextPrimary)
                }
            }
        }
    }
}

@Composable
fun BlueprintPage(name: String, weight: Float, height: Float, bodyFat: Int, onComplete: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Your Blueprint Is Ready", style = Typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Calculated for $name", style = Typography.labelSmall)
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, AccentSubtle, RoundedCornerShape(20.dp)),
            colors = CardDefaults.cardColors(containerColor = BgSurface)
        ) {
            Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                BlueprintRow("Calculated TDEE", "2,400 kcal")
                BlueprintRow("Protein Target", "165g")
                BlueprintRow("Ideal V-Taper Ratio", "1.618")
            }
        }
    }
}

@Composable
fun BlueprintRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = TextSecondary)
        Text(value, color = AccentBright, fontWeight = FontWeight.Bold)
    }
}

// Custom modifier for bounce click effect
fun Modifier.bounceClick() = composed {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (isPressed) 0.96f else 1f, label = "bounce")
    
    this
        .scale(scale)
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = {
                    isPressed = true
                    tryAwaitRelease()
                    isPressed = false
                }
            )
        }
}
