import markets.crypto_exchanges.CryptoExchange
import markets.fiat_exchanges.FiatExchange
import tickers.CryptoTicker
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import tickers.*

/**
 * Created by johannesC on 2017/09/03.
 */
class TickerStreamingService constructor(private val fiatRepository: FiatExchange,
                                         private val writer: TickerPrinter, vararg val cryptoExchanges: CryptoExchange) {

    private var scheduler: ScheduledExecutorService? = null

    fun startDownloadingTickerData() {
        scheduler = Executors.newSingleThreadScheduledExecutor()
        scheduler?.scheduleAtFixedRate({

            val fiatRates = fiatRepository.getRates()

            val tickers = arrayListOf<CryptoTicker>()
            for (exchange in cryptoExchanges) {
                tickers.addAll(exchange.getTicker(fiatRates))
            }

            writeToFile(tickers, fiatRates)
            println(tickers)
        }, 0, 15, TimeUnit.MINUTES)
    }

    private fun writeToFile(tickers: ArrayList<CryptoTicker>, fiatRates: Rates?) {
        try {
            if (fiatRates != null) {
                writer.print(OutputCryptoTicker(cryptoTickers = tickers, zar = fiatRates.ZAR!!, eur = fiatRates.EUR!!))
            } else {
                writer.print(OutputCryptoTicker(cryptoTickers = tickers))
            }
        } catch (e: Exception) {
        }
    }

    fun stopDownloadingTickerData() {
        scheduler?.shutdown()
    }
}