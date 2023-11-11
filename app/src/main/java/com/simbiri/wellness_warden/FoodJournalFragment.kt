package com.simbiri.wellness_warden

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FoodJournalFragment : Fragment() {

    companion object {
        fun newInstance() = FoodJournalFragment()
    }

    private lateinit var viewModel: FoodJournalViewModel
    //for now don't worry about ViewModels and Companion Objects, just concern yourself with the UI logic inside OnCreateView
    private lateinit var  recyclerViewSearch: RecyclerView

    private lateinit var recyclerViewBreakFast : RecyclerView
    private lateinit var recyclerViewLunch : RecyclerView
    private lateinit var recyclerViewDinner :RecyclerView
    private lateinit var recyclerViewSnacks : RecyclerView
    private lateinit var textRefresh : TextView

    private lateinit var cardViewCalculateBreak : CardView
    private lateinit var cardViewCalculateLunch : CardView
    private lateinit var cardViewCalculateDinner : CardView
    private lateinit var cardViewCalculateSnack : CardView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewFrag =  inflater.inflate(R.layout.food_journal_fragment, container, false)

        //this is how you initialize a variable in a fragment. A little different but same logic
        initializeViews(viewFrag)

        setUpAdaptersAndLayoutManagers()

        setUpListeners()

        //add other UI logic here i.e recyclerViewsEtc

        return viewFrag
    }


    private fun setUpListeners() {

        textRefresh.setOnClickListener {

            recyclerViewBreakFast.adapter!!.notifyDataSetChanged()
            recyclerViewLunch.adapter!!.notifyDataSetChanged()
            recyclerViewDinner.adapter!!.notifyDataSetChanged()
            recyclerViewSnacks.adapter!!.notifyDataSetChanged()

            Toast.makeText(requireContext(), "Updated meals info", Toast.LENGTH_LONG).show()

        }
    }

    private fun initializeViews(viewFrag: View) {

        recyclerViewSearch =  viewFrag.findViewById(R.id.recyclerViewSearches)
        recyclerViewBreakFast = viewFrag.findViewById(R.id.recyclerViewBreakFast)
        recyclerViewLunch = viewFrag.findViewById(R.id.recyclerViewLunch)
        recyclerViewDinner = viewFrag.findViewById(R.id.recyclerViewDinner)
        recyclerViewSnacks = viewFrag.findViewById(R.id.recyclerViewSnack)

        textRefresh = viewFrag.findViewById(R.id.updateMealsButton)

    }

    private fun setUpAdaptersAndLayoutManagers() {

        val context = requireContext()
        val adapterForCommon = FoodSearchAdapter(context, CommonFoods.arrayListFoods!!)
        val adapterForBreakFast = MealListAdapter(context, CommonFoods.allBreakFast)
        val adapterForLunch = MealListAdapter(context, CommonFoods.allLunch)
        val adapterForDinner = MealListAdapter(context, CommonFoods.allDinner)
        val adapterForSnacks = MealListAdapter(context, CommonFoods.allSnacks)


        val layoutManagerCommon = GridLayoutManager(context, 2)
        layoutManagerCommon.orientation = RecyclerView.VERTICAL

        val layoutManagerBreakFast = LinearLayoutManager(context)
        layoutManagerBreakFast.orientation = RecyclerView.HORIZONTAL

        val layoutManagerLunch = LinearLayoutManager(context)
        layoutManagerLunch.orientation = RecyclerView.HORIZONTAL

        val layoutManagerDinner = LinearLayoutManager(context)
        layoutManagerDinner.orientation = RecyclerView.HORIZONTAL

        val layoutManagerSnack = LinearLayoutManager(context)
        layoutManagerSnack.orientation = RecyclerView.HORIZONTAL

        recyclerViewSearch.adapter = adapterForCommon
        recyclerViewSearch.layoutManager = layoutManagerCommon

        recyclerViewBreakFast.adapter = adapterForBreakFast
        recyclerViewBreakFast.layoutManager = layoutManagerBreakFast

        recyclerViewLunch.adapter = adapterForLunch
        recyclerViewLunch.layoutManager = layoutManagerLunch

        recyclerViewDinner.adapter = adapterForDinner
        recyclerViewDinner.layoutManager = layoutManagerDinner

        recyclerViewSnacks.adapter = adapterForSnacks
        recyclerViewSnacks.layoutManager = layoutManagerSnack

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FoodJournalViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
