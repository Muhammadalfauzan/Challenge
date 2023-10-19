package com.example.challengeempat.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface CartDao {
    @Insert
    fun insert (cartData: CartData)

    @Delete
    fun delete(cartData: CartData)
    @Query("DELETE FROM cart")
    fun deleteALlItems()
    @Update
    fun update(cartData: CartData)

    @Query( "SELECT * FROM cart ORDER BY ID DESC")
    fun getAllItemCart() : LiveData<List<CartData>>

    @Query("SELECT * FROM cart WHERE id = :itemId")
    fun getCartItemById(itemId: Long): LiveData<CartData?>

}