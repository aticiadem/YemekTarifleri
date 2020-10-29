package com.adematici.yemektarifleri.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adematici.yemektarifleri.R
import com.adematici.yemektarifleri.util.gorselIndir
import com.adematici.yemektarifleri.util.placeholderyap
import com.adematici.yemektarifleri.viewmodel.YemekDetayiViewModel
import kotlinx.android.synthetic.main.fragment_yemek_detayi.*

class YemekDetayiFragment : Fragment() {

    private lateinit var viewModel: YemekDetayiViewModel
    private var yemekId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_yemek_detayi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            yemekId = YemekDetayiFragmentArgs.fromBundle(it).yemekId
        }

        viewModel = ViewModelProviders.of(this).get(YemekDetayiViewModel::class.java)
        viewModel.roomVerisiniAl(yemekId)

        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.yemekLiveData.observe(viewLifecycleOwner, Observer { yemek ->
            yemek?.let {
                yemek_detay_isim.text = it.yemekIsim
                yemek_detay_malzemeler.text = it.yemekMalzeme
                yemek_detay_yapilis.text = it.yemekYapilis
                context?.let {
                    yemek_detay_image.gorselIndir(yemek.yemekGorsel, placeholderyap(it))
                }
            }
        })
    }


}