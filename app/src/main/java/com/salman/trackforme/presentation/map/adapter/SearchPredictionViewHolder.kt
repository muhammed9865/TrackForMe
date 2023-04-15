package com.salman.trackforme.presentation.map.adapter

import androidx.recyclerview.widget.RecyclerView
import com.salman.trackforme.databinding.ItemSearchPredictionBinding
import com.salman.trackforme.domain.model.SearchPrediction

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/9/2023.
 */
class SearchPredictionViewHolder(
    private val binding: ItemSearchPredictionBinding,
    private val onItemClick: (SearchPrediction) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: SearchPrediction) {
        binding.textViewSearchPredictionPrimary.text = item.primaryText
        binding.textViewSearchPredictionSecondary.text = item.secondaryText
        binding.root.setOnClickListener {
            onItemClick(item)
        }
    }
}