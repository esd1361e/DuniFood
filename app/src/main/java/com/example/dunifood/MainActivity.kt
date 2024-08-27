package com.example.dunifood

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dunifood.databinding.ActivityMainBinding
import com.example.dunifood.databinding.DialogAddNewItemBinding
import com.example.dunifood.databinding.DialogDeleteItemBinding
import com.example.dunifood.databinding.DialogUpdateItemBinding

class MainActivity : AppCompatActivity() , FoodAdapter.FoodEvents {
    private lateinit var binding: ActivityMainBinding
    lateinit var myAdapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView( binding.root )

         ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val foodList = arrayListOf(

            Food( "Hamburger" , "15" , "3" , "Isfahan, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg" ,  20 , 4.5f ) ,
            Food( "Grilled fish" , "20" , "2.1" , "Tehran, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg" ,  10 , 4f ) ,
            Food( "Lasania" , "40" , "1.4" , "Isfahan, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg" ,  30 , 2f ) ,
            Food( "pizza" , "10" , "2.5" , "Zahedan, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg" ,  80 , 1.5f ) ,
            Food( "Sushi" , "20" , "3.2" , "Mashhad, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg" ,  200 , 3f ) ,
            Food( "Roasted Fish" , "40" , "3.7" , "Jolfa, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg" ,  50 , 3.5f ) ,
            Food( "Fried chicken" , "70" , "3.5" , "NewYork, USA" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg" ,  70 , 2.5f ) ,
            Food( "Vegetable salad" , "12" , "3.6" , "Berlin, Germany" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg" ,  40 , 4.5f ) ,
            Food( "Grilled chicken" , "10" , "3.7" , "Beijing, China" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg" ,  15 , 5f ) ,
            Food( "Baryooni" , "16" , "10" , "Ilam, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg" ,  28 , 4.5f ) ,
            Food( "Ghorme Sabzi" , "11.5" , "7.5" , "Karaj, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg" ,  27 , 5f ) ,
            Food( "Rice" , "12.5" , "2.4" , "Shiraz, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg" ,  35 , 2.5f ) ,

        )
        myAdapter = FoodAdapter(foodList.clone() as ArrayList<Food>,this)
        binding.recyclerMain.adapter = myAdapter

        binding.recyclerMain.layoutManager = LinearLayoutManager( this,RecyclerView.VERTICAL,false )


        //addFood
        binding.btnAddNewFood.setOnClickListener {

            val dialog = AlertDialog.Builder(this).create()

            val dialogBinding = DialogAddNewItemBinding.inflate(layoutInflater)
            dialog.setView(dialogBinding.root)
            dialog.setCancelable(true)

            dialog.show()

            dialogBinding.dialogBtnDone.setOnClickListener{

                if (
                    dialogBinding.dialogEdtFoodName.length() > 0 &&
                    dialogBinding.dialogEdtFoodCity.length() > 0 &&
                    dialogBinding.dialogEdtFoodPrice.length() > 0 &&
                    dialogBinding.dialogEdtFoodDistance.length() > 0
                ){

                    val txtName = dialogBinding.dialogEdtFoodName.text.toString()
                    val txtPrice =dialogBinding.dialogEdtFoodPrice.text.toString()
                    val txtDistans =dialogBinding.dialogEdtFoodDistance.text.toString()
                    val txtCity = dialogBinding.dialogEdtFoodCity.text.toString()
                    val textRatingNumber :Int = (1..150).random()
                    val ratingBarStar :Float = (1..5).random().toFloat()

                    val randumForUrl = (0 until 12).random()
                    val urlPic = foodList[randumForUrl].urlImage

                    val newFood = Food(txtName , txtPrice , txtDistans , txtCity , urlPic , textRatingNumber , ratingBarStar)
                    myAdapter.addFood(newFood)

                    dialog.dismiss()
                    binding.recyclerMain.scrollToPosition(0)

                }else{

                    Toast.makeText(this , "لطفا همه مقادیر را وارد کنید " , Toast.LENGTH_SHORT).show()

                }


            }


        }

        binding.edtSearch.addTextChangedListener {editTextInput ->

            if (editTextInput!!.isNotEmpty()) {

                    //filter data

                val cloneList = foodList.clone() as ArrayList<Food>
                val filteredList = cloneList.filter { foodItem ->
                   foodItem.txtSubject.contains( editTextInput )

                }

                myAdapter.setData(ArrayList(filteredList))

                } else{

                    //show all data
                    myAdapter.setData(foodList.clone() as ArrayList<Food>)


            }

        }

    }

    override fun onFoodCliked(food: Food , position :Int) {

        val dialog =AlertDialog.Builder(this).create()

        val updateDialogBinding = DialogUpdateItemBinding.inflate(layoutInflater)

        dialog.setView(updateDialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()


        updateDialogBinding.dialogEdtFoodName.setText(food.txtSubject)
        updateDialogBinding.dialogEdtFoodCity.setText(food.txtCity)
        updateDialogBinding.dialogEdtFoodPrice.setText(food.txtPrice)
        updateDialogBinding.dialogEdtFoodDistance.setText(food.txtDistance)

        updateDialogBinding.dialogupddateBtnCancel.setOnClickListener{
            dialog.dismiss()
        }

        updateDialogBinding.dialogupdaateBtnDone.setOnClickListener{


            if (
                updateDialogBinding.dialogEdtFoodName.length() > 0 &&
                updateDialogBinding.dialogEdtFoodCity.length() > 0 &&
                updateDialogBinding.dialogEdtFoodPrice.length() > 0 &&
                updateDialogBinding.dialogEdtFoodDistance.length() > 0
            ){

            val txtName = updateDialogBinding.dialogEdtFoodName.text.toString()
            val txtPrice =updateDialogBinding.dialogEdtFoodPrice.text.toString()
            val txtDistans =updateDialogBinding.dialogEdtFoodDistance.text.toString()
            val txtCity = updateDialogBinding.dialogEdtFoodCity.text.toString()



            val newFood = Food(txtName,txtPrice,txtDistans,txtCity,food.urlImage ,food.numOfRating,food.rating)

            myAdapter.updateFood(newFood , position)
                dialog.dismiss()
        }else{
            Toast.makeText(this , "لطفا همه مقادیر را وارد کنید" , Toast.LENGTH_SHORT).show()
            }



        }

    }

    override fun onFoodLongCliced(food: Food, pos: Int) {
        val dialog = AlertDialog.Builder(this).create()
        val dialogDeleteBinding = DialogDeleteItemBinding.inflate(layoutInflater)
        dialog.setView(dialogDeleteBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogDeleteBinding.dialogBtnDeleteCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogDeleteBinding.dialogBtnDeleteSure.setOnClickListener{

            dialog.dismiss()
            myAdapter.removeFood(food,pos)
        }
    }


}