import markets.Ticker
import markets.crypto_exchanges.CryptoExchange
import markets.fiat_exchanges.FiatExchange
import printer.TickerPrinter
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * Created by johannesC on 2017/09/03.
 */
class TickerStreamingService(private val writer: TickerPrinter,
                             private val fiatRepository: FiatExchange,
                             private val cryptoExchanges: ArrayList<CryptoExchange>) {

    private var scheduler: ScheduledExecutorService? = null

    fun startDownloadingTickerData() {
        scheduler = Executors.newSingleThreadScheduledExecutor()
        scheduler?.scheduleAtFixedRate({

            val fiatRates = fiatRepository.getRates()

            val tickers = arrayListOf<Ticker.CryptoTicker>()
            for (exchange in cryptoExchanges) {
                println("Checking ${exchange.exchangeName()}")
                tickers.addAll(exchange.getTicker(fiatRates))
                println("Done")
            }

            writeToFile(tickers)
            println(tickers)
            println("Press 1 to quit")
        }, 0, 15, TimeUnit.MINUTES)
    }

    private fun writeToFile(tickers: ArrayList<Ticker.CryptoTicker>) {
        try {
            writer.print(Ticker.OutputCryptoTicker(cryptoTickers = tickers))
        } catch (e: Exception) {
            println(e.toString())
        }
    }

    fun stopDownloadingTickerData() {
        scheduler?.shutdown()
    }
}