package com.mohamed.vtaper.ui.workout

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamed.vtaper.ui.theme.*

@Composable
fun WorkoutScreen() {
    var isActiveWorkoutMode by remember { mutableStateOf(false) }

    if (isActiveWorkoutMode) {
        ActiveWorkoutMode(onClose = { isActiveWorkoutMode = false })
    } else {
        WorkoutList(onStartWorkout = { isActiveWorkoutMode = true })
    }
}

@Composable
fun WorkoutList(onStartWorkout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgPrimary)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Today's Workout", style = Typography.headlineMedium)
            Button(
                onClick = onStartWorkout,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Brush.horizontalGradient(listOf(AccentPrimary, AccentBright)),
                            RoundedCornerShape(50.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Start", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ExerciseCard(
                    name = "Overhead Press",
                    environment = "GYM",
                    muscle = "Shoulders",
                    sets = "3 × 10–12 reps",
                    rest = "60s rest",
                    rpe = "RPE 7",
                    setup = "feet hip-width, brace core",
                    move = "drive elbows up, not hands",
                    avoid = "shrugging your traps",
                    tip = "creates the V",
                    alternative = "Dumbbell Press"
                )
            }
            item {
                ExerciseCard(
                    name = "Lateral Raise",
                    environment = "HOME",
                    muscle = "Shoulders",
                    sets = "4 × 12–15 reps",
                    rest = "45s rest",
                    rpe = "RPE 8",
                    setup = "lean slightly forward",
                    move = "lead with elbows",
                    avoid = "using momentum",
                    tip = "builds width",
                    alternative = "Band Lateral Raise"
                )
            }
        }
    }
}

@Composable
fun ActiveWorkoutMode(onClose: () -> Unit) {
    var reps by remember { mutableStateOf(0) }
    var restTime by remember { mutableStateOf(60) }
    var isTimerRunning by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgPrimary)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onClose) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
            }
            Text("1 of 4 Exercises", color = TextSecondary)
            Spacer(modifier = Modifier.width(48.dp)) // Balance the back button
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text("Overhead Press", style = Typography.headlineMedium, fontSize = 32.sp)
        Text("Set 1 of 3", color = TextSecondary)

        Spacer(modifier = Modifier.weight(1f))

        // Animated Rep Counter (Tap to count)
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(AccentSubtle, RoundedCornerShape(100.dp))
                .border(2.dp, AccentBright, RoundedCornerShape(100.dp))
                .clickable { reps++ },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(reps.toString(), fontSize = 64.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text("Tap to count", fontSize = 14.sp, color = TextSecondary)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Rest Timer / Log Set
        if (!isTimerRunning) {
            Button(
                onClick = { isTimerRunning = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
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
                    Text("Log Set & Start Rest", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
        } else {
            // Timer Countdown UI
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .border(1.dp, AccentPrimary, RoundedCornerShape(50.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Resting: $restTime s", color = AccentBright, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            // In a real app, a LaunchedEffect would decrement restTime
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ExerciseCard(
    name: String,
    environment: String,
    muscle: String,
    sets: String,
    rest: String,
    rpe: String,
    setup: String,
    move: String,
    avoid: String,
    tip: String,
    alternative: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, AccentSubtle, RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(containerColor = BgSurface),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Badge(text = environment, color = AccentPrimary)
                    Badge(text = muscle, color = AccentBright)
                }
                Text("▶ Demo", color = AccentBright, fontWeight = FontWeight.Bold)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(name, style = Typography.headlineMedium, fontSize = 20.sp)
            Text("$sets · $rest · $rpe", style = Typography.labelSmall)
            
            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = DividerColor)
            Spacer(modifier = Modifier.height(8.dp))
            
            Text("📐 Setup: $setup", style = Typography.bodyLarge, fontSize = 14.sp)
            Text("⚡ Move: $move", style = Typography.bodyLarge, fontSize = 14.sp)
            Text("⚠️ Avoid: $avoid", style = Typography.bodyLarge, fontSize = 14.sp)
            
            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = DividerColor)
            Spacer(modifier = Modifier.height(8.dp))
            
            Text("V-Taper tip: $tip", style = Typography.bodyLarge, fontSize = 14.sp, color = AccentBright)
            Text("Can't do it yet? → $alternative", style = Typography.bodyLarge, fontSize = 14.sp, color = TextSecondary)
        }
    }
}

@Composable
fun Badge(text: String, color: Color) {
    Box(
        modifier = Modifier
            .background(color.copy(alpha = 0.2f), RoundedCornerShape(50.dp))
            .border(1.dp, color, RoundedCornerShape(50.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text, color = color, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}
