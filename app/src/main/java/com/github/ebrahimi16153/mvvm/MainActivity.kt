package com.github.ebrahimi16153.mvvm

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ebrahimi16153.mvvm.adapter.TodoAdapter
import com.github.ebrahimi16153.mvvm.databinding.ActivityMainBinding
import com.github.ebrahimi16153.mvvm.handler.SwipeHandler
import com.github.ebrahimi16153.mvvm.model.Todo
import com.github.ebrahimi16153.mvvm.staticvariable.Const
import com.github.ebrahimi16153.mvvm.viewmodel.TodoViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var binding: ActivityMainBinding
    private var adapter = TodoAdapter()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
      init()
    }



    @SuppressLint("NotifyDataSetChanged")
    private fun init(){
        // create a object from view model and assign with ViewModelProvider
        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        todoViewModel.getAllTodo().observe(this) {
            adapter.diff.submitList(it)
            adapter.notifyDataSetChanged()
        }

        // recyclerView
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(this)

        // activity for result
        val resultAddActivity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data = result.data ?: return@registerForActivityResult
                    val title = data.getStringExtra(Const.EXTRA_TITLE)
                    val desc = data.getStringExtra(Const.EXTRA_DESC)
                    val todo = Todo(0, title!!, desc!!, false)
                    MainScope().launch { todoViewModel.insert(todo) }
                }
            }

        // fab click
        binding.addTodo.setOnClickListener {

            resultAddActivity.launch(Intent(this, AddActivity::class.java))
        }

        // swipe down to refresh
        binding.swipeToRefresh.setOnRefreshListener {
            adapter.notifyDataSetChanged()
            binding.swipeToRefresh.isRefreshing = false
        }

        // on recycler swipe to delete
        val swipeHandler = object : SwipeHandler() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                MainScope().launch { todoViewModel.delete(adapter.diff.currentList[viewHolder.layoutPosition]) }
                Snackbar.make(binding.root, "Item Removed", Snackbar.LENGTH_SHORT).show()

            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.list)



        val resultEdieActivity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK ) {
                    val data = result.data ?: return@registerForActivityResult
                    if (data.getIntExtra(Const.EXTRA_ID,-1) != -1){
                        val title = data.getStringExtra(Const.EXTRA_TITLE)
                        val desc = data.getStringExtra(Const.EXTRA_DESC)
                        val id = data.getIntExtra(Const.EXTRA_ID,0)
                        val todo = Todo(id, title!!, desc!!, false)
                        MainScope().launch { todoViewModel.update(todo) }
                    }else{
                        return@registerForActivityResult

                    }

                }
            }
        adapter.setonItemClickListener(object :TodoAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(this@MainActivity,AddActivity::class.java)
                val todo = adapter.diff.currentList[position]
                intent.putExtra(Const.EXTRA_ID,todo.todoID)
                intent.putExtra(Const.EXTRA_TITLE,todo.title)
                intent.putExtra(Const.EXTRA_DESC,todo.description)
                resultEdieActivity.launch(intent)
            }

        })

    }
}