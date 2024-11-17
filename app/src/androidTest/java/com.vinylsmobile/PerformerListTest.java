package com.vinylsmobile;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertTrue;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.vinylsmobile.view.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CompletableFuture;


@RunWith(AndroidJUnit4.class)
public class PerformerListTest {
    // Regla que inicia MainActivity antes de cada prueba para que esté disponible
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);


    public static class RecyclerViewItemCountAssertion implements ViewAssertion {
        private final int minItemCount;

        public RecyclerViewItemCountAssertion() {
            this.minItemCount = 1;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            assertTrue(adapter.getItemCount() >= minItemCount);
        }
    }


    @Test
    public void testDisplayPerformerList() {

        // Verifica que el botón "visitante" esté visible y luego realiza clic en él
        onView(withId(R.id.visitButton)).check(matches(isDisplayed()));
        onView(withId(R.id.visitButton)).perform(click());

        // Clic en el botón para avanzar a la lista de artistas
        onView(withId(R.id.performerForwardButton)).check(matches(isDisplayed()));
        onView(withId(R.id.performerForwardButton)).perform(click());

        // Espera fija para dar tiempo a que se carguen los datos del artista
        espera(4000);

        // El recycler view debe estar presente y contener al menos un elemento
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerView)).check(new RecyclerViewItemCountAssertion());

    }

    @Test
    public void testViewMorePerformerList() {
        // Verifica que el botón "visitante" esté visible y realiza clic
        onView(withId(R.id.visitButton))
                .check(matches(isDisplayed()))
                .perform(click());

        espera(4000);

        // Realiza scroll horizontal en el RecyclerView para encontrar el botón "Ver más"
        onView(withId(R.id.recyclerViewArtist))
                .perform(actionOnItemAtPosition(2, scrollTo()));

        espera(3000);

        // Clic en el botón que contiene el texto "Ver más álbumes"
        onView(allOf(withId(R.id.viewMoreButton), hasDescendant(withText("Ver más artistas"))))
                .check(matches(isDisplayed()))
                .perform(click());

        // Espera para cargar los datos
        espera(4000);

        // Verifica que el RecyclerView de la nueva vista esté visible y tenga elementos
        onView(withId(R.id.recyclerView))
                .check(matches(isDisplayed()))
                .check(new RecyclerViewItemCountAssertion());
    }

    private void espera(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

