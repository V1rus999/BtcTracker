package markets.crypto_exchanges.cryptowatch

/**
 * Created by johannesC on 2017/09/03.
 */
class CryptoWatchTicker {
    data class CryptoWatchResult(var result: Map<String, PriceHolder>? = null)

    data class PriceHolder(val price: Price)

    data class Price(
            var last: Double? = null,
            var high: Double? = null,
            var low: Double? = null)
}
