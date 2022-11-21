package test.ecommerce.domain.useCases

import android.util.Log
import kotlinx.coroutines.*
import test.ecommerce.domain.IConnection
import test.ecommerce.domain.models.MainData
import test.ecommerce.domain.models.ProductDetails

class GetDetails(private val connection: IConnection) {
    fun execute(callback: (ProductDetails?) -> Unit) {
        MainScope().launch(Dispatchers.IO){
            connection.getDetails {
                MainScope().launch(Dispatchers.Main) { callback(it) }
            }
        }
    }
}