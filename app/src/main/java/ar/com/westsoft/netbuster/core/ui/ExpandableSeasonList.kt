package ar.com.westsoft.netbuster.core

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import ar.com.westsoft.netbuster.R

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
