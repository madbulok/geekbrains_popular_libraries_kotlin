package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model

class CountersModel {

    private val counters = mutableMapOf<Int, Int>()

    fun addButtonId(id: Int) {
        counters[id] = 0
    }
    private fun getCurrent(index: Int): Int {
        return counters[index] ?: 0
    }

    // можно было getOrDefault но это зависимость android
    fun next(index: Int): Int{
        var current = counters[index]
        if (current == null) current = 0
        counters[index] = current + 1
        return getCurrent(index)
    }

    fun set(index: Int, value: Int){
        counters[index] = value
    }

}