package com.example.grocerlist

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import java.io.File

const val FILE_NAME = "groceries.txt"

class MainActivity : AppCompatActivity() {
    companion object ListHandler {
        private var groceryList = mutableListOf<String>("bla")
        fun appendItem(value: String) {
            groceryList.add(value);
        }

        fun clearProducts() {
            groceryList.clear()
        }

        fun readFromString(str: String) {
            val products = str.split(',');
            groceryList = products as MutableList<String>
        }

        override fun toString(): String {
            return groceryList.joinToString { it }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val newListButton = findViewById<Button>(R.id.newListButton)
        val appendToList = findViewById<Button>(R.id.appendProduct_button)
        val productList = findViewById<RecyclerView>(R.id.productList);
        productList.setHasFixedSize(true);
        productList.layoutManager = LinearLayoutManager(this);
        productList.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        val mAdapter = ProductListAdapter(ListHandler.groceryList)
        productList.adapter = mAdapter
        appendToList?.setOnClickListener() {

            val input = findViewById<EditText>(R.id.newProduct_input);
            val text = input.text.toString()
            if (text.isNotEmpty()) {
                ListHandler.appendItem(text)
                mAdapter.notifyDataSetChanged()
                input.setText("");
                val productList = findViewById<RecyclerView>(R.id.productList);
                productList.scrollToPosition(groceryList.size - 1);
            }
        }
        newListButton?.setOnClickListener() {
            ListHandler.clearProducts()
            mAdapter.notifyDataSetChanged()
        }
    }

    override fun onStop() {
        super.onStop()
        this.applicationContext.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
            it.write(ListHandler.toString().toByteArray())
        }
    }

    override fun onStart() {
        super.onStart()
        try {
            val file = File(this.applicationContext.filesDir, FILE_NAME)
            if (file.canRead()) {
                file.readText();
            }
        } catch (ex: Exception) {
            throw ex;
        }
    }
}