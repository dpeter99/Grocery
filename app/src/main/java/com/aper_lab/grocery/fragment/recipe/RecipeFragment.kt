package com.aper_lab.grocery.fragment.recipe

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aper_lab.grocery.FABFragment
import com.aper_lab.grocery.IFABProvider
import com.aper_lab.grocery.R
import com.aper_lab.grocery.databinding.FragmentRecepieBinding
import com.aper_lab.grocery.fragment.recipe.recipeMenu.RecipeMenuFragment
import com.aper_lab.scraperlib.data.Ingredient
import com.aper_lab.scraperlib.data.RecipeStep
import com.google.android.material.bottomsheet.BottomSheetBehavior


/**
 * A simple [Fragment] subclass.
 */
class RecipeFragment : FABFragment() {

    private lateinit var viewModel: RecipeViewModel;
    private lateinit var viewModelFactory: RecipeViewModelFactory;

    private lateinit var binding: FragmentRecepieBinding;
    private lateinit var recipeID: String;

    private lateinit var adapter_ingredients : RecipeIngredientAdapter;
    private lateinit var adapter_steps : RecipeDirectionsAdapter;

    private lateinit var bottom_sheet_behav: BottomSheetBehavior<CardView?>;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentRecepieBinding>(
            inflater,
            R.layout.fragment_recepie, container, false
        )
        binding.lifecycleOwner = this;

        setHasOptionsMenu(true);

        recipeID = RecipeFragmentArgs.fromBundle(
            arguments!!
        ).recipeID

        //viewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java);
        //viewModelFactory = RecipeViewModelFactory(args.recipeID)
        //viewModel = ViewModelProvider(this, viewModelFactory).get(RecipeViewModel::class.java);

        adapter_ingredients =
            RecipeIngredientAdapter();
        binding.ingredientList.adapter = adapter_ingredients;

        adapter_steps =
            RecipeDirectionsAdapter();
        binding.stepsList.adapter = adapter_steps;

        //bottom_sheet_behav = BottomSheetBehavior.from(binding.menuBottomSheet);
        //bottom_sheet_behav.state = BottomSheetBehavior.STATE_HIDDEN;

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            RecipeViewModelFactory(recipeID)
        ).get(RecipeViewModel::class.java);
        binding.viewModel = viewModel;

        adapter_ingredients.data = viewModel.recipe.value?.recipe?.ingredients ?: listOf(Ingredient("", ""));
        adapter_steps.data = viewModel.recipe.value?.recipe?.directions ?: listOf(RecipeStep(0, ""));

        viewModel.recipe.observe(this.viewLifecycleOwner, Observer { recipe ->
            // Update the UI, in this case, a TextView.
            adapter_steps.data = recipe.recipe.directions
            adapter_ingredients.data = recipe.recipe.ingredients
        })

        binding.favCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.setRecipeFavorite(isChecked);
        }

        binding.recipeSourceLink.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.setData(Uri.parse(viewModel.recipe.value?.recipe?.link?:""));
            startActivity(i);
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_recipe,menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.recipe_menu -> {
                val bottomSheet = RecipeMenuFragment()
                bottomSheet.show(childFragmentManager, "exampleBottomSheet")
                //bottom_sheet_behav.state = BottomSheetBehavior.STATE_EXPANDED;
                super.onOptionsItemSelected(item)
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is IFABProvider) {
            context.setFABProperties(null);
            context.setFABListener(this);
        }
    }

}