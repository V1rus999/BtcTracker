package printer

/**
 * Created by johannesC on 2017/09/03.
 */
interface TickerPrinter {

    fun print(tickers: OutputCryptoTicker)

    fun createCsvFile(fileName : String)
}