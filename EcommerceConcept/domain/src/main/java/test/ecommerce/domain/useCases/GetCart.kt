package test.ecommerce.domain.useCases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import test.ecommerce.domain.IConnection
import test.ecommerce.domain.models.Cart
import test.ecommerce.domain.models.ProductDetails

class GetCart(private val connection: IConnection) {
    fun execute(callback: (Cart?) -> Unit) {
        MainScope().launch(Dispatchers.IO){
            connection.getCart {
                MainScope().launch(Dispatchers.Main) { callback(it) }
            }
        }
    }
}