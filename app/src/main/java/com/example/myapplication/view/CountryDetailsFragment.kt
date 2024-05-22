package com.example.myapplication.view
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.util.downloadFromUrl
import com.example.myapplication.util.placeholderProgressBar
import com.example.myapplication.viewmodel.CountryViewModel


class CountryDetailsFragment : Fragment() {

    private lateinit var viewModel : CountryViewModel
    private var countryUuid = 0
    private lateinit var cname : TextView
    private lateinit var ccapital : TextView
    private lateinit var ccurrency : TextView
    private lateinit var clanguage : TextView
    private lateinit var cregion : TextView
    private lateinit var countryImage : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { countryUuid = CountryDetailsFragmentArgs.fromBundle(it).countryUuid }

        viewModel = ViewModelProvider(this)[CountryViewModel::class.java]
        viewModel.getDataFromRoom(countryUuid)

        cname = view.findViewById(R.id.countryName)
        ccapital = view.findViewById(R.id.countryCapital)
        ccurrency = view.findViewById(R.id.countryCurrency)
        clanguage = view.findViewById(R.id.countryLanguage)
        cregion = view.findViewById(R.id.countryRegion)
        countryImage = view.findViewById(R.id.countryImage)
        observeLiveData()

    }

    private fun observeLiveData() {
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country->
            country?.let {
                cname.text = country.countryName
                ccapital.text = country.countryCapital
                ccurrency.text = country.countryCurrency
                clanguage.text = country.countryLanguage
                cregion.text = country.countryRegion
                context?.let {
                countryImage.downloadFromUrl(country.countryFlagImageUrl, placeholderProgressBar(it))
                }
            }
        })
    }
}