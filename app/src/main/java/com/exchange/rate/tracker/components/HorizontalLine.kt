package com.exchange.rate.tracker.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.exchange.rate.tracker.R

@NonRestartableComposable
@Composable
fun HorizontalLine(
  modifier: Modifier = Modifier,
  lineColor: Color = colorResource(id = R.color.outline)
) {
  Canvas(
    modifier = modifier
      .height(1.dp)
      .fillMaxWidth()
  ) {
    drawLine(
      color = lineColor,
      start = Offset(0f, 0f),
      end = Offset(size.width, 0f),
    )
  }
}