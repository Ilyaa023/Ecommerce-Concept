package test.ecommerce.concept.views

import android.content.Context
import android.graphics.Paint
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import test.ecommerce.concept.R
import test.ecommerce.domain.models.BestSellerElement

class EcommerceItemView(context: Context, element: BestSellerElement): FrameLayout(context) {
    init {
        inflate(context, R.layout.layout_best_seller, this).apply {
            findViewById<TextView>(R.id.text_item_name).text = element.title
            findViewById<TextView>(R.id.text_sale_price).text = "\$${element.price_without_discount}"
            val usualPrice = findViewById<TextView>(R.id.text_usual_price)
            usualPrice.text = "\$${element.discount_price}"
            usualPrice.paintFlags += Paint.STRIKE_THRU_TEXT_FLAG
            findViewById<ImageView>(R.id.button_favorite).setImageDrawable(
                resources.getDrawable(if (element.is_favorites) R.drawable.ic_favorite
                                      else R.drawable.ic_not_favorite, null))
            val itemImage = findViewById<ImageView>(R.id.item_image)
            Glide.with(itemImage.context)
                .load(element.picture)
                .into(itemImage)
        }
        requestLayout()
    }
}