package com.salman.trackforme.presentation.map.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.salman.trackforme.databinding.ItemSearchPredictionBinding
import com.salman.trackforme.domain.model.SearchPrediction

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/9/2023.
 */
class SearchAdapter(
    private val onItemClick: (SearchPrediction) -> Unit
) : ListAdapter<SearchPrediction, SearchPredictionViewHolder>(PredictionItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchPredictionViewHolder {
        return SearchPredictionViewHolder(
            ItemSearchPredictionBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            ),
            onItemClick
        )
    }


    override fun onBindViewHolder(holder: SearchPredictionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}