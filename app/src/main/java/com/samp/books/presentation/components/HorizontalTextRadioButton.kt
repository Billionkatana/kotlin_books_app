package com.samp.books.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalTextRadioButton(color: Color, height: Dp = 40.dp, selected: Boolean, text: String, onOptionSelected: (String) -> Unit) {

    Row(
        Modifier
            .height(height)
            .selectable(
                selected = (selected),
                onClick = { onOptionSelected(text) },
                role = Role.RadioButton
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
       RadioButton(
           selected = (selected),
           colors = RadioButtonDefaults.colors().copy(selectedColor = color),
           onClick = null // null recommended for accessibility with screen readers
       )
       Text(
           text = text,
           style = MaterialTheme.typography.bodyLarge,
           color = color,
           modifier = Modifier.padding(start = 16.dp)
       )
    }

}