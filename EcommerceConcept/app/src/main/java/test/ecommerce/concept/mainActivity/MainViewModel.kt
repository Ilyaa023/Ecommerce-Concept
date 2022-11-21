package test.ecommerce.concept.mainActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import test.ecommerce.data.Connection
import test.ecommerce.domain.IConnection
import test.ecommerce.domain.models.MainData
import test.ecommerce.domain.useCases.GetMainContent

class MainViewModel: ViewModel() {

    companion object{
        const val PHONE_CATEGORY = 0
        const val COMPUTER_CATEGORY = 1
        const val HEALTH_CATEGORY = 2
        const val BOOKS_CATEGORY = 3
    }

    private val connection: IConnection = Connection()

    val data = MutableLiveData<MainData>()
    val selectedCategory = MutableLiveData(0)

    fun getContent(errCallback: () -> Unit){
        GetMainContent(connection).execute {
            if(it != null) data.postValue(it)
            else errCallback()
        }
    }

    fun setCategory(categoryNum: Int){
        when (categoryNum){
            PHONE_CATEGORY, COMPUTER_CATEGORY,
            HEALTH_CATEGORY, BOOKS_CATEGORY -> selectedCategory.value = categoryNum
        }
    }
}