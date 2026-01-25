package ar.com.westsoft.netbuster.core.ui

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import ar.com.westsoft.netbuster.databinding.ExpandableEpisodesViewBinding

class ExpandableEpisodesList(ctx: Context) : ConstraintLayout(ctx) {
    val binding: ExpandableEpisodesViewBinding =
        ExpandableEpisodesViewBinding.inflate(LayoutInflater.from(ctx), this, true)

    init {
        binding.season.setOnClickListener { toggle() }
        binding.expandButton.setOnClickListener { toggle() }
    }
    private fun toggle() {
        if (binding.episodeListView.isVisible) {
            binding.episodeListView.visibility = GONE
            binding.expandButton.setImageResource(android.R.drawable.arrow_down_float)
        } else {
            binding.episodeListView.visibility = VISIBLE
            binding.expandButton.setImageResource(android.R.drawable.arrow_up_float)
        }
    }
    fun setTitle(text: String) {
        binding.season.text = text
    }
}