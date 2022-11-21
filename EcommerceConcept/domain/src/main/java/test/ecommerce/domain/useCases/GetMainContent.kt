package test.ecommerce.domain.useCases

import android.util.Log
import kotlinx.coroutines.*
import test.ecommerce.domain.IConnection
import test.ecommerce.domain.models.MainData

class GetMainContent(private val connection: IConnection) {
    fun execute(callback: (MainData?) -> Unit) {
        MainScope().launch(Dispatchers.IO){
            connection.getMain {
                MainScope().launch(Dispatchers.Main) { callback(it) }
            }
        }
    }
}