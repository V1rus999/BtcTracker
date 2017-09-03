import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

/**
 * Created by johannesC on 2017/09/03.
 */
class CryptoWatchRepository : CryptoRepository {

    private val requestUrl = HttpUrl.parse("https://api.cryptowat.ch/")

    override fun getTicker(): TickerResponse {
        val retrofit = Retrofit.Builder().baseUrl(requestUrl).addConverterFactory(GsonConverterFactory.create()).build()
        val btcApi = retrofit.create(retrofit.RetrofitBitcoinApi::class.java)
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

    private fun extractTickers(result: Result?): ArrayList<Ticker> {
        val tickers: ArrayList<Ticker> = arrayListOf()

        result?.result?.entries?.forEach {
            if (it.key.contains("btc") && (it.key.contains("usd") || it.key.contains("eur"))) {
                val pairSplit = it.key.split(":")
                tickers.add(Ticker(Calendar.getInstance().time.toString(), it.value.price.last.toString(), pairSplit[1], pairSplit[0]))
            }
        }
        return tickers
    }
}