package com.codingub.belarusianhistory.utils

import android.util.Log
import com.codingub.belarusianhistory.utils.logger.HistoryLogger
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions

/**
 * Firebase translator
 */
object RemoteTranslator {

    private val log: HistoryLogger = HistoryLogger()
    private lateinit var translator: Translator

    fun prepareMode() {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.RUSSIAN)
            .setTargetLanguage(TranslateLanguage.BELARUSIAN)
            .build()
        translator = Translation.getClient(options)

        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()

        translator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                log.d("RemoteTranslator","Translator installed successfully")
            }
            .addOnFailureListener { exception ->
                log.d("RemoteTranslator",exception)
            }
    }

    /**
     *  Used for translating the text
     */
    fun translateText(text: String, callback: (String) -> Unit) {
        prepareMode()
        translator.translate(text)
            .addOnSuccessListener { result ->
                callback(result)
            }
    }
}