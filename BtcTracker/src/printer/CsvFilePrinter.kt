package printer

/**
 * Created by johannesC on 2017/09/03.
 */
import markets.Ticker
import java.io.*
import java.nio.charset.StandardCharsets
import java.text.DecimalFormat


class CsvFilePrinter : TickerPrinter {
    var fileName: String = "data/TickerData.csv"

    override fun print(tickers: Ticker.OutputCryptoTicker) {
        try {
            BufferedWriter(OutputStreamWriter(FileOutputStream(fileName, true), StandardCharsets.UTF_8)).use {
                writer ->
                val stringBuilder = StringBuilder()

                tickers.cryptoTickers.forEach {
                    stringBuilder.append(tickers.timeStamp).append(",")
                    stringBuilder.append(it.exchange).append(",")
                    stringBuilder.append(it.pair).append(",")

                    val df = DecimalFormat("#")
                    df.maximumFractionDigits = 10

                    stringBuilder.append(df.format(it.price)).append(",")
                    stringBuilder.append(df.format(it.usdPrice)).append(",\n")
                }
                writer.write(stringBuilder.toString())
            }
        } catch (ex: IOException) {
            // Handle me
        }
    }

    override fun createCsvFile(fileName: String) {
        File("data").mkdir()
        this.fileName = "data/$fileName"
        val file = File(this.fileName)
        if (!file.exists()) {
            println("Created file : $fileName")
            try {
                BufferedWriter(OutputStreamWriter(FileOutputStream(this.fileName, true), StandardCharsets.UTF_8)).use {
                    writer ->
                    writer.write("TimeStamp,Exchange,CurrencyPair,Price,Dollar Price\n")
                }
            } catch (ex: IOException) {
                println("Create File Error : $ex")
            }
        }
    }
}