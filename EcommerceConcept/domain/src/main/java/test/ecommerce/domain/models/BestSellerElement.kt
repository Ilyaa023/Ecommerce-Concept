package test.ecommerce.domain.models

data class BestSellerElement(
        val id: Int,
        val is_favorites: Boolean,
        val title: String,
        val price_without_discount: Float,
        val discount_price: Float,
        val picture: String
) {}