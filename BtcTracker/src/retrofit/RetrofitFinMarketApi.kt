package retrofit

import retrofit2.http.GET
import tickers.LunoTicker
import retrofit2.Call
import tickers.Result
import tickers.FiatTicker

/**
 * Created by johannesC on 2017/09/03.
 */
interface RetrofitFinMarketApi {

    @GET("api/1/ticker?pair=XBTZAR")
    fun getLunoTicker(): Call<LunoTicker>

    @GET("markets/summaries")
    fun getCryptoWatchTicker(): Call<Result>

    @GET("latest?base=USD&symbols=ZAR,EUR")
    fun getFiatTicker(): Call<FiatTicker>

}