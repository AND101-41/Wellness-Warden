package com.simbiri.wellness_warden

import android.app.Dialog
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetSearchFragment : BottomSheetDialogFragment() {

    companion object {
        private const val ARGS_FOOD_ITEM = "food_item"
        fun newInstance(food: Food): BottomSheetSearchFragment {
            val fragment = BottomSheetSearchFragment()
            val args = Bundle()

            args.putParcelable(ARGS_FOOD_ITEM, food)
            fragment.arguments = args

            return fragment
        }
    }

    private lateinit var viewModel: BottomSheetSearchViewModel
    private lateinit var textAddMeal: TextView
    private lateinit var addBreakFastButton: Button
    private lateinit var addLunchButton: Button
    private lateinit var addDinnerButton: Button
    private lateinit var addSnackButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_search_fragment, container, false)

        requireActivity().supportFragmentManager.findFragmentById(R.id.foodJournalFragment)?.onPause()

        view.let {
            textAddMeal = it.findViewById(R.id.textViewAddMeal)
            addBreakFastButton = it.findViewById(R.id.buttonAddToBreakFast)
            addLunchButton = it.findViewById(R.id.buttonAddToLunch)
            addDinnerButton = it.findViewById(R.id.buttonAddToDinner)
            addSnackButton = it.findViewById(R.id.buttonAddToSnacks)
        }

        val foodInstance = arguments?.getParcelable<Food>(ARGS_FOOD_ITEM)
        foodInstance?.let {
            textAddMeal.text = "Add ${it.foodName} to any meal below "

        }

        addOnClickListeners(foodInstance)


        return view
    }

    private fun addOnClickListeners(foodInstance: Food?) {

        addBreakFastButton.setOnClickListener {
            CommonFoods.allBreakFast.add(foodInstance!!)
            Toast.makeText(requireContext(), "Added food to Breakfast", Toast.LENGTH_SHORT).show()
            dismiss()

        }
        addLunchButton.setOnClickListener {
            CommonFoods.allLunch.add(foodInstance!!)
            Toast.makeText(requireContext(), "Added food to Lunch", Toast.LENGTH_SHORT).show()
            dismiss()
        }
        addDinnerButton.setOnClickListener {
            CommonFoods.allDinner.add(foodInstance!!)
            Toast.makeText(requireContext(), "Added food to Dinner", Toast.LENGTH_SHORT).show()
            dismiss()
        }
        addSnackButton.setOnClickListener {
            CommonFoods.allSnacks.add(foodInstance!!)
            Toast.makeText(requireContext(), "Added meal to Snacks", Toast.LENGTH_SHORT).show()
            dismiss()
        }

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
        viewModel = ViewModelProvider(this).get(BottomSheetSearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

}