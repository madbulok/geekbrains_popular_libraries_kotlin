package ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.geekbrains_popular_libraries_kotlin.databinding.ItemRepositoryBinding
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.list.IReposListPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.list.RepoItemView
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.adapter.ReposRVAdapter.ViewHolder

class ReposRVAdapter(val presenter: IReposListPresenter) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)).apply {
            itemView.setOnClickListener{ presenter.itemClickListener?.invoke(this) }
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        presenter.bindView(holder.apply { pos = position })
    }

    override fun getItemCount(): Int = presenter.getCount()

    inner class ViewHolder(val vb: ItemRepositoryBinding) : RecyclerView.ViewHolder(vb.root), RepoItemView {
        override var pos: Int = -1

        override fun setName(name: String) {
            with(vb) {
                tvNameRepo.text = name
            }
        }
    }
}