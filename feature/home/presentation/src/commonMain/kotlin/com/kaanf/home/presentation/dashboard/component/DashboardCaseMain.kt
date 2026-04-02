package com.kaanf.home.presentation.dashboard.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaanf.home.domain.model.Case

@Composable
fun DashboardCaseMain(
    cases: List<Case>
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 8.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item(
            key = "active_cases_header",
            contentType = "header",
        ) {
            ActiveCasesHeader()
        }

        items(
            items = cases,
            key = { it.id },
            contentType = { "case_card" },
        ) { case ->
            DashboardCaseCard(case) {
            }
        }
    }
}
