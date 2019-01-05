package com.example.boeferrob.menuapp

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.hasErrorText
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.runner.AndroidJUnit4
import com.example.boeferrob.menuapp.activities.FoodActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FoodActivityTest {

    @get:Rule
    val foodActivity = IntentsTestRule<FoodActivity>(FoodActivity::class.java)

    @Test
    fun addFoodTest_TitleOnly(){
        onView(withId(R.id.txtTitleFood)).perform(typeText(titleFood))
        onView(withId(R.id.action_save)).perform(click())
    }

    @Test
    fun addFoodTest_DescriptionOnly(){
        testRequiredInput(R.id.txtDescriptionFood, titleFood, R.id.txtTitleFood)
    }

    @Test
    fun addIngredient(){
        for (ingredient in ingredientListFood) {
            onView(withId(R.id.txtQuantity)).perform(typeText(ingredient.quantity.toString()))
/*
            onView(withId(R.id.spinnerMeasurement)).perform(click())
            onData(allOf(`is`(instanceOf(String::class.java)), `is`(ingredient.measurement))).perform(click())
*/
            onView(withId(R.id.txtName)).perform(typeText(ingredient.name))

            onView(withId(R.id.imgAddIngredient)).perform(click())
        }
    }

//help fun
    private fun testRequiredInput(inputToFillId:Int, textToType: String, inputToCheckId: Int){
        onView(withId(inputToFillId)).perform(typeText(textToType)).perform(closeSoftKeyboard())
        onView(withId(R.id.action_save)).perform(click())
        onView(withId(inputToCheckId)).check(matches(hasErrorText(getString(R.string.required))))
}
    private fun getString(resourceId: Int): String {
        return InstrumentationRegistry.getTargetContext().getString(resourceId)
    }

    //vast object aanmaken voor het toevoegen/verwijderen/aanpassen in de recyclerview
    companion object {
        private const val titleFood= "Pizza"
        private const val descriptionFood = "ronde vorm met ingredienten op"
        private val ingredientListFood = listOf<Ingredient>(
            Ingredient("Tomaat",3,"Unit"),
            Ingredient("Peperoni",3,"Unit"))
    }
}