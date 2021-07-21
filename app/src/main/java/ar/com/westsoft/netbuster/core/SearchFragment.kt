package ar.com.westsoft.netbuster.core

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.com.westsoft.netbuster.R
import com.android.volley.toolbox.NetworkImageView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray

class SearchFragment(val callback: MainActivity) : Fragment() {

    var serieAdapter: SerieAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)
        var serieArray =  JSONArray()

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        serieAdapter = SerieAdapter(callback, serieArray, callback.favoritySerieArray)
        recyclerView.adapter = serieAdapter
        recyclerView.invalidate()

        GlobalScope.launch {
            serieArray = callback.tvAPIClient?.getSyncSerieArrayJsonResponse("girl")?:serieArray
            activity?.runOnUiThread {
                recyclerView.adapter = SerieAdapter(callback, serieArray, callback.favoritySerieArray)
                recyclerView.invalidate()
            }
        }

        val searchField:EditText = rootView.findViewById(R.id.search_key)
        val delButton:ImageButton = rootView.findViewById(R.id.delete_text_button)
        val searchButton:ImageButton = rootView.findViewById(R.id.search_button)

        delButton.setOnClickListener { searchField.setText("") }
        searchButton.setOnClickListener {
            GlobalScope.launch {
                serieArray = callback.tvAPIClient?.
                        getSyncSerieArrayJsonResponse(searchField.text.toString())?:serieArray
                activity?.runOnUiThread {
                    recyclerView.adapter = SerieAdapter(callback, serieArray, callback.favoritySerieArray)
                    recyclerView.invalidate()

                    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view?.windowToken, 0)
                }
            }
        }
        return rootView
    }
}



