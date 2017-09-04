import markets.CryptoWatchRepository
import markets.FiatRepository
import markets.LunoRepository
import java.io.BufferedReader
import java.io.InputStreamReader


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

    println("Press 1 to quit")
    val `in` = BufferedReader(InputStreamReader(System.`in`))
    val a = `in`.readLine()
    if (a == "1") {
        println("Stopping Service...")
        service.stopDownloadingTickerData()
        println("Quitting...")
    }
}