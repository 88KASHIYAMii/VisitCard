package com.example.diplom.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.diplom.R
import com.example.diplom.model.Card
import com.example.diplom.viewModel.CardViewModel

class CreateCardsFragment: Fragment(R.layout.fragment_create_cards) {

    lateinit var cViewModel: CardViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firstName = view.findViewById<EditText>(R.id.createFirstName)
        val secondName = view.findViewById<EditText>(R.id.createSecondName)
        val thirdName = view.findViewById<EditText>(R.id.createThirdName)

        val post = view.findViewById<EditText>(R.id.createPost)

        val address = view.findViewById<EditText>(R.id.createAddress)

        val phoneNumber = view.findViewById<EditText>(R.id.createPhone)
        val mail = view.findViewById<EditText>(R.id.createMail)

        cViewModel = ViewModelProvider(this)[CardViewModel::class.java]

        val saveCurrentNoteImage = view.findViewById<ImageView>(R.id.imageView_save)
        saveCurrentNoteImage.setOnClickListener {

            val currentFirstName = firstName.text.toString()
            val currentSecondName = secondName.text.toString()
            val currentThirdName = thirdName.text.toString()

            val currentPost = post.text.toString()

            val currentAddress = address.text.toString()

            val currentPhoneNumber = phoneNumber.text.toString()
            val currentMail = mail.text.toString()

            val card = Card(0, currentFirstName, currentSecondName, currentThirdName, currentPost, currentAddress, currentPhoneNumber, currentMail)
            cViewModel.addNote(card)
            findNavController().navigate(R.id.createCardsFragmentToCardsFragment)
        }
    }
}