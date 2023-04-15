package com.salman.trackforme.data.extension

import com.google.android.gms.tasks.Task
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/9/2023.
 */

suspend fun <T> Task<T>.await() : T = suspendCoroutine { cont ->
    addOnSuccessListener {
        cont.resume(it)
    }
    addOnFailureListener {
        cont.resumeWithException(it)
    }
}