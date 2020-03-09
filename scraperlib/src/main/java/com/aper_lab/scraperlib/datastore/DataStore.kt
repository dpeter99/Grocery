package com.aper_lab.scraperlib.datastore

import com.aper_lab.scraperlib.data.Producte
import com.aper_lab.scraperlib.data.Recipe
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
//import com.google.api.client.json.gson.GsonFactory
//import com.google.api.services.kgsearch.v1.Kgsearch
//import com.google.api.services.kgsearch.v1.KgsearchRequest

class DataStore {

    var recipes = mutableListOf<Recipe>();
    var ingredients = mutableListOf<Producte>()

    fun getIngredientByName(name: String){
        this.ingredients.find {
            ingredient -> ingredient.isAcceptableName(name)
        }

        /*
        var kgsearch = Kgsearch.Builder(
            GoogleNetHttpTransport.newTrustedTransport(),
            GsonFactory(),
            null
        ).build()

        kgsearch.Entities().search()

         */

        println("ERROR: No suitable ingridient")
    }
}