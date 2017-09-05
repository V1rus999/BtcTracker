package markets.crypto_exchanges

import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import tickers.CryptoTicker
import tickers.Rates
import tickers.Result

/**
 * Created by johannesC on 2017/09/03.
 */
class CryptoWatchExchange : CryptoExchange {

    private val requestUrl = HttpUrl.parse("https://api.cryptowat.ch/")
    private val retrofit = Retrofit.Builder().baseUrl(requestUrl).addConverterFactory(GsonConverterFactory.create()).build()
    private val btcApi = retrofit.create(retrofit.RetrofitFinMarketApi::class.java)

    override fun getTicker(rates: Rates?): ArrayList<CryptoTicker> {
        val call = btcApi.getCryptoWatchTicker()
        var tickers: ArrayList<CryptoTicker>? = arrayListOf()

        try {
            val response = call.execute()
            if (response.isSuccessful) {
                tickers = extractTickers(response.body())
            }

        } catch (e: Exception) {
        }

        if (tickers == null) {
            tickers = arrayListOf()
        }

        rates?.let { addUsdPrices(tickers!!, rates) }
        return tickers
    }

    fun addUsdPrices(tickers: ArrayList<CryptoTicker>, rates: Rates): ArrayList<CryptoTicker> {
        tickers.forEach {
            try {
                val price: Double? = it.price?.toDouble()

                val rate: Double? =
                        if (it.pair.contains("eur")) rates.EUR?.toDouble()
                        else if (it.pair.contains("zar")) rates.ZAR?.toDouble()
                        else null

                if (price != null && rate != null) {
                    it.usdPrice = (price / rate).toString()
                }
            } catch (e: Exception) {
            }
        }
        return tickers
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