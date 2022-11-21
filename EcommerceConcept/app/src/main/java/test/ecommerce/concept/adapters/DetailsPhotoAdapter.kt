package test.ecommerce.concept.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import test.ecommerce.concept.R
import test.ecommerce.concept.databinding.LayoutDetailsPhotosBinding

class DetailsPhotoAdapter(private val list: List<String>): RecyclerView.Adapter<DetailViewHolder>() {
    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.setImage(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(
            LayoutDetailsPhotosBinding.inflate(LayoutInflater.from(parent.context),
                                               parent, false).root
        )
    }

    override fun getItemCount() = list.size
}

class DetailViewHolder(private val view: View): RecyclerView.ViewHolder(view){
    fun setImage(url: String) {
        Glide.with(view)
            .load(url)
            .centerCrop()
            .into(view.findViewById(R.id.detail_image))
    }

}