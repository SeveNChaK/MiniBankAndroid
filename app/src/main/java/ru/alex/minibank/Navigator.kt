package ru.alex.minibank

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class Navigator(private val fragmentManager: FragmentManager) {

    fun show(clazz: Class<out Fragment>, addToBackStack: Boolean, args: Bundle ? = null) {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, clazz.newInstance().apply { if (args != null) arguments = args }, clazz.simpleName)
            .apply { if (addToBackStack) addToBackStack(clazz.simpleName) }
            .commit()
    }
}
