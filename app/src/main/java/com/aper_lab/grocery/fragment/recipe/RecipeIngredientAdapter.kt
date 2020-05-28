package com.aper_lab.grocery.fragment.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aper_lab.grocery.R
import com.aper_lab.grocery.databinding.ListItemIngredientBinding
import com.aper_lab.scraperlib.data.Ingredient

//TODO(Diff Utils)

class RecipeIngredientAdapter: RecyclerView.Adapter<RecipeIngredientAdapter.ViewHolder>() {

    var data = listOf<Ingredient>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder.from(
            parent
        );
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class ViewHolder(val binding: ListItemIngredientBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(ingredient: Ingredient){
            binding.ingredient = ingredient;
            binding.executePendingBindings();
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context);

                //val view = layoutInflater.inflate(R.layout.list_item_ingredient, parent);

                var binding : ListItemIngredientBinding = DataBindingUtil.inflate(layoutInflater,R.layout.list_item_ingredient, parent,false);

                //val binding = ListItemIngredientBinding.inflate(layoutInflater);
                return ViewHolder(
                    binding
                );
            }
        }
    }

}