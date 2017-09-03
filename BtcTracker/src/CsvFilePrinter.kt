/**
 * Created by johannesC on 2017/09/03.
 */
import java.io.BufferedWriter
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets
import java.util.*


class CsvFilePrinter : TickerPrinter {

    override fun print(tickers: ArrayList<Ticker>) {
        try {
            BufferedWriter(OutputStreamWriter(FileOutputStream("tickerData.csv", true), StandardCharsets.UTF_8)).use {
                writer ->
                val stringBuilder = StringBuilder()
                tickers.forEach {
                    stringBuilder.append(Calendar.getInstance().time.toString() + " " + it.toString() + " ")
                }
                stringBuilder.append("\n\n")
                writer.write(stringBuilder.toString())
            }
        } catch (ex: IOException) {
            // Handle me
        }

    }
}