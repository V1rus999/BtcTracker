package retrofit

import markets.Ticker
import retrofit2.http.GET
import markets.crypto_exchanges.luno.LunoTicker
import retrofit2.Call
import markets.crypto_exchanges.cryptowatch.CryptoWatchTicker

/**
 * Created by johannesC on 2017/09/03.
 */
interface RetrofitFinMarketApi {

    @GET("api/1/ticker?pair=XBTZAR")
    fun getLunoTicker(): Call<LunoTicker>

    @GET("markets/summaries")
    fun getCryptoWatchTicker(): Call<CryptoWatchTicker.CryptoWatchResult>

    @GET("latest?base=USD")
    fun getFiatTicker(): Call<Ticker.FiatTicker>

}