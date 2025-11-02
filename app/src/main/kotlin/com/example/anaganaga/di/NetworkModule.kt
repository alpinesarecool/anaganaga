package di

import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val GRAPHQL_ENDPOINT = "http://10.0.2.2:3000/graphql"
    // Use 10.0.2.2 for localhost access from Android emulator
    // If testing on physical device, replace with your computer’s local IP (like http://192.168.x.x:3000/graphql)

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(GRAPHQL_ENDPOINT)
            .build()
    }
}
