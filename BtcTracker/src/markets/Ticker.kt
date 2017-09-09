package markets

import java.util.*

/**
 * Created by johannesC on 2017/09/03.
 */
class Ticker {
    data class CryptoTicker(val price: Double? = null, val pair: String = "TestPair", val exchange: String = "TestExchange", var usdPrice: Double? = if (pair.contains("usd")) price else 0.0)

    data class OutputCryptoTicker(val timeStamp: String? = Calendar.getInstance().time.toString(), val cryptoTickers: ArrayList<CryptoTicker> = arrayListOf(), val zar: String = "", val eur: String = "")

    data class FiatTicker(val base: String? = null, val date: String? = null, val rates: Map<String, Double>? = null)

    class Rates(val name: String = "", val value: Double = 0.0)
}