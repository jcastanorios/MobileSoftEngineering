package com.vinylsmobile;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.View;
import android.widget.RatingBar;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.vinylsmobile.view.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CommentAlbumTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testCommentAlbumMain() {
        onView(withId(R.id.collectButton)).perform(click());
        espera(4000);

        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, click()));
        espera(1000);

        onView(withId(R.id.commentAlbumContainer)).perform(click());

        onView(withId(R.id.albumRating)).perform(setRating(4));

        commentAlbum();

        onView(withId(R.id.albumDetailBackButton)).perform(click());
        espera(1000);

        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, click()));

        espera(1000);
        onView(withId(R.id.commentsRecyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void testCommentAlbumList() {
        onView(withId(R.id.collectButton)).check(matches(isDisplayed()));
        onView(withId(R.id.collectButton)).perform(click());

        onView(withId(R.id.albumForwardButton)).check(matches(isDisplayed()));
        onView(withId(R.id.albumForwardButton)).perform(click());

        espera(4000);

        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, click()));

        espera(1000);

        onView(withId(R.id.commentAlbumContainer)).perform(click());

        onView(withId(R.id.albumRating)).perform(setRating(5));

        commentAlbum();
        onView(withId(R.id.albumDetailBackButton)).perform(click());
        espera(1000);

        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, click()));

        espera(1000);
        onView(withId(R.id.commentsRecyclerView)).check(matches(isDisplayed()));
    }

    private ViewAction setRating(final float rating) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(RatingBar.class);
            }

            @Override
            public String getDescription() {
                return "Set RatingBar rating to " + rating;
            }

            @Override
            public void perform(UiController uiController, View view) {
                RatingBar ratingBar = (RatingBar) view;
                ratingBar.setRating(rating);
            }
        };
    }

    private void commentAlbum(){
        onView(withId(R.id.commentDescription)).perform(replaceText("Este álbum es genial!"));
        espera(1000);

        onView(withId(R.id.saveButton)).perform(click());
    }


    private void espera(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
