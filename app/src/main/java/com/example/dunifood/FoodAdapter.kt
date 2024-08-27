package com.example.dunifood

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FoodAdapter(private val data :ArrayList<Food> , private val foodEvents: FoodEvents) :RecyclerView.Adapter <FoodAdapter.FoodViewHolder> () {


    inner class FoodViewHolder( itemView: View , private val context: Context) :RecyclerView.ViewHolder( itemView ) {

        val imgMain = itemView.findViewById<ImageView>(R.id.item_img_main)
        val txtSubject = itemView.findViewById<TextView>(R.id.iteme_txt_subject)
        val txtCity = itemView.findViewById<TextView>(R.id.item_txt_city)
        val txtPrice = itemView.findViewById<TextView>(R.id.item_txt_price)
        val txtDistance = itemView.findViewById<TextView>(R.id.item_txt_distance)
        val ratingBar = itemView.findViewById<RatingBar>(R.id.item_rating_main)
        val txtRating = itemView.findViewById<TextView>(R.id.item_txt_rating)

        @SuppressLint("SetTextI18n")
        fun bindData(position: Int ){

            txtSubject.text = data[position].txtSubject
            txtCity.text = data[position].txtCity
            txtPrice.text = "$" + data[position].txtPrice + "vip"
            txtDistance.text = data[position].txtDistance + "miles from you"
            ratingBar.rating =data[position].rating
            txtRating.text = "(" + data[position].numOfRating.toString() + "Ratings)"



           Glide
               .with(context)
               .load(data[position].urlImage)
               .into(imgMain)

            itemView.setOnClickListener{

                foodEvents.onFoodCliked(data[adapterPosition] ,adapterPosition)
            }

            itemView.setOnLongClickListener{

                foodEvents.onFoodLongCliced(data[adapterPosition] ,adapterPosition)
                true
            }

        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food ,parent , false)
        return FoodViewHolder( view , parent.context)

    }

    override fun getItemCount(): Int {

        return data.size

    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {

       holder.bindData( position )

    }

    //addFood
    fun addFood(newFood :Food){

        data.add(0 , newFood)
        notifyItemInserted(0)

    }

    //removeFood
    fun removeFood(oldFood :Food , oldPosition :Int){

        data.remove(oldFood)
        notifyItemRemoved(oldPosition)

    }

    //update
    fun updateFood(newFood: Food , position: Int){
        data.set(position , newFood)
        notifyItemChanged(position)
    }

    //set new data to list
    fun setData (newList: ArrayList<Food>){
        data.clear()
        data.addAll(newList)

        notifyDataSetChanged()
    }

    interface FoodEvents{

        fun onFoodCliked(food: Food , position: Int)
        fun onFoodLongCliced(food: Food ,pos: Int)

    }

}