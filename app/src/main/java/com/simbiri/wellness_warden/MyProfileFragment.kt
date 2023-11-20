package com.simbiri.wellness_warden

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simbiri.wellness_warden.model.Gender

@Suppress("DEPRECATION")
class MyProfileFragment : Fragment() {

    private lateinit var viewModel: MyProfileViewModel
    private lateinit var weightProgressBar: ProgressBar
    private lateinit var heightEditText: EditText
    private lateinit var weightEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var genderRadio: RadioGroup
    private lateinit var submitButton: Button
    private lateinit var kcalLimit: TextView
    private lateinit var kcalCurr: TextView
    private lateinit var recyclerNutrientView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.my_profile_fragment, container, false)

        initializeViews(view)
        setUpAdaptersAndLayoutManagers()
        setListeners()
        updateViews()

        return view
    }

    private fun initializeViews(view: View) {

        weightProgressBar = view.findViewById(R.id.weightProgressBar)
        heightEditText = view.findViewById(R.id.enterHeight)
        weightEditText = view.findViewById(R.id.enterWeight)
        ageEditText = view.findViewById(R.id.enterAge)
        genderRadio = view.findViewById(R.id.genderRadioGroup)
        submitButton = view.findViewById(R.id.calcButton)
        kcalLimit = view.findViewById(R.id.kcalLimit)
        kcalCurr = view.findViewById(R.id.kcalCurrent)
        recyclerNutrientView = view.findViewById(R.id.recyclerViewNutrients)

    }

    private fun setListeners() {
        genderRadio.setOnCheckedChangeListener { _, checkedId ->
            CommonFoods.personTrack.gender = when (checkedId) {
                R.id.radioMale -> Gender.MALE
                R.id.radioFemale -> Gender.FEMALE
                else -> throw IllegalArgumentException("Unexpected gender id")
            }

            submitButton.setOnClickListener {
                if (heightEditText.text.isNotEmpty() && weightEditText.text.isNotEmpty() && ageEditText.text.isNotEmpty() && genderRadio.checkedRadioButtonId != -1) {
                    // All required views are filled out, calculate BMR and BMI
                    val height = heightEditText.text.toString().toDoubleOrNull()
                    val weight = weightEditText.text.toString().toDoubleOrNull()
                    val age = ageEditText.text.toString().toInt()
                    val gender =
                        if (genderRadio.checkedRadioButtonId == R.id.radioMale) Gender.MALE else Gender.FEMALE

                    // Calculate BMR and BMI (using some hypothetical functions calculateBmr and calculateBmi)
                    CommonFoods.personTrack.bmr = calculateBmr(height!!, weight!!, age, gender)
                    CommonFoods.personTrack.bmi = (703 * weight) / (height * height)
                    getRecommendedIntakeInfo(CommonFoods.personTrack.bmi)


                    updateViews()

                    // Update RecyclerView
                    setUpAdaptersAndLayoutManagers()


                } else {
                    // Not all required views are filled out, show an error message
                    Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }

    private fun updateViews() {

        // Display BMR
        kcalLimit.text =
            getString(R.string.calories_limit, String.format("%.2f", CommonFoods.personTrack.bmr))
        weightProgressBar.max = CommonFoods.personTrack.bmr.toInt()

        // Display Current Consumed
        kcalCurr.text = getString(
            R.string.calories_consumed,
            String.format("%.2f", CommonFoods.personTrack.caloricIntake)
        )
        weightProgressBar.progress = CommonFoods.personTrack.caloricIntake.toInt()
        weightProgressBar.max = CommonFoods.personTrack.bmr.toInt()

    }

    private fun setUpAdaptersAndLayoutManagers() {

        val context = requireContext()

        val adapterForNutri = NutrientListAdapter(context, CommonFoods.personTrack.nutrients)
        val layoutManagerNutri = LinearLayoutManager(context)
        layoutManagerNutri.orientation = RecyclerView.HORIZONTAL


        recyclerNutrientView.adapter = adapterForNutri
        recyclerNutrientView.layoutManager = layoutManagerNutri


    }


    private fun calculateBmr(height: Double, weight: Double, age: Int, gender: Gender): Double {
        return if (gender == Gender.MALE) {
            (66.47 + (6.24 * weight) + (12.7 * height) - (6.755 * age))
        } else {
            (655.1 + (4.35 * weight) + (4.7 * height) - (4.7 - age))
        }

    }

    private fun getRecommendedIntakeInfo(bmi: Double) {

        if (bmi < 18.5) {
            CommonFoods.personTrack.nutrients["protein"]?.rdi = 56.0
            CommonFoods.personTrack.nutrients["carbs"]?.rdi = 130.0
            CommonFoods.personTrack.nutrients["fats"]?.rdi = 70.0
            CommonFoods.personTrack.nutrients["vitaminA"]?.rdi = 900.0
            CommonFoods.personTrack.nutrients["vitaminD"]?.rdi = 20.0
            CommonFoods.personTrack.nutrients["sugars"]?.rdi = 50.0
            CommonFoods.personTrack.nutrients["iron"]?.rdi = 18.0
            CommonFoods.personTrack.nutrients["potassium"]?.rdi = 4700.0
            CommonFoods.personTrack.nutrients["magnesium"]?.rdi = 420.0
        } else if (bmi in 18.5..24.9) {
            CommonFoods.personTrack.nutrients["protein"]?.rdi = 50.0
            CommonFoods.personTrack.nutrients["carbs"]?.rdi = 130.0
            CommonFoods.personTrack.nutrients["fats"]?.rdi = 70.0
            CommonFoods.personTrack.nutrients["vitaminA"]?.rdi = 900.0
            CommonFoods.personTrack.nutrients["vitaminD"]?.rdi = 20.0
            CommonFoods.personTrack.nutrients["sugars"]?.rdi = 50.0
            CommonFoods.personTrack.nutrients["iron"]?.rdi = 18.0
            CommonFoods.personTrack.nutrients["potassium"]?.rdi = 4700.0
            CommonFoods.personTrack.nutrients["magnesium"]?.rdi = 420.0
        } else if (bmi in 25.0..29.9) {
            CommonFoods.personTrack.nutrients["protein"]?.rdi = 45.0
            CommonFoods.personTrack.nutrients["carbs"]?.rdi = 130.0
            CommonFoods.personTrack.nutrients["fats"]?.rdi = 70.0
            CommonFoods.personTrack.nutrients["vitaminA"]?.rdi = 900.0
            CommonFoods.personTrack.nutrients["vitaminD"]?.rdi = 20.0
            CommonFoods.personTrack.nutrients["sugars"]?.rdi = 50.0
            CommonFoods.personTrack.nutrients["iron"]?.rdi = 18.0
            CommonFoods.personTrack.nutrients["potassium"]?.rdi = 4700.0
            CommonFoods.personTrack.nutrients["magnesium"]?.rdi = 420.0
        } else {
            CommonFoods.personTrack.nutrients["protein"]?.rdi = 40.0
            CommonFoods.personTrack.nutrients["carbs"]?.rdi = 130.0
            CommonFoods.personTrack.nutrients["fats"]?.rdi = 70.0
            CommonFoods.personTrack.nutrients["vitaminA"]?.rdi = 900.0
            CommonFoods.personTrack.nutrients["vitaminD"]?.rdi = 20.0
            CommonFoods.personTrack.nutrients["sugars"]?.rdi = 50.0
            CommonFoods.personTrack.nutrients["iron"]?.rdi = 18.0
            CommonFoods.personTrack.nutrients["potassium"]?.rdi = 4700.0
            CommonFoods.personTrack.nutrients["magnesium"]?.rdi = 420.0
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[MyProfileViewModel::class.java]
        // Use the ViewModel as needed
    }
}

