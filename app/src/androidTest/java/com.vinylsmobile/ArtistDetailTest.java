package com.vinylsmobile;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.allOf;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.vinylsmobile.view.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ArtistDetailTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void testDisplayArtistDetailFromCollection() {

        // Realiza clic en el boton visitante
        onView(withId(R.id.visitButton)).check(matches(isDisplayed()));
        onView(withId(R.id.visitButton)).perform(click());

        // Espera fija para dar tiempo a que se carguen los datos del artist
        espera(4000);
        // Clic en el primer artist del RecyclerView
        onView(withId(R.id.recyclerViewArtist)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerViewArtist)).perform(actionOnItemAtPosition(0, click()));

        //Espera a que se carguen los datos del artist para el detalle
        espera(1000);

        //Valida que todos los elementeos del detalle del artist esten presentes
        onView(withId(R.id.performerImageDetail)).check(matches(isDisplayed()));
        onView(withId(R.id.performerNameDetail)).check(matches(isDisplayed()));
        onView(withId(R.id.performerDescription)).check(matches(isDisplayed()));
        // Valida que performerFecNaci ó (performerDay, performerMonth, and performerYear) del artista estén present
        onView(anyOf(
                withId(R.id.performerFecNaci),
                allOf(
                        withId(R.id.performerDay),
                        withId(R.id.performerMonth),
                        withId(R.id.performerYear)
                )
        )).check(matches(isDisplayed()));

    }

    @Test
    public void testDisplayArtistDetailFromArtistList() {

        // Realiza clic en el boton visitante
        onView(withId(R.id.visitButton)).check(matches(isDisplayed()));
        onView(withId(R.id.visitButton)).perform(click());

        //Clic en el botón para avanzar a la lista de artistas
        onView(withId(R.id.performerListButton)).check(matches(isDisplayed()));
        onView(withId(R.id.performerListButton)).perform(click());

        // Espera fija para dar tiempo a que se carguen los datos del artist
        espera(4000);
        // Clic en el primer artista del RecyclerView
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, click()));

        //Espera a que se carguen los datos del artist para el detalle
        espera(1000);

        //Valida que todos los elementeos del detalle del artist esten presentes
        onView(withId(R.id.performerImageDetail)).check(matches(isDisplayed()));
        onView(withId(R.id.performerNameDetail)).check(matches(isDisplayed()));
        onView(withId(R.id.performerDescription)).check(matches(isDisplayed()));
        // Valida que performerFecNaci ó (performerDay, performerMonth, and performerYear) del artista estén present
        onView(anyOf(
                withId(R.id.performerFecNaci),
                allOf(
                        withId(R.id.performerDay),
                        withId(R.id.performerMonth),
                        withId(R.id.performerYear)
                )
        )).check(matches(isDisplayed()));

    }


    private void espera(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
