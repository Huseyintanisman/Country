package com.example.myapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.viewmodel.AddCountryViewModel

class AddCountryFragment : Fragment() {
    private lateinit var viewModel: AddCountryViewModel
    private lateinit var nameEditText: EditText
    private lateinit var capitalEditText: EditText
    private lateinit var regionEditText: EditText
    private lateinit var languageEditText: EditText
    private lateinit var flagEditText:EditText
    private lateinit var currencyEditText : EditText
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[AddCountryViewModel::class.java]

        nameEditText = view.findViewById(R.id.et_country_name)
        capitalEditText = view.findViewById(R.id.et_country_capital)
        currencyEditText = view.findViewById(R.id.et_country_currency)
        regionEditText = view.findViewById(R.id.et_country_region)
        languageEditText = view.findViewById(R.id.et_country_language)
        flagEditText = view.findViewById(R.id.et_country_flag_url)
        saveButton = view.findViewById(R.id.btn_add_country)

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val capital = capitalEditText.text.toString()
            val region = regionEditText.text.toString()
            val currency = currencyEditText.toString()
            val language = languageEditText.text.toString()
            val flag = flagEditText.text.toString()

            viewModel.addCountry(name, capital, region, currency, language, flag)

            val action = AddCountryFragmentDirections.actionAddCountryFragmentToCountryFeedFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }
}
