package com.example.diplom

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.diplom.adapter.CardAdapter
import com.example.diplom.viewModel.CardViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UpdateFragment:Fragment(R.layout.fragment_update) {

    lateinit var cViewModel: CardViewModel

    private val adapter = CardAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        cViewModel = ViewModelProvider(this)[CardViewModel::class.java]


        val notesRecycler = view.findViewById<RecyclerView>(R.id.recycler)
        notesRecycler.adapter = adapter
        notesRecycler.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        val goToAddFragmentButton = view.findViewById<FloatingActionButton>(R.id.addButton)
        goToAddFragmentButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.cardsFragmentToCreateCardsFragment)
        }

        // отслеживаем за изменением данных в таблице
        cViewModel.readAllData().observe(viewLifecycleOwner, Observer{
            adapter.setData(it)
        })
    }
}