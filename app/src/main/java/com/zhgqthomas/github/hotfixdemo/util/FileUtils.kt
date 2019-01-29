package com.zhgqthomas.github.hotfixdemo.util

import java.io.*

object FileUtils {

    fun copyFiles(sources: File, target: File) {
        val input = FileInputStream(sources)
        val inputBuffer = BufferedInputStream(input)

        val output = FileOutputStream(target)
        val outputBuffer = BufferedOutputStream(output)

        val buffer = ByteArray(1024)
        while (true) {
            val length = inputBuffer.read(buffer)
            if (length <= 0) {
                break
            }

            outputBuffer.write(buffer, 0, length)
        }

        outputBuffer.flush()

        input.close()
        inputBuffer.close()
        output.close()
        outputBuffer.close()
    }
}