package retrofit

import CryptoRepository
import TickerResponse
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import Ticker
import TickerPrinter

/**
 * Created by johannesC on 2017/09/03.
 */
class TickerService constructor(private val lunoRepository: CryptoRepository,
                                private val cryptoWatchRepository: CryptoRepository,
                                private val writer: TickerPrinter) {

    private var scheduler: ScheduledExecutorService? = null

    fun startDownloadingTickerData() {
        scheduler = Executors.newScheduledThreadPool(2)
        scheduler?.scheduleAtFixedRate({
            val tickers = arrayListOf<Ticker>()
            val lunoResponse = lunoRepository.getTicker()
            when (lunoResponse) {
                is TickerResponse.onSuccess -> lunoResponse.ticker?.let { tickers.addAll(lunoResponse.ticker) }
            }

            val cryWResponse = cryptoWatchRepository.getTicker()
            when (cryWResponse) {
                is TickerResponse.onSuccess -> cryWResponse.ticker?.let { tickers.addAll(cryWResponse.ticker) }
            }

            writer.print(tickers)
            println(tickers)
        }, 0, 15, TimeUnit.SECONDS)
    }

    fun stopDownloadingTickerData() {
        scheduler?.shutdown()
    }
}