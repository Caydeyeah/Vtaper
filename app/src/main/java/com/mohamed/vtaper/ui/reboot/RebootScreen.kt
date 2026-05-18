package com.mohamed.vtaper.ui.reboot

import android.app.Activity
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamed.vtaper.ui.theme.*

@Composable
fun RebootScreen() {
    val context = LocalContext.current
    val activity = context as? Activity
    var isUrgeSurfingActive by remember { mutableStateOf(false) }

    // Apply FLAG_SECURE to prevent screenshots
    DisposableEffect(Unit) {
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        onDispose {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }

    if (isUrgeSurfingActive) {
        UrgeSurfingFlow(onClose = { isUrgeSurfingActive = false })
    } else {
        RebootHome(onPanicClick = { isUrgeSurfingActive = true })
    }
}

@Composable
fun RebootHome(onPanicClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgPrimary)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Reboot Mode", style = Typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))
        
        // Streak Counter
        Box(
            modifier = Modifier
                .size(220.dp)
                .background(BgSurface, RoundedCornerShape(110.dp))
                .border(4.dp, AccentPrimary, RoundedCornerShape(110.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("05", fontSize = 64.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text("Days", fontSize = 16.sp, color = TextSecondary)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Best: 14 days", fontSize = 12.sp, color = AccentBright)
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Panic Button
        Button(
            onClick = onPanicClick,
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
                        Brush.horizontalGradient(listOf(Destructive, Color(0xFFE07777))),
                        RoundedCornerShape(50.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("I'm struggling right now", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Identity Statement
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, AccentSubtle, RoundedCornerShape(20.dp)),
            colors = CardDefaults.cardColors(containerColor = BgSurface),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("My Why", style = Typography.headlineMedium, fontSize = 18.sp, color = AccentBright)
                Spacer(modifier = Modifier.height(8.dp))
                Text("I channel my energy into building something real.", style = Typography.bodyLarge)
            }
        }
    }
}

@Composable
fun UrgeSurfingFlow(onClose: () -> Unit) {
    var step by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgPrimary)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (step) {
            1 -> {
                Text("Step 1: Acknowledge", style = Typography.headlineMedium, color = AccentBright)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "You caught it. That's strength.\nIt will pass in minutes.",
                    style = Typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text("Urge wave peaks in ~10 min then falls.", color = TextSecondary)
            }
            2 -> {
                Text("Step 2: Breathe (4-7-8)", style = Typography.headlineMedium, color = AccentBright)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Inhale 4s → Hold 7s → Exhale 8s", style = Typography.bodyLarge)
                Spacer(modifier = Modifier.height(32.dp))
                // Animated circle placeholder
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(AccentSubtle, RoundedCornerShape(50.dp))
                )
            }
            3 -> {
                Text("Step 3: Physical Interrupt", style = Typography.headlineMedium, color = AccentBright)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Do 30 push-ups NOW\nor splash cold water on your face.", style = Typography.bodyLarge, textAlign = TextAlign.Center)
            }
            4 -> {
                Text("Step 4: Redirect Energy", style = Typography.headlineMedium, color = AccentBright)
                Spacer(modifier = Modifier.height(16.dp))
                Text("That drive is testosterone. Let's use it.", style = Typography.bodyLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onClose) {
                    Text("Open Today's Workout")
                }
            }
            5 -> {
                Text("Step 5: Check In", style = Typography.headlineMedium, color = AccentBright)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Did you make it through?", style = Typography.bodyLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(onClick = onClose) { Text("Yes 💪") }
                    Button(onClick = onClose) { Text("Not this time") }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (step < 5) {
            Button(onClick = { step++ }) {
                Text("Next")
            }
        }
    }
}
