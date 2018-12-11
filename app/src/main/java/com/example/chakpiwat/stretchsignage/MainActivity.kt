package com.example.chakpiwat.stretchsignage

import android.app.Activity
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : Activity() {
    var currentUriIndex = 0
    val uriList: Array<String> = arrayOf(
//        "https://firebasestorage.googleapis.com/v0/b/firestrore-practice.appspot.com/o/bp-video%2Flisa-2.mp4?alt=media&token=07aa0a40-b0ef-4c74-81e6-45e07d220916"
//        "android.resource://com.example.chakpiwat.stretchsignage/raw/lisa"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val haveExSD = Environment.getExternalStorageState()

        val extStore = System.getenv("EXTERNAL_STORAGE")
        val f_exts = File(extStore + "lisa.mp4")

        val secStore = System.getenv("SECONDARY_STORAGE")
        val f_secs = File(secStore + "lisa.mp4")

        myVideo.setVideoURI(Uri.parse(extStore + "/lisa.mp4"))

        myVideo.start()

        myVideo.setOnCompletionListener {
//            myVideo.stopPlayback()
            currentUriIndex += 1
            if (currentUriIndex == uriList.count()) {
                currentUriIndex = 0
            }
            val currentUri = uriList[currentUriIndex]
            myVideo.setVideoURI(Uri.parse(currentUri))
            myVideo.start()
        }
    }
}
