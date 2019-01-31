package com.zhgqthomas.github.hotfixdemo

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import com.zhgqthomas.github.hotfixdemo.util.FileUtils
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var classLoader = this.classLoader

        // 打印 ClassLoader 继承关系
        while (classLoader != null) {
            Log.d("MainActivity", classLoader.toString())
            classLoader = classLoader.parent
        }

        button = findViewById(R.id.calculate_btn)
        button.setOnClickListener {
            val bug = Bug()
            bug.calculate(applicationContext)
        }
    }

    fun fixBug() {
        // TODO 下载补丁 patch.dex 到 SD 卡中

        // 复制补丁 patch.dex 到应用的私有目录下 odex 文件夹
        val sourcePath = Environment.getExternalStorageDirectory().absolutePath + File.separator + Constants.PATH_DEX_NAME
        val sourceFile = File(sourcePath)

        val targetPath = getDir(Constants.DEX_DIR, Context.MODE_PRIVATE).absolutePath + File.separator + Constants.PATH_DEX_NAME
        val targetFile = File(targetPath)

        // 删除已存在的补丁
        if (targetFile.exists()) {
            targetFile.delete()
            Log.i("Hotfix", "删除了原有补丁包")
        }

        // 执行复制操作
        FileUtils.copyFiles(sourceFile, targetFile)


    }
}
