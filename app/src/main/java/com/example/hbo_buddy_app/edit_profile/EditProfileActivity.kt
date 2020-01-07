package com.example.hbo_buddy_app.edit_profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.bumptech.glide.Glide
import com.example.hbo_buddy_app.R
import com.example.hbo_buddy_app.authenticator.TokenHeaderInterceptor
import com.example.hbo_buddy_app.models.*
import com.example.hbo_buddy_app.retrofit.RetroFitService
import com.example.hbo_buddy_app.retrofit.RetrofitImageBb
import kotlinx.android.synthetic.main.activity_edit_profile.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okio.Utf8
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

class EditProfileActivity : AppCompatActivity() {

    var url : String = ""

    fun encodeForEmojis(string: String) : String {
        var byteArray : ByteArray = string.toByteArray(Charsets.UTF_8)
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun decodeForEmojis(string : String) : String{
        var byteArray: ByteArray = Base64.decode(string, Base64.DEFAULT)
        return String(byteArray, Charsets.UTF_8)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123 && resultCode == Activity.RESULT_OK ){
            val extras = data!!.extras
            val picture :  Bitmap = extras!!.get("data") as Bitmap


            val baos : ByteArrayOutputStream = ByteArrayOutputStream()
            picture.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val byteArray = baos.toByteArray()
            val imageB64 = Base64.encodeToString(byteArray,Base64.DEFAULT)

            Log.d("test", "${imageB64}")

            //picture.rea
            //val byteArray : ByteArray = picture.compress(BitMap.c)

            //
            val baseUrl = "https://api.imgbb.com/"
            val secret = "f6bd9d91c8ed4fa4c9867de1d3425fc3"

            val okHttpClient: OkHttpClient = OkHttpClient()
                .newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS).build()


            val retroFitService = Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(RetrofitImageBb::class.java)

            retroFitService.uploadImage(imageB64,secret).enqueue(object: Callback<UploadImageResponse>{
                override fun onFailure(call: Call<UploadImageResponse>, t: Throwable) {

                }

                override fun onResponse(call: Call<UploadImageResponse>, response: Response<UploadImageResponse>) {
                   if(response.code() == 200){
                       selected_image.setImageBitmap(picture)
                       url = response.body()!!.data.url
                   }
                }

            })


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val intent: Intent = getIntent()
        val profile: Student = intent.getParcelableExtra("profile")

        val voornaam: EditText = findViewById(R.id.edit_first_name)
        voornaam.setText(decodeForEmojis(profile.firstName))
        val achternaam: EditText = findViewById(R.id.edit_last_name)
        achternaam.setText(profile.surName)
        val degree: EditText = findViewById(R.id.edit_degree)
        degree.setText(profile.degree)
        val description: EditText = findViewById(R.id.edit_description)
        description.setText(profile.description)
        val phoneNumber: EditText = findViewById(R.id.edit_phone_number)
        phoneNumber.setText(profile.phoneNumber)
        val interests: EditText = findViewById(R.id.edit_interest)
        interests.setText(profile.interests)

        Log.d("image", "${profile.photo}")

        if(profile.photo != ""){
            try{Glide.with(this).load(profile.photo).into(selected_image)}
            catch (e: Exception){
                Log.d("fout", "${e}")

            }

        }

        val study: EditText = findViewById(R.id.edit_study)
        study.setText(profile.study)
        val studyyear: EditText = findViewById(R.id.edit_study_year)
        studyyear.setText(profile.studyYear.toString())
        val button: Button = findViewById(R.id.edit_profile_button)


        upload_image_button.setOnClickListener {
            val imageTakeIntent :  Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (imageTakeIntent.resolveActivity(packageManager)!= null){
                startActivityForResult(imageTakeIntent, 123)

            }
        }










        button.setOnClickListener {

            val voornaam: String = voornaam.text.toString()
            val achternaam: String = achternaam.text.toString()
            val degree: String = degree.text.toString()
            val description: String = description.text.toString()
            val phoneNumber: String = phoneNumber.text.toString()
            val interests: String = interests.text.toString()
            val study: String = study.text.toString()
            val studyyear: String = studyyear.text.toString()




            val profile2 = Student(degree, description, encodeForEmojis(voornaam), interests, phoneNumber, url, profile.studentID, study, studyyear.toInt(), achternaam)


            val baseUrl = "https://dev-tinderclonefa-test.azurewebsites.net/api/"

            val okHttpClient: OkHttpClient = OkHttpClient()
                .newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS).build()


            val retroFitService = Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(RetroFitService::class.java)


            retroFitService.login(LoginModel("581433", "test")).enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        Log.d("AdminToken", "${response.body()}")
                        val okHttpClient2 = OkHttpClient()
                            .newBuilder()
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .addInterceptor(TokenHeaderInterceptor(response.body()!!))
                            .writeTimeout(60, TimeUnit.SECONDS).build()

                        val retrofitService2 = Retrofit
                            .Builder()
                            .baseUrl(baseUrl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(okHttpClient2)
                            .build()
                            .create(RetroFitService::class.java)

                        retrofitService2.editProfile(profile.studentID, profile2).enqueue(object: Callback<ResponseBody>{
                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                Log.d("sss", "ss")
                            }

                            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                if (response.isSuccessful){
                                    Log.d("sss", "${response.message()}")
                                    Log.d("testSendmessage", "${profile2.firstName}")
                                    Log.d("testrecievemessage", "${profile2.firstName}")

                                }

                                else{
                                    Log.d("ss","${response.message()}")
                                    profile.toString()
                                }
                            }

                        })
                    }
                }
            })
        }
    }


}








