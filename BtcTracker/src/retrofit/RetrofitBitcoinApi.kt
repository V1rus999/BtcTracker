package retrofit

import retrofit2.http.GET
import LunoTicker
import retrofit2.Call
import Result

/**
 * Created by johannesC on 2017/09/03.
 */
interface RetrofitBitcoinApi {

    @GET("api/1/ticker?pair=XBTZAR")
    fun getLunoTicker(): Call<LunoTicker>

    @GET("markets/summaries")
    fun getCryptoWatchTicker(): Call<Result>

}