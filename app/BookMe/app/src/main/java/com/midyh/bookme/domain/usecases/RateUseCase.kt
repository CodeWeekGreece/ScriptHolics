package com.midyh.bookme.domain.usecases

import com.midyh.bookme.api.ShopsApi
import com.midyh.bookme.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RateUseCase @Inject constructor(
    private val api: ShopsApi,
) {
    operator fun invoke(
        category: String,
        shopId: String,
        userId: String,
        rating: Int
    ): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading<String>())
            api.rate(category, shopId, userId, rating)
            emit(Resource.Success<String>(data = ""))
        } catch (e: HttpException) {
            emit(Resource.Error<String>(e.localizedMessage ?: "An unexpected error has occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<String>(e.localizedMessage ?: "An unexpected error has occurred"))
        }
    }
}
