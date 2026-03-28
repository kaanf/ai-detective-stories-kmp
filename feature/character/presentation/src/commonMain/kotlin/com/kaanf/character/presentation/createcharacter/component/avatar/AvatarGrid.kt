package com.kaanf.character.presentation.createcharacter.component.avatar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kaanf.character.domain.model.avatar.UserAvatar
import com.kaanf.core.designsystem.theme.AccessDefaults

@Composable
fun AvatarGrid(
    avatars: List<UserAvatar>,
    selectedAvatarId: String?,
    onAvatarSelected: (String) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier =
            Modifier
                .fillMaxWidth()
                .heightIn(max = 584.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        items(
            items = avatars,
            key = { avatar -> avatar.id },
        ) { avatar ->
            AvatarCard(
                avatar = avatar,
                isSelected = avatar.id == selectedAvatarId,
                onClick = { onAvatarSelected(avatar.id) },
            )
        }
    }
}

@Composable
private fun AvatarCard(
    avatar: UserAvatar,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(184.dp)
                .background(Color(0xFF111111))
                .border(
                    width = 1.dp,
                    color = if (isSelected) AccessDefaults.FieldText else AccessDefaults.ButtonBorder,
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick,
                ),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            AsyncImage(
                model = avatar.avatarImageUrl,
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }
    }
}
