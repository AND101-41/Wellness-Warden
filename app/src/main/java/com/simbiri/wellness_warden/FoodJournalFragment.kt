package com.simbiri.wellness_warden

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
//import android.widget.SearchView
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.simbiri.wellness_warden.model.FoodItem
import com.simbiri.wellness_warden.model.MacroNutrients
import com.simbiri.wellness_warden.model.MicroNutrients
import okhttp3.Call
import okhttp3.Headers
import java.io.IOException
import kotlin.system.exitProcess

class FoodJournalFragment : Fragment() {

    companion object {
        fun newInstance() = FoodJournalFragment()
    }

    private lateinit var viewModel: FoodJournalViewModel

    //for now don't worry about ViewModels and Companion Objects, just concern yourself with the UI logic inside OnCreateView
    private lateinit var recyclerViewSearch: RecyclerView

    private lateinit var recyclerViewBreakFast: RecyclerView
    private lateinit var recyclerViewLunch: RecyclerView
    private lateinit var recyclerViewDinner: RecyclerView
    private lateinit var recyclerViewSnacks: RecyclerView
    private lateinit var textRefresh: TextView
    private lateinit var foodSearch: SearchView
    private lateinit var totalFoodIntakes: TextView
    private lateinit var totalMacroText: TextView
    private lateinit var totalMicroTextView: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewFrag = inflater.inflate(R.layout.food_journal_fragment, container, false)


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

            val totalFoodItem = calculateMealsTotal(CommonFoods.allFoods)

            totalFoodItem.let {
                totalMacroText.text = getString(
                    R.string.macro_nutrients,
                    it.macroNutrients.calories.toInt(),
                    it.macroNutrients.protein.toInt(),
                    it.macroNutrients.carbs.toInt(),
                    it.macroNutrients.fats.toInt()
                )

                totalMicroTextView.text = getString(
                    R.string.micro_nutrients,
                    it.microNutrients.vitaminA.toInt(),
                    it.microNutrients.vitaminD.toInt(),
                    it.microNutrients.sugars.toInt(),
                    it.microNutrients.iron.toInt(),
                    it.microNutrients.calcium.toInt(),
                    it.microNutrients.fiber.toInt(),
                    it.microNutrients.potassium.toInt(),
                    it.microNutrients.magnesium.toInt()
                )
            }


            Toast.makeText(requireContext(), "Updated meals info", Toast.LENGTH_LONG).show()

        }
        foodSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Calls API, parses request, and updates recycler view
                getRequestFood(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Respond to real-time text changes if needed
                return false
            }
        })
    }

    private fun calculateMealsTotal(allFoodItems: ArrayList<FoodItem>): FoodItem {

        var totalMacroNutrients = MacroNutrients(0.0, 0.0, 0.0, 0.0)
        var totalMicroNutrients = MicroNutrients(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)

        for (foodItem in allFoodItems) {
            totalMacroNutrients = totalMacroNutrients.addInstances(foodItem.macroNutrients)
            totalMicroNutrients = totalMicroNutrients.addInstances(foodItem.microNutrients)
        }

        return FoodItem("", "", " ", 1.0, totalMacroNutrients, totalMicroNutrients, "")

    }

    private fun initializeViews(viewFrag: View) {

        recyclerViewSearch = viewFrag.findViewById(R.id.recyclerViewSearches)
        recyclerViewBreakFast = viewFrag.findViewById(R.id.recyclerViewBreakFast)
        recyclerViewLunch = viewFrag.findViewById(R.id.recyclerViewLunch)
        recyclerViewDinner = viewFrag.findViewById(R.id.recyclerViewDinner)
        recyclerViewSnacks = viewFrag.findViewById(R.id.recyclerViewSnack)

        totalFoodIntakes = viewFrag.findViewById(R.id.totalIntakesTodayText)
        totalMicroTextView = viewFrag.findViewById(R.id.totalMicroTextView)
        totalMacroText = viewFrag.findViewById(R.id.totalMacroTextView)
        foodSearch = viewFrag.findViewById(R.id.searchView)
        textRefresh = viewFrag.findViewById(R.id.updateMealsButton)

    }

    private fun setUpAdaptersAndLayoutManagers() {

        val context = requireContext()

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

    private fun getRequestFood(queryString: String) {
        val client = AsyncHttpClient()
        val params = RequestParams()
        val endpoint = "https://api.nal.usda.gov/fdc/v1/foods/search"

        params["query"] = queryString
        params["pageSize"] = "50"
        params["dataType"] = "Survey (FNDDS)"
        params["api_key"] = getString(R.string.USDA_key)

        client[endpoint, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {

                val foodResults = ArrayList<FoodItem>()
                val foodArr = json?.jsonObject?.getJSONArray("foods")
                for (i in 0 until (foodArr?.length() ?: exitProcess(1))) {
                    val foodObj = foodArr.getJSONObject(i)
                    val foodNutrients = foodObj.getJSONArray("foodNutrients")

                    // Macros
                    val calories = foodNutrients.getJSONObject(3).getString("value").toDouble()
                    val protein = foodNutrients.getJSONObject(0).getString("value").toDouble()
                    val carbs = foodNutrients.getJSONObject(2).getString("value").toDouble()
                    val fat = foodNutrients.getJSONObject(1).getString("value").toDouble()

                    // Micros
                    val sugars = foodNutrients.getJSONObject(8).getString("value").toDouble()
                    val fiber = foodNutrients.getJSONObject(9).getString("value").toDouble()
                    val calcium = foodNutrients.getJSONObject(10).getString("value").toDouble()
                    val iron = foodNutrients.getJSONObject(11).getString("value").toDouble()
                    val mag = foodNutrients.getJSONObject(12).getString("value").toDouble()
                    val potassium = foodNutrients.getJSONObject(14).getString("value").toDouble()
                    val vitaminA = foodNutrients.getJSONObject(20).getString("value").toDouble()
                    val vitaminD = foodNutrients.getJSONObject(24).getString("value").toDouble()

//                    Log.d("test", foodNutrients.getJSONObject(8).toString())

                    // Food
                    val id = foodObj.getString("fdcId").toString()
                    val name = foodObj.getString("description")
                    var info = foodObj.getString("additionalDescriptions")
                    info = info.ifEmpty { "N/A" }

                    // Class Obj
                    val macros = MacroNutrients(calories, protein, carbs, fat)
                    val micros = MicroNutrients(
                        vitaminA,
                        vitaminD,
                        sugars,
                        iron,
                        calcium,
                        fiber,
                        potassium,
                        mag
                    )
                    val foodItem = FoodItem(id, name, info, 1.0, macros, micros, "")

                    foodResults.add(foodItem)

                }
                val context = requireContext()
                val adapter = FoodSearchAdapter(context, foodResults)
                recyclerViewSearch.adapter = adapter
                recyclerViewSearch.layoutManager =
                    StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.HORIZONTAL)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                t: Throwable?
            ) {
                Toast.makeText(
                    requireContext(),
                    "Request failed with status code: $statusCode",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }]

    }

}
