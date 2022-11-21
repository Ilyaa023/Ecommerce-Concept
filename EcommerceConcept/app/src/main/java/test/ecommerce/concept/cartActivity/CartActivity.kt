package test.ecommerce.concept.cartActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import test.ecommerce.concept.R
import test.ecommerce.concept.adapters.BasketAdapter
import test.ecommerce.concept.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCartBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this)[CartViewModel::class.java] }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.data.observe(this){
            binding.deliveryText.text = it.delivery
            binding.totalPriceText.text = "\$${it.total} us"
            binding.cartRecycler.adapter = BasketAdapter(
                it.basket, viewModel, this@CartActivity
            )
            binding.cartRecycler.layoutManager = LinearLayoutManager(this)
        }
        viewModel.getContent {
            Toast.makeText(
                this, getString(R.string.error_network_fail), Toast.LENGTH_SHORT
            ).show()
        }
        binding.backButton.setOnClickListener { finish() }
    }
}