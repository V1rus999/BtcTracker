import markets.crypto_exchanges.CryptoWatchExchange
import markets.fiat_exchanges.FixerExchange
import markets.crypto_exchanges.LunoExchange
import java.io.BufferedReader
import java.io.InputStreamReader


/**
 * Created by johannesC on 2017/09/03.
 */
fun main(args: Array<String>) {
    println("Starting...")
    val csvPrinter = CsvFilePrinter()
    val time = System.currentTimeMillis()
    val filename = "${time}_TickerData.csv"
    println("Created file : $filename")
    csvPrinter.createCsvFile(filename)
    val service = TickerStreamingService(FixerExchange(), csvPrinter, CryptoWatchExchange(), LunoExchange())
    println("Starting streaming service")
    service.startDownloadingTickerData()

    println("Press 1 to quit")
    val `in` = BufferedReader(InputStreamReader(System.`in`))
    val a = `in`.readLine()
    if (a == "1") {
        println("Stopping Service...")
        service.stopDownloadingTickerData()
        println("Quitting...")
    }
}