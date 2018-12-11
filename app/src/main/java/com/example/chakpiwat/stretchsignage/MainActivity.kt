package com.example.chakpiwat.stretchsignage

import android.app.Activity
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    var currentUriIndex = 0
    val uriList: Array<String> = arrayOf(
//        "https://firebasestorage.googleapis.com/v0/b/firestrore-practice.appspot.com/o/bp-video%2Flisa-2.mp4?alt=media&token=07aa0a40-b0ef-4c74-81e6-45e07d220916"
        "android.resource://com.example.chakpiwat.stretchsignage/raw/lisa"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myVideo.setVideoURI(Uri.parse(uriList[currentUriIndex]))

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
