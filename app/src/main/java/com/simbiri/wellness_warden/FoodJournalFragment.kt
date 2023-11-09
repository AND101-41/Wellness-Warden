package com.simbiri.wellness_warden

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class FoodJournalFragment : Fragment() {

    companion object {
        fun newInstance() = FoodJournalFragment()
    }
    //for now don't worry about ViewModels and Companion Objects, just concern yourself with the UI logic inside OnCreateView
    private lateinit var viewModel: FoodJournalViewModel
    private lateinit var textHello :TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewFrag =  inflater.inflate(R.layout.food_journal_fragment, container, false)


        //this is how you initialize a variable in a fragment. A little different but same logic
        textHello = viewFrag.findViewById(R.id.helloFJText)

        //add other UI logic here i.e recyclerViewsEtc

        return viewFrag
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FoodJournalViewModel::class.java)
        // TODO: Use the ViewModel
    }

}