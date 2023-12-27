package com.codingub.belarusianhistory.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.codingub.belarusianhistory.App
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay

object NetworkManager {

    private val context: Context get() = App.getInstance()
    private val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val isConnected: Boolean
        get() = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
            hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false

    //for checking user connection
    private suspend fun awaitConnection() {
        while(true){
            if(isConnected) return
            delay(100)
        }
    }

    suspend fun <T : Any> awaitNetworkConnection(channel: Channel<T>, response: T) {
        if (!isConnected) {
            channel.send(response)
            awaitConnection()
        }
    }
 }