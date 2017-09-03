/**
 * Created by johannesC on 2017/09/03.
 */
sealed class TickerResponse {

    data class onSuccess<out T>(val response: T? = null) : TickerResponse()

    data class onFailure(val throwable: Throwable? = null) : TickerResponse()

}