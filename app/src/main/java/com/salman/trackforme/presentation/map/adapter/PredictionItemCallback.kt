package com.salman.trackforme.presentation.map.adapter

import androidx.recyclerview.widget.DiffUtil
import com.salman.trackforme.domain.model.SearchPrediction

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/9/2023.
 */
class PredictionItemCallback : DiffUtil.ItemCallback<SearchPrediction>() {
    override fun areItemsTheSame(oldItem: SearchPrediction, newItem: SearchPrediction): Boolean {
        return oldItem.placeId == newItem.placeId
    }

    override fun areContentsTheSame(oldItem: SearchPrediction, newItem: SearchPrediction): Boolean {
        return oldItem == newItem
    }
}
