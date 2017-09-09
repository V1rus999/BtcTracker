package markets.crypto_exchanges.bittrex

import markets.Ticker
import markets.crypto_exchanges.CryptoExchange
import okhttp3.HttpUrl
import retrofit.RetrofitFinMarketApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by johannesC on 2017/09/09.
 */
class BittrexExchange : CryptoExchange {

    private val requestUrl = HttpUrl.parse("https://bittrex.com/")
    private val retrofit = Retrofit.Builder().baseUrl(requestUrl).addConverterFactory(GsonConverterFactory.create()).build()
    private val btcApi = retrofit.create(RetrofitFinMarketApi::class.java)

    override fun exchangeName(): String = "Bittrex"

    override fun getTicker(rates: ArrayList<Ticker.Rates>): ArrayList<Ticker.CryptoTicker> {
        val call = btcApi.getBittrexTicker()
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

        return tickers
    }

    private fun extractTickers(result: BittrexTicker.BittrexResult): ArrayList<Ticker.CryptoTicker> {
        val cryptoTickers: ArrayList<Ticker.CryptoTicker> = arrayListOf()

        result.result?.forEach {
            val pair = it.MarketName?.toLowerCase()?.replace("-", "")
            if (pair != null) {
                cryptoTickers.add(Ticker.CryptoTicker(it.Last, pair, "bittrex"))
            }
        }
        return cryptoTickers
    }
}