package markets

import TickerResponse

/**
 * Created by johannesC on 2017/09/03.
 */
interface FinMarketRepository {

    fun getTicker() : TickerResponse

}