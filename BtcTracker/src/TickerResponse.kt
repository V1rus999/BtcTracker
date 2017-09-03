/**
 * Created by johannesC on 2017/09/03.
 */
sealed class TickerResponse {

    data class onSuccess(val ticker: List<Ticker>? = null) : TickerResponse()

    data class onFailure(val throwable: Throwable? = null) : TickerResponse()

}