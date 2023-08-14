package com.example.myadapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.bthbuoi10.R;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.http.HttpStatus;


@SuppressWarnings("deprecation")
public class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewWeakReference;

    public ImageDownloaderTask(ImageView imageView) {
        imageViewWeakReference = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        return downloadBitmap(params[0]);
    }

    private Bitmap downloadBitmap(String url) {
        HttpURLConnection urlConnection = null;
        //InputStream inputStream = null;
        Bitmap bitmap = null;

        try {
            URL uri = new URL(url);
            urlConnection = (HttpURLConnection) uri.openConnection();
            int statusCode = urlConnection.getResponseCode();

            if (statusCode != HttpURLConnection.HTTP_OK) {
                return null;
            }
            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            urlConnection.disconnect();
            Log.w("Image download failed", "Error: " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        ImageView imageView = imageViewWeakReference.get();
        if (imageView != null) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                Drawable noimg = imageView.getContext().getResources().getDrawable(R.drawable.noimg);
                imageView.setImageDrawable(noimg);
            }
        }
    }
}
