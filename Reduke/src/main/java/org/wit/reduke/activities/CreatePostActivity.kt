package org.wit.reduke.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.feed_card.*
import org.jetbrains.anko.*
import org.wit.post.R
import org.wit.reduke.main.MainApp
import org.wit.reduke.models.PostModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class CreatePostActivity : AppCompatActivity(), AnkoLogger {

    var reduke = PostModel()
    lateinit var app: MainApp
    val LOCATION_REQUEST = 1
    val FIRST_GALLERY_IMAGE_REQUEST = 2
    val SECOND_GALLERY_IMAGE_REQUEST = 3
    val THIRD_GALLERY_IMAGE_REQUEST = 4
    val FOURTH_GALLERY_IMAGE_REQUEST = 5

    val FIRST_CAMERA_IMAGE_REQUEST = 6
    val SECOND_CAMERA_IMAGE_REQUEST = 7
    val THIRD_CAMERA_IMAGE_REQUEST = 8
    val FOURTH_CAMERA_IMAGE_REQUEST = 9

    var mCurrentPhotoPath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reduke)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        info("Reduke Activity started..")

        app = application as MainApp
        var edit = false


        if (intent.hasExtra("reduke_edit")) {
            edit = true
            toolbarAdd.title = "Edit Reduke"
            setSupportActionBar(toolbarAdd)
            reduke = intent.extras.getParcelable<RedukeModel>("reduke_edit")
            cardRedukeTitle.setText(reduke.title)
            cardRedukeDescription.setText(reduke.description)
            additionalNotes.setText(reduke.addNotes)
            visitedSwitch.isChecked = reduke.visited
            redukeLocation.setText(R.string.button_changeLocation)
            addressPreview.text = reduke.address
            deleteRedukeBtn.visibility = View.VISIBLE
            if (reduke.visited) {
                dateVisited.text = reduke.dateVisited
                dateVisited.visibility = View.VISIBLE
            }
            if (reduke.firstImage.isNotEmpty()) {
                redukeFirstImage.setImageBitmap(readImageFromPath(this, reduke.firstImage))
                redukeFirstImage.visibility = View.VISIBLE
                chooseFirstImageGallery.visibility = View.VISIBLE
                chooseFirstImageCamera.visibility = View.VISIBLE
            }
            if (reduke.secondImage.isNotEmpty()) {
                redukeSecondImage.setImageBitmap(readImageFromPath(this, reduke.secondImage))
                redukeSecondImage.visibility = View.VISIBLE
                chooseSecondImageGallery.visibility = View.VISIBLE
                chooseSecondImageCamera.visibility = View.VISIBLE
            }
            if (reduke.thirdImage.isNotEmpty()) {
                redukeThirdImage.setImageBitmap(readImageFromPath(this, reduke.thirdImage))
                redukeThirdImage.visibility = View.VISIBLE
                chooseThirdImageGallery.visibility = View.VISIBLE
                chooseThirdImageCamera.visibility = View.VISIBLE
            }
            if (reduke.fourthImage.isNotEmpty()) {
                redukeFourthImage.setImageBitmap(readImageFromPath(this, reduke.fourthImage))
                redukeFourthImage.visibility = View.VISIBLE
                chooseFourthImageGallery.visibility = View.VISIBLE
                chooseFourthImageCamera.visibility = View.VISIBLE
            }
            addRedukeBtn.setText(R.string.button_saveReduke)
        }

        addRedukeBtn.setOnClickListener {
            reduke.title = cardRedukeTitle.text.toString()
            reduke.description = cardRedukeDescription.text.toString()
            reduke.addNotes = additionalNotes.text.toString()
            reduke.visited = visitedSwitch.isChecked
            reduke.dateVisited = dateVisited.text.toString()
            if (reduke.title.isEmpty() or reduke.description.isEmpty()) {
                toast(R.string.hint_EnterRedukeTitle)
            } else {
                if (edit) {
                    app.redukes.update(reduke.copy())
                } else {
                    app.redukes.create(reduke.copy())
                }
                info("add Button Pressed: $cardRedukeTitle")
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }
        }

        deleteRedukeBtn.setOnClickListener {
            alert(R.string.deletePrompt) {
                yesButton {
                    app.redukes.delete(reduke)
                    finish()
                }
                noButton {}
            }.show()
        }

        // Gallery
        chooseFirstImageGallery.setOnClickListener {
            showImagePicker(this, FIRST_GALLERY_IMAGE_REQUEST)
        }

        chooseSecondImageGallery.setOnClickListener {
            showImagePicker(this, SECOND_GALLERY_IMAGE_REQUEST)
        }

        chooseThirdImageGallery.setOnClickListener {
            showImagePicker(this, THIRD_GALLERY_IMAGE_REQUEST)
        }

        chooseFourthImageGallery.setOnClickListener {
            showImagePicker(this, FOURTH_GALLERY_IMAGE_REQUEST)
        }

        // Camera
        chooseFirstImageCamera.setOnClickListener {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                // Ensure that there's a camera activity to handle the intent
                takePictureIntent.resolveActivity(packageManager)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        null
                    }
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                                this,
                                "org.wit.reduke.fileprovider", it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, FIRST_CAMERA_IMAGE_REQUEST)
                    }
                }
            }
        }

        chooseSecondImageCamera.setOnClickListener {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                // Ensure that there's a camera activity to handle the intent
                takePictureIntent.resolveActivity(packageManager)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        null
                    }
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                                this,
                                "org.wit.reduke.fileprovider",
                                it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, SECOND_CAMERA_IMAGE_REQUEST)
                    }
                }
            }
        }

        chooseThirdImageCamera.setOnClickListener {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                // Ensure that there's a camera activity to handle the intent
                takePictureIntent.resolveActivity(packageManager)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        null
                    }
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                                this,
                                "org.wit.reduke.fileprovider",
                                it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, THIRD_CAMERA_IMAGE_REQUEST)
                    }
                }
            }
        }

        chooseFourthImageCamera.setOnClickListener {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                // Ensure that there's a camera activity to handle the intent
                takePictureIntent.resolveActivity(packageManager)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        null
                    }
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                                this,
                                "org.wit.reduke.fileprovider",
                                it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, FOURTH_CAMERA_IMAGE_REQUEST)
                    }
                }
            }
        }

        redukeLocation.setOnClickListener {
            val location = RedukeModel(
                    reduke.id,
                    reduke.title,
                    reduke.description,
                    reduke.addNotes,
                    reduke.visited,
                    reduke.dateVisited,
                    52.245696,
                    -7.139102,
                    15f,
                    reduke.address,
                    reduke.firstImage,
                    reduke.secondImage,
                    reduke.thirdImage,
                    reduke.fourthImage)
            if (location.zoom != 0f) {
                location.lat = reduke.lat
                location.lng = reduke.lng
                location.zoom = reduke.zoom
            }
            startActivityForResult(intentFor<RedukeMapsActivity>().putExtra("location", location), LOCATION_REQUEST)
        }

        visitedSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
                dateVisited.text = current.format(formatter).toString()
                dateVisited.visibility = View.VISIBLE
            } else {
                dateVisited.visibility = View.INVISIBLE
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_create_post, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                alert(R.string.unsavedPrompt) {
                    yesButton {
                        finish()
                    }
                    noButton {}
                }.show()
            }
            R.id.item_deleteReduke -> {
                alert(R.string.deletePrompt) {
                    yesButton {
                        app.redukes.delete(reduke)
                        finish()
                    }
                    noButton {}
                }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            // GALLERY
            FIRST_GALLERY_IMAGE_REQUEST -> {
                if (data != null) {
                    reduke.firstImage = data.getData().toString()
                    redukeFirstImage.setImageBitmap(readImage(this, resultCode, data))
                    redukeFirstImage.visibility = View.VISIBLE
                    chooseFirstImageCamera.isClickable = false
                    chooseFirstImageCamera.setBackgroundColor(Color.parseColor("#FF9E9E9E"))
                    chooseSecondImageGallery.visibility = View.VISIBLE
                    chooseSecondImageCamera.visibility = View.VISIBLE
                }
            }
            SECOND_GALLERY_IMAGE_REQUEST -> {
                if (data != null) {
                    reduke.secondImage = data.getData().toString()
                    redukeSecondImage.setImageBitmap(readImage(this, resultCode, data))
                    redukeSecondImage.visibility = View.VISIBLE
                    chooseSecondImageCamera.setBackgroundColor(Color.parseColor("#FF9E9E9E"))
                    chooseSecondImageCamera.isClickable = false
                    chooseThirdImageGallery.visibility = View.VISIBLE
                    chooseThirdImageCamera.visibility = View.VISIBLE
                }
            }
            THIRD_GALLERY_IMAGE_REQUEST -> {
                if (data != null) {
                    reduke.thirdImage = data.getData().toString()
                    redukeThirdImage.setImageBitmap(readImage(this, resultCode, data))
                    redukeThirdImage.visibility = View.VISIBLE
                    chooseThirdImageCamera.setBackgroundColor(Color.parseColor("#FF9E9E9E"))
                    chooseThirdImageCamera.isClickable = false
                    chooseFourthImageGallery.visibility = View.VISIBLE
                    chooseFourthImageCamera.visibility = View.VISIBLE
                }
            }
            FOURTH_GALLERY_IMAGE_REQUEST -> {
                if (data != null) {
                    reduke.fourthImage = data.getData().toString()
                    redukeFourthImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseFourthImageCamera.setBackgroundColor(Color.parseColor("#FF9E9E9E"))
                    chooseFourthImageCamera.isClickable = false
                    redukeFourthImage.visibility = View.VISIBLE
                }
            }

            // CAMERA
            FIRST_CAMERA_IMAGE_REQUEST -> {
                if (data != null) {
                    if (resultCode == Activity.RESULT_OK) {
                        redukeFirstImage.setImageBitmap(decodeBitmap())
                        reduke.firstImage = cameraPicSaveAndGet()
                    }
                    chooseFirstImageGallery.setBackgroundColor(Color.parseColor("#FF9E9E9E"))
                    chooseFirstImageGallery.isClickable = false
                    redukeFirstImage.visibility = View.VISIBLE
                    chooseSecondImageGallery.visibility = View.VISIBLE
                    chooseSecondImageCamera.visibility = View.VISIBLE
                }
            }
            SECOND_CAMERA_IMAGE_REQUEST -> {
                if (data != null && resultCode == Activity.RESULT_OK) {
                    redukeSecondImage.setImageBitmap(decodeBitmap())
                    reduke.secondImage = cameraPicSaveAndGet()
                    chooseSecondImageGallery.setBackgroundColor(Color.parseColor("#FF9E9E9E"))
                    chooseSecondImageGallery.isClickable = false
                    redukeSecondImage.visibility = View.VISIBLE
                    chooseThirdImageGallery.visibility = View.VISIBLE
                    chooseThirdImageCamera.visibility = View.VISIBLE
                }
            }
            THIRD_CAMERA_IMAGE_REQUEST -> {
                if (data != null && resultCode == Activity.RESULT_OK) {
                    redukeThirdImage.setImageBitmap(decodeBitmap())
                    reduke.thirdImage = cameraPicSaveAndGet()
                    chooseThirdImageGallery.setBackgroundColor(Color.parseColor("#FF9E9E9E"))
                    chooseThirdImageGallery.isClickable = false
                    redukeThirdImage.visibility = View.VISIBLE
                    chooseFourthImageGallery.visibility = View.VISIBLE
                    chooseFourthImageCamera.visibility = View.VISIBLE
                }
            }
            FOURTH_CAMERA_IMAGE_REQUEST -> {
                if (data != null && resultCode == Activity.RESULT_OK) {
                    redukeFourthImage.setImageBitmap(decodeBitmap())
                    reduke.fourthImage = cameraPicSaveAndGet()
                    chooseFourthImageGallery.setBackgroundColor(Color.parseColor("#FF9E9E9E"))
                    chooseFourthImageGallery.isClickable = false
                    redukeFourthImage.visibility = View.VISIBLE
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    val location = data.extras.getParcelable<RedukeModel>("location")
                    reduke.lat = location.lat
                    reduke.lng = location.lng
                    reduke.zoom = location.zoom
                    reduke.address = location.address
                    redukeLocation.setText(R.string.button_changeLocation)
                    val geocoder = Geocoder(this)
                    val addresses = geocoder.getFromLocation(location.lat, location.lng, 1)
                    addressPreview.text = addresses[0].getAddressLine(0)
                }
            }
        }
    }

    override fun onBackPressed() {
        alert(R.string.unsavedPrompt) {
            yesButton {
                finish()
                super.onBackPressed()
            }
            noButton {}
        }.show()
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = absolutePath
        }
    }

    fun decodeBitmap(): Bitmap {
        return BitmapFactory.decodeFile(mCurrentPhotoPath)
    }

    fun cameraPicSaveAndGet(): String {
        val f = File(mCurrentPhotoPath)
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            mediaScanIntent.data = Uri.fromFile(f)
            sendBroadcast(mediaScanIntent)
        }
        return Uri.fromFile(f).toString()
    }

}

