package ru.alex.minibank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ru.alex.minibank.login.LoginFragment

class MainActivity : AppCompatActivity() {

    private lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigator = Navigator(supportFragmentManager)
        navigator.show(LoginFragment::class.java, false)
    }
}

fun View.visible() {
    if (this.visibility != View.VISIBLE) {
        this.visibility = View.VISIBLE
    }
}

fun View.gone() {
    if (this.visibility != View.GONE) {
        this.visibility = View.GONE
    }
}
