package ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.databinding.UserContentLayoutBinding
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.UserPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.UserView
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.App
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.BackButtonListener

class UserFragment : MvpAppCompatFragment(), UserView, BackButtonListener {

    private val presenter by moxyPresenter {
        UserPresenter(App.instance.router, arguments?.getParcelable("userKey"))
    }
    private var _viewBinding: UserContentLayoutBinding? = null

    companion object {
        fun newInstance(user: GithubUser): UserFragment {
            val args = Bundle()
            args.putParcelable("userKey", user)
            val fragment = UserFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = UserContentLayoutBinding.inflate(layoutInflater, container, false).also {
        _viewBinding = it
    }.root

    override fun loadUser(user: GithubUser) {
        _viewBinding?.textView?.text = user.login
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    override fun backPressed() = presenter.backClick()
}