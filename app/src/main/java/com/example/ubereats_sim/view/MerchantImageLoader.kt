package com.example.ubereats_sim.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext

private val merchantImageCache = mutableMapOf<String, ImageBitmap?>()
private val merchantProductImageCache = mutableMapOf<String, ImageBitmap?>()

@Composable
fun rememberMerchantImage(
    merchantName: String,
    reqWidth: Int = 640,
    reqHeight: Int = 360
): ImageBitmap? {
    val context = LocalContext.current
    val cacheKey = "$merchantName@$reqWidth@$reqHeight"
    return remember(merchantName, context, reqWidth, reqHeight) {
        synchronized(merchantImageCache) {
            if (!merchantImageCache.containsKey(cacheKey)) {
                merchantImageCache[cacheKey] = loadMerchantImage(context, merchantName, reqWidth, reqHeight)
            }
            merchantImageCache[cacheKey]
        }
    }
}

@Composable
fun rememberMerchantProductImage(
    merchantName: String,
    productName: String,
    reqWidth: Int = 640,
    reqHeight: Int = 360
): ImageBitmap? {
    val context = LocalContext.current
    val cacheKey = "$merchantName|$productName@$reqWidth@$reqHeight"
    return remember(merchantName, productName, context, reqWidth, reqHeight) {
        synchronized(merchantProductImageCache) {
            if (!merchantProductImageCache.containsKey(cacheKey)) {
                merchantProductImageCache[cacheKey] =
                    loadMerchantProductImage(context, merchantName, productName, reqWidth, reqHeight)
            }
            merchantProductImageCache[cacheKey]
        }
    }
}

private fun loadMerchantImage(
    context: Context,
    merchantName: String,
    reqWidth: Int,
    reqHeight: Int
): ImageBitmap? {
    val imagePath = findMerchantImagePath(context, merchantName) ?: return null
    return runCatching {
        context.assets.open(imagePath).use { input ->
            val bytes = input.readBytes()
            decodeSampledBitmap(bytes, reqWidth, reqHeight)?.asImageBitmap()
        }
    }.getOrNull()
}

private fun findMerchantImagePath(context: Context, merchantName: String): String? {
    val candidateDirs = listOf("dianpu", "Grocery", "Convenience")
    val candidateExts = listOf("jpg", "jpeg", "png", "webp")

    for (dir in candidateDirs) {
        for (ext in candidateExts) {
            val path = "$dir/$merchantName.$ext"
            val ok = runCatching {
                context.assets.open(path).close()
                true
            }.getOrDefault(false)
            if (ok) return path
        }

        val files = context.assets.list(dir) ?: continue
        val exactFile = files.firstOrNull { file ->
            val stem = file.substringBeforeLast('.')
            stem.equals(merchantName, ignoreCase = true)
        }
        if (exactFile != null) return "$dir/$exactFile"
    }
    return null
}

private fun loadMerchantProductImage(
    context: Context,
    merchantName: String,
    productName: String,
    reqWidth: Int,
    reqHeight: Int
): ImageBitmap? {
    val imagePath = findMerchantProductImagePath(context, merchantName, productName) ?: return null
    return runCatching {
        context.assets.open(imagePath).use { input ->
            val bytes = input.readBytes()
            decodeSampledBitmap(bytes, reqWidth, reqHeight)?.asImageBitmap()
        }
    }.getOrNull()
}

private fun findMerchantProductImagePath(context: Context, merchantName: String, productName: String): String? {
    val files = context.assets.list(merchantName)?.filter {
        it.endsWith(".jpg", true) || it.endsWith(".jpeg", true) || it.endsWith(".png", true) || it.endsWith(".webp", true)
    } ?: return null
    if (files.isEmpty()) return null

    val targetStemRaw = productName.trim()
    val exact = files.firstOrNull { file ->
        val stem = file.substringBeforeLast('.')
        stem.equals(targetStemRaw, ignoreCase = true)
    }
    if (exact != null) return "$merchantName/$exact"

    val targetNorm = normalizeName(targetStemRaw)
    val targetTokens = tokenizeName(targetStemRaw)
    var bestFile: String? = null
    var bestScore = -1

    for (file in files) {
        val stem = file.substringBeforeLast('.')
        val fileNorm = normalizeName(stem)
        val fileTokens = tokenizeName(stem)
        val sharedTokenCount = targetTokens.intersect(fileTokens.toSet()).size
        var score = sharedTokenCount * 3

        if (targetNorm.isNotEmpty() && fileNorm.isNotEmpty()) {
            if (targetNorm == fileNorm) score += 100
            if (targetNorm.contains(fileNorm) || fileNorm.contains(targetNorm)) score += 20
        }

        if (score > bestScore) {
            bestScore = score
            bestFile = file
        }
    }

    return if (bestScore > 0 && bestFile != null) "$merchantName/$bestFile" else null
}

private fun normalizeName(value: String): String {
    return value.lowercase()
        .replace("w/", "with")
        .replace("&", "and")
        .replace(Regex("[^a-z0-9]"), "")
}

private fun tokenizeName(value: String): List<String> {
    return value.lowercase()
        .replace("w/", "with")
        .replace("&", " and ")
        .split(Regex("[^a-z0-9]+"))
        .filter { it.isNotBlank() }
}

private fun decodeSampledBitmap(bytes: ByteArray, reqWidth: Int, reqHeight: Int): Bitmap? {
    val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
    BitmapFactory.decodeByteArray(bytes, 0, bytes.size, bounds)

    val sampleSize = calculateInSampleSize(bounds, reqWidth, reqHeight)
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = false
        inSampleSize = sampleSize
        inPreferredConfig = Bitmap.Config.RGB_565
    }
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
}

private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    val (height, width) = options.outHeight to options.outWidth
    if (height <= reqHeight && width <= reqWidth) return 1

    var inSampleSize = 1
    var halfHeight = height / 2
    var halfWidth = width / 2
    while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
        inSampleSize *= 2
    }
    return inSampleSize.coerceAtLeast(1)
}
