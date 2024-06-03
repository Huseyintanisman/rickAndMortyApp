package com.huseyin.rickandmortyapi.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.huseyin.rickandmortyapi.R
import com.huseyin.rickandmortyapi.model.RickAndMorty
import com.huseyin.rickandmortyapi.util.downloadFromUrl
import com.huseyin.rickandmortyapi.util.placeholderProgressBar

import com.huseyin.rickandmortyapi.view.RickAndMortyFeedFragmentDirections

class rickAndMortyAdapter(private val rickAndMortyList: ArrayList<RickAndMorty>): RecyclerView.Adapter<rickAndMortyAdapter.rickAndMortyViewHolder>() {
    class rickAndMortyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): rickAndMortyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_rick_and_morty, parent, false)
        return rickAndMortyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rickAndMortyList.size
    }

    override fun onBindViewHolder(holder: rickAndMortyViewHolder, position: Int) {
        holder.view.findViewById<TextView>(R.id.rickAndMortyNameText)?.text =
            rickAndMortyList[position].RickAndMortyName
        holder.view.findViewById<TextView>(R.id.rickAndMortyStatusText)?.text =
            rickAndMortyList[position].RickAndMortyStatus
        holder.view.findViewById<TextView>(R.id.rickAndMortyIdText)?.text =
            rickAndMortyList[position].RickAndMortyID.toString()



        holder.view.setOnClickListener {
            val action =
                RickAndMortyFeedFragmentDirections.actionRickAndMortyFeedFragmentToRickAndMortyDetailsFragment(
                    rickAndMortyList[position].RickAndMortyID
                )
            Navigation.findNavController(it).navigate(action)
        }

        holder.view.findViewById<ImageView>(R.id.imageView)?.downloadFromUrl(
            rickAndMortyList[position].RıckAndMortyImageUrl,
            placeholderProgressBar(holder.view.context)
        )

    }

    fun removeItem(position: Int) {
        rickAndMortyList.removeAt(position)
        notifyItemRemoved(position)
    }

    //fun getCountryAtPosition(position: Int): RickAndMorty {
    //   return rickAndMortyList[position]
    //}


    @SuppressLint("NotifyDataSetChanged")
    fun updateRickAndMortyList(newRickAndMortList: List<RickAndMorty>) {
        rickAndMortyList.clear()
        rickAndMortyList.addAll(newRickAndMortList)
        notifyDataSetChanged()
    }
}
