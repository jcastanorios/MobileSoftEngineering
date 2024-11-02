package com.vinylsmobile;


import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.vinylsmobile.view.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class AlbumDetailTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testDisplayAlbumDetail() {
        // Id Button Visit en MainActivity
        ViewInteraction visitButton = onView(withId(R.id.visitButton));
        visitButton.check(matches(isDisplayed()));

        // clic en el coton visitante
        visitButton.perform(click());

        //Click en el primer album
        onView(withId(R.id.recyclerView))
                .perform(click());


        // Verificacion que esta presente el elemento albumGenre
        onView(withId(R.id.albumGenre))
                .check(matches(isDisplayed()));

        // Verificacion que esta presente el elemento albumCover
        onView(withId(R.id.albumCover))
                .check(matches(isDisplayed()));

        // Verificacion que esta presente el elemento albumName
        onView(withId(R.id.albumName))
                .check(matches(isDisplayed()));

        // Verificacion que esta presente el elemento albumDescription
        onView(withId(R.id.albumDescription))
                .check(matches(isDisplayed()));

    }
}


