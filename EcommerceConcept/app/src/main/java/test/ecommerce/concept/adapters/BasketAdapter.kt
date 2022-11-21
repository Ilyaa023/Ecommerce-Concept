package test.ecommerce.concept.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import test.ecommerce.concept.cartActivity.CartViewModel
import test.ecommerce.concept.databinding.LayoutCartElementBinding
import test.ecommerce.domain.models.BasketElement

class BasketAdapter(private val list: Array<BasketElement>,
                    private val viewModel: CartViewModel,
                    private val lifecycleOwner: LifecycleOwner
): RecyclerView.Adapter<BasketHolder>() {
    override fun onBindViewHolder(holder: BasketHolder, position: Int) {
        holder.setContent(list[position], viewModel, position, lifecycleOwner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketHolder {
        return BasketHolder(LayoutCartElementBinding.inflate(LayoutInflater.from(parent.context),
                                                             parent, false))
    }

    override fun getItemCount() = list.size
}

class BasketHolder(private val binding: LayoutCartElementBinding): RecyclerView.ViewHolder(binding.root){
    fun setContent(basket: BasketElement, viewModel: CartViewModel,
                   position: Int, lifecycleOwner: LifecycleOwner) = with(binding){
        itemName.text = basket.title
        itemPrice.text = "\$${basket.price}"
        Glide.with(binding.root.context)
            .load(basket.images)
            .centerCrop()
            .into(itemImage)
        viewModel.counters.observe(lifecycleOwner){
            try {
                binding.itemCount.text = it[position].toString()
            }catch (e:ArrayIndexOutOfBoundsException){
                binding.itemCount.text = "0"
            }
        }
        itemMinus.setOnClickListener {
            viewModel.minusCounters(position)
        }
        itemPlus.setOnClickListener {
            viewModel.plusCounters(position)
        }
    }
}