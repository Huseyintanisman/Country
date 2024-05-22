package com.example.myapplication.adapter
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Country
import com.example.myapplication.util.downloadFromUrl
import com.example.myapplication.util.placeholderProgressBar
import com.example.myapplication.view.CountryFeedFragmentDirections

class CountryAdapter(private val countryList: ArrayList<Country>):RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {
    class CountryViewHolder(var view : View): RecyclerView.ViewHolder(view){


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_country,parent,false)
        return CountryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.view.findViewById<TextView>(R.id.countryNameText)?.text = countryList[position].countryName
        holder.view.findViewById<TextView>(R.id.CountryRegionText)?.text = countryList[position].countryRegion



        holder.view.setOnClickListener{
            val action = CountryFeedFragmentDirections.actionCountryFeedFragmentToCountryDetailsFragment(countryList[position].uuid)
            //action.countryUuid
            Navigation.findNavController(it).navigate(action)
        }

        holder.view.findViewById<ImageView>(R.id.imageView)?.downloadFromUrl(countryList[position].countryFlagImageUrl,
            placeholderProgressBar(holder.view.context)
        )

    }

    fun removeItem(position: Int){
        countryList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getCountryAtPosition(position: Int): Country {
        return countryList[position]
    }



    @SuppressLint("NotifyDataSetChanged")
    fun updateCountryList(newCountryList: List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }


}