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
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class StartAppTest {

    @Rule public ActivityTestRule<NavigationDrawerActivity> mActivityTestRule = new ActivityTestRule<>(NavigationDrawerActivity.class);

    @Test
    public void startAppTest() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open Drawer"), withParent(withId(R.id.tool_bar)), isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatButton = onView(allOf(withId(R.id.wegweiserButton), withText("GO TO WEGWEISER APP")));
        appCompatButton.perform(scrollTo(), click());

    }

}
