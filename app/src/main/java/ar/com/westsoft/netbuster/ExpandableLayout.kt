package ar.com.westsoft.netbuster

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.core.view.isVisible
import org.json.JSONObject

class ExpandableSeasonList(ctx: Context, attrs: AttributeSet?) :
    ConstraintLayout(ctx, attrs) {

    init {
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.expandable_sessons_view,this)

        val seasonButton: Button = findViewById(R.id.season_button)
        val expandButton: ImageButton = findViewById(R.id.expand_button)
        val seasonListView: LinearLayout = findViewById(R.id.season_list)

        seasonButton.setOnClickListener {
            if (seasonListView.isVisible) {
                seasonListView.visibility = GONE
                expandButton.setImageResource(android.R.drawable.arrow_down_float)
            } else {
                seasonListView.visibility = VISIBLE
                expandButton.setImageResource(android.R.drawable.arrow_up_float)
            }
            this.invalidate()
        }
    }
    fun setTitle(text: String) {
        findViewById<Button>(R.id.season_button).text = text
    }
    fun appendSeason(idx: Int) {
        findViewById<LinearLayout>(R.id.season_list).
            addView(ExpandableEpisodesList(context))
    }
    fun getSeason(idx: Int): ExpandableEpisodesList =
        findViewById<LinearLayout>(R.id.season_list).
            get(idx) as ExpandableEpisodesList
}
class ExpandableEpisodesList(ctx: Context) : ConstraintLayout(ctx) {
    init {
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.expandable_episodes_view,this)

        val seasonButton: Button = findViewById(R.id.season)
        val expandButton: ImageButton = findViewById(R.id.expandButton)
        val seasonListView: LinearLayout = findViewById(R.id.episodeListView)

        seasonButton.setOnClickListener {
            if (seasonListView.isVisible) {
                seasonListView.visibility = GONE
                expandButton.setImageResource(android.R.drawable.arrow_down_float)
            } else {
                seasonListView.visibility = VISIBLE
                expandButton.setImageResource(android.R.drawable.arrow_up_float)
            }
        }
    }
    fun setTitle(text: String) {
        findViewById<Button>(R.id.season).text = text
    }
    fun appendEpisode(jsonObject: JSONObject) {
        val textView = TextView(context)
        textView.text = "Texto Prueba"
        findViewById<LinearLayout>(R.id.episodeListView).
            addView(textView)
    }
    fun getEpisode(idx: Int): TextView {
        return findViewById<LinearLayout>(R.id.season_list)[idx] as TextView
    }
}