package ru.alex.minibank.main_screen

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import cz.msebera.android.httpclient.HttpHeaders
import cz.msebera.android.httpclient.HttpStatus
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity
import cz.msebera.android.httpclient.client.methods.HttpGet
import cz.msebera.android.httpclient.client.methods.HttpPost
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder
import cz.msebera.android.httpclient.message.BasicNameValuePair
import cz.msebera.android.httpclient.util.EntityUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.alex.minibank.MemoryStorage
import ru.alex.minibank.api.Links
import ru.alex.minibank.api.RequestParams
import ru.alex.minibank.login.onMain
import ru.alex.minibank.model.User
import java.lang.Exception

class MainScreenViewModel : ViewModel() {

    private val httpClient = HttpClientBuilder.create().build()
    private val gson = Gson()

    fun addMoney(amount: Long, callbackResult: (User?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                val post = HttpPost(Links.ADD_MONEY_LINK)
                val requestParams = listOf(
                    BasicNameValuePair(RequestParams.AMOUNT, amount.toString())
                )
                post.entity = UrlEncodedFormEntity(requestParams)
                post.addHeader(HttpHeaders.AUTHORIZATION, MemoryStorage.token)
                try {
                    val response = httpClient.execute(post)
                    val statusCode = response.statusLine.statusCode
                    val user = gson.fromJson(
                        EntityUtils.toString(response.entity),
                        User::class.java
                    )
                    onMain {
                        if (statusCode == HttpStatus.SC_OK) {
                            callbackResult.invoke(user)
                        } else {
                            callbackResult.invoke(null)
                        }
                    }

                } catch (e: Exception) {
                    onMain {
                        callbackResult.invoke(null)
                    }
                }
            }
        }
    }

    fun getMoney(amount: Long, callbackResult: (User?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                val post = HttpPost(Links.GET_MONEY_LINK)
                val requestParams = listOf(
                    BasicNameValuePair(RequestParams.AMOUNT, amount.toString())
                )
                post.entity = UrlEncodedFormEntity(requestParams)
                post.addHeader(HttpHeaders.AUTHORIZATION, MemoryStorage.token)
                try {
                    val response = httpClient.execute(post)
                    val statusCode = response.statusLine.statusCode
                    val user = gson.fromJson(
                        EntityUtils.toString(response.entity),
                        User::class.java
                    )
                    onMain {
                        if (statusCode == HttpStatus.SC_OK) {
                            callbackResult.invoke(user)
                        } else {
                            callbackResult.invoke(null)
                        }
                    }

                } catch (e: Exception) {
                    onMain {
                        callbackResult.invoke(null)
                    }
                }
            }
        }
    }

    fun sendMoney(targetName: String, amount: Long, callbackResult: (User?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                val post = HttpPost(Links.SEND_MONEY_LINK)
                val requestParams = listOf(
                    BasicNameValuePair(RequestParams.TARGET_NAME, targetName),
                    BasicNameValuePair(RequestParams.AMOUNT, amount.toString())
                )
                post.entity = UrlEncodedFormEntity(requestParams)
                post.addHeader(HttpHeaders.AUTHORIZATION, MemoryStorage.token)
                try {
                    val response = httpClient.execute(post)
                    val statusCode = response.statusLine.statusCode
                    val user = gson.fromJson(
                        EntityUtils.toString(response.entity),
                        User::class.java
                    )
                    onMain {
                        if (statusCode == HttpStatus.SC_OK) {
                            callbackResult.invoke(user)
                        } else {
                            callbackResult.invoke(null)
                        }
                    }

                } catch (e: Exception) {
                    onMain {
                        callbackResult.invoke(null)
                    }
                }
            }
        }
    }

    fun loadHistory(callbackResult: (String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                val get = HttpGet(Links.HISTORY_LINK)
                get.addHeader(HttpHeaders.AUTHORIZATION, MemoryStorage.token)
                try {
                    val response = httpClient.execute(get)
                    val statusCode = response.statusLine.statusCode
                    val history = EntityUtils.toString(response.entity)

                    onMain {
                        if (statusCode == HttpStatus.SC_OK) {
                            callbackResult.invoke(history)
                        } else {
                            callbackResult.invoke("Error")
                        }
                    }

                } catch (e: Exception) {
                    onMain {
                        callbackResult.invoke("Error")
                    }
                }
            }
        }
    }

    fun signOut(callbackResult: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                val post = HttpPost(Links.SIGN_OUT_LINK)
                post.addHeader(HttpHeaders.AUTHORIZATION, MemoryStorage.token)
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
}
