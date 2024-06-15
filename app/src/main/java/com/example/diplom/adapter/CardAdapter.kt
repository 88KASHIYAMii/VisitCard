package com.example.diplom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.fragments.CardsFragmentDirections
import com.example.diplom.model.Card

class CardAdapter: RecyclerView.Adapter<CardAdapter.MineHolder>() {

    private var cardList = emptyList<Card>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MineHolder {
        return  MineHolder(
            LayoutInflater
            .from(parent.context)
            .inflate(R.layout.card_element, parent, false))
    }


    override fun onBindViewHolder(holder: MineHolder, position: Int){
        val cardItem = cardList[position]

        holder.aFirstName.text = cardItem.firstName
        holder.aSecondName.text = cardItem.secondName
        holder.aThirdName.text = cardItem.thirdName

        holder.aPost.text = cardItem.post

        holder.aAddress.text = cardItem.address

        holder.aPhone.text = cardItem.number
        holder.aMail.text = cardItem.mail


        holder.cards_container.setOnClickListener {
            val value = CardsFragmentDirections.actionCardsFragmentToUpdateFragment(cardItem)
            holder.itemView.findNavController().navigate(value)
        }
    }


    override fun getItemCount(): Int {
        return cardList.size
    }

    class MineHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val aFirstName: TextView = itemView.findViewById(R.id.firstNameText)
        val aSecondName: TextView = itemView.findViewById(R.id.secondNameText)
        val aThirdName: TextView = itemView.findViewById(R.id.thirdNameText)

        val aPost: TextView = itemView.findViewById(R.id.postText)

        val aAddress: TextView = itemView.findViewById(R.id.addressText)

        val aPhone: TextView = itemView.findViewById(R.id.phoneText)
        val aMail: TextView = itemView.findViewById(R.id.mailText)

        val cards_container: CardView = itemView.findViewById(R.id.cards_container)

    }

    fun setData(newCardList: List<Card>){
        this.cardList = newCardList
        notifyDataSetChanged()
    }
}