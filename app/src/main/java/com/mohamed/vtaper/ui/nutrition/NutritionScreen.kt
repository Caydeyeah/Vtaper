package com.mohamed.vtaper.ui.nutrition

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamed.vtaper.ui.theme.*

@Composable
fun NutritionScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgPrimary)
            .padding(16.dp)
    ) {
        Text("Nutrition Blueprint", style = Typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        
        // Macros Chart Placeholder
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, AccentSubtle, RoundedCornerShape(20.dp)),
            colors = CardDefaults.cardColors(containerColor = BgSurface),
            shape = RoundedCornerShape(20.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Donut chart placeholder
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(AccentSubtle, RoundedCornerShape(50.dp))
                        .border(4.dp, AccentBright, RoundedCornerShape(50.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("2000", color = TextPrimary, fontWeight = FontWeight.Bold)
                        Text("kcal", color = TextSecondary, fontSize = 10.sp)
                    }
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    MacroRow("Protein", "150g", AccentBright)
                    MacroRow("Carbs", "200g", Color.Cyan)
                    MacroRow("Fats", "60g", GoldAchievement)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        Text("Today's Meals", style = Typography.headlineMedium, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                MealCard(
                    name = "Oatmeal with Protein",
                    emoji = "🥣",
                    time = "10 min",
                    cals = "400",
                    pcf = "30/50/10",
                    tip = "Slow digesting carbs for workout energy."
                )
            }
            item {
                MealCard(
                    name = "Chicken and Rice",
                    emoji = "🍗",
                    time = "20 min",
                    cals = "600",
                    pcf = "50/60/10",
                    tip = "Classic muscle building meal."
                )
            }
        }
    }
}

@Composable
fun MacroRow(label: String, value: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(10.dp).background(color, RoundedCornerShape(2.dp)))
        Spacer(modifier = Modifier.width(8.dp))
        Text(label, color = TextSecondary, fontSize = 14.sp)
        Spacer(modifier = Modifier.weight(1f))
        Text(value, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}

@Composable
fun MealCard(
    name: String,
    emoji: String,
    time: String,
    cals: String,
    pcf: String,
    tip: String
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
                Text("$emoji $name", style = Typography.headlineMedium, fontSize = 18.sp)
                Text("$cals kcal", color = AccentBright, fontWeight = FontWeight.Bold)
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            Text("Prep: $time | P/C/F: $pcf", style = Typography.labelSmall)
            
            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = DividerColor)
            Spacer(modifier = Modifier.height(8.dp))
            
            Text("Why this helps: $tip", style = Typography.bodyLarge, fontSize = 14.sp)
        }
    }
}
