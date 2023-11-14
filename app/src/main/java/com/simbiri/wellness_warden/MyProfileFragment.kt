package com.simbiri.wellness_warden
import android.annotation.SuppressLint
import android.graphics.Color
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

@Suppress("DEPRECATION")
class MyProfileFragment : Fragment() {

    private lateinit var viewModel: MyProfileViewModel
    private lateinit var weightProgressBar: ProgressBar
    private lateinit var heightEditText: EditText
    private lateinit var weightEditText: EditText
    private lateinit var bmiCalculationTextView: TextView

    private lateinit var tvEnergy: TextView
    private lateinit var tvTotalLipid: TextView
    private lateinit var tvProtein: TextView
    private lateinit var tvFiber: TextView
    private lateinit var tvFattyAcidsTrans: TextView
    private lateinit var tvFattyAcidsSaturated: TextView
    private lateinit var tvCarbohydrates: TextView
    private lateinit var tvSugars: TextView
    private lateinit var tvCholesterol: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.my_profile_fragment, container, false)
        tvEnergy = view.findViewById(R.id.tvEnergy)
        tvTotalLipid = view.findViewById(R.id.tvTotalLipid)
        tvProtein = view.findViewById(R.id.tvProtein)
        tvFiber = view.findViewById(R.id.tvFiber)
        tvFattyAcidsTrans = view.findViewById(R.id.tvFattyAcidsTotalTrans)
        tvFattyAcidsSaturated = view.findViewById(R.id.tvFattyAcidsTotalSaturated)
        tvCarbohydrates = view.findViewById(R.id.tvCarbohydrate)
        tvSugars = view.findViewById(R.id.tvSugars)
        tvCholesterol = view.findViewById(R.id.tvCholesterol)

        // Initialize ProgressBar, EditText, and TextViews
        weightProgressBar = view.findViewById(R.id.weightProgressBar)
        heightEditText = view.findViewById(R.id.enterHeight)
        weightEditText = view.findViewById(R.id.enterWeight)
        bmiCalculationTextView = view.findViewById(R.id.tvBMICalculation)

        // Set a default or retrieved progress on the ProgressBar
        weightProgressBar.progress = 50 // This value should be retrieved from a ViewModel or user input

        // Add the TextChangedListener to the height and weight EditTexts
        heightEditText.addTextChangedListener(textWatcher)
        weightEditText.addTextChangedListener(textWatcher)

        return view
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            calculateAndDisplayBMI()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    @SuppressLint("SetTextI18n")
    private fun calculateAndDisplayBMI() {
        val heightInInches = heightEditText.text.toString().toDoubleOrNull()
        val weightInPounds = weightEditText.text.toString().toDoubleOrNull()

        if (heightInInches != null && heightInInches > 0 && weightInPounds != null && weightInPounds > 0) {
            val bmi = (703 * weightInPounds) / (heightInInches * heightInInches)
            bmiCalculationTextView.text = String.format("BMI %.2f", bmi)
            bmiCalculationTextView.setTextColor(Color.GREEN)

            displayRecommendedIntakeInfo(bmi)
        } else {
            bmiCalculationTextView.text = "Please enter valid height and weight"
            bmiCalculationTextView.setTextColor(Color.RED)
        }
    }

    data class RecommendedIntake(
        val energy: Int,
        val totalLipid: Double,
        val protein: Double,
        val fiber: Double,
        val fattyAcidsTrans: Double,
        val fattyAcidsSaturated: Double,
        val carbohydrates: Double,
        val sugars: Double,
        val cholesterol: Double
    )

    private fun getRecommendedIntakeInfo(bmi: Double): RecommendedIntake {
        return when {
            bmi < 18.5 -> RecommendedIntake(
                energy = 2000,
                totalLipid = 60.0,
                protein = 50.0,
                fiber = 30.0,
                fattyAcidsTrans = 0.0,
                fattyAcidsSaturated = 20.0,
                carbohydrates = 200.0,
                sugars = 40.0,
                cholesterol = 200.0
            )
            bmi in 18.5..24.9 -> RecommendedIntake(
                energy = 2300,
                totalLipid = 65.0,
                protein = 55.0,
                fiber = 35.0,
                fattyAcidsTrans = 0.0,
                fattyAcidsSaturated = 25.0,
                carbohydrates = 220.0,
                sugars = 45.0,
                cholesterol = 180.0
            )
            bmi in 25.0..29.9 -> RecommendedIntake(
                energy = 2600,
                totalLipid = 70.0,
                protein = 60.0,
                fiber = 40.0,
                fattyAcidsTrans = 2.0,
                fattyAcidsSaturated = 22.0,
                carbohydrates = 210.0,
                sugars = 50.0,
                cholesterol = 250.0
            )
            else -> RecommendedIntake(
                energy = 3000,
                totalLipid = 75.0,
                protein = 65.0,
                fiber = 45.0,
                fattyAcidsTrans = 3.0,
                fattyAcidsSaturated = 30.0,
                carbohydrates = 250.0,
                sugars = 55.0,
                cholesterol = 300.0
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayRecommendedIntakeInfo(bmi: Double) {
        val recommendedIntakeInfo = getRecommendedIntakeInfo(bmi)

        tvEnergy.text = "Energy: ${recommendedIntakeInfo.energy} kcal"
        tvTotalLipid.text = "Total Lipid: ${recommendedIntakeInfo.totalLipid} g"
        tvProtein.text = "Protein: ${recommendedIntakeInfo.protein} g"
        tvFiber.text = "Fiber: ${recommendedIntakeInfo.fiber} g"
        tvFattyAcidsTrans.text = "Fatty Acids (Trans): ${recommendedIntakeInfo.fattyAcidsTrans} g"
        tvFattyAcidsSaturated.text = "Fatty Acids (Saturated): ${recommendedIntakeInfo.fattyAcidsSaturated} g"
        tvCarbohydrates.text = "Carbohydrates: ${recommendedIntakeInfo.carbohydrates} g"
        tvSugars.text = "Sugars: ${recommendedIntakeInfo.sugars} g"
        tvCholesterol.text = "Cholesterol: ${recommendedIntakeInfo.cholesterol} mg"
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[MyProfileViewModel::class.java]
        // Use the ViewModel as needed
    }
}

