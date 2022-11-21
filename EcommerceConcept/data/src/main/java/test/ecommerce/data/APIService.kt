package test.ecommerce.data

import retrofit2.Call
import retrofit2.http.GET
import test.ecommerce.domain.models.Cart
import test.ecommerce.domain.models.MainData
import test.ecommerce.domain.models.ProductDetails

interface APIService {
    @GET("654bd15e-b121-49ba-a588-960956b15175")
    fun getMain(): Call<MainData>

    @GET("6c14c560-15c6-4248-b9d2-b4508df7d4f5")
    fun getProductDetails(): Call<ProductDetails>

    @GET("53539a72-3c5f-4f30-bbb1-6ca10d42c149")
    fun getCart(): Call<Cart>
}