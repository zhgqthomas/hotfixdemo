package com.zhgqthomas.github.hotfixdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var classLoader = this.classLoader

        // 打印 ClassLoader 继承关系
        while (classLoader != null) {
            Log.d("MainActivity", classLoader.toString())
            classLoader = classLoader.parent
        }
    }
}
