package test.ecommerce.concept.detailsActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import test.ecommerce.data.Connection
import test.ecommerce.domain.models.ProductDetails
import test.ecommerce.domain.useCases.GetDetails

class DetailsViewModel: ViewModel(){

    private val connection = Connection()

    val data = MutableLiveData<ProductDetails>()
    val checkedColor = MutableLiveData<String>()
    val checkedCapacity = MutableLiveData<String>()

    fun getContent(errCallback: () -> Unit){
        GetDetails(connection).execute {
            if (it != null) data.value = it
            else errCallback()
        }
    }
}