package ar.com.westsoft.netbuster.core.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import ar.com.westsoft.netbuster.databinding.ExpandableSessonsViewBinding

class ExpandableSeasonList(ctx: Context, attrs: AttributeSet?) : ConstraintLayout(ctx, attrs) {

    val binding = ExpandableSessonsViewBinding.inflate(LayoutInflater.from(ctx), this, true)

    init {
        binding.seasonButton.setOnClickListener { toggle() }
        binding.expandButton.setOnClickListener { toggle() }
    }
    private fun toggle() {
        if (binding.seasonList.isVisible) {
            binding.seasonList.visibility = GONE
            binding.expandButton.setImageResource(android.R.drawable.arrow_down_float)
        } else {
            binding.seasonList.visibility = VISIBLE
            binding.expandButton.setImageResource(android.R.drawable.arrow_up_float)
        }
    }
    fun setTitle(text: String) {
        binding.seasonButton.text = text
    }
}
