package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter

import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.CountersModel
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.MainView
import java.io.Serializable


class MainPresenter(private val view: MainView) : Serializable {

    private val model = CountersModel()

    fun addButton(id: Int){
        model.addButtonId(id)
    }

    fun counterButtonClick(id: Int) {
        val nextValue = model.next(id)
        view.setButtonText(id, nextValue.toString())
    }
}