package net.pedromalta.mirrorclock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ClockViewModel : ViewModel() {

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long> = _currentTime

    init {
        startClock()
    }

    private fun startClock() {
        viewModelScope.launch {
            while (isActive) {
                val now = System.currentTimeMillis()
                _currentTime.value = now
                val delayTime = 1000 - (now % 1000)
                delay(delayTime)
            }
        }
    }
}
