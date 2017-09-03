package tickers

import java.util.*

/**
 * Created by johannesC on 2017/09/03.
 */
data class CryptoTicker(val price: String? = null, val pair: String = "TestPair", val exchange: String = "TestExchange", var usdPrice : String? = price)

data class OutputCryptoTicker(val timeStamp: String? = Calendar.getInstance().time.toString(), val cryptoTickers: ArrayList<CryptoTicker> = arrayListOf(), val zar: String = "", val eur: String = "")

data class FiatTicker(val base: String? = null, val date: String? = null, val rates: Rates)

class Rates(val ZAR: String? = null, val EUR: String? = null)