package markets.crypto_exchanges.luno

import markets.Ticker
import markets.crypto_exchanges.CryptoExchange
import okhttp3.HttpUrl
import retrofit.RetrofitFinMarketApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.ArrayList

/**
 * Created by johannesC on 2017/09/03.
 */
class LunoExchange : CryptoExchange {

    private val requestUrl = HttpUrl.parse("https://api.mybitx.com/")
    private val retrofit = Retrofit.Builder().baseUrl(requestUrl).addConverterFactory(GsonConverterFactory.create()).build()
    private val btcApi = retrofit.create(RetrofitFinMarketApi::class.java)

    override fun getTicker(rates: ArrayList<Ticker.Rates>): ArrayList<Ticker.CryptoTicker> {
        val call = btcApi.getLunoTicker()
        var tickers: ArrayList<Ticker.CryptoTicker>? = arrayListOf()

        try {
            val response = call.execute()
            if (response.isSuccessful) {
                tickers = extractTickers(response.body())
            }

        } catch (e: Exception) {
            println(e.toString())
        }

        if (tickers == null) {
            tickers = arrayListOf()
        }

        rates.let { addUsdPrices(tickers!!, rates) }
        return tickers
    }

    private fun extractTickers(result: LunoTicker?): ArrayList<Ticker.CryptoTicker> {
        return arrayListOf(Ticker.CryptoTicker(result?.ask, "btczar", "luno za"))
    }

    private fun addUsdPrices(tickers: ArrayList<Ticker.CryptoTicker>, rates: ArrayList<Ticker.Rates>): ArrayList<Ticker.CryptoTicker> {
        val currencyUsdRate = rates.find { it.name.contains("ZAR") }

        currencyUsdRate?.let {
            tickers.forEach {
                try {
                    val price: Double? = it.price

                    if (price != null) {
                        it.usdPrice = (price / currencyUsdRate.value)
                    }
                } catch (e: Exception) {
                    print("${this.javaClass.name} : $e")
                }
            }
        }
        return tickers
    }
}