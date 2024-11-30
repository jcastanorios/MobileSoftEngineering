package com.vinylsmobile;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.vinylsmobile.view.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.widget.TextView;


@RunWith(AndroidJUnit4.class)
public class CollectorDetailTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void testDisplayArtistDetailFromCollection() {

        // Realiza clic en el boton visitante
        onView(withId(R.id.visitButton)).check(matches(isDisplayed()));
        onView(withId(R.id.visitButton)).perform(click());

        // Espera fija para dar tiempo a que se carguen los datos del coleccionista
        espera(3000);
        // Clic en el primer artist del RecyclerView
        onView(withId(R.id.recyclerViewCollector)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerViewCollector)).perform(actionOnItemAtPosition(0, click()));

        //Espera a que se carguen los datos del artist para el detalle
        espera(2000);

        //Valida que todos los elementeos del detalle del Coleccionsta esten presentes
        onView(withId(R.id.collectorCover)).check(matches(isDisplayed()));
        onView(withId(R.id.collectorName)).check(matches(isDisplayed()));
        onView(withId(R.id.collectorTelephone)).check(matches(isDisplayed()));
        onView(withId(R.id.collectorEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.collectorAlbumsCount)).check(matches(isDisplayed()));

        // Valida que el nombre no sea una cadena vacía
        onView(withId(R.id.collectorName)).check((view, noViewFoundException) -> {
            if (noViewFoundException != null) throw noViewFoundException;
            TextView textView = (TextView) view;
            String text = textView.getText().toString();
            if (text.isEmpty()) {
                throw new AssertionError("El nombre del coleccionista está vacío");
            }
        });

        // Valida que el telefono no este vacio
        // No se pude validar que sea un número de teléfono válido porque no hay función de crear un coleccionista
        onView(withId(R.id.collectorTelephone)).check((view, noViewFoundException) -> {
            if (noViewFoundException != null) throw noViewFoundException;
            TextView textView = (TextView) view;
            String text = textView.getText().toString();
            if (text.isEmpty()) {
                throw new AssertionError("El nombre del coleccionista está vacío");
            }
        });

        // Valida que el Correo no este vacio
        // No se pude validar que sea un correo válido porque no hay función de crear un coleccionista
        onView(withId(R.id.collectorEmail)).check((view, noViewFoundException) -> {
            if (noViewFoundException != null) throw noViewFoundException;
            TextView textView = (TextView) view;
            String text = textView.getText().toString();
            if (text.isEmpty()) {
                throw new AssertionError("El nombre del coleccionista está vacío");
            }
        });


    }


    @Test
    public void testDisplayCollectorDetailFromCollectorList() {

        // Realiza clic en el boton visitante
        onView(withId(R.id.visitButton)).check(matches(isDisplayed()));
        onView(withId(R.id.visitButton)).perform(click());

        //Clic en el botón para avanzar a la lista de coleccionistas
        onView(withId(R.id.collectorListButton)).check(matches(isDisplayed()));
        onView(withId(R.id.collectorListButton)).perform(click());

        // Espera fija para dar tiempo a que se carguen los datos del artist
        espera(3000);
        // Clic en el primer artista del RecyclerView
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, click()));

        //Espera a que se carguen los datos del artist para el detalle
        espera(2000);

        //Valida que todos los elementeos del detalle del Coleccionsta esten presentes
        onView(withId(R.id.collectorCover)).check(matches(isDisplayed()));
        onView(withId(R.id.collectorName)).check(matches(isDisplayed()));
        onView(withId(R.id.collectorTelephone)).check(matches(isDisplayed()));
        onView(withId(R.id.collectorEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.collectorAlbumsCount)).check(matches(isDisplayed()));

        // Valida que el nombre no sea una cadena vacía
        onView(withId(R.id.collectorName)).check((view, noViewFoundException) -> {
            if (noViewFoundException != null) throw noViewFoundException;
            TextView textView = (TextView) view;
            String text = textView.getText().toString();
            if (text.isEmpty()) {
                throw new AssertionError("El nombre del coleccionista está vacío");
            }
        });

        // Valida que el telefono no este vacio
        // No se pude validar que sea un número de teléfono válido porque no hay función de crear un coleccionista
        onView(withId(R.id.collectorTelephone)).check((view, noViewFoundException) -> {
            if (noViewFoundException != null) throw noViewFoundException;
            TextView textView = (TextView) view;
            String text = textView.getText().toString();
            if (text.isEmpty()) {
                throw new AssertionError("El nombre del coleccionista está vacío");
            }
        });

        // Valida que el Correo no este vacio
        // No se pude validar que sea un correo válido porque no hay función de crear un coleccionista
        onView(withId(R.id.collectorEmail)).check((view, noViewFoundException) -> {
            if (noViewFoundException != null) throw noViewFoundException;
            TextView textView = (TextView) view;
            String text = textView.getText().toString();
            if (text.isEmpty()) {
                throw new AssertionError("El nombre del coleccionista está vacío");
            }
        });

    }


    private void espera(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
