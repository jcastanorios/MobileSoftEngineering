package com.vinylsmobile;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.vinylsmobile.R;
import com.vinylsmobile.view.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CreateAlbumTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);
/*
    @Test
    public void testCreateAlbum() {
        // Hacer clic en el botón "Agregar Álbum"
        Espresso.onView(ViewMatchers.withId(R.id.create_album_button))
                .perform(ViewActions.click());

        // Verificar que se muestra el formulario de creación de álbum
        Espresso.onView(ViewMatchers.withId(R.id.main))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Ingresar los datos del álbum utilizando replaceText()
        Espresso.onView(ViewMatchers.withId(R.id.txt_post_name))
                .perform(ViewActions.replaceText("Test Nuevo Álbum"));
        Espresso.onView(ViewMatchers.withId(R.id.txt_post_cover_url))
                .perform(ViewActions.replaceText("https://upload.wikimedia.org/wikipedia/commons/thumb/9/9c/El_lado_oscuro_de_la_luna_-_The_dark_side_of_the_moon.jpg"));
        Espresso.onView(ViewMatchers.withId(R.id.txt_post_date))
                .perform(ViewActions.replaceText("2023-06-08"));
        Espresso.onView(ViewMatchers.withId(R.id.txt_post_description))
                .perform(ViewActions.replaceText("Descripción del álbum"));
        Espresso.onView(ViewMatchers.withId(R.id.txt_post_genre))
                .perform(ViewActions.replaceText("Rock"));
        Espresso.onView(ViewMatchers.withId(R.id.txt_album_create_record_label))
                .perform(ViewActions.replaceText("Sony Music"));

        // Hacer clic en el botón "Crear Álbum"
        Espresso.onView(ViewMatchers.withId(R.id.create_button))
                .perform(ViewActions.click());

        // Verificar que se muestra el mensaje de éxito
        Espresso.onView(ViewMatchers.withText("Éxito"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testErrorOnCreateAlbum() {
        // Hacer clic en el botón "Agregar Álbum"
        Espresso.onView(ViewMatchers.withId(R.id.create_album_button))
                .perform(ViewActions.click());

        // Verificar que se muestra el formulario de creación de álbum
        Espresso.onView(ViewMatchers.withId(R.id.main))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Ingresar los datos del álbum utilizando replaceText() con discográfica inválida
        Espresso.onView(ViewMatchers.withId(R.id.txt_post_name))
                .perform(ViewActions.replaceText("Nuevo Álbum"));
        Espresso.onView(ViewMatchers.withId(R.id.txt_post_cover_url))
                .perform(ViewActions.replaceText("https://example.com/cover.jpg"));
        Espresso.onView(ViewMatchers.withId(R.id.txt_post_date))
                .perform(ViewActions.replaceText("2023-06-08"));
        Espresso.onView(ViewMatchers.withId(R.id.txt_post_description))
                .perform(ViewActions.replaceText("Descripción del álbum"));
        Espresso.onView(ViewMatchers.withId(R.id.txt_post_genre))
                .perform(ViewActions.replaceText("Rock"));
        Espresso.onView(ViewMatchers.withId(R.id.txt_album_create_record_label))
                .perform(ViewActions.replaceText("Sello discográfico no permitido"));

        // Hacer clic en el botón "Crear Álbum"
        Espresso.onView(ViewMatchers.withId(R.id.create_button))
                .perform(ViewActions.click());

        // Verificar que se muestra el diálogo de error con el mensaje esperado
        Espresso.onView(ViewMatchers.withText("Discográfica no permitida, deben ser: [Sony Music, EMI, Discos Fuentes, Elektra, Fania Records]\n(Selecciona por favor una discográfica válida)"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }*/
}