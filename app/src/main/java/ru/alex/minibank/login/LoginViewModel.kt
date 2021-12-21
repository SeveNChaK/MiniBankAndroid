package ru.alex.minibank.login

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
import java.lang.Exception

class LoginViewModel : ViewModel() {
    private val httpClient = HttpClientBuilder.create().build()

    fun tryToMain(callbackResult: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {

//                Thread.sleep(2000)

                val get = HttpGet(Links.MAIN_PAGE_LINK)
                try {
                    val response = httpClient.execute(get)
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

    fun signIn(login: String, password: String, callbackResult: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {

//                Thread.sleep(2000)

                val post = HttpPost(Links.SIGN_IN_LINK)
                val requestParams = listOf(
                    BasicNameValuePair(RequestParams.LOGIN, login),
                    BasicNameValuePair(RequestParams.PASSWORD, password)
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

    fun signUp(login: String, password: String, confirmPassword: String, callbackResult: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            withContext(Dispatchers.IO) {

//                Thread.sleep(2000)

                val post = HttpPost(Links.SIGN_UP_LINK)
                val requestParams = listOf(
                    BasicNameValuePair(RequestParams.LOGIN, login),
                    BasicNameValuePair(RequestParams.PASSWORD, password),
                    BasicNameValuePair(RequestParams.CONFIRM_PASSWORD, confirmPassword)
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
}

suspend fun CoroutineScope.onMain(block: () -> Unit) {
    withContext(Dispatchers.Main) {
        block.invoke()
    }
}
