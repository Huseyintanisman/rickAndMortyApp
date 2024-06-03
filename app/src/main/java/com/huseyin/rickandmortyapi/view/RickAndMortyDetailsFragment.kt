package com.huseyin.rickandmortyapi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.huseyin.rickandmortyapi.R
import com.huseyin.rickandmortyapi.adapter.rickAndMortyAdapter
import com.huseyin.rickandmortyapi.util.placeholderProgressBar
import com.huseyin.rickandmortyapi.util.downloadFromUrl
import com.huseyin.rickandmortyapi.viewModel.RickAndMortyViewModel

class RickAndMortyDetailsFragment : Fragment() {


    private lateinit var viewModel : RickAndMortyViewModel
    private var rickAndMortyUuid = 0
    private lateinit var rname : TextView
    private lateinit var rid : TextView
    private lateinit var rstatus : TextView
    private lateinit var rspecies : TextView
    private lateinit var rgender : TextView
    private lateinit var rImage : ImageView
    private lateinit var rurl : TextView
    private lateinit var rcreated : TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rick_and_morty_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { rickAndMortyUuid = RickAndMortyDetailsFragmentArgs.fromBundle(it).countryUuid }

        viewModel = ViewModelProvider(this)[RickAndMortyViewModel::class.java]
        viewModel.getDataFromRoom(rickAndMortyUuid)

        rname = view.findViewById(R.id.rickAndMortyName)
        rid = view.findViewById(R.id.rickAndMortyID)
        rstatus = view.findViewById(R.id.rickAndMortyStatus)
        rspecies = view.findViewById(R.id.RickAndMortySpecies)
        rgender = view.findViewById(R.id.rickAndMortyGender)
        rImage = view.findViewById(R.id.rickAndMortyImage)
        rcreated = view.findViewById(R.id.RickAndMortyCreated)
        rurl = view.findViewById(R.id.RickAndMortyUrl)
        observeLiveData()

    }

    private fun observeLiveData() {
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { rickAndMorty->
            rickAndMorty?.let {
                rname.text = rickAndMorty.RickAndMortyName
                rid.text = rickAndMorty.RickAndMortyID.toString()
                rstatus.text = rickAndMorty.RickAndMortyStatus
                rgender.text = rickAndMorty.RickAndMortyGender
                rspecies.text = rickAndMorty.RickAndMortySpecies
                rcreated.text = rickAndMorty.RıckAndMortyCreated
                rurl.text = rickAndMorty.RıckAndMortyUrl
                context?.let {
                    rImage.downloadFromUrl(rickAndMorty.RıckAndMortyImageUrl, placeholderProgressBar(it))
                }
            }
        })
    }
}