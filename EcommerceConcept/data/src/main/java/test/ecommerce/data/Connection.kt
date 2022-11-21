package test.ecommerce.data

import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import test.ecommerce.domain.IConnection
import test.ecommerce.domain.models.Cart
import test.ecommerce.domain.models.MainData
import test.ecommerce.domain.models.ProductDetails

class Connection: IConnection() {

    private val service: APIService
    init {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        service = retrofit.create(APIService::class.java)
    }

    override fun getMain(callback:(MainData?) -> Unit) {
        val call = service.getMain()
        call.enqueue(object : Callback<MainData>{
            override fun onResponse(call: Call<MainData>, response: Response<MainData>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<MainData>, t: Throwable) {
                callback(null)
            }
        })
    }

    override fun getDetails(callback: (ProductDetails?) -> Unit){
        val call = service.getProductDetails()
        call.enqueue(object : Callback<ProductDetails>{
            override fun onResponse(
                    call: Call<ProductDetails>, response: Response<ProductDetails>
            ) {
                callback(response.body())
            }
            override fun onFailure(call: Call<ProductDetails>, t: Throwable) {
                callback(null)
            }
        })
    }

    override fun getCart(callback: (Cart?) -> Unit) {
        val call = service.getCart()
        call.enqueue(object : Callback<Cart>{
            override fun onResponse(
                    call: Call<Cart>, response: Response<Cart>
            ) {
                callback(response.body())
            }
            override fun onFailure(call: Call<Cart>, t: Throwable) {
                callback(null)
            }
        })
    }
}