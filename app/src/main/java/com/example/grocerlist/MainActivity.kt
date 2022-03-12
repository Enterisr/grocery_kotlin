package com.example.grocerlist

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    companion object ListHandler{
        private val groceryList = mutableListOf<String>("bla")
            fun appendItem(value:String){
                groceryList.add(value);
            }
            fun clearProducts() {
                groceryList.clear()
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
        appendToList?.setOnClickListener(){
            val text = findViewById<EditText>(R.id.newProduct_input).text.toString()
            ListHandler.appendItem(text)
            mAdapter.notifyDataSetChanged()
        }
        newListButton?.setOnClickListener(){
            ListHandler.clearProducts()
            mAdapter.notifyDataSetChanged()
        }
    }
}