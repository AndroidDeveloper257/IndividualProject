package com.example.individualproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.individualproject.databinding.RegistrationSpecialityItemBinding
import com.example.individualproject.models.Speciality

class ChooseSpecialityAdapter(
    private val specialityList: ArrayList<Speciality>,
    val onItemClick: (Speciality) -> Unit
) : RecyclerView.Adapter<ChooseSpecialityAdapter.Vh>() {

    inner class Vh(var itemBinding: RegistrationSpecialityItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(speciality: Speciality) {
            itemBinding.specialityNameTv.text = speciality.title
            itemBinding.specialityNameTv.setOnClickListener {
                onItemClick.invoke(speciality)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            RegistrationSpecialityItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(specialityList[position])
    }

    override fun getItemCount(): Int = specialityList.size
}