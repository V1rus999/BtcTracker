package markets

import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import TickerResponse
import tickers.CryptoTicker
import tickers.Result

/**
 * Created by johannesC on 2017/09/03.
 */
class CryptoWatchRepository : FinMarketRepository {

    private val requestUrl = HttpUrl.parse("https://api.cryptowat.ch/")

    override fun getTicker(): TickerResponse {
        val retrofit = Retrofit.Builder().baseUrl(requestUrl).addConverterFactory(GsonConverterFactory.create()).build()
        val btcApi = retrofit.create(retrofit.RetrofitFinMarketApi::class.java)
        val call = btcApi.getCryptoWatchTicker()
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

    private fun extractTickers(result: Result?): ArrayList<CryptoTicker> {
        val cryptoTickers: ArrayList<CryptoTicker> = arrayListOf()

        result?.result?.entries?.forEach {
            if (it.key.contains("btc") && (it.key.contains("usd") || it.key.contains("eur"))) {
                val pairSplit = it.key.split(":")
                cryptoTickers.add(CryptoTicker(it.value.price.last.toString(), pairSplit[1], pairSplit[0]))
            }
        }
        return cryptoTickers
    }
}