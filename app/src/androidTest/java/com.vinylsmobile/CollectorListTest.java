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
public class CollectorListTest {
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
    public void testDisplayCollectorList() {

        onView(withId(R.id.visitButton)).check(matches(isDisplayed()));
        onView(withId(R.id.visitButton)).perform(click());

        onView(withId(R.id.collectorListButton)).check(matches(isDisplayed()));
        onView(withId(R.id.collectorListButton)).perform(click());

        espera(4000);

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerView)).check(new RecyclerViewItemCountAssertion());

    }

    private void espera(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

