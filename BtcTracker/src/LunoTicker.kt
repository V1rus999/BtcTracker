/**
 * Created by johannesC on 2017/09/03.
 */
data class LunoTicker(
        var timestamp: Long? = null,
        var bid: String? = null,
        var ask: String? = null,
        var lastTrade: String? = null,
        var rolling24HourVolume: String? = null,
        var pair: String? = null)

