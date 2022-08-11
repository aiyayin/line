package com.line.java

import android.util.Log
import android.util.SparseBooleanArray
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.internal.JsonReaderInternalAccess
import com.google.gson.internal.bind.TypeAdapters.BOOLEAN
import com.google.gson.internal.bind.TypeAdapters.INTEGER
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

/**
 *
 * @author ying.fu
 * @date 2022/08/10
 * @desc
 * @tapd
 *
 */

fun testGsonSparseBooleanArray() {
    var sparseBooleanArray = SparseBooleanArray()
    val hashMap = HashMap<Int, Boolean>()
    for (index in 1..10) {
        sparseBooleanArray.put(index, true)
        hashMap.put(index, true)
    }

    val gson = GsonBuilder().registerTypeAdapter(
        SparseBooleanArray::class.java,
        SparseBooleanArrayTypeAdapter()
    ).create()
    val toJson = gson.toJson(sparseBooleanArray)
    Log.d("yin>> ", "toJson : $toJson")
    val fromJson = gson.fromJson<SparseBooleanArray>(
        toJson,
        SparseBooleanArray::class.java
    )
    Log.d("yin>> ", "fromJson : $fromJson")


    val gson2 = Gson()
    val toJson2 = gson2.toJson(hashMap)
    Log.d("yin>> ", "toJson hashMap : $toJson2")
    val fromJson2 = gson2.fromJson<HashMap<Int, Boolean>>(
        toJson2,
        object : TypeToken<HashMap<Int, Boolean>>() {}.type
    )
//
    Log.d("yin>> ", "fromJson hashMap : $fromJson2")
    Log.d("yin>> ", "toJson hashMap 22 : ${gson2.toJson(fromJson2)}")
}

class SparseBooleanArrayTypeAdapter : TypeAdapter<SparseBooleanArray>() {

    override fun write(out: JsonWriter?, value: SparseBooleanArray) {
        val valueS = value.toString().replace("=", ":")
        Log.d("yin>> ", "write : $valueS")
        out?.apply {
            beginObject()
            for (i in 0 until value.size()) {
                this.name(value.keyAt(i).toString())
                BOOLEAN.write(this, value.get(i))
            }
            endObject()
        }
    }

    override fun read(reader: JsonReader?): SparseBooleanArray? {
        val array = SparseBooleanArray()
        reader?.apply {
            beginObject()
            while (hasNext()) {
                JsonReaderInternalAccess.INSTANCE.promoteNameToValue(this)
                array.put(INTEGER.read(this).toInt(), BOOLEAN.read(this))
            }
            endObject()
        }
        return array
    }
}
