package ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.databinding.RepositoryLayoutBinding
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.RepositoryPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.RepositoryView
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.App
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.BackButtonListener

class RepositoryFragment : MvpAppCompatFragment(), RepositoryView, BackButtonListener {


    private var _viewBinding: RepositoryLayoutBinding? = null

    private val presenter by moxyPresenter {
        RepositoryPresenter(
            App.instance.router,
            arguments?.getString("repoKey")
        )
    }

    companion object {
        fun newInstance(repo: String): RepositoryFragment {
            val args = Bundle()
            args.putString("repoKey", repo)
            val fragment = RepositoryFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RepositoryLayoutBinding.inflate(layoutInflater, container, false).also {
        _viewBinding = it
    }.root


    override fun backPressed() = presenter.backClick()
    override fun showRepoInfo(text: String) {
        _viewBinding?.tvForkCount?.text = text
    }
}