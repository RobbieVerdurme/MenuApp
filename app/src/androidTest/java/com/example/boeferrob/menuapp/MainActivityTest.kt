package com.example.boeferrob.menuapp

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.*
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.boeferrob.menuapp.activities.MainActivity
import com.example.boeferrob.menuapp.fragments.Adapter.FoodRecyclerAdapter
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val mainActivity = IntentsTestRule<MainActivity>(MainActivity::class.java)

    /////test Decide fragment

    @Test
    fun launchTest(){
        onView(withId(R.id.btnDecide)).check(matches(isDisplayed()))
        onView(withId(R.id.lblMenu)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation)).check(matches(isDisplayed()))
    }

    @Test
    fun changeRandomFood(){
        onView(withId(R.id.btnDecide)).perform(click())
    }

    @Test
    fun goToFood(){
        onView(withId(R.id.lblMenu)).perform(click())
    }

    ////test FoodListFragment
    @Test
    fun lanchTestFoodListFragment(){
        onView(withId(R.id.navigation_recipeList)).perform(click())

        onView(withId(R.id.txtFilter)).check(matches(isDisplayed()))
        onView(withId(R.id.listFood)).check(matches(isDisplayed()))
        onView(withId(R.id.fab)).check(matches(isDisplayed()))
    }

    @Test
    fun addFoodTest(){
        onView(withId(R.id.navigation_recipeList)).perform(click())
        onView(withId(R.id.fab)).perform(click())

        onView(withId(R.id.txtTitleFood)).perform(typeText(titleFood)).perform(closeSoftKeyboard())
        onView(withId(R.id.txtDescriptionFood)).perform(typeText(descriptionFood)).perform(closeSoftKeyboard())
        for (ingredient in ingredientListFood) {
            onView(withId(R.id.txtQuantity)).perform(typeText(ingredient.quantity.toString()))
/*
            onView(withId(R.id.spinnerMeasurement)).perform(click())
            onData(allOf(`is`(instanceOf(String::class.java)), `is`(ingredient.measurement))).perform(click())
*/
            onView(withId(R.id.txtName)).perform(typeText(ingredient.name))

            onView(withId(R.id.imgAddIngredient)).perform(click())
        }

        onView(withId(R.id.action_save)).perform(click())

        onView(withId(R.id.navigation_recipeList)).perform(click())
        onView(withId(R.id.listFood)).perform(RecyclerViewActions.scrollToHolder(withHolderPersonView(titleFood)))
    }

    @Test
    fun editFoodtest() {
        onView(withId(R.id.navigation_recipeList)).perform(click())
        onView(withId(R.id.listFood)).perform(RecyclerViewActions.actionOnItemAtPosition<FoodRecyclerAdapter.ViewHolder>(0, click()))
        onView(withId(R.id.txtTitleFood))
            .perform(clearText())
            .perform(typeText("testing"))
            .perform(closeSoftKeyboard())
        onView(withId(R.id.action_save)).perform(click())
        onView(withId(R.id.navigation_recipeList)).perform(click())
        onView(withId(R.id.listFood)).perform(RecyclerViewActions.scrollToHolder(withHolderPersonView("testing")))
    }

    @Test
    fun removeIngredient(){
        onView(withId(R.id.navigation_recipeList)).perform(click())
        onView(withId(R.id.listFood)).perform(RecyclerViewActions.actionOnItemAtPosition<FoodRecyclerAdapter.ViewHolder>(0, click()))
        onView(withId(R.id.txtTitleFood)).perform(closeSoftKeyboard())
        onView(withId(R.id.listFoodIngredients)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, swipeLeft()))
        onView(withId(R.id.listFoodIngredients)).check(matches(CoreMatchers.not(atPosition(0, withText("Tomaat")))))
    }

    @Test
    fun removeFoodTest() {
        onView(withId(R.id.navigation_recipeList)).perform(click())
        onView(withId(R.id.listFood)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, swipeLeft()))
        onView(withId(R.id.listFood)).check(matches(CoreMatchers.not(atPosition(0, withText("testing")))))
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

fun withHolderPersonView(text: String): Matcher<RecyclerView.ViewHolder> {
    return object : BoundedMatcher<RecyclerView.ViewHolder, FoodRecyclerAdapter.ViewHolder>(FoodRecyclerAdapter.ViewHolder::class.java) {

        override fun describeTo(description: Description) {
            description.appendText("ViewHolder found with text: $text")
        }

        override fun matchesSafely(item: FoodRecyclerAdapter.ViewHolder): Boolean {
            val nameViewText = item.itemView.findViewById<TextView>(R.id.txtTitleFood)
            return nameViewText != null && nameViewText.text
                .toString().contains(text)
        }
    }
}

fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
    checkNotNull(itemMatcher)
    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has item at position $position: ")
            itemMatcher.describeTo(description)
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            val viewHolder = view.findViewHolderForAdapterPosition(position) ?:
            return false // has no item on such position
            return itemMatcher.matches(viewHolder.itemView)
        }
    }
}