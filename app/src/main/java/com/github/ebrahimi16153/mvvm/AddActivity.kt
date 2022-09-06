package com.github.ebrahimi16153.mvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.ebrahimi16153.mvvm.databinding.ActivityAddBinding
import com.github.ebrahimi16153.mvvm.staticvariable.Const
import com.google.android.material.snackbar.Snackbar

class AddActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)


      val inputIntent = intent

        if (inputIntent.hasExtra(Const.EXTRA_ID)){
             title = "Edit"
            binding.btnAdd.text = "update"
            val title = inputIntent.getStringExtra(Const.EXTRA_TITLE)
            val desc = inputIntent.getStringExtra(Const.EXTRA_DESC)
            binding.ethTitle.setText(title)
            binding.ethDiccription.setText(desc)

        }


        binding.apply {

            btnAdd.setOnClickListener{
                val title = ethTitle.text.toString()
                val desc = ethDiccription.text.toString()
                if (title == "" || desc == ""){
                    Snackbar.make(it,"title and Description can't be Empty",Snackbar.LENGTH_LONG).show()
                }else{
                    val data = Intent()
                    val id = intent.getIntExtra(Const.EXTRA_ID,-1)
                    if (id != -1){
                        data.putExtra(Const.EXTRA_ID,id)
                    }
                    data.putExtra(Const.EXTRA_TITLE,title)
                    data.putExtra(Const.EXTRA_DESC,desc)

                    setResult(RESULT_OK,data)
                    finish()
                }
            }


        }
    }
}