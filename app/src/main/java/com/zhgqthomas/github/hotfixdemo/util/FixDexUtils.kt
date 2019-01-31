package com.zhgqthomas.github.hotfixdemo.util

import android.content.Context
import com.zhgqthomas.github.hotfixdemo.Constants
import dalvik.system.DexClassLoader
import java.io.File
import java.util.*


object FixDexUtils {

    val dexLists = HashSet<File>()

    fun loadFixedDex(context: Context) {
        // 遍历私有补丁目录下的 dex 相关文件
        val dexDir = context.getDir(Constants.DEX_DIR, Context.MODE_PRIVATE)
        val dexFiles = dexDir.listFiles()
        for (file in dexFiles) {
            if (file.name.endsWith(Constants.DEX_SUFFIX) && !file.name.equals("class.dex", true)) {
                dexLists.add(file)
            }
        }

        // 进行补丁 dex 的插桩
        doDexInject(context, dexDir)
    }

    private fun doDexInject(context: Context, dexDir: File) {
        val optDir = dexDir.absolutePath + File.separator + "opt_dex"
        val optFile = File(optDir)
        // 不存在就创建
        if (!optFile.exists()) {
            optFile.mkdirs()
        }

        for (dex in dexLists) {
            // 创建 DexClassLoader 并加载补丁 dex 相关文件
            val dexClassLoader = DexClassLoader(dex.absolutePath, optFile.absolutePath, null, context.classLoader)

            // 获得 DexClassLoader 中 DexElementsList
            val dexPathList = getPathList(dexClassLoader)
        }

    }

    private fun getPathList(dexClassLoader: DexClassLoader): Any {

    }

    @Throws(Exception::class)
    private fun getField(obj: Any, cl: Class<*>, filed: String): Any {
        val localField = cl.getDeclaredField(filed)
        localField.isAccessible = true
        return localField.get(obj)
    }

    @Throws(Exception::class)
    private fun getDexElements(obj: Any): Any {
        return getField(obj, obj.javaClass, "dexElements")
    }

    @Throws(Exception::class)
    private fun getPathList(baseDexClassLoader: Any): Any {
        return getField(baseDexClassLoader, Class.forName("dalvik.system.BaseDexClassLoader"), "pathList")
    }

    @Throws(Exception::class)
    private fun setField(obj: Any, cl: Class<*>, field: String, value: Any) {
        val localField = cl.getDeclaredField(field)
        localField.isAccessible = true
        localField.set(obj, value)
    }

    private fun combineArray(arrayLhs: Any, arrayRhs: Any): Any {
        val localClass = arrayLhs.javaClass.componentType
        val i = (arrayLhs as Array<*>).size
        val j = i + (arrayRhs as Array<*>).size
        val result = Array.newInstance(localClass, j)
        for (k in 0 until j) {
            if (k < i) {
                Array.set(result, k, Array.get(arrayLhs, k))
            } else {
                Array.set(result, k, Array.get(arrayRhs, k - i))
            }
        }
        return result
    }

    fun clearDexLists() {
        dexLists.clear()
    }
}