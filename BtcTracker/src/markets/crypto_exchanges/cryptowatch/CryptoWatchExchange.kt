package markets.crypto_exchanges.cryptowatch

import markets.Ticker
import markets.crypto_exchanges.CryptoExchange
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import markets.crypto_exchanges.cryptowatch.CryptoWatchTicker.CryptoWatchResult
import kotlin.collections.ArrayList

/**
 * Created by johannesC on 2017/09/03.
 */
class CryptoWatchExchange : CryptoExchange {

    private val requestUrl = HttpUrl.parse("https://api.cryptowat.ch/")
    private val retrofit = Retrofit.Builder().baseUrl(requestUrl).addConverterFactory(GsonConverterFactory.create()).build()
    private val btcApi = retrofit.create(retrofit.RetrofitFinMarketApi::class.java)

    override fun getTicker(rates: ArrayList<Ticker.Rates>): ArrayList<Ticker.CryptoTicker> {
        val call = btcApi.getCryptoWatchTicker()
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

    private fun addUsdPrices(tickers: ArrayList<Ticker.CryptoTicker>, rates: ArrayList<Ticker.Rates>): ArrayList<Ticker.CryptoTicker> {
        tickers.forEach {
            val ticker = it
            try {
                val currencyUsdRate = rates.find { ticker.pair.toUpperCase().contains(it.name.toUpperCase()) }
                val price: Double? = it.price

                if (price != null && currencyUsdRate != null) {
                    it.usdPrice = (price / currencyUsdRate.value)
                }
            } catch (e: Exception) {
                print("${this.javaClass.name} : $e")
            }
        }
        return tickers
    }

    private fun extractTickers(result: CryptoWatchResult?): ArrayList<Ticker.CryptoTicker> {
        val cryptoTickers: ArrayList<Ticker.CryptoTicker> = arrayListOf()

        result?.result?.entries?.forEach {
            if (it.value.price.last != null && it.value.price.last!! > 0) {
                val pairSplit = it.key.split(":")
                cryptoTickers.add(Ticker.CryptoTicker(it.value.price.last, pairSplit[1], pairSplit[0]))
            }
        }
        return cryptoTickers
    }
}