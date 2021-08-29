package ar.com.westsoft.netbuster.core.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import ar.com.westsoft.netbuster.databinding.FragmentSearchBinding

class SearchFragment(private val callback: MainActivity) : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        val rootView = binding.view
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)

//        serieAdapter = SerieAdapter(callback, serieArray, callback.favoriteSerieArray)
//        recyclerView.adapter = serieAdapter
//        recyclerView.invalidate()
//
//        GlobalScope.launch {
//            serieArray = callback.tvAPIClient?.getSyncSerieArrayJsonResponse("girl")?:serieArray
//            activity?.runOnUiThread {
//                recyclerView.adapter = SerieAdapter(callback, serieArray, callback.favoriteSerieArray)
//                recyclerView.invalidate()
//            }
//        }

//        val searchField:EditText = rootView.findViewById(R.id.search_key)
//        val delButton:ImageButton = rootView.findViewById(R.id.delete_text_button)
//        val searchButton:ImageButton = rootView.findViewById(R.id.search_button)

//        delButton.setOnClickListener { searchField.setText("") }
//        searchButton.setOnClickListener {
//            GlobalScope.launch {
//                serieArray = callback.tvAPIClient?.
//                        getSyncSerieArrayJsonResponse(searchField.text.toString())?:serieArray
//                activity?.runOnUiThread {
//                    recyclerView.adapter = SerieAdapter(callback, serieArray, callback.favoriteSerieArray)
//                    recyclerView.invalidate()
//
//                    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                    imm.hideSoftInputFromWindow(view?.windowToken, 0)
//                }
//            }
//        }
        return rootView
    }
}



