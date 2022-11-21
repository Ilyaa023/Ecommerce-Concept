package test.ecommerce.concept.detailsActivity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import test.ecommerce.concept.R
import test.ecommerce.concept.adapters.DetailsPhotoAdapter
import test.ecommerce.concept.cartActivity.CartActivity
import test.ecommerce.concept.databinding.ActivityDetailsBinding
import kotlin.math.abs

class DetailsActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailsBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this)[DetailsViewModel::class.java] }

    private lateinit var referencedColorIds: IntArray
    private lateinit var referencedCapacityIds: IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.data.observe(this){
            binding.swipeLayout.isRefreshing = false
            val imagesUrls = ArrayList<String>()
            it.images.forEach { image ->
                imagesUrls.add(image)
            }
            binding.detailRating.rating = it.rating
            binding.detailName.text = it.title
            binding.cpuText.text = it.CPU
            binding.cameraText.text = it.camera
            binding.ramText.text = it.ssd
            binding.sdText.text = it.sd
            binding.buyButton.text = getString(R.string.button_add_to_cart) + "\t\t\$${it.price}"
            try {
                for(id in referencedColorIds){
                    with(binding.detailsLayoutWithFlow){
                        removeView(getViewById(id))
                    }
                }
                for(id in referencedCapacityIds){
                    with(binding.detailsLayoutWithFlow){
                        removeView(getViewById(id))
                    }
                }
            } catch (e: UninitializedPropertyAccessException){}
            finally {
                referencedColorIds = IntArray(it.color.size)
                it.color.forEach { color ->
                    val backDrawable = ResourcesCompat.getDrawable(
                        resources, R.drawable.background_oval_colored,null
                    ) as GradientDrawable
                    backDrawable.setColor(Color.parseColor(color))
                    referencedColorIds[it.color.indexOf(color)] = ImageView(this).apply {
                        background = backDrawable
                        val params = ConstraintLayout.LayoutParams(100, 100)
                        layoutParams = params

                        viewModel.checkedColor.observe(this@DetailsActivity){ chColor ->
                            setImageDrawable(if (color == chColor) ResourcesCompat.getDrawable(
                                resources, R.drawable.ic_checked, null
                            )
                                             else null)
                        }
                        setPadding(20, 20, 20, 20)
                        setOnClickListener { viewModel.checkedColor.postValue(color) }
                        id = View.generateViewId()
                        binding.detailsLayoutWithFlow.addView(this)
                        binding.colorsFlow.addView(this)
                    }.id
                }
                referencedCapacityIds = IntArray(it.capacity.size)
                it.capacity.forEach { capacity ->
                    val backDrawable = ResourcesCompat.getDrawable(
                        resources, R.drawable.background_button_done,null
                    ) as GradientDrawable
                    referencedCapacityIds[it.capacity.indexOf(capacity)] = Button(this)
                        .apply {
                        viewModel.checkedCapacity.observe(this@DetailsActivity) { chCapacity ->
                            backDrawable.setColor(when(chCapacity){
                                                      capacity -> getColor(R.color.orange)
                                                      null -> getColor(R.color.white)
                                                      else -> getColor(R.color.white)
                            })
                            setTextColor(getColor(if (capacity == chCapacity) R.color.white
                                                  else R.color.black_overlay))
                            text = "$capacity ${if (capacity == chCapacity) "GB" else "gb"}"
                        }
                        background = backDrawable
                        layoutParams = ConstraintLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            typeface = resources.getFont(R.font.mark_pro)
                        }
                        textSize = 13f
                        isAllCaps = false
                        viewModel.checkedCapacity.postValue(capacity)
                        setOnClickListener { viewModel.checkedCapacity.postValue(capacity) }
                        id = View.generateViewId()
                        binding.detailsLayoutWithFlow.addView(this)
                        binding.capFlow.addView(this)
                    }.id
                }
                binding.capFlow.referencedIds = referencedCapacityIds
                binding.colorsFlow.referencedIds = referencedColorIds
                binding.colorsFlow.post { binding.colorsFlow.requestLayout() }
                binding.capFlow.post { binding.capFlow.requestLayout() }
                binding.colCapFlow.post { binding.colCapFlow.requestLayout() }
            }


            binding.buttonFavourite.setImageDrawable(ResourcesCompat.getDrawable(
                resources,
                if (!it.is_favorites) R.drawable.ic_liked_active else R.drawable.ic_liked,
                null
            ))
            initViewPager(imagesUrls)
        }
        updateContent()
        binding.swipeLayout.setOnRefreshListener{
            updateContent()
        }
        binding.swipeLayout
            .setColorSchemeColors(getColor(R.color.dark_blue), getColor(R.color.orange))
        binding.backButton.setOnClickListener { finish() }
        binding.cartButton.setOnClickListener { startActivity(Intent(this@DetailsActivity, CartActivity::class.java)) }
    }

    private fun initViewPager(images: List<String>) = with(binding.detailsViewPager) {
        adapter = DetailsPhotoAdapter(images)
        clipToPadding = false
        clipChildren = false
        offscreenPageLimit = 3
        getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        setPageTransformer(CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
            addTransformer{ page, position ->
                page.scaleY = 0.85f + (1 - abs(position)) * 0.15f
            }
        })
    }

    private fun updateContent() {
        viewModel.getContent {
            Toast.makeText(
                this, getString(R.string.error_network_fail), Toast.LENGTH_SHORT
            ).show()
            binding.swipeLayout.isRefreshing = false
        }
    }
}