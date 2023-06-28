package com.gautam.validatonformgrewon.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.gautam.validatonformgrewon.R
import com.gautam.validatonformgrewon.base64.BaseImage
import com.gautam.validatonformgrewon.databinding.FragmentProfileFragmentsBinding
import com.gautam.validatonformgrewon.entiy.AppDataBase
import com.gautam.validatonformgrewon.modal.Users
import com.gautam.validatonformgrewon.shareprefrence.PrefManager
import java.io.FileDescriptor
import java.io.IOException


class ProfileFragments : Fragment() {

    lateinit var binding: FragmentProfileFragmentsBinding
    lateinit var db: AppDataBase
    var imagebase64: String? = null
    lateinit var prefManager: PrefManager

    private val galleryContract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let {
            binding.upChangeimage.setImageURI(it)
            uriToBitmap(it)?.let {
                imagebase64 = BaseImage.bitmapToBase64(it)
                binding.upChangeimage.setImageBitmap(it)
            }
        }
    }

    private fun uriToBitmap(selectedFileUri: Uri): Bitmap? {
        try {
            val parcelFileDescriptor =
                requireContext().contentResolver.openFileDescriptor(selectedFileUri, "r")
            val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor.close()
            return image
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null

    }


    private val cameraContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it.let {
                it?.data.let {
                    imagebase64 = BaseImage.bitmapToBase64(it?.extras!!["data"] as Bitmap)
                    binding.upChangeimage.setImageBitmap(it.extras!!["data"] as Bitmap)

                }
            }
        }

//    Glide.with(context)
//    .load("h")
//    .centerCrop().into(holder.binde.ivRowimage)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileFragmentsBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        db = AppDataBase.getInstance(requireContext())
        prefManager = PrefManager(requireContext())
        val users = prefManager.getLoginCredentials()?.email?.let { db.userDao().getEmail(it) }
        if (users != null) {
            binding.tvUpname.setText(users.name)
            binding.dropDownfiledee.setText(users.gender)
            imagebase64 = users.image
            if (!users.image.isNullOrEmpty()) {
                if (URLUtil.isValidUrl(users.image)){
                    Glide.with(requireContext()).load(users.image).into(binding.upChangeimage)
                } else {
                    binding.upChangeimage.setImageBitmap(users.image?.let { it1 -> BaseImage.base64ToBitmap(it1) })
                }
            }
        }
        val items = listOf("Female", "Male", "Other")
        val gender = ArrayAdapter(requireContext(), R.layout.listofmanu, items)
        binding.dropDownfiledee.setAdapter(gender)
        binding.upChangeimage.setOnClickListener {
            showOptionDialog()
        }
        binding.btUpdateprofile.setOnClickListener {
            val name = binding.tvUpname.text.toString()
            val gender = binding.dropDownfiledee.text.toString()
            validation(name, gender)
        }
    }

    private fun validation(name: String, gender: String) {
        resetfocus()
        if (name.isEmpty()) {
            binding.tvUpname.error = getString(R.string.Enter_name)
            binding.tvUpname.requestFocus()
        } else if (gender.isEmpty()) {
            binding.dropDownfiledee.error = getString(R.string.select_gender)
            binding.dropDownfiledee.requestFocus()
        } else {
            updaterecourd(name, gender, imagebase64.toString())

        }

    }

    private fun updaterecourd(name: String, gender: String, imagebase64: String) {
        val email = prefManager.getLoginCredentials()?.email
        val passeord = prefManager.getLoginCredentials()?.passworld
        var muser = Users(
            name = name,
            gender = gender,
            email = email!!,
            passworld = passeord!!,
            image = imagebase64,
            conformpassworld = passeord,
        )
        try {
            db.userDao().updateRecord(name, gender, email, passeord, imagebase64, passeord)
            Toast.makeText(
                requireContext(),
                getString(R.string.record_add_successfully),
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun resetfocus() {
        binding.tvUpname.clearFocus()
        binding.tvUpname.clearFocus()
    }

    private fun showOptionDialog() {

        val items = arrayOf("From Gallery", "From Camera")

        AlertDialog.Builder(requireContext()).setTitle("Select option")
            .setItems(items) { dialogInterface, i ->

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
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraContract.launch(cameraIntent)

    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }
    /*  for (data in arrayList) {
          totalAmount += data.price.times(data.quantity)
          itemCount += data.quantity

      }*/
}