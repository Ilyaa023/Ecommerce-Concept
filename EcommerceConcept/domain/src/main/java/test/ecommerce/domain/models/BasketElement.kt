package test.ecommerce.domain.models

data class BasketElement(
        val id: Int,
        val images: String,
        val price: Float,
        val title: String
)
