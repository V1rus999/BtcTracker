import markets.FinMarketRepository
import tickers.CryptoTicker
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import tickers.*

/**
 * Created by johannesC on 2017/09/03.
 */
class TickerStreamingService constructor(private val lunoRepository: FinMarketRepository,
                                         private val finMarketWatchRepository: FinMarketRepository,
                                         private val fiatRepository: FinMarketRepository,
                                         private val writer: TickerPrinter) {

    private var scheduler: ScheduledExecutorService? = null

    fun startDownloadingTickerData() {
        scheduler = Executors.newSingleThreadScheduledExecutor()
        scheduler?.scheduleAtFixedRate({
            val tickers = arrayListOf<CryptoTicker>()
            val lunoResponse = lunoRepository.getTicker()
            when (lunoResponse) {
                is TickerResponse.onSuccess<*> -> lunoResponse.response?.let { tickers.addAll(lunoResponse.response as ArrayList<CryptoTicker>) }
            }

            val cryWResponse = finMarketWatchRepository.getTicker()
            when (cryWResponse) {
                is TickerResponse.onSuccess<*> -> cryWResponse.response?.let { tickers.addAll(cryWResponse.response as ArrayList<CryptoTicker>) }
            }

            val fiatResponse = fiatRepository.getTicker()
            var rate = Rates()
            when (fiatResponse) {
                is TickerResponse.onSuccess<*> -> fiatResponse.response?.let { rate = it as Rates }
            }

            try {
                writer.print(OutputCryptoTicker(cryptoTickers = addUsdPrices(tickers, rate), zar = rate.ZAR!!, eur = rate.EUR!!))
            } catch (e: Exception) { }
            println(tickers)
        }, 0, 15, TimeUnit.MINUTES)
    }

    fun addUsdPrices(tickers: ArrayList<CryptoTicker>, rates: Rates): ArrayList<CryptoTicker> {
        tickers.forEach {
            try {
                val price: Double? = it.price?.toDouble()

                val rate: Double? =
                        if (it.pair.contains("eur")) rates.EUR?.toDouble()
                        else if (it.pair.contains("zar")) rates.ZAR?.toDouble()
                        else null

                if (price != null && rate != null) {
                    it.usdPrice = (price / rate).toString()
                }
            } catch (e: Exception) {
            }
        }
        return tickers
    }

    fun stopDownloadingTickerData() {
        scheduler?.shutdown()
    }
}