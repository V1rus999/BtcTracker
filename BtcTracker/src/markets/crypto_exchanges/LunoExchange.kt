package markets.crypto_exchanges

import okhttp3.HttpUrl
import retrofit.RetrofitFinMarketApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import tickers.LunoTicker
import tickers.CryptoTicker
import tickers.Rates

/**
 * Created by johannesC on 2017/09/03.
 */
class LunoExchange : CryptoExchange {

    private val requestUrl = HttpUrl.parse("https://api.mybitx.com/")
    private val retrofit = Retrofit.Builder().baseUrl(requestUrl).addConverterFactory(GsonConverterFactory.create()).build()
    private val btcApi = retrofit.create(RetrofitFinMarketApi::class.java)

    override fun getTicker(rates: Rates?): ArrayList<CryptoTicker> {
        val call = btcApi.getLunoTicker()
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

    private fun extractTickers(result: LunoTicker?): ArrayList<CryptoTicker> {
        return arrayListOf(CryptoTicker(result?.ask, "btczar", "luno ZA"))
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
}