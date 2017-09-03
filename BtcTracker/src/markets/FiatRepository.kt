package markets

import TickerResponse
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by johannesC on 2017/09/03.
 */
class FiatRepository : FinMarketRepository {

    private val requestUrl = HttpUrl.parse("http://api.fixer.io/")

    override fun getTicker(): TickerResponse {
        val retrofit = Retrofit.Builder().baseUrl(requestUrl).addConverterFactory(GsonConverterFactory.create()).build()
        val btcApi = retrofit.create(retrofit.RetrofitFinMarketApi::class.java)
        val call = btcApi.getFiatTicker()
        var tickerResponse: TickerResponse?

        try {
            val response = call.execute()
            if (response.isSuccessful) {
                tickerResponse = TickerResponse.onSuccess(response.body().rates)
            } else {
                tickerResponse = TickerResponse.onFailure(Throwable("Failure"))
            }

        } catch (e: Exception) {
            tickerResponse = TickerResponse.onFailure(e)
        }

        if (tickerResponse == null) {
            tickerResponse = TickerResponse.onFailure(Throwable("Failure"))
        }
        return tickerResponse
    }
}