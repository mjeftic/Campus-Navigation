package unima.campus_navigation.activities;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import unima.campus_navigation.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class IndoorNavigationTest {

    @Rule public ActivityTestRule<NavigationDrawerActivity> mActivityTestRule = new ActivityTestRule<>(NavigationDrawerActivity.class);

    @Test
    public void indoorNavigationTest() {
        ViewInteraction appCompatButton = onView(allOf(withId(R.id.wegweiserButton), withText("GO TO WEGWEISER APP")));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction linearLayout = onView(allOf(withId(R.id.favorite_item), withParent(
                allOf(withId(R.id.bb_bottom_bar_item_container), withParent(withId(R.id.bb_bottom_bar_outer_container)))), isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction appCompatSpinner = onView(allOf(withId(R.id.indoorspinner), isDisplayed()));
        appCompatSpinner.perform(click());

        ViewInteraction appCompatCheckedTextView = onView(allOf(withId(android.R.id.text1), withText("Röchling Hörsaal"), isDisplayed()));
        appCompatCheckedTextView.perform(click());

    }

}
