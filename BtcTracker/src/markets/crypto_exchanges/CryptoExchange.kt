package markets.crypto_exchanges

import tickers.CryptoTicker
import tickers.Rates

/**
 * Created by johannesC on 2017/09/03.
 */
interface CryptoExchange {

    fun getTicker(rates: ArrayList<Rates>) : ArrayList<CryptoTicker>

}