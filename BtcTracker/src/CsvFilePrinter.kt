/**
 * Created by johannesC on 2017/09/03.
 */
import tickers.OutputCryptoTicker
import java.io.*
import java.nio.charset.StandardCharsets


class CsvFilePrinter : TickerPrinter {
    var fileName: String = "data/TickerData.csv"

    override fun print(tickers: OutputCryptoTicker) {
        try {
            BufferedWriter(OutputStreamWriter(FileOutputStream(fileName, true), StandardCharsets.UTF_8)).use {
                writer ->
                val stringBuilder = StringBuilder()

                tickers.cryptoTickers.forEach {
                    stringBuilder.append(tickers.timeStamp).append(",")
                    stringBuilder.append(it.exchange).append(",")
                    stringBuilder.append(it.pair).append(",")
                    stringBuilder.append(it.price).append(",")
                    stringBuilder.append(it.usdPrice).append(",")
                    stringBuilder.append(tickers.zar).append(",")
                    stringBuilder.append(tickers.eur).append(",\n")
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
        try {
            BufferedWriter(OutputStreamWriter(FileOutputStream(this.fileName, true), StandardCharsets.UTF_8)).use {
                writer ->
                writer.write("TimeStamp,Exchange,CurrencyPair,Price,Dollar Price,UsdZar,UsdEur\n")
            }
        } catch (ex: IOException) {
            // Handle me
        }
    }
}