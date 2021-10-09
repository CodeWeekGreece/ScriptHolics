package com.midyh.bookme.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
) = flow {
    val data = query().first()

    emit(Resource.Loading(data))

    val flow = try {
        saveFetchResult(fetch())
        query().map { Resource.Success(it) }
    } catch (err: Throwable) {
        query().map { Resource.Error(err.localizedMessage ?: "", it) }
    }

    emitAll(flow)
}
