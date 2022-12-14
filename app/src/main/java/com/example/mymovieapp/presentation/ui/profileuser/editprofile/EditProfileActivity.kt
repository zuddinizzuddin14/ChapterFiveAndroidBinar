package com.example.mymovieapp.presentation.ui.profileuser.editprofile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import coil.load
import coil.transform.CircleCropTransformation
import com.example.mymovieapp.R
import com.example.mymovieapp.databinding.ActivityEditProfileBinding
import com.example.mymovieapp.utils.ImageUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {

    private val binding: ActivityEditProfileBinding by lazy {
        ActivityEditProfileBinding.inflate(layoutInflater)
    }

    private val viewModel: EditProfileViewModel by viewModels()

    private val imageUtil = ImageUtil()

    private val REQUEST_CODE_PERMISSION = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListeners()
    }

    override fun onResume() {
        super.onResume()
        obServeData()
    }

    private fun obServeData() {
        val user = viewModel.currentUser
        if (user != null) {
            if (user.displayName.isNullOrEmpty().not()) {
                binding.etName.setText(user.displayName)
            }
            if (user.photoUrl.toString() != "null") {
                binding.ivProfile.load(user.photoUrl) {
                    transformations(CircleCropTransformation())
                    placeholder(R.drawable.ic_account)
                }
            }
        }
    }

    private fun setClickListeners() {
        binding.btnSave.setOnClickListener {
            changeName()
        }
        binding.btnCancel.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.ivProfile.setOnClickListener {
            checkingPermissions()
        }
//        binding.btnBlur.setOnClickListener {
//            var image: String? = null
//            viewModel.getImage().observe(this) {
//                if (!it.equals("null")) {
//                    image = it
//                }
//            }
//
//            viewModel.blurImage(this@EditProfileActivity)
//            val bitmap = imageUtil.stringToBitMap(image.toString())
//            val blurImage = imageUtil.applyBlur(this@EditProfileActivity, bitmap)
//            viewModel.saveImage(imageUtil.bitmapToString(blurImage))
//        }
    }

    private fun changeName() {
        val name = binding.etName.text.toString().trim()
        if (validateForm(name)) {
            Toast.makeText(this@EditProfileActivity, getString(R.string.success_change_name), Toast.LENGTH_SHORT).show()
            viewModel.changeName(name)
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun validateForm(name: String): Boolean {
        var isFormValid = true
        if (name.isEmpty()) {
            isFormValid = false
            binding.tilName.isErrorEnabled = true
            binding.tilName.error = getString(R.string.error_text_empty_name)
        } else {
            binding.tilName.isErrorEnabled = false
            binding.ivProfile.buildDrawingCache()
        }
        return isFormValid
    }

    private fun checkingPermissions() {
        if (isGranted(
                this,
                Manifest.permission.CAMERA,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_PERMISSION,
            )
        ) {
            chooseImageDialog()
        }
    }

    private fun isGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        request: Int
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }

    private fun chooseImageDialog() {
        AlertDialog.Builder(this)
            .setMessage("Pilih Gambar")
            .setPositiveButton("Gallery") { _, _ ->
                intent.type = "image/*"
                galleryResult.launch("image/*")
            }
            .setNegativeButton("Camera") { _, _ ->
                cameraResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
            }
            .show()
    }

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
//            val bitmap = imageUtil.storageToBitmap(result.toString())
            binding.ivProfile.load(result) {
                transformations(CircleCropTransformation())
            }
//            binding.tvTitle.text = result.toString()
//            viewModel.saveImage(imageUtil.bitmapToString(bitmap))
        }


    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
//                handleCameraImage(result.data)
                val bitmap = result.data?.extras?.get("data") as Bitmap
                binding.ivProfile.load(bitmap) {
                    transformations(CircleCropTransformation())
                }
                viewModel.saveImage(bitmap)
            }
        }

//    private fun handleCameraImage(intent: Intent?) {
//        val bitmap = intent?.extras?.get("data") as Bitmap
//        binding.ivProfile.load(bitmap) {
//            transformations(CircleCropTransformation())
//        }
//        viewModel.saveImage(bitmap)
//    }

    fun getBitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(
            view.width, view.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton("App Settings") { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

}