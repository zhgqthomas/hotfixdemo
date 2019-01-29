package com.zhgqthomas.github.hotfixdemo

import android.content.Context
import android.widget.Toast

class Bug {

    fun calculate(context: Context) {
        val a = 2
        val b = 0
        Toast.makeText(context, "计算结果为 ${ a / b }", Toast.LENGTH_SHORT).show()
    }
}