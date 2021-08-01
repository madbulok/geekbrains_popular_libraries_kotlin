package ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.databinding.UserContentLayoutBinding
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.api.IDataSource
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache.room.RoomGithubRepositoriesCache
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.db.Database
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.UserPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.UserView
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.App
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.BackButtonListener
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.adapter.ReposotoriesRVAdapter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.navigation.AndroidScreens
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo.RetrofitGithubRepositoriesRepo
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.network.AndroidNetworkStatus
import javax.inject.Inject

class UserFragment : MvpAppCompatFragment(), UserView, BackButtonListener {

    @Inject
    lateinit var api: IDataSource

    @Inject
    lateinit var database: Database

    @Inject
    lateinit var router: Router

    companion object {
        private const val USER_ARG = "user"

        fun newInstance(user: GithubUser) = UserFragment().apply {
            arguments = Bundle().apply {
                putParcelable(USER_ARG, user)
            }
            App.instance.appComponent.inject(this)
        }
    }

    val presenter: UserPresenter by moxyPresenter {
        val user = arguments?.getParcelable<GithubUser>(USER_ARG) as GithubUser
        UserPresenter(
            AndroidSchedulers.mainThread(),
            RetrofitGithubRepositoriesRepo(api, AndroidNetworkStatus(App.instance), RoomGithubRepositoriesCache(database)),
            router,
            user,
            AndroidScreens()
        )
    }

    private var vb: UserContentLayoutBinding? = null

    var adapter: ReposotoriesRVAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        UserContentLayoutBinding.inflate(inflater, container, false).also {
            vb = it
        }.root

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

    override fun init() {
        vb?.rvRepositories?.layoutManager = LinearLayoutManager(context)
        adapter = ReposotoriesRVAdapter(presenter.repositoriesListPresenter)
        vb?.rvRepositories?.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }


    override fun backPressed() = presenter.backPressed()
}