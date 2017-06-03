package com.heiman.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.concurrent.Executors;

public class AsyncBitmapLoader
{
    /**
     * 内存图片软引用缓冲
     */
    private HashMap<String, SoftReference<Bitmap>> imageCache = null;

    private static AsyncBitmapLoader instance;

    private AsyncBitmapLoader()
    {
        imageCache = new HashMap<String, SoftReference<Bitmap>>();
    }

    public static AsyncBitmapLoader getInstance() {
        if (instance == null) {
            instance = new AsyncBitmapLoader();
        }
        return instance;
    }

    public Bitmap loadBitmap(View view, String imageURL, ImageCallback imageCallback)
    {
        //在内存缓存中，则返回Bitmap对象
        if(imageCache.containsKey(imageURL))
        {
            SoftReference<Bitmap> reference = imageCache.get(imageURL);
            Bitmap bitmap = reference.get();
            if(bitmap != null)
            {
                return bitmap;
            }
        }
        else
        {
            /**
             * 加上一个对本地缓存的查找
             */
            String bitmapName = UsefullUtill.md5(imageURL);
            LogUtil.e("bitmapName:" + bitmapName);
            if (TextUtils.isEmpty(bitmapName)) {
                bitmapName = imageURL.substring(imageURL.lastIndexOf("/") + 1);
            }
            File cacheDir = new File(SmartHomeSave.getSmarthomePhotoCachePath());
            File[] cacheFiles = cacheDir.listFiles();
            int i = 0;
            if(null!=cacheFiles){
                for(; i<cacheFiles.length; i++)
                {
                    if(bitmapName.equals(cacheFiles[i].getName()))
                    {
                        break;
                    }
                }

                if(i < cacheFiles.length)
                {
                    return BitmapFactory.decodeFile(SmartHomeSave.getSmarthomePhotoCachePath() + File.separator + bitmapName);
                }
            }
            DownloadRunnable download = new DownloadRunnable(view, bitmapName,imageURL, imageCallback);
            Executors.newCachedThreadPool().execute(download);
        }
        return null;
    }

    private class DownloadRunnable implements Runnable {
        private View view;
        private String imageName;
        private String imageURL;
        private ImageCallback imageCallback;

        public DownloadRunnable(View view, String imageName, String imageURL, ImageCallback imageCallback) {
            this.view = view;
            this.imageName = imageName;
            this.imageURL = imageURL;
            this.imageCallback = imageCallback;
        }

        @Override
        public void run() {
            InputStream bitmapIs = HttpUtils.getStreamFromURL(imageURL);

            if (bitmapIs == null) {
                return;
            }
            Bitmap bitmap = BitmapFactory.decodeStream(bitmapIs);
            try {
                bitmapIs.close();
            } catch (IOException e) {
                LogUtil.e(e.getMessage());
            }

            if (bitmap == null) {
                return;
            }
            imageCache.put(imageURL, new SoftReference<Bitmap>(bitmap));
            if (imageCallback != null) {
                imageCallback.imageLoad(view, bitmap);
            }
            File dir = new File(SmartHomeSave.getSmarthomePhotoCachePath());
            if(!dir.exists())
            {
                dir.mkdirs();
            }

            File bitmapFile = new File(SmartHomeSave.getSmarthomePhotoCachePath() + File.separator + imageName);
            if(!bitmapFile.exists())
            {
                try
                {
                    bitmapFile.createNewFile();
                }
                catch (IOException e)
                {
                    LogUtil.e(e.getMessage());
                }
            }
            FileOutputStream fos;
            try
            {
                fos = new FileOutputStream(bitmapFile);
                bitmap.compress(Bitmap.CompressFormat.PNG,
                        100, fos);
                fos.close();
            }
            catch (FileNotFoundException e)
            {
                LogUtil.e(e.getMessage());
            }
            catch (IOException e)
            {
                LogUtil.e(e.getMessage());
            }
        }
    }

    public interface ImageCallback
    {
        public void imageLoad(View view, Bitmap bitmap);
    }
}


