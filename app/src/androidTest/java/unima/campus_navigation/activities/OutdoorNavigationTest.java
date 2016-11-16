package unima.campus_navigation.activities;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import unima.campus_navigation.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class OutdoorNavigationTest {

    @Rule public ActivityTestRule<NavigationDrawerActivity> mActivityTestRule = new ActivityTestRule<>(NavigationDrawerActivity.class);

    @Test
    public void outdoorNavigationTest() {
        ViewInteraction appCompatButton = onView(allOf(withId(R.id.wegweiserButton), withText("GO TO WEGWEISER APP")));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction actionMenuItemView = onView(allOf(withId(R.id.action_search), withContentDescription("Suchen…"), isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction actionMenuItemView2 = onView(allOf(withId(R.id.action_search), withContentDescription("Suchen…"), isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.searchTextView), withParent(withId(R.id.search_top_bar)), isDisplayed()));
        appCompatEditText.perform(replaceText("o14"), closeSoftKeyboard());

        ViewInteraction relativeLayout = onView(allOf(childAtPosition(withId(R.id.suggestion_list), 0), isDisplayed()));
        relativeLayout.perform(click());

        ViewInteraction floatingActionButton = onView(allOf(withId(R.id.indoor_fab), withParent(
                allOf(withId(R.id.activity_main), withParent(withId(R.id.bb_user_content_container)))), isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatButton2 = onView(allOf(withId(android.R.id.button1), withText("OK"),
                                                        withParent(allOf(withId(R.id.buttonPanel), withParent(withId(R.id.parentPanel)))),
                                                        isDisplayed()));
        appCompatButton2.perform(click());

    }

    private static Matcher<View> childAtPosition(final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent) && view.equals(
                        ((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
