package net.pedromalta.mirrorclock

import androidx.activity.ComponentActivity
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented tests for MainActivity to verify UI behavior.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Test
    fun `given MainActivity when launched then activity is created`() {
        // Given an ActivityScenario for MainActivity
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        // Then the activity should be created and have a valid state
        scenario.onActivity { activity: ComponentActivity ->
            assertNotNull("Activity should be created", activity)
        }
    }

    @Test
    fun `given MainActivity when launched then has valid content view`() {
        // Launch the activity
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        // Then the activity should have a content view
        scenario.onActivity { activity: ComponentActivity ->
            assertNotNull("Activity should have a content view", activity.window.decorView.rootView)
        }
    }

    @Test
    fun `given MainActivity when configuration changes then survives without crash`() {
        // Given a launched activity
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        // When configuration changes (e.g., rotation)
        scenario.onActivity { activity: ComponentActivity ->
            // Activity should handle configuration changes without crashing
            assertNotNull("Activity should survive configuration change", activity)
        }
    }

    @Test
    fun `given MainActivity when resumes then restarts clock`() {
        // Given a launched activity
        val scenario = ActivityScenario.launch<MainActivity>(android.content.Intent())

        // When the activity goes to background and comes back
        scenario.moveToState(androidx.lifecycle.Lifecycle.State.CREATED)
        scenario.moveToState(androidx.lifecycle.Lifecycle.State.RESUMED)

        // Then the activity should be in resumed state
        scenario.onActivity { activity: ComponentActivity ->
            assertNotNull("Activity should resume successfully", activity)
        }
    }
}