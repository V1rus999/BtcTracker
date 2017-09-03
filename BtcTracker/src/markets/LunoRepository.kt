package markets

import okhttp3.HttpUrl
import retrofit.RetrofitFinMarketApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import TickerResponse
import tickers.LunoTicker
import tickers.CryptoTicker

/**
 * Created by johannesC on 2017/09/03.
 */
class LunoRepository : FinMarketRepository {

    private val requestUrl = HttpUrl.parse("https://api.mybitx.com/")

    override fun getTicker(): TickerResponse {
        val retrofit = Retrofit.Builder().baseUrl(requestUrl).addConverterFactory(GsonConverterFactory.create()).build()
        val btcApi = retrofit.create(RetrofitFinMarketApi::class.java)
        val call = btcApi.getLunoTicker()
        var tickerResponse: TickerResponse?

        try {
            val response = call.execute()
            if (response.isSuccessful) {
                tickerResponse = TickerResponse.onSuccess(extractTickers(response.body()))
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

    private fun extractTickers(result: LunoTicker?): ArrayList<CryptoTicker> {
        return arrayListOf(CryptoTicker(result?.ask, "btczar", "luno ZA"))
    }
}