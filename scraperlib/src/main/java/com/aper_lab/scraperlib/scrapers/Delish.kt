package com.aper_lab.scraperlib.scrapers

import com.aper_lab.scraperlib.api.RecipeScraper
import com.aper_lab.scraperlib.api.RecipeScraperAnotation
import com.aper_lab.scraperlib.data.Ingredient
import com.aper_lab.scraperlib.data.Recipe
import com.aper_lab.scraperlib.data.RecipeStep
import org.jsoup.Jsoup
import java.nio.charset.Charset

@RecipeScraperAnotation()
class Delish : RecipeScraper{

    override fun scrapFromLink(link: String):Recipe {
        val doc = Jsoup.connect(link).get()
        doc.charset(Charset.forName("UTF-8"))

        val recipe = Recipe();
        recipe.link = link;

        var recipeFragment = doc.select(".site-content");

        recipe.name = recipeFragment.select("h1.recipe-hed").text();
        recipe.time = recipeFragment.select(".total-time-amount").text()
        recipe.yields = recipeFragment.select(".yields .yields-amount").text()
        recipe.image = doc.select("meta[name=og:image]").attr("content")
        recipe.description = "";

        recipe.ingredients = recipeFragment.select(".ingredient-lists .ingredient-item").map {
                element -> Ingredient(element.text(),"");
        }

        recipe.directions = recipeFragment.select(".direction-lists ol").select("li").mapIndexed {
                id, element -> RecipeStep(id+1,element.text());
        }

        return recipe;
    }

}