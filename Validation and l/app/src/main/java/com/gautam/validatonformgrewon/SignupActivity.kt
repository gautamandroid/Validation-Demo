package com.gautam.validatonformgrewon

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.gautam.validatonformgrewon.apimodal.RegisterResponse
import com.gautam.validatonformgrewon.apiretrofit.ApiClient
import com.gautam.validatonformgrewon.base64.BaseImage
import com.gautam.validatonformgrewon.databinding.ActivitySingupBinding
import com.gautam.validatonformgrewon.entiy.AppDataBase
import com.gautam.validatonformgrewon.modal.Users
import com.gautam.validatonformgrewon.singuperrorbody.SingUpErrorBody
import com.gautam.validatonformgrewon.utils.Utils
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.create
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.util.*


open class SignupActivity : AppCompatActivity() {

    lateinit var binding: ActivitySingupBinding
    lateinit var db: AppDataBase
    var store: String? = null
    var filePath: String = ""
    var imageUri: Uri? = null
    private val galleryContract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it.let {
            imageUri = it
            filePath = fileFromContentUri(this, imageUri!!)

         //   val file = File("/storage/emulated/0/Download/Corrections 6.jpg")
          //  val requestFile: RequestBody = create("multipart/form-data".toMediaTypeOrNull(), file)
          //  Log.e("temp_file", "onFiless: " + requestFile)
            Log.e("temp_file", "validationForm: " + filePath)

            it.let {
                binding.upImage.setImageURI(it)
                if (it != null) {
                    uriToBitmap(it)?.let {

                        store = BaseImage.bitmapToBase64(it)
                        binding.upImage.setImageBitmap(it)
                    }
                }
            }
        }
    }

    private fun fileFromContentUri(context: Context, contentUri: Uri): String {
        val fileExtension = getFileExtension(context, contentUri)
        val fileName = "temp_file" + if (fileExtension != null) ".$fileExtension" else ""
        val tempFile = File(context.cacheDir, fileName)
        tempFile.createNewFile()
        try {
            val oStream = FileOutputStream(tempFile)
            val inputStream = context.contentResolver.openInputStream(contentUri)

            inputStream?.let {
                copy(inputStream, oStream)
            }
            oStream.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return tempFile.absolutePath
    }

    private fun getFileExtension(context: Context, uri: Uri): String? {
        val fileType: String? = context.contentResolver.getType(uri)
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(fileType)
    }

    @Throws(IOException::class)
    private fun copy(source: InputStream, target: OutputStream) {
        val buf = ByteArray(8192)
        var length: Int
        while (source.read(buf).also { length = it } > 0) {
            target.write(buf, 0, length)
        }
    }


    private fun uriToBitmap(selectedFileUri: Uri): Bitmap? {
        try {
            val parcelFileDescriptor = contentResolver.openFileDescriptor(selectedFileUri, "r")
            val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor.close()
            return image

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    private val cameraContract = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            //  pickImageFromCamera()
            binding.upImage.setImageURI(imageUri)

        }


//            it.let {
//                it?.data.let {
//                    store = BaseImage.bitmapToBase64(it?.extras!!["data"] as Bitmap)
//                    binding.upImage.setImageBitmap(it.extras!!["data"] as Bitmap)
//                }
//            }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rdAlrediyaccount.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        val items = listOf("Male", "Female", "Other")
        val gender = ArrayAdapter(this, R.layout.list_ofiteamlistdropmenu, items)
        binding.dropDownfiled.setAdapter(gender)

        db = AppDataBase.getInstance(this)

//          imageUri = createImageUri()!!

        binding.rdAlrediyaccount.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.upImage.setOnClickListener {
            showOptionDialog()
        }

        binding.etPickdateofbirth.setOnClickListener {

            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")

            // Setting up the event for when ok is clicked
            datePicker.addOnPositiveButtonClickListener {
                // formatting date in dd-mm-yyyy format.
                val dateFormatter = SimpleDateFormat("yyyy-dd-MM")
                val date = dateFormatter.format(Date(it))
                binding.etPickdateofbirth.setText(date)
                Toast.makeText(this, "$date is selected", Toast.LENGTH_LONG).show()

            }

            // Setting up the event for when cancelled is clicked
            datePicker.addOnNegativeButtonClickListener {
                //  Toast.makeText(this, "${datePicker.headerText} is cancelled", Toast.LENGTH_LONG)
                //  .show()
            }

            // Setting up the event for when back button is pressed
            datePicker.addOnCancelListener {
                // Toast.makeText(this, "Date Picker Cancelled", Toast.LENGTH_LONG).show()
            }

        }

        binding.btSingup.setOnClickListener {

            val name = binding.tvSname.text.toString().trim()
            val email = binding.edSetemail.text.toString().trim()
            val password = binding.edSpassworld.text.toString().trim()
            val cPassword = binding.etSconpasssworld.text.toString().trim()
            val gender = binding.dropDownfiled.text.toString().trim()
            val phonenumber = binding.edMobilenumber.text.toString().trim()
            val dob = binding.etPickdateofbirth.text.toString().trim()
            validationForm(name, email, password, cPassword, gender, phonenumber, dob)
        }

        binding.dropDownfiled.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                  if (!charSequence.isNullOrEmpty()){
                      binding.dropDownfiled.error=null
                  }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun createImageUri(): Uri? {
        val image = File(applicationContext.filesDir, "camera_photo.png")
        imageUri =
            FileProvider.getUriForFile(applicationContext, packageName.plus(".provider"), image)
        filePath = image.absolutePath
        return imageUri

    }

    private fun showOptionDialog() {
        val items = arrayOf("From Gallery", "From Camera")

        AlertDialog.Builder(this).setTitle("Select option").setItems(items) { dialogInterface, i ->

            when (i) {
                0 -> {
                    // Caller
                    galleryContract.launch("image/*")
                }

                1 -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkCameraPermission()) {
                            pickImageFromCamera()
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
                            }
                        }
                    } else {
                        pickImageFromCamera()
                    }
                }
            }
        }.show()
    }

    private fun pickImageFromCamera() {
        cameraContract.launch(createImageUri())

    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun validationForm(
        name: String,
        email: String,
        password: String,
        cPassword: String,
        gender: String,
        phonenumber: String,
        dob: String

    ) {
        //  resetFocus()
        if (name.isEmpty()) {
            binding.tvSname.error = getString(R.string.Enter_name)
            /* getString(R.string.enter_your_name)*/
            binding.tvSname.requestFocus()
        } else if (imageUri == null) {
            Toast.makeText(this, "select image", Toast.LENGTH_SHORT).show()
        } else if (email.isEmpty()) {
            binding.edSetemail.error = getString(R.string.Enter_email)
            binding.edSetemail.requestFocus()
        } else if (dob.isEmpty()) {
            Toast.makeText(this, "Select date of birth", Toast.LENGTH_SHORT).show()
        //    binding.etPickdateofbirth.error = getString(R.string._enter_date_of_birth)
          //  binding.etPickdateofbirth.requestFocus()
        } else if (!Utils.isValidPhone(phonenumber)) {
            //  Toast.makeText(this, getString(R.string._entermobilenimber), Toast.LENGTH_SHORT).show()
            binding.edMobilenumber.error = getString(R.string._entermobilenimber)
            binding.edSpassworld.requestFocus()
        } else if (!Utils.isValidEmail(email)) {
            binding.edSetemail.error = getString(R.string.enter_valid_format_email)
            binding.edSetemail.requestFocus()
//        } else if (db.userDao().getEmail(email) != null) {
//            binding.edSetemail.error = getString(R.string.Already_register_email)
//            binding.edSetemail.requestFocus()
        } else if (password.isEmpty()) {
            /* val options = ActivityOptions.makeSceneTransitionAnimation(requireActivity())
             options.toBundle()//in startactivityh*/
            binding.edSpassworld.error = getString(R.string.create_password)
            binding.edSpassworld.requestFocus()
        } else if (!Utils.isValidpassword(password)) {
            binding.edSpassworld.error = getString(R.string.uppercase_with_lower_case)
            binding.edSpassworld.requestFocus()
        } else if (cPassword.isEmpty()) {
            binding.etSconpasssworld.error = getString(R.string.Enter_same_password)
            binding.etSconpasssworld.requestFocus()
        } else if (cPassword != password) {
            binding.etSconpasssworld.error = getString(R.string.uppercase_with_lower_case)
            binding.etSconpasssworld.requestFocus()
//        } else if (store.isNullOrEmpty()) {
//            Toast.makeText(this, getString(R.string.please_select_image), Toast.LENGTH_SHORT).show()
//        }
        } else if (gender.isNullOrEmpty()) {
            binding.dropDownfiled.setError(" select gender ")
            binding.dropDownfiled.requestFocus()
        } else {
            apiCallSingUp()
            binding.progressBar.isVisible = true
        }
    }

    //  insertRecourd(name, email, password, cPassword, gender, phonenumber, dob)


    private fun apiCallSingUp() {

        val name = binding.tvSname.text.toString().trim()
        val email = binding.edSetemail.text.toString().trim()
        val password = binding.edSpassworld.text.toString().trim()
        val phonenumber = binding.edMobilenumber.text.toString().trim()
        val dob = binding.etPickdateofbirth.text.toString().trim()
        val file = File(filePath)
        val requestBody = file.asRequestBody("image.jpg".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("upload", file.name, requestBody)

        val emailReq: RequestBody = RequestBody.create("text/plain".toMediaType(), email)
        val nameReq: RequestBody = RequestBody.create("text/plain".toMediaType(), name)
        val passwordReq: RequestBody = RequestBody.create("text/plain".toMediaType(), password)
        val dobReq: RequestBody = RequestBody.create("text/plain".toMediaType(), dob)
        val phonenumberReq: RequestBody =
            RequestBody.create("text/plain".toMediaType(), phonenumber)


        val singupList = ApiClient.init(this).getSingupList(
            upload = part,
            name = nameReq,
            email = emailReq,
            mobileno = phonenumberReq,
            password = passwordReq,
            dob = dobReq
        )
        singupList.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>, response: Response<RegisterResponse>
            ) {
                Log.e("temp_file", "onResponseerror: " + Gson().toJson(response.errorBody()))
                Log.e("temp_file", "onResponse: " + response.body())
                if (response.isSuccessful) {

                    // Toast.makeText(this@SignupActivity, "successful register", Toast.LENGTH_SHORT)
                    //    .show()
                    val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    try {

                        val gson = Gson()
                        val type = object : TypeToken<SingUpErrorBody>() {}.type
                        var errorResponse: SingUpErrorBody? =
                            gson.fromJson(response.errorBody()!!.charStream(), type)
                        Log.e("lucifer", "${errorResponse?.msg}")
                        binding.progressBar.isVisible = false

                        Toast.makeText(this@SignupActivity, errorResponse?.msg, Toast.LENGTH_LONG)
                            .show()
                    } catch (e: Exception) {

                    }
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@SignupActivity, "faile regiter", Toast.LENGTH_SHORT).show()
                binding.progressBar.isVisible = false

                try {

                    errorBodiy(t)
                } catch (e: Exception) {

                }
            }
        })
    }


    private fun errorBodiy(t: Throwable) {


    }

    private fun insertRecourd(
        name: String,
        email: String,
        password: String,
        cpassworld: String,
        gender: String,
        phonenumber: String,
        dob: String

    ) {
        val user = Users(
            name = name,
            email = email,
            passworld = password,
            image = store,
            gender = gender,
            conformpassworld = cpassworld,
            phonenumber = phonenumber,
            dob = dob

        )

        // db.userDao().insertRecord(user)
    }


    private fun getRealPathFromURI(uri: Uri?): String {
        var filePath = ""
        val wholeID = DocumentsContract.getDocumentId(uri)
        val id = wholeID.split(":").toTypedArray()[1]
        val column = arrayOf(MediaStore.Images.Media.DATA)
        val sel = MediaStore.Images.Media._ID + "=?"
        val cursor: Cursor? = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel, arrayOf(id), null
        )
        val columnIndex = cursor?.getColumnIndex(column[0])
        if (cursor?.moveToFirst() == true) {
            filePath = columnIndex?.let { cursor.getString(it).toString() }.toString()
        }
        cursor?.close()
        return filePath
    }


    private fun resetFocus() {
        binding.tvSname.clearFocus()
        binding.edSetemail.clearFocus()
        binding.edSpassworld.clearFocus()
        binding.edSpassworld.clearFocus()
        binding.etSconpasssworld.clearFocus()
        binding.dropDownfiled.clearFocus()
        binding.edMobilenumber.clearFocus()
        binding.etPickdateofbirth.clearFocus()
    }


}
