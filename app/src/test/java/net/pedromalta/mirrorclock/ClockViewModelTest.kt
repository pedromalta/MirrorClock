package net.pedromalta.mirrorclock

import androidx.lifecycle.Observer
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Unit tests for ClockViewModel to verify the clock functionality.
 */
class ClockViewModelTest {

    private lateinit var viewModel: ClockViewModel
    private var receivedValue: Long? = null
    private lateinit var observer: Observer<Long>

    @Before
    fun setup() {
        receivedValue = null
        observer = Observer { value ->
            receivedValue = value
        }
    }

    @Test
    fun `given ClockViewModel when initialized then currentTime is set`() {
        // Given a new ClockViewModel
        viewModel = ClockViewModel()

        // When we observe the currentTime
        viewModel.currentTime.observeForever(observer)

        // Wait for the coroutine to execute
        Thread.sleep(500)

        // Then the received value should not be null and should be a valid timestamp
        assertNotNull("currentTime should be initialized", receivedValue)
        assertTrue("currentTime should be a positive timestamp", receivedValue!! > 0)
    }

    @Test
    fun `given ClockViewModel when observed then time updates every second`() {
        // Given a ClockViewModel
        viewModel = ClockViewModel()

        // When we observe the currentTime
        viewModel.currentTime.observeForever(observer)

        // Wait for the first update
        Thread.sleep(100)
        val firstValue = receivedValue

        // Wait for the next update (approximately 1 second)
        Thread.sleep(1100)
        val secondValue = receivedValue

        // Then the second value should be different from the first
        // (indicating the clock is updating)
        assertTrue("Time should advance between updates", secondValue!! > firstValue!!)
    }

    @Test
    fun `given ClockViewModel when observed then currentTime is not null`() {
        // Given a ClockViewModel
        viewModel = ClockViewModel()

        // When we observe the currentTime
        viewModel.currentTime.observeForever(observer)

        // Wait for the update
        Thread.sleep(100)

        // Then the LiveData should have a value
        assertNotNull("currentTime LiveData should have a value", receivedValue)
    }

    @Test
    fun `given ClockViewModel when observed then time value is valid timestamp`() {
        // Given a ClockViewModel
        viewModel = ClockViewModel()

        // When we observe the currentTime
        viewModel.currentTime.observeForever(observer)

        // Wait for the update
        Thread.sleep(100)

        // Then the value should be a valid current timestamp
        val currentTime = System.currentTimeMillis()
        val observedTime = receivedValue!!

        assertTrue("Observed time should not be in the future", observedTime <= currentTime)
        assertTrue("Observed time should be within 2 seconds of current time", 
                   currentTime - observedTime < 2000)
    }
}