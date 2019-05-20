package com.example.android.kotlincoroutines.main

import com.example.android.kotlincoroutines.main.fakes.makeFailureCall
import com.example.android.kotlincoroutines.main.fakes.makeSuccessCall
import com.example.android.kotlincoroutines.util.FakeNetworkException
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FakeNetworkCallAwaitTest {
    companion object {
        const val TITLE = "The title"
    }

    @Test
    fun whenFakeNetworkCallSuccess_resumeWithResult() {
        val subject = makeSuccessCall(TITLE)
        runBlocking {
            subject.await()
            Truth.assertThat(subject.await()).isEqualTo(TITLE)
        }
    }

    @Test(expected = FakeNetworkException::class)
    fun whenFakeNetworkCallFailure_throws() {
        val subject = makeFailureCall(FakeNetworkException("the error"))
        // this is the correct way to run a coroutine, it will return immediately
//        GlobalScope.launch {
//            subject.await()
//        }

        // this makes the coroutine blocking, don't use it in real code
        runBlocking {
            subject.await()
        }
    }
}
