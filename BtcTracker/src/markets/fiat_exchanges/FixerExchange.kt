package markets.fiat_exchanges

import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import markets.Ticker.Rates

/**
 * Created by johannesC on 2017/09/03.
 */
class FixerExchange : FiatExchange {

    private val requestUrl = HttpUrl.parse("http://api.fixer.io/")
    private val retrofit = Retrofit.Builder().baseUrl(requestUrl).addConverterFactory(GsonConverterFactory.create()).build()
    private val fiatApi = retrofit.create(retrofit.RetrofitFinMarketApi::class.java)

    override fun getRates(): ArrayList<Rates> {
        val call = fiatApi.getFiatTicker()
        var rates = arrayListOf<Rates>()

        try {
            val response = call.execute()
            if (response.isSuccessful) {
                rates = extractRates(response.body().rates)
            }

        } catch (e: Exception) {
            println(e.toString())
        }
        return rates
    }

    private fun extractRates(rates: Map<String, Double>?): ArrayList<Rates> {
        val list = arrayListOf<Rates>()
        rates?.forEach { name, value -> list.add(Rates(name, value)) }
        return list
    }
}