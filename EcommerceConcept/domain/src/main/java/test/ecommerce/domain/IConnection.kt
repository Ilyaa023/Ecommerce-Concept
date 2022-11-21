package test.ecommerce.domain

import test.ecommerce.domain.models.Cart
import test.ecommerce.domain.models.MainData
import test.ecommerce.domain.models.ProductDetails

abstract class IConnection {
    companion object{
        const val SUCCESS = 0
        const val FAIL = 1
    }

    open fun getMain(callback: (MainData?) -> Unit){}
    open fun getDetails(callback: (ProductDetails?) -> Unit){}
    open fun getCart(callback: (Cart?) -> Unit){}
}