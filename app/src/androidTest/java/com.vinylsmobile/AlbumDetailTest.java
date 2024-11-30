package com.vinylsmobile;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.vinylsmobile.view.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static org.hamcrest.Matchers.anyOf;

@RunWith(AndroidJUnit4.class)
public class AlbumDetailTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void testDisplayAlbumDetailFromCollection() {

        // Realiza clic en el boton visitante
        onView(withId(R.id.visitButton)).check(matches(isDisplayed()));
        onView(withId(R.id.visitButton)).perform(click());

        // Espera fija para dar tiempo a que se carguen los datos del album
        espera(3000);
        // Clic en el primer álbum del RecyclerView
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, click()));

        //Espera a que se carguen los datos del album para el detalle
        espera(2000);

        //Valida que todos los elementeos del detalle del album esten presentes
        onView(withId(R.id.albumCover)).check(matches(isDisplayed()));
        onView(withId(R.id.albumName)).check(matches(isDisplayed()));
        onView(withId(R.id.albumDescription)).check(matches(isDisplayed()));
        onView(withId(R.id.albumYear)).check(matches(isDisplayed()));
        onView(withId(R.id.albumLabel)).check(matches(isDisplayed()));
        onView(withId(R.id.albumGenre)).check(matches(isDisplayed()));

        // Valida que el campo Género contiene una de las opciones permitidas
        onView(withId(R.id.albumGenre))
                .check(matches(anyOf(
                        withText("Classical"),
                        withText("Salsa"),
                        withText("Rock"),
                        withText("Folk")
                )));

        //Valida que el campo Disquera contiene una de las opciones permitidas
        onView(withId(R.id.albumLabel))
                .check(matches(anyOf(
                        withText("Sony Music"),
                        withText("Discos Fuentes"),
                        withText("Elektra"),
                        withText("Fania Records"),
                        withText("EMI")
                )));


    }

    @Test
    public void testDisplayAlbumDetailFromAlbumList() {

        // Realiza clic en el boton visitante
        onView(withId(R.id.visitButton)).check(matches(isDisplayed()));
        onView(withId(R.id.visitButton)).perform(click());

        //Clic en el botón para avanzar a la lista de álbumes
        onView(withId(R.id.albumForwardButton)).check(matches(isDisplayed()));
        onView(withId(R.id.albumForwardButton)).perform(click());

        // Espera fija para dar tiempo a que se carguen los datos del album
        espera(3000);
        // Clic en el primer álbum del RecyclerView
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, click()));

        //Espera a que se carguen los datos del album para el detalle
        espera(2000);

        //Valida que todos los elementeos del detalle del album esten presentes
        onView(withId(R.id.albumCover)).check(matches(isDisplayed()));
        onView(withId(R.id.albumName)).check(matches(isDisplayed()));
        onView(withId(R.id.albumDescription)).check(matches(isDisplayed()));
        onView(withId(R.id.albumYear)).check(matches(isDisplayed()));
        onView(withId(R.id.albumLabel)).check(matches(isDisplayed()));
        onView(withId(R.id.albumGenre)).check(matches(isDisplayed()));

        //Valida que el campo Género contiene una de las opciones permitidas
        onView(withId(R.id.albumGenre))
                .check(matches(anyOf(
                        withText("Classical"),
                        withText("Salsa"),
                        withText("Rock"),
                        withText("Folk")
                )));

        // Valida que el campo Disquera contiene una de las opciones permitidas
        onView(withId(R.id.albumLabel))
                .check(matches(anyOf(
                        withText("Sony Music"),
                        withText("Discos Fuentes"),
                        withText("Elektra"),
                        withText("Fania Records"),
                        withText("EMI")
                )));

    }


    private void espera(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
