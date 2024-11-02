package com.vinylsmobile;


import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.vinylsmobile.view.AlbumDetailActivity;
import com.vinylsmobile.view.AlbumListFragment;
import com.vinylsmobile.view.CollectionActivity;
import com.vinylsmobile.view.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
//import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static org.hamcrest.CoreMatchers.allOf;

@RunWith(AndroidJUnit4.class)
public class AlbumDetailTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);
    @Rule
    public ActivityScenarioRule<CollectionActivity> mCollectionTestRule = new ActivityScenarioRule<>(CollectionActivity.class);//
    //@Rule
    public ActivityScenarioRule<AlbumDetailActivity> mAlbumDetailActivityTestRule = new ActivityScenarioRule<>(AlbumDetailActivity.class);//

    @Test
    public void testDisplayAlbumDetailFromCollection() {

        // Id Button Visit en MainActivity
        ViewInteraction visitButton = onView(withId(R.id.visitButton));
        visitButton.check(matches(isDisplayed()));

        // clic en el boton visitante
        visitButton.perform(click());

        //Click en el primer album
        //int albumCard = R.id.albumCard;
        //onView(withId(R.id.albumCard))
        //        .perform(click());
        //onView(allOf(withId(R.id.albumCard), isDisplayed())).perform(click());
        // Click en el primer album
        onView(withId(R.id.recyclerView))
                .perform(actionOnItemAtPosition(0, click()));

        //onView(withId(R.id.recyclerView))
        //        .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));



        // Verificacion que esta presente el elemento albumCover
        onView(withId(R.id.albumCover))
                .check(matches(isDisplayed()));

        // Verificacion que esta presente el elemento albumName
        onView(withId(R.id.albumName))
                .check(matches(isDisplayed()));

        // Verificacion que esta presente el elemento albumDescription
        onView(withId(R.id.albumDescription))
                .check(matches(isDisplayed()));


        // Verificacion que esta presente el elemento albumName
        onView(withId(R.id.albumYear))
                .check(matches(isDisplayed()));

        // Verificacion que esta presente el elemento albumDescription
        onView(withId(R.id.albumLabel))
                .check(matches(isDisplayed()));

        // Verificacion que esta presente el elemento albumGenre
        onView(withId(R.id.albumGenre))
                .check(matches(isDisplayed()));

    }

    @Test
    public void testDisplayAlbumDetailFromAlbumList() {
        // Id Button Visit en MainActivity
        ViewInteraction visitButton = onView(withId(R.id.visitButton));
        visitButton.check(matches(isDisplayed()));

        // clic en el boton visitante
        visitButton.perform(click());

        ViewInteraction albumForwardButton = onView(withId(R.id.albumForwardButton));
        albumForwardButton.perform(click());


        //Click en el primer album
        //onView(withId(R.id.recyclerView))
        //        .perform(click());
        //onView(withId(R.id.recyclerView))
        //        .perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recyclerView))
                .perform(actionOnItemAtPosition(0, click()));
        //onView(allOf(withId(R.id.albumCard), isDisplayed())).perform(click());


        // Verificacion que esta presente el elemento albumCover
        onView(withId(R.id.albumCover))
                .check(matches(isDisplayed()));

        // Verificacion que esta presente el elemento albumName
        onView(withId(R.id.albumName))
                .check(matches(isDisplayed()));

        // Verificacion que esta presente el elemento albumDescription
        onView(withId(R.id.albumDescription))
                .check(matches(isDisplayed()));

        // Verificacion que esta presente el elemento albumName
        onView(withId(R.id.albumYear))
                .check(matches(isDisplayed()));

        // Verificacion que esta presente el elemento albumDescription
        onView(withId(R.id.albumLabel))
                .check(matches(isDisplayed()));

        // Verificacion que esta presente el elemento albumGenre
        onView(withId(R.id.albumGenre))
                .check(matches(isDisplayed()));

    }

}




