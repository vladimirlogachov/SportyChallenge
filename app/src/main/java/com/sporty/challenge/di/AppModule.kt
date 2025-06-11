package com.sporty.challenge.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DataConversion
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatterBuilder
import java.time.format.SignStyle
import java.time.temporal.ChronoField
import java.util.Locale
import kotlin.coroutines.CoroutineContext

val appModule = module {

    single<CoroutineContext>(qualifier = named("ioDispatcher")) {
        Dispatchers.IO
    }

    single {
        HttpClient {
            install(HttpCache)
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
            install(DataConversion) {
                convert<LocalDate> {
                    val formatter = DateTimeFormatterBuilder()
                        .appendValue(ChronoField.YEAR, 4, 4, SignStyle.NEVER)
                        .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                        .appendValue(ChronoField.DAY_OF_MONTH, 2)
                        .toFormatter(Locale.ROOT)

                    decode { values ->
                        LocalDate.from(formatter.parse(values.single()))
                    }

                    encode { value ->
                        listOf(SimpleDateFormat.getInstance().format(value))
                    }
                }
            }
        }
    }

}
