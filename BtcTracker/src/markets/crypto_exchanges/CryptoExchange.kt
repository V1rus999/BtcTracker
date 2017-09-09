package markets.crypto_exchanges

import markets.CryptoTicker
import markets.Rates

/**
 * Created by johannesC on 2017/09/03.
 */
interface CryptoExchange {

    fun getTicker(rates: ArrayList<Rates>) : ArrayList<CryptoTicker>

}