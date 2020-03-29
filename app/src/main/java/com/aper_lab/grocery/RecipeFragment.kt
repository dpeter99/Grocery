package com.aper_lab.grocery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aper_lab.grocery.RecipeFragmentArgs.fromBundle
import com.aper_lab.grocery.databinding.FragmentRecepieBinding
import com.aper_lab.grocery.viewModel.recipe.RecipeDirectionsAdapter
import com.aper_lab.grocery.viewModel.recipe.RecipeIngredientAdapter
import com.aper_lab.grocery.viewModel.recipe.RecipeViewModel
import com.aper_lab.grocery.viewModel.recipe.RecipeViewModelFactory
import com.aper_lab.scraperlib.data.Ingredient
import com.aper_lab.scraperlib.data.Recipe
import com.aper_lab.scraperlib.data.RecipeStep


/**
 * A simple [Fragment] subclass.
 */
class RecipeFragment : Fragment() {

    private lateinit var viewModel: RecipeViewModel;
    private lateinit var viewModelFactory: RecipeViewModelFactory


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentRecepieBinding>(inflater, R.layout.fragment_recepie,container,false)

        val args = RecipeFragmentArgs.fromBundle(arguments!!)

        //viewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java);
        viewModelFactory = RecipeViewModelFactory(args.recipeID)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RecipeViewModel::class.java);


        binding.viewModel = viewModel;
        binding.lifecycleOwner = this;

        var adapter_ingredients = RecipeIngredientAdapter();
        adapter_ingredients.data = viewModel.recipe.value?.ingredients ?: listOf(Ingredient("",""));
        binding.ingredientList.adapter = adapter_ingredients;

        var adapter_directions = RecipeDirectionsAdapter();
        adapter_directions.data = viewModel.recipe.value?.directions ?: listOf(RecipeStep(0,""));
        binding.stepsList.adapter = adapter_directions;

        // Create the observer which updates the UI.
        val recipeObserver = Observer<Recipe> { recipe ->
            // Update the UI, in this case, a TextView.
            adapter_directions.data = recipe.directions
            adapter_ingredients.data = recipe.ingredients
        }

        viewModel.recipe.observe(this.viewLifecycleOwner,recipeObserver)

        return binding.root
    }

}