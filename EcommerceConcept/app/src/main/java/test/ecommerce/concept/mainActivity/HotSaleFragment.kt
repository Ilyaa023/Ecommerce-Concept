package test.ecommerce.concept.mainActivity

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import test.ecommerce.concept.databinding.FragmentHotSaleBinding
import test.ecommerce.domain.models.HomeStoreElement
import java.security.MessageDigest


class HotSaleFragment(private val homeStoreElement: HomeStoreElement? = null) : Fragment() {

    private lateinit var binding: FragmentHotSaleBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHotSaleBinding.inflate(inflater, container, false)
        with(binding){
            homeStoreElement?.let {
                textTitle.text = it.title
                textSubtitle.text = it.subtitle
                buyButton.visibility = if (it.is_buy) View.VISIBLE else View.INVISIBLE
                iconNew.visibility = if (it.is_new) View.VISIBLE else View.INVISIBLE
                val image = ImageView(binding.hotCard.context)
                image.maxWidth = binding.hotCard.width
                image.maxHeight = binding.hotCard.height
                image.scaleType = ImageView.ScaleType.FIT_XY
                GlideApp.with(image)
                    .load(homeStoreElement.picture)
                    .transform(CutOffHot(Resources.getSystem().displayMetrics.widthPixels,
                                         Resources.getSystem().displayMetrics.heightPixels / 3))
                    .into(image)
                binding.backgroundImage.addView(image)
            }
        }
        return binding.root
    }
}

@GlideModule
class EcommerceGlideApp : AppGlideModule()

class CutOffHot(val width: Int, val height: Int) : BitmapTransformation() {
    override fun transform(
            pool: BitmapPool,
            toTransform: Bitmap,
            outWidth: Int,
            outHeight: Int
    ): Bitmap{
        val cutBitmap = if (width / height > toTransform.width / toTransform.height)
            Bitmap.createBitmap(toTransform, 0, 0,
                                toTransform.width, toTransform.height * width / height)
        else Bitmap.createBitmap(toTransform, 0, 0,
                                 toTransform.width * height / width, toTransform.height)
        return Bitmap.createScaledBitmap(cutBitmap, width, height, false)
    }
    override fun updateDiskCacheKey(messageDigest: MessageDigest) {}
}