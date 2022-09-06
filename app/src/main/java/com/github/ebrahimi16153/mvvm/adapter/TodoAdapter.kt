package com.github.ebrahimi16153.mvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.ebrahimi16153.mvvm.databinding.TodoItemBinding
import com.github.ebrahimi16153.mvvm.model.Todo


class TodoAdapter : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    private lateinit var  mListener:OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),mListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setViews(diff.currentList[position])
    }

    override fun getItemCount() = diff.currentList.size


    private val diffCalBack = object : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }
    val diff = AsyncListDiffer(this, diffCalBack)

    inner class ViewHolder(private val binding: TodoItemBinding,listener: OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        // on item click listener
        init {
            binding.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
        fun setViews(item: Todo) {
            binding.checkBox.isChecked = item.done
            binding.title.text = item.title
        }
    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setonItemClickListener(listener:OnItemClickListener){
        mListener = listener
    }

}