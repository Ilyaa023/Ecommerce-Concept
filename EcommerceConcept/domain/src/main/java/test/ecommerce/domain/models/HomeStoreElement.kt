package test.ecommerce.domain.models

data class HomeStoreElement(
        val id: Int,
        val is_new: Boolean = false,
        val title: String,
        val subtitle: String,
        val picture: String,
        val is_buy: Boolean
) {}