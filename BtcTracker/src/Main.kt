import markets.ExchangeFactory
import markets.fiat_exchanges.FixerExchange
import printer.CsvFilePrinter
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import java.util.Calendar.*


/**
 * Created by johannesC on 2017/09/03.
 */
fun main(args: Array<String>) {
    println("Starting...")

    val csvPrinter = CsvFilePrinter()
    val cal = Calendar.getInstance()
    val time = "${cal.get(YEAR)}_${cal.get(MONTH) + 1}_${cal.get(DAY_OF_MONTH)}"
    val filename = "${time}_TickerData.csv"
    csvPrinter.createCsvFile(filename)

    val exchangeFactory = ExchangeFactory()
    val service = TickerStreamingService(csvPrinter, FixerExchange(), exchangeFactory.getExchanges())
    println("Starting streaming service")
    service.startDownloadingTickerData()

    val `in` = BufferedReader(InputStreamReader(System.`in`))
    val a = `in`.readLine()
    if (a == "1") {
        println("Stopping Service...")
        service.stopDownloadingTickerData()
        println("Quitting...")
    }
}

