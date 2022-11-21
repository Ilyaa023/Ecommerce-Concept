package test.ecommerce.concept.adapters

import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import test.ecommerce.concept.R

class FilterAdapter(
        context: Context,
        resourceId: Int
) : ArrayAdapter<String>(context, 0, context.resources.getStringArray(resourceId)) {
    val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.layout_filter_spinner_item, parent, false)
        } else {
            view = convertView
        }
        getItem(position)?.let {
            setItemForFilter(view, it)
        }
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = layoutInflater.inflate(R.layout.layout_filter_spinner_item, parent, false)
//        val dropdownImage = view.findViewById<ImageView>(R.id.dropdown_image)
        if (position == 0){
//            dropdownImage.visibility = View.VISIBLE
            view.setOnClickListener {
                parent.rootView.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK))
                parent.rootView.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK))
            }
        } else {
//            dropdownImage.visibility = View.GONE
        }
        getItem(position)?.let {
            setItemForFilter(view, it)
        }

        return view
    }
    override fun getItem(position: Int): String? {
        if (position == 0) {
            return null
        }
        return super.getItem(position - 1)
    }
    override fun getCount() = super.getCount() + 1
    override fun isEnabled(position: Int) = position != 0

    private fun setItemForFilter(view: View, filter: String) {
        val tvFilter = view.findViewById<TextView>(R.id.filter_text)
        tvFilter.text = filter
    }
}