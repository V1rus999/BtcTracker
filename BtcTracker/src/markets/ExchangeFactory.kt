package markets

import markets.crypto_exchanges.CryptoExchange
import markets.crypto_exchanges.bittrex.BittrexExchange
import markets.crypto_exchanges.cryptowatch.CryptoWatchExchange
import markets.crypto_exchanges.luno.LunoExchange

/**
 * Created by johannesC on 2017/09/09.
 */
class ExchangeFactory {

    fun getExchanges(): ArrayList<CryptoExchange> {
        return arrayListOf(
                CryptoWatchExchange(),
                LunoExchange(),
                BittrexExchange()
        )
    }
}