package com.example.ubereats_sim.model

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File

object AppEventLogger {
    private const val fileName = "messages.json"
    private const val tag = "AppEventLogger"
    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val listType = object : TypeToken<MutableList<Map<String, Any?>>>() {}.type
    private val mapType = object : TypeToken<Map<String, Any?>>() {}.type

    fun append(
        context: Context,
        action: String,
        page: String,
        extraData: Map<String, Any?> = emptyMap()
    ) {
        runCatching {
            val file = File(context.filesDir, fileName)
            val events = loadEvents(file)
            events.add(
                linkedMapOf(
                    "action" to action,
                    "page" to page,
                    "timestamp" to System.currentTimeMillis(),
                    "extra_data" to extraData
                )
            )
            file.writeText(gson.toJson(events))
        }.onFailure {
            Log.e(tag, "Failed to append event", it)
        }
    }

    private fun loadEvents(file: File): MutableList<Map<String, Any?>> {
        if (!file.exists()) {
            return mutableListOf()
        }
        val content = file.readText().trim()
        if (content.isBlank()) {
            return mutableListOf()
        }
        return runCatching {
            gson.fromJson<MutableList<Map<String, Any?>>>(content, listType) ?: mutableListOf()
        }.getOrElse {
            // Backward compatibility: migrate legacy JSONL content into a JSON array.
            content
                .lineSequence()
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .mapNotNull { line ->
                    runCatching {
                        gson.fromJson<Map<String, Any?>>(line, mapType)
                    }.getOrNull()
                }
                .toMutableList()
        }
    }
}
