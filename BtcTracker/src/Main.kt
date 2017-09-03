import retrofit.TickerService

/**
 * Created by johannesC on 2017/09/03.
 */
fun main(args: Array<String>) {
    println("Starting ticker service")
    val service = TickerService(LunoRepository(), CryptoWatchRepository())
    service.startDownloadingTickerData()
}