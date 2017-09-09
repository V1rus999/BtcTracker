package printer

import markets.Ticker

/**
 * Created by johannesC on 2017/09/03.
 */
interface TickerPrinter {

    fun print(tickers: Ticker.OutputCryptoTicker)

    fun createCsvFile(fileName : String)
}