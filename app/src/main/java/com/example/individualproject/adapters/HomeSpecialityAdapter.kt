package com.example.individualproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.individualproject.databinding.HomeSpecialityItemBinding
import com.example.individualproject.models.Speciality

class HomeSpecialityAdapter(
    private val specialityList: ArrayList<Speciality>,
    private val onItemClick:(Speciality) -> Unit
) : RecyclerView.Adapter<HomeSpecialityAdapter.Vh>(){

    inner class Vh(private val itembinding: HomeSpecialityItemBinding) :
        RecyclerView.ViewHolder(itembinding.root) {
        fun onBind(speciality: Speciality) {
            itembinding.specialityNameTv.text = speciality.title
            itembinding.layout.setOnClickListener {
                onItemClick.invoke(speciality)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(HomeSpecialityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(specialityList[position])
    }

    override fun getItemCount(): Int = specialityList.size
}