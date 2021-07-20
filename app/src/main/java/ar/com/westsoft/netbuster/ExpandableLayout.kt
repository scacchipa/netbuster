package ar.com.westsoft.netbuster

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible

class ExpandableSeasonList(ctx: Context, attrs: AttributeSet?) :
    ConstraintLayout(ctx, attrs) {

    init {
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.expandable_sessons_view,this)

        val seasonButton: Button = findViewById(R.id.season_button)
        val expandButton: ImageButton = findViewById(R.id.expand_button)
        val seasonListView: LinearLayout = findViewById(R.id.season_list)

        seasonButton.setOnClickListener { toggle(seasonListView, expandButton) }
        expandButton.setOnClickListener { toggle(seasonListView, expandButton) }
    }
    private fun toggle(seasonListView: LinearLayout, expandButton: ImageButton ) {
        if (seasonListView.isVisible) {
            seasonListView.visibility = GONE
            expandButton.setImageResource(android.R.drawable.arrow_down_float)
        } else {
            seasonListView.visibility = VISIBLE
            expandButton.setImageResource(android.R.drawable.arrow_up_float)
        }
    }
    fun setTitle(text: String) {
        findViewById<Button>(R.id.season_button).text = text
    }
}
class ExpandableEpisodesList(ctx: Context) : ConstraintLayout(ctx) {
    init {
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.expandable_episodes_view,this)

        val seasonButton: Button = findViewById(R.id.season)
        val expandButton: ImageButton = findViewById(R.id.expandButton)
        val episodeListView: LinearLayout = findViewById(R.id.episodeListView)

        seasonButton.setOnClickListener { toggle(episodeListView, expandButton) }
        expandButton.setOnClickListener { toggle(episodeListView, expandButton) }
    }
    private fun toggle(episodeListView: LinearLayout, expandButton: ImageButton ) {
        if (episodeListView.isVisible) {
            episodeListView.visibility = GONE
            expandButton.setImageResource(android.R.drawable.arrow_down_float)
        } else {
            episodeListView.visibility = VISIBLE
            expandButton.setImageResource(android.R.drawable.arrow_up_float)
        }
    }
    fun setTitle(text: String) {
        findViewById<Button>(R.id.season).text = text
    }
}