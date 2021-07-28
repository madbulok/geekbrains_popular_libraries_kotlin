package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.api.IDataSource
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubRepository
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.RoomGithubRepository
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.RoomGithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.dao.RepositoryDao
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.dao.UserDao
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.network.INetworkStatus

class GithubUsersRepo(
    private val api: IDataSource,
    private val userDao: UserDao,
    private val repositoryDao: RepositoryDao,
    private val networkStatus: INetworkStatus
) : IGithubUsersRepo {

    override fun getUsers(): Single<List<GithubUser>> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                api.getUsers().flatMap { users ->
                    Single.fromCallable {
                        val roomUsers = users.map { user ->
                            RoomGithubUser(
                                user.id,
                                user.login,
                                user.avatarUrl,
                                user.reposUrl,
                            )
                        }
                        userDao.insert(roomUsers)
                        users
                    }
                }.subscribeOn(Schedulers.io())
            } else {
                Single.fromCallable {
                    userDao.getAll().map { roomUser ->
                        GithubUser(
                            roomUser.id,
                            roomUser.login,
                            roomUser.avatarUrl,
                            roomUser.reposUrl,
                        )
                    }
                }.subscribeOn(Schedulers.io())
            }
        }

    override fun getRepositories(user: GithubUser): Single<List<GithubRepository>> {
        return networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                api.userRepositories(user.reposUrl).flatMap { repos ->
                    Single.fromCallable {
                        val roomUser = userDao.findByLogin(user.login)
                        val roomRepositories = repos.map { repo ->
                            RoomGithubRepository(
                                repo.id,
                                repo.name,
                                repo.forksCount,
                                roomUser.id,
                            )
                        }
                        repositoryDao.insert(roomRepositories)
                        repos
                    }
                }.subscribeOn(Schedulers.io())
            } else {
                Single.fromCallable {
                    val roomUser = userDao.findByLogin(user.login)
                    repositoryDao.findByLogin(roomUser.id).map { roomRepository ->
                        GithubRepository(
                            roomRepository.id,
                            roomRepository.name,
                            roomRepository.forksCount,
                        )
                    }
                }.subscribeOn(Schedulers.io())
            }
        }
    }
}