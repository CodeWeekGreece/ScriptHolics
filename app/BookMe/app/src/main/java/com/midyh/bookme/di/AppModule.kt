package com.midyh.bookme.di

import android.app.Application
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.midyh.bookme.api.ReservationsApi
import com.midyh.bookme.api.ShopsApi
import com.midyh.bookme.api.UsersApi
import com.midyh.bookme.data.Dev
import com.midyh.bookme.data.authentication.UserDatabase
import com.midyh.bookme.data.reservations.ReservationsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(Dev.DB_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideShopsApi(retrofit: Retrofit): ShopsApi =
        retrofit.create(ShopsApi::class.java)

    @Provides
    @Singleton
    fun provideReservationsApi(retrofit: Retrofit): ReservationsApi =
        retrofit.create(ReservationsApi::class.java)

    @Provides
    @Singleton
    fun provideUsersApi(retrofit: Retrofit): UsersApi =
        retrofit.create(UsersApi::class.java)

    @Provides
    @Singleton
    fun provideAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideDB(): DatabaseReference =
        FirebaseDatabase
            .getInstance(Dev.DB_URL)
            .reference

    @Provides
    @Singleton
    fun provideReservationsDB(app: Application): ReservationsDatabase =
        Room.databaseBuilder(app, ReservationsDatabase::class.java, "reservations_database")
            .build()

    @Provides
    @Singleton
    fun provideUserDB(app: Application): UserDatabase =
        Room.databaseBuilder(app, UserDatabase::class.java, "user_database")
            .build()
}
