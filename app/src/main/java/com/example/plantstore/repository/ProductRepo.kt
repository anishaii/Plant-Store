package com.example.plantstore.repository

import android.content.Context
import android.net.Uri
import com.example.plantstore.model.ProductModel

interface ProductRepo {
    //addProduct
    //updateProduct
    //deleteProduct
    //getProduct
    //getAllProduct
    //searchProduct

    /*
       success : true,
       message : "product deleted succesfully"
        */

    fun addProduct(
        model: ProductModel,
        callback: (Boolean, String) -> Unit
    )

    fun updateProduct(
        productId: String,
        data: MutableMap<String, Any?>,
        callback: (Boolean, String) -> Unit
    )

    fun deleteProduct(
        productId: String,
        callback: (Boolean, String) -> Unit
    )

    /*
  success : true,
  message : "product fetched Succesfully"
   */
    fun getProductById(
        productId: String,
        callback: (Boolean, String, ProductModel?) -> Unit
    )

    fun getAllProduct(callback: (Boolean, String,
                                 List<ProductModel?>) -> Unit)

    //present - true
    //absent - false
    //    fun attendance(name:String,callback: (Boolean) -> Unit)

    fun uploadImage(context: Context,imageUri: Uri, callback: (String?) -> Unit)

    fun getFileNameFromUri(context: Context,uri: Uri): String?
}