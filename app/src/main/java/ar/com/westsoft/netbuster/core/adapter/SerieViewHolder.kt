package ar.com.westsoft.netbuster.core.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ar.com.westsoft.netbuster.R
import com.android.volley.toolbox.NetworkImageView

class SerieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imageV: NetworkImageView = view.findViewById(R.id.cardview_image)
    val titleV: TextView = view.findViewById(R.id.cardview_text)
    val starIV: ImageView = view.findViewById(R.id.star_view)
}