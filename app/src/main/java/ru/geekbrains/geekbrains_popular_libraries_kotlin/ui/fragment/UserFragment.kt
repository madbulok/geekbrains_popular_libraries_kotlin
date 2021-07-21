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
        UserPresenter(
            GithubUsersRepo(ApiHolder.api),
            App.instance.router,
            arguments?.getParcelable("userKey"),
            AndroidSchedulers.mainThread()
        )
    }

    private var adapterRepos: ReposRVAdapter? = null

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

    override fun init() {
        adapterRepos = ReposRVAdapter(presenter.reposPresenterList)
        _viewBinding?.rvRepositories?.adapter = adapterRepos

    }

    override fun startLoading() {
        _viewBinding?.progressBar?.visibility = View.VISIBLE
        _viewBinding?.rvRepositories?.visibility = View.GONE
    }

    override fun stopLoading() {
        _viewBinding?.progressBar?.visibility = View.GONE
        _viewBinding?.rvRepositories?.visibility = View.VISIBLE
    }

    override fun updateList() {
        adapterRepos?.notifyDataSetChanged()
        Log.e(javaClass.simpleName, "LOAD OK!")
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    override fun backPressed() = presenter.backClick()
}