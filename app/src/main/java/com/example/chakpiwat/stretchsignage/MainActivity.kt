package com.example.chakpiwat.stretchsignage

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.*
import android.os.AsyncTask



class MainActivity : Activity() {
    var currentUriIndex = 0
    val uriList: Array<String> = arrayOf(
//        "https://firebasestorage.googleapis.com/v0/b/firestrore-practice.appspot.com/o/bp-video%2Flisa-2.mp4?alt=media&token=07aa0a40-b0ef-4c74-81e6-45e07d220916"
//        "android.resource://com.example.chakpiwat.stretchsignage/raw/lisa"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add
        val retrofit = Retrofit.Builder().baseUrl("https://firebasestorage.googleapis.com").build()

        val downloadService = retrofit.create(LoadVideoService::class.java)

        val call = downloadService.downloadFileWithDynamicUrlSync("/v0/b/firestrore-practice.appspot.com/o/bp-video%2Flisa-2.mp4?alt=media&token=07aa0a40-b0ef-4c74-81e6-45e07d220916")

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {

                    object : AsyncTask<Void, Void, Void>() {
                        override fun doInBackground(vararg voids: Void): Void? {
                            val writtenToDisk = writeResponseBodyToDisk(response.body()!!)

                            val extStore = System.getenv("EXTERNAL_STORAGE")

                            runOnUiThread {
                                myVideo.setVideoURI(Uri.parse(extStore!! + "/lisa.mp4"))
                                myVideo.start()
                            }

                            return null
                        }
                    }.execute()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })

//        // Old Code
//        val haveExSD = Environment.getExternalStorageState()
//
//        val extStore = System.getenv("EXTERNAL_STORAGE")
//        val f_exts = File(extStore + "lisa.mp4")
//
//        val secStore = System.getenv("SECONDARY_STORAGE")
//        val f_secs = File(secStore + "lisa.mp4")
//
//        myVideo.setVideoURI(Uri.parse(extStore + "/lisa.mp4"))
//
//        myVideo.start()
//
//        myVideo.setOnCompletionListener {
////            myVideo.stopPlayback()
//            currentUriIndex += 1
//            if (currentUriIndex == uriList.count()) {
//                currentUriIndex = 0
//            }
//            val currentUri = uriList[currentUriIndex]
//            myVideo.setVideoURI(Uri.parse(currentUri))
//            myVideo.start()
//        }
    }

    private fun writeResponseBodyToDisk(body: ResponseBody): Boolean {
        try {
            val extStore = System.getenv("EXTERNAL_STORAGE")

            val futureStudioIconFile = File(extStore!! + "/lisa.mp4")

            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                val fileReader = ByteArray(4096)

                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0

                inputStream = body.byteStream()
                outputStream = FileOutputStream(futureStudioIconFile)

                while (true) {
                    val read = inputStream!!.read(fileReader)

                    if (read == -1) {
                        break
                    }

                    outputStream.write(fileReader, 0, read)

                    fileSizeDownloaded += read.toLong()
                }

                outputStream.flush()

                return true
            } catch (e: IOException) {
                return false
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: IOException) {
            return false
        }
    }
}
