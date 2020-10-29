package com.adematici.yemektarifleri.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.adematici.yemektarifleri.R
import com.adematici.yemektarifleri.adapter.YemekRecylcerAdapter
import com.adematici.yemektarifleri.viewmodel.YemekListesiViewModel
import kotlinx.android.synthetic.main.fragment_yemek_listesi.*

class YemekListesiFragment : Fragment() {

    private lateinit var viewModel: YemekListesiViewModel
    private val recyclerYemekAdapter = YemekRecylcerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_yemek_listesi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(YemekListesiViewModel::class.java)
        viewModel.refreshData()

        yemekListRecycler.layoutManager = LinearLayoutManager(context)
        yemekListRecycler.adapter = recyclerYemekAdapter

        swipeRefreshLayout.setOnRefreshListener {
            yemek_listesi_progressbar.visibility = View.VISIBLE
            yemek_listesi_hata.visibility = View.GONE
            yemekListRecycler.visibility = View.GONE
            viewModel.refreshFromInternet()
            swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()

    }

    fun observeLiveData(){
        viewModel.yemekler.observe(viewLifecycleOwner, Observer { yemekler ->
            yemekler?.let {
                yemekListRecycler.visibility = View.VISIBLE
                recyclerYemekAdapter.yemekListesiGuncelle(yemekler)
            }
        })
        viewModel.yemekHataMesaji.observe(viewLifecycleOwner, Observer { hata ->
            hata?.let {
                if(it){
                    yemek_listesi_hata.visibility = View.VISIBLE
                    yemekListRecycler.visibility = View.GONE
                } else {
                    yemek_listesi_hata.visibility = View.GONE
                }
            }
        })
        viewModel.yemekYukleniyor.observe(viewLifecycleOwner, Observer { yukleniyor ->
            yukleniyor?.let {
                if(it){
                    yemekListRecycler.visibility = View.GONE
                    yemek_listesi_hata.visibility = View.GONE
                    yemek_listesi_progressbar.visibility = View.VISIBLE
                } else {
                    yemek_listesi_progressbar.visibility = View.GONE
                }
            }
        })
    }

}