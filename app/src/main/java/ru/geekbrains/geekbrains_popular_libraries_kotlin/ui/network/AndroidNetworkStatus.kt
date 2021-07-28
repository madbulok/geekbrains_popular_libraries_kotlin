package ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.network.INetworkStatus

class AndroidNetworkStatus(context: Context) : INetworkStatus {

    private val statusBehaviorSubject: BehaviorSubject<Boolean> = BehaviorSubject.create()

    init {
        statusBehaviorSubject.onNext(false)
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val request = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(request, object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                statusBehaviorSubject.onNext(true)
            }

            override fun onLost(network: Network) {
                statusBehaviorSubject.onNext(false)
            }
        })
    }


    override fun isOnline(): Observable<Boolean> = statusBehaviorSubject

    override fun isOnlineSingle(): Single<Boolean> = statusBehaviorSubject.first(false)
}