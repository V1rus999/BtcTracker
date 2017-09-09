package tickers

/**
 * Created by johannesC on 2017/09/03.
 */
data class Result(
        var result: Map<String, PriceHolder>? = null)

data class PriceHolder(val price: Price)

data class Price(
        var last: Double? = null,
        var high: Double? = null,
        var low: Double? = null)