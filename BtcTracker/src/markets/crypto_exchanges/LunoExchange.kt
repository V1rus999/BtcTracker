package markets.crypto_exchanges

import okhttp3.HttpUrl
import retrofit.RetrofitFinMarketApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tickers.LunoTicker
import tickers.CryptoTicker
import tickers.Rates
import kotlin.collections.ArrayList

/**
 * Created by johannesC on 2017/09/03.
 */
class LunoExchange : CryptoExchange {

    private val requestUrl = HttpUrl.parse("https://api.mybitx.com/")
    private val retrofit = Retrofit.Builder().baseUrl(requestUrl).addConverterFactory(GsonConverterFactory.create()).build()
    private val btcApi = retrofit.create(RetrofitFinMarketApi::class.java)

    override fun getTicker(rates: ArrayList<Rates>): ArrayList<CryptoTicker> {
        val call = btcApi.getLunoTicker()
        var tickers: ArrayList<CryptoTicker>? = arrayListOf()

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

    private fun extractTickers(result: LunoTicker?): ArrayList<CryptoTicker> {
        return arrayListOf(CryptoTicker(result?.ask, "btczar", "luno za"))
    }

    private fun addUsdPrices(tickers: ArrayList<CryptoTicker>, rates: ArrayList<Rates>): ArrayList<CryptoTicker> {
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