package com.aper_lab.grocery.viewModel.recipe

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aper_lab.grocery.databinding.ListItemDirectionBinding
import com.aper_lab.scraperlib.data.Ingredient
import com.aper_lab.scraperlib.data.RecipeStep

//TODO(Diff Utils)

class RecipeDirectionsAdapter: RecyclerView.Adapter<RecipeDirectionsAdapter.ViewHolder>() {

    var data = listOf<RecipeStep>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder.from(parent);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], data.size)
    }

    class ViewHolder(val binding: ListItemDirectionBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(direction: RecipeStep, itemCount:Int){
            binding.step = direction;
            if(direction.num == 1)
                binding.topLine.visibility = View.GONE
            if(direction.num == itemCount)
                binding.bottomLine.visibility = View.GONE

            binding.executePendingBindings();
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context);
                val binding = ListItemDirectionBinding.inflate(layoutInflater);
                binding.root.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                return ViewHolder(binding);
            }
        }
    }

}