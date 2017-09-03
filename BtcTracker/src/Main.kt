import markets.CryptoWatchRepository
import markets.FiatRepository
import markets.LunoRepository

/**
 * Created by johannesC on 2017/09/03.
 */
fun main(args: Array<String>) {
    println("Starting response service")
    val csvPrinter = CsvFilePrinter()
    val time = System.currentTimeMillis()
    csvPrinter.createCsvFile("${time}_TickerData.csv")
    val service = TickerStreamingService(LunoRepository(), CryptoWatchRepository(), FiatRepository() ,csvPrinter)
    service.startDownloadingTickerData()
}