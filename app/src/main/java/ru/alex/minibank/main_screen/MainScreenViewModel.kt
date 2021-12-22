package ru.alex.minibank.main_screen

import androidx.lifecycle.ViewModel
import cz.msebera.android.httpclient.HttpStatus
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity
import cz.msebera.android.httpclient.client.methods.HttpGet
import cz.msebera.android.httpclient.client.methods.HttpPost
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder
import cz.msebera.android.httpclient.message.BasicNameValuePair
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.alex.minibank.api.Links
import ru.alex.minibank.api.RequestParams
import ru.alex.minibank.login.onMain
import java.lang.Exception

class MainScreenViewModel : ViewModel() {

    private val httpClient = HttpClientBuilder.create().build()

    fun addMoney(amount: Long, callbackResult: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                val post = HttpPost(Links.ADD_MONEY_LINK)
                val requestParams = listOf(
                    BasicNameValuePair(RequestParams.AMOUNT, amount.toString())
                )
                post.entity = UrlEncodedFormEntity(requestParams)
                try {
                    val response = httpClient.execute(post)
                    val statusCode = response.statusLine.statusCode

                    onMain {
                        if (statusCode == HttpStatus.SC_OK) {
                            callbackResult.invoke(true)
                        } else {
                            callbackResult.invoke(false)
                        }
                    }

                } catch (e: Exception) {
                    onMain {
                        callbackResult.invoke(false)
                    }
                }
            }
        }
    }

    fun getMoney(amount: Long, callbackResult: (Boolean) -> Unit) {

    }

    fun sendMoney(targetName: String, amount: Long, callbackResult: (Boolean) -> Unit) {

    }

    fun loadHistory(callbackResult: (String) -> Unit) {

    }
}
