package com.example.myapplication.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.R
import com.example.myapplication.viewmodel.FeedViewModel
import com.example.myapplication.adapter.CountryAdapter

class CountryFeedFragment : Fragment() {
    private lateinit var viewModel: FeedViewModel
    private lateinit var radioButton1: RadioButton
    private lateinit var radioButton2: RadioButton
    private lateinit var radioButton3: RadioButton
    private lateinit var radioButton4: RadioButton
    private lateinit var radioButton0: RadioButton
    private lateinit var radioButton5: AppCompatButton
    private lateinit var radioButton6: AppCompatButton
    private lateinit var countryError: TextView
    private lateinit var swipeRefreshLayout : SwipeRefreshLayout
    private lateinit var countryList: RecyclerView
    private lateinit var countryLoadingProgressBar: ProgressBar
    private var countryAdapter = CountryAdapter(arrayListOf())


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_country_feed, container, false)

        countryError = view.findViewById(R.id.countryError)
        radioButton1 = view.findViewById(R.id.radioButton)
        radioButton2 = view.findViewById(R.id.radioButton2)
        radioButton3 = view.findViewById(R.id.radioButton3)
        radioButton4 = view.findViewById(R.id.radioButton4)
        radioButton0 = view.findViewById(R.id.radioButton0)
        radioButton5 = view.findViewById(R.id.radioButton5)
        radioButton6 = view.findViewById(R.id.radioButton6)
        countryList = view.findViewById(R.id.countryList)
        countryLoadingProgressBar = view.findViewById(R.id.countryLoadingProgressBar)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[FeedViewModel::class.java]
        viewModel.refreshData()
        countryList.layoutManager = LinearLayoutManager(context)
        countryList.adapter = countryAdapter

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedItem = countryAdapter.getCountryAtPosition(position)

                AlertDialog.Builder(requireContext())
                    .setTitle("Delete Country? ")
                    .setMessage("Are you sure you want to delete ${deletedItem.countryName}?")
                    .setPositiveButton("Yes") { _, _ ->
                        countryAdapter.removeItem(position)
                        viewModel.deleteSelectedCountries(uuid = deletedItem.uuid)
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        countryAdapter.notifyItemChanged(position)
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        })

        itemTouchHelper.attachToRecyclerView(countryList)

        radioButton0.setOnClickListener{
            if (radioButton0.isChecked){
                radioButton1.isChecked = false
                radioButton2.isChecked = false
                radioButton3.isChecked = false
                radioButton4.isChecked = false
                viewModel.getAllCountries()
            }
        }

        radioButton5.setOnClickListener{
            val action = CountryFeedFragmentDirections.actionCountryFeedFragmentToAddCountryFragment()
            Navigation.findNavController(it).navigate(action)
        }

        radioButton6.setOnClickListener{
            viewModel.deleteCountries()
            viewModel.refreshData()
        }

        radioButton0.setOnClickListener{
            if (radioButton0.isChecked){
                radioButton1.isChecked = false
                radioButton2.isChecked = false
                radioButton3.isChecked = false
                radioButton4.isChecked = false
                viewModel.getAllCountries()
            }
        }

        radioButton1.setOnClickListener{
            if (radioButton1.isChecked){
                radioButton3.isChecked = false
                radioButton2.isChecked = false
                radioButton4.isChecked = false
                radioButton0.isChecked = false
                viewModel.europaitemSelected()
            }
        }

        radioButton2.setOnClickListener{
            if (radioButton2.isChecked){
                radioButton3.isChecked = false
                radioButton1.isChecked = false
                radioButton4.isChecked = false
                radioButton0.isChecked = false
                viewModel.asiaitemSelected()
            }
        }

        radioButton3.setOnClickListener{
            if (radioButton3.isChecked){
                radioButton1.isChecked = false
                radioButton2.isChecked = false
                radioButton4.isChecked = false
                radioButton0.isChecked = false
                viewModel.africaitemSelected()
            }
        }

        radioButton4.setOnClickListener{
            if (radioButton4.isChecked){
                radioButton1.isChecked = false
                radioButton2.isChecked = false
                radioButton3.isChecked = false
                radioButton0.isChecked = false
                viewModel.americaitemSelected()
            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            countryList.visibility = View.GONE
            countryError.visibility = View.GONE
            countryLoadingProgressBar.visibility =  View.GONE
            viewModel.refreshData()
            swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()

    }

    private fun observeLiveData() {
        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    countryLoadingProgressBar.visibility = View.GONE
                    countryError.visibility = View.GONE
                    countryList.visibility = View.GONE
                } else {
                    countryLoadingProgressBar.visibility = View.GONE
                }
            }
        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it) {
                    countryError.visibility = View.VISIBLE
                } else {
                    countryError.visibility = View.GONE
                }
            }
        })

        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                countryList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
        })
    }
}







