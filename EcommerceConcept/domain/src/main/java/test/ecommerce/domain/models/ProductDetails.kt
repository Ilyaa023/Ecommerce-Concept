package test.ecommerce.domain.models

data class ProductDetails(
        val CPU: String,
        val camera: String,
        val capacity: Array<String>,
        val color: Array<String>,
        val id: String,
        val images: Array<String>,
        val is_favorites: Boolean,
        val price: Float,
        val rating: Float,
        val sd: String,
        val ssd: String,
        val title: String
) {}