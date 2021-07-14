package ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import ru.geekbrains.geekbrains_popular_libraries_kotlin.databinding.ActivityMainBinding
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.MainPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.MainView

class MainActivity : AppCompatActivity(), MainView {

    private var vb: ActivityMainBinding? = null
    private var presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb?.root)

        val listener = View.OnClickListener {

            presenter.counterButtonClick(it.id)
        }

        presenter.addButton(vb?.btnCounter1?.id!!)
        presenter.addButton(vb?.btnCounter2?.id!!)
        presenter.addButton(vb?.btnCounter3?.id!!)

        vb?.btnCounter1?.setOnClickListener(listener)
        vb?.btnCounter2?.setOnClickListener(listener)
        vb?.btnCounter3?.setOnClickListener(listener)

    }

    override fun setButtonText(id: Int, text: String) {
        when(id){
            vb?.btnCounter1?.id ->vb?.btnCounter1?.text = text
            vb?.btnCounter2?.id ->vb?.btnCounter2?.text = text
            vb?.btnCounter3?.id ->vb?.btnCounter3?.text = text
        }
    }
}