package com.simbiri.wellness_warden

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.simbiri.wellness_warden.model.FoodItem

class BottomSheetMealFragment : BottomSheetDialogFragment() {

    companion object {
        private const val ARGS_FOOD_ITEM = "food_item"

        fun newInstance(food: FoodItem): BottomSheetMealFragment {
            val fragment = BottomSheetMealFragment()
            val args = Bundle()
            args.putParcelable(ARGS_FOOD_ITEM, food)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: BottomSheetMealViewModel

    private lateinit var nameFoodTextView: TextView
    private lateinit var macroDetailsTextView: TextView
    private lateinit var microDetailsTextView: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_meal_fragment, container, false)
        nameFoodTextView = view.findViewById(R.id.nameOfFoodMealBottomSheet)
        macroDetailsTextView = view.findViewById(R.id.macroNutrientsText)
        microDetailsTextView = view.findViewById(R.id.microNutrientsText)

        val foodItem = arguments?.getParcelable<FoodItem>(ARGS_FOOD_ITEM)



        foodItem?.let {
            nameFoodTextView.text = it.name
            macroDetailsTextView.text =
                "Calories - ${it.macroNutrients.calories}kcals," + " Protein - ${it.macroNutrients.protein}g," + "\n" + " Carbs - ${it.macroNutrients.carbs}g," + " Fats - ${it.macroNutrients.fats}g "
            microDetailsTextView.text =
                "Vitamin A - ${it.microNutrients.vitaminA}mg," + " Vitamin D - ${it.microNutrients.vitaminD}mg," + "\n" + " Sugars - ${it.microNutrients.sugars}g," + "  Iron - ${it.microNutrients.iron}mg," + "\n" +
                        "Calcium - ${it.microNutrients.calcium}mg," + " Fiber - ${it.microNutrients.fiber}g," + "\n" + " Potassium - ${it.microNutrients.potassium}mg," + " Magnesium - ${it.microNutrients.magnesium}mg"
        }

        return view
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogFragment = super.onCreateDialog(savedInstanceState)
        dialogFragment.setContentView(R.layout.bottom_sheet_meal_fragment)

        dialogFragment.setCanceledOnTouchOutside(true)

        dialogFragment.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(bottomSheet)
                behavior.peekHeight = resources.getDimensionPixelSize(R.dimen.peek_height)
                behavior.isDraggable = true
                behavior.isHideable = true
            }
        }

        return dialogFragment

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BottomSheetMealViewModel::class.java)
        // TODO: Use the ViewModel
    }

}