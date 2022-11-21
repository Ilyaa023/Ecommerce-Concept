package test.ecommerce.domain.models

data class Cart(
        val basket: Array<BasketElement>,
        val id: String,
        val delivery: String,
        val total: Float
)
