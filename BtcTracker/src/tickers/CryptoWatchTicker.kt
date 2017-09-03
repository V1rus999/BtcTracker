package tickers

/**
 * Created by johannesC on 2017/09/03.
 */
data class Result(
        var result: Map<String, PriceHolder>? = null)

data class PriceHolder(val price: Price)

data class Price(
        var last: Float? = null,
        var high: Float? = null,
        var low: Float? = null)