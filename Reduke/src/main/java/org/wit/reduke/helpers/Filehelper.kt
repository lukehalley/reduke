package org.wit.reduke.helpers

import android.content.Context
import android.util.Log
import java.io.*

// Write Posts and Users to a file.
fun write(context: Context, fileName: String, data: String) {
    try {
        val outputStreamWriter = OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE))
        outputStreamWriter.write(data)
        outputStreamWriter.close()
    } catch (e: Exception) {
        Log.e("Error: ", "Cannot read file: " + e.toString());
    }
}

// Read Posts and Users from a file.
fun read(context: Context, fileName: String): String {
    var str = ""
    try {
        val inputStream = context.openFileInput(fileName)
        if (inputStream != null) {
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val partialStr = StringBuilder()
            var done = false
            while (!done) {
                var line = bufferedReader.readLine()
                done = (line == null);
                if (line != null) partialStr.append(line);
            }
            inputStream.close()
            str = partialStr.toString()
        }
    } catch (e: FileNotFoundException) {
        Log.e("Error: ", "file not found: " + e.toString());
    } catch (e: IOException) {
        Log.e("Error: ", "cannot read file: " + e.toString());
    }
    return str
}

// Check if a file exists.
fun exists(context: Context, filename: String): Boolean {
    val file = context.getFileStreamPath(filename)
    return file.exists()
}