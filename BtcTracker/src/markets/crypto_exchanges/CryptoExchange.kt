package markets.crypto_exchanges

import markets.Ticker

/**
 * Created by johannesC on 2017/09/03.
 */
interface CryptoExchange {

    fun exchangeName() : String

    fun getTicker(rates: ArrayList<Ticker.Rates>) : ArrayList<Ticker.CryptoTicker>

}