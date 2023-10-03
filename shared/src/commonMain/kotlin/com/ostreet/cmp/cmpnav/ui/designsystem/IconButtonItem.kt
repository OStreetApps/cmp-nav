package com.ostreet.cmp.cmpnav.ui.designsystem

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector

sealed interface IconButtonItem {
    object NoIcon : IconButtonItem
    data class VectorIcon(val imageVector: ImageVector, val contentDes: String, val onClick: () -> Unit) :
        IconButtonItem
    data class BitmapIcon(val imageBitmap: ImageBitmap, val contentDes: String, val onClick: () -> Unit) :
        IconButtonItem
}