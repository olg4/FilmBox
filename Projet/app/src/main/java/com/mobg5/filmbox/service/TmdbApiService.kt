package com.mobg5.filmbox.service

import com.mobg5.filmbox.data.model.ListResultsMovies
import com.mobg5.filmbox.data.model.ResultsMovies
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.themoviedb.org/3/"

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface TmdbApiService {
    @GET("movie/{movie_id}")
    suspend fun  getAMovie(@Path("movie_id") id: Int,
                           @Query("api_key") key: String): ResultsMovies

    @GET("discover/movie")
    suspend fun getMovies(@Query("api_key") key: String,
                          @Query("page") pageNum: Int = 1,
                          @Query("sort_by") sortBy: String = "vote_count.desc",
                          @Query("include_adult") adult: Boolean = false): ListResultsMovies

    @GET("search/movie")
    suspend fun search(@Query("api_key") key: String,
                       @Query("query") input: String,
                       @Query("include_adult") adult: Boolean = false): ListResultsMovies

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") key: String,
                                  @Query("page") pageNum: Int = 1,
                                  @Query("region") iso: String = "US"): ListResultsMovies

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") key: String,
                                 @Query("page") pageNum: Int = 1,
                                 @Query("region") iso: String = "US"): ListResultsMovies

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") key: String,
                                  @Query("page") pageNum: Int = 1,
                                  @Query("region") iso: String = "US"): ListResultsMovies

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key") key: String,
                                    @Query("page") pageNum: Int = 1,
                                    @Query("region") iso: String = "US"): ListResultsMovies
}

object TmdbApi {
    val retrofitService : TmdbApiService by lazy {
        retrofit.create(TmdbApiService::class.java) }
}