package test.ecommerce.concept.cartActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import test.ecommerce.data.Connection
import test.ecommerce.domain.models.Cart
import test.ecommerce.domain.useCases.GetCart

class CartViewModel: ViewModel(){

    private val connection = Connection()

    val data = MutableLiveData<Cart>()
    val counters = MutableLiveData<IntArray>(IntArray(0))

    fun getContent(errCallback: () -> Unit){
        GetCart(connection).execute {
            if (it != null) data.value = it
            else errCallback()
        }
    }
    fun plusCounters(num: Int){
        val newArray = counters.value!!.copyOf(data.value?.basket!!.size)
        if (newArray[num] < Integer.MAX_VALUE) newArray[num]++
        counters.value = newArray
    }
    fun minusCounters(num: Int){
        val newArray = counters.value!!.copyOf(data.value?.basket!!.size)
        if (newArray[num] > 0) newArray[num]--
        counters.value = newArray
    }
}