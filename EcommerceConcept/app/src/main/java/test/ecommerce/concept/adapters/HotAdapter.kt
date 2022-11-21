package test.ecommerce.concept.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class HotAdapter(activity: FragmentActivity,
                 private val list: List<Fragment>): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment = list[position]

}