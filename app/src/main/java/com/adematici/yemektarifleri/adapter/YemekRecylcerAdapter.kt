package com.adematici.yemektarifleri.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.adematici.yemektarifleri.R
import com.adematici.yemektarifleri.model.Yemek
import com.adematici.yemektarifleri.util.gorselIndir
import com.adematici.yemektarifleri.util.placeholderyap
import com.adematici.yemektarifleri.view.YemekListesiFragmentDirections
import kotlinx.android.synthetic.main.yemek_recycler_row.view.*

class YemekRecylcerAdapter(val yemekListesi: ArrayList<Yemek>) : RecyclerView.Adapter<YemekRecylcerAdapter.YemekViewHolder>() {
    class YemekViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    }

    override fun getItemCount(): Int {
        return yemekListesi.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YemekViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.yemek_recycler_row,parent,false)
        return YemekViewHolder(view)
    }

    override fun onBindViewHolder(holder: YemekViewHolder, position: Int) {
        holder.itemView.yemek_recylcler_isim.text = yemekListesi.get(position).yemekIsim
        //görsel gelicek
        holder.itemView.setOnClickListener {
            val action = YemekListesiFragmentDirections.actionYemekListesiFragmentToYemekDetayiFragment(yemekListesi.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }
        holder.itemView.yemek_recylcler_imageView.gorselIndir(yemekListesi.get(position).yemekGorsel,
            placeholderyap(holder.itemView.context))
    }

    fun yemekListesiGuncelle(yeniYemekListesi: List<Yemek>){
        yemekListesi.clear()
        yemekListesi.addAll(yeniYemekListesi)
        notifyDataSetChanged()
    }

}