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
class TickerStreamingService constructor(private val writer: TickerPrinter,
                                         private val fiatRepository: FiatExchange,
                                         vararg val cryptoExchanges: CryptoExchange) {

    private var scheduler: ScheduledExecutorService? = null

    fun startDownloadingTickerData() {
        scheduler = Executors.newSingleThreadScheduledExecutor()
        scheduler?.scheduleAtFixedRate({

            val fiatRates = fiatRepository.getRates()

            val tickers = arrayListOf<CryptoTicker>()
            for (exchange in cryptoExchanges) {
                tickers.addAll(exchange.getTicker(fiatRates))
            }

            writeToFile(tickers)
            println(tickers)
            println("Press 1 to quit")
        }, 0, 15, TimeUnit.SECONDS)
    }

    private fun writeToFile(tickers: ArrayList<CryptoTicker>) {
        try {
            writer.print(OutputCryptoTicker(cryptoTickers = tickers))
        } catch (e: Exception) {
            println(e.toString())
        }
    }

    fun stopDownloadingTickerData() {
        scheduler?.shutdown()
    }
}