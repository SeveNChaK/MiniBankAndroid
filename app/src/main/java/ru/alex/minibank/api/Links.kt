package ru.alex.minibank.api

object Links {
    private const val HOST = "http://192.168.0.106:8080"
    private const val HOST_PROTECTED = "$HOST/api"

    const val SIGN_IN_LINK = "$HOST/signIn"
    const val SIGN_UP_LINK = "$HOST/signUp"

    const val MAIN_PAGE_LINK = "$HOST_PROTECTED/main"
    const val ADD_MONEY_LINK = "$HOST_PROTECTED/addMoney"
    const val GET_MONEY_LINK = "$HOST_PROTECTED/getMoney"
    const val SEND_MONEY_LINK = "$HOST_PROTECTED/send"
    const val HISTORY_LINK = "$HOST_PROTECTED/transactions"
    const val SIGN_OUT_LINK = "$HOST_PROTECTED/signOut"
}
