package com.vinylsmobile;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.vinylsmobile.view.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class AlbumListTest {

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
    public void testDisplayAlbumList() {

        // Realiza clic en el boton visitante
        onView(withId(R.id.visitButton)).check(matches(isDisplayed()));
        onView(withId(R.id.visitButton)).perform(click());

        //Clic en el botón para avanzar a la lista de álbumes
        onView(withId(R.id.albumForwardButton)).check(matches(isDisplayed()));
        onView(withId(R.id.albumForwardButton)).perform(click());

        // Espera fija para dar tiempo a que se carguen los datos del album
        espera(4000);

        // El recycler view debe estar presente y contener al menos un elemento
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerView)).check(new RecyclerViewItemCountAssertion());

    }

    @Test
    public void testScrollAlbumList() {

        // Realiza clic en el boton visitante
        onView(withId(R.id.visitButton)).check(matches(isDisplayed()));
        onView(withId(R.id.visitButton)).perform(click());

        //Clic en el botón para avanzar a la lista de álbumes
        onView(withId(R.id.albumForwardButton)).check(matches(isDisplayed()));
        onView(withId(R.id.albumForwardButton)).perform(click());

        // Espera fija para dar tiempo a que se carguen los datos del album
        espera(4000);

        // El recycler view debe estar presente y poder hacer scroll
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(10, scrollTo()));


    }

    private void espera(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

