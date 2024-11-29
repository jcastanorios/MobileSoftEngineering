package com.vinylsmobile;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

import android.widget.DatePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.vinylsmobile.view.MainActivity;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class CreateAlbumTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    private void goToForm(Boolean fromDetail) {
        // Realiza clic en el boton visitante
        onView(withId(R.id.collectButton)).check(matches(isDisplayed()));
        onView(withId(R.id.collectButton)).perform(click());

        if (fromDetail) {
            //Clic en el botón para avanzar a la lista de álbumes
            onView(withId(R.id.albumListButton)).check(matches(isDisplayed()));
            onView(withId(R.id.albumListButton)).perform(click());

            // Espera fija para dar tiempo a que se carguen los datos del album
            espera(4000);
        }
        // Clic en el floating action button
        onView(withId(R.id.fab_collection_activity)).check(matches(isDisplayed()));
        onView(withId(R.id.fab_collection_activity)).perform(click());
        espera(1000);

        // Clic en el boton para ir al formulario de creación de álbum
        onView(withText("Crear Album")).inRoot(isPlatformPopup()).perform(click());


        //Espera a que se cargue el formulario
        espera(1000);

        //Valida que todos los elementeos del formulario esten presentes
        onView(withId(R.id.etCover)).check(matches(isDisplayed()));
        onView(withId(R.id.etName)).check(matches(isDisplayed()));
        onView(withId(R.id.btnReleaseDate)).check(matches(isDisplayed()));
        onView(withId(R.id.etGenre)).check(matches(isDisplayed()));
        onView(withId(R.id.etRecordLabel)).check(matches(isDisplayed()));
        onView(withId(R.id.etDescription)).check(matches(isDisplayed()));

    }

    @Test
    public void testFABNotDisplayedIfVisitor() {
        // Realiza clic en el boton visitante
        onView(withId(R.id.visitButton)).check(matches(isDisplayed()));
        onView(withId(R.id.visitButton)).perform(click());

        // Valida que el FAB no se muestre
        onView(withId(R.id.fab_collection_activity)).check(matches(not(isDisplayed())));
    }

    @Test
    public void testDisplayCreateAlbumFormFromCollection() {
        goToForm(false);
    }

    @Test
    public void testDisplayCreateAlbumFormFromAlbumList() {
        goToForm(true);
    }

    @Test
    public void testValidateRequiredFields() {
        goToForm(false);

        onView(withId(R.id.btnSave)).check(matches(isDisplayed()));
        onView(withId(R.id.btnSave)).perform(click());
        onView(withText("Invalid cover URL")).check(matches(isDisplayed()));

        onView(withId(R.id.etCover)).perform(click());
        onView(withId(R.id.etCover)).perform(replaceText("https://example.com/cover.jpg"));
        onView(withId(R.id.btnSave)).perform(click());
        onView(withText("Name is required")).check(matches(isDisplayed()));

        onView(withId(R.id.etName)).perform(click());
        onView(withId(R.id.etName)).perform(replaceText("Test album"));
        onView(withId(R.id.btnSave)).perform(click());
        onView(withText("Release date is required")).check(matches(isDisplayed()));
    }

    @Test
    public void testValidURLForCover() {
        goToForm(false);

        onView(withId(R.id.etCover)).perform(click());
        onView(withId(R.id.etCover)).perform(replaceText("https://example"));
        onView(withId(R.id.btnSave)).perform(click());
        onView(withText("Invalid cover URL")).check(matches(isDisplayed()));
    }


    private void espera(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
