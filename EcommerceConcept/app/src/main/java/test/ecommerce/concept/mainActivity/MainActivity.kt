package test.ecommerce.concept.mainActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import test.ecommerce.concept.R
import test.ecommerce.concept.adapters.FilterAdapter
import test.ecommerce.concept.adapters.HotAdapter
import test.ecommerce.concept.cartActivity.CartActivity
import test.ecommerce.concept.databinding.ActivityMainBinding
import test.ecommerce.concept.detailsActivity.DetailsActivity
import test.ecommerce.concept.views.EcommerceItemView
import test.ecommerce.domain.models.BestSellerElement
import kotlin.math.abs

class MainActivity : FragmentActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }

    private lateinit var referencedIds: IntArray

    private var menuState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(binding.root)
        viewModel.data.observe(this) {
            binding.swipeLayout.isRefreshing = false
            val hotList = ArrayList<HotSaleFragment>()
            it.home_store.forEach { hot ->
                hotList.add(HotSaleFragment(hot))
            }
            initHotSellers(hotList)
            val bestList = ArrayList<BestSellerElement>()
            it.best_seller.forEach { best ->
                bestList.add(best)
            }
            initBestSeller(bestList)
        }
        updateContent()
        initCategories()
        initFilter()
        binding.cartButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, CartActivity::class.java))
        }
        super.onCreate(savedInstanceState)
    }

    private fun updateContent(){
        viewModel.getContent {
            Toast.makeText(
                this, getString(R.string.error_network_fail), Toast.LENGTH_SHORT
            ).show()
            binding.swipeLayout.isRefreshing = false
        }
    }

    private fun initCategories(){
        viewModel.selectedCategory.observe(this) {
            with(binding){
                categoryPhoneButton.isActivated = MainViewModel.PHONE_CATEGORY == it
                categoryComputerButton.isActivated = MainViewModel.COMPUTER_CATEGORY == it
                categoryHealthButton.isActivated = MainViewModel.HEALTH_CATEGORY == it
                categoryBooksButton.isActivated = MainViewModel.BOOKS_CATEGORY == it

                categoryPhoneButton.setTextColor(
                    if(categoryPhoneButton.isActivated) getColor(R.color.orange)
                    else getColor(R.color.dark_blue))
                categoryComputerButton.setTextColor(
                    if(categoryComputerButton.isActivated) getColor(R.color.orange)
                    else getColor(R.color.dark_blue))
                categoryHealthButton.setTextColor(
                    if(categoryHealthButton.isActivated) getColor(R.color.orange)
                    else getColor(R.color.dark_blue))
                categoryBooksButton.setTextColor(
                    if(categoryBooksButton.isActivated) getColor(R.color.orange)
                    else getColor(R.color.dark_blue))
            }
        }
        binding.swipeLayout.setOnRefreshListener{
            updateContent()
        }
        binding.swipeLayout
            .setColorSchemeColors(getColor(R.color.dark_blue), getColor(R.color.orange))
        binding.categoryPhoneButton
            .setOnClickListener { viewModel.setCategory(MainViewModel.PHONE_CATEGORY) }
        binding.categoryComputerButton
            .setOnClickListener { viewModel.setCategory(MainViewModel.COMPUTER_CATEGORY) }
        binding.categoryHealthButton
            .setOnClickListener { viewModel.setCategory(MainViewModel.HEALTH_CATEGORY) }
        binding.categoryBooksButton
            .setOnClickListener { viewModel.setCategory(MainViewModel.BOOKS_CATEGORY) }
    }

    private fun initHotSellers(list: List<HotSaleFragment>){
        binding.hotSalesLayout.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE
        with(binding.hotSalesViewPager){
            adapter = HotAdapter(this@MainActivity, list)
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(CompositePageTransformer().apply {
                addTransformer(MarginPageTransformer(40))
                addTransformer { page, position ->
                    page.scaleY = 0.85f + (1 - abs(position)) * 0.15f
                }
            })
        }
    }

    private fun initBestSeller(list: List<BestSellerElement>){
        try {
            for(id in referencedIds){
                with(binding.mainConstraint){
                    removeView(getViewById(id))
                }
            }
        } catch (e: UninitializedPropertyAccessException){}
        finally {
            referencedIds = IntArray(list.size)
            for (index in list.indices){
                referencedIds[index] = EcommerceItemView(this, list[index]).apply {
                    layoutParams = ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                    )
                    id = View.generateViewId()
                    binding.mainConstraint.addView(this)
                    binding.bestSellersFlow.addView(this)
                    setOnClickListener {
                        startActivity(
                            Intent(this@MainActivity, DetailsActivity::class.java)
                        )
                    }
                }.id
            }
            binding.bestSellersFlow.referencedIds = referencedIds
            binding.bestSellersFlow.post { binding.bestSellersFlow.requestLayout() }
        }
    }
    private fun initFilter(){
        binding.filterButton.setOnClickListener {
            if (!menuState)
                binding.bottomFilter.animate()
                    .translationY(-(binding.bottomFilter.height).toFloat())
                    .setListener(null)
                    .duration = 300
            menuState = true
        }
        binding.closeFilterButton.setOnClickListener {
            if (menuState)
                binding.bottomFilter.animate()
                    .translationY((binding.bottomFilter.height).toFloat())
                    .setListener(null)
                    .duration = 300
            menuState = false
        }
        binding.spinnerBrand.adapter = ArrayAdapter
            .createFromResource(this, R.array.strings_filter_names,
                                R.layout.layout_filter_spinner_item)
        binding.spinnerPrice.adapter = ArrayAdapter
            .createFromResource(this, R.array.strings_filter_prices,
                                R.layout.layout_filter_spinner_item)
        binding.spinnerSize.adapter = ArrayAdapter
            .createFromResource(this, R.array.strings_filter_size,
                                R.layout.layout_filter_spinner_item)
    }
}