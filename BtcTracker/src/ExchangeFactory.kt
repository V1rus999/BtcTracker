import markets.crypto_exchanges.CryptoExchange
import markets.crypto_exchanges.CryptoWatchExchange
import markets.crypto_exchanges.LunoExchange

/**
 * Created by johannesC on 2017/09/09.
 */
class ExchangeFactory {

    fun getExchanges():ArrayList<CryptoExchange> {
        return arrayListOf(
                CryptoWatchExchange(),
                LunoExchange()
        )
    }
}