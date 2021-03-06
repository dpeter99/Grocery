package com.aper_lab.grocery.fragment.recipeList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aper_lab.grocery.databinding.ListItemRecipeBinding
import com.aper_lab.grocery.liveData.LiveUserRecipe
import com.aper_lab.grocery.model.UserRecipe
import com.aper_lab.scraperlib.RecipeAPIService
import com.aper_lab.scraperlib.data.Recipe

class RecipeListAdapter(val clickListener: RecipeListener) :
    RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {

    var data = listOf<UserRecipe>()
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
        data.elementAt(position).let { holder.bind(it, data.size, clickListener) }
    }

    class ViewHolder(val binding: ListItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            recipe: UserRecipe,
            itemCount: Int,
            clickListener: RecipeListener
        ) {
            binding.recipe = recipe.recipe;
            binding.clickListener = clickListener;



            val id = recipe.recipe.link.let { RecipeAPIService.getSourceIDfromURL(it) };
            binding.icon.setImageResource(
                binding.root.context.resources.getIdentifier(
                    "icon_" + id,
                    "drawable",
                    "com.aper_lab.grocery"
                )
            )

            if (recipe.userData?.favorite == true) {
                binding.favouriteIcon.visibility = View.VISIBLE;
            }
            else{
                binding.favouriteIcon.visibility = View.INVISIBLE;
            }



            binding.executePendingBindings();
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context);

                //val view = layoutInflater.inflate(R.layout.list_item_recipe, parent);

                var binding = ListItemRecipeBinding.inflate(layoutInflater);
                binding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                );
                return ViewHolder(
                    binding
                );
            }
        }

    }

    class RecipeListener(val clickListener: (name: String) -> Unit) {
        fun onClick(recipe: Recipe) = clickListener(recipe.id)
    }

}