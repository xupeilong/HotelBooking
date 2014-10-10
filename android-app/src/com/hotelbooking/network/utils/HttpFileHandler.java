package com.hotelbooking.network.utils;

import java.io.File;

import android.graphics.Bitmap;
import android.widget.ImageView;

public interface HttpFileHandler {
	public void handleFile(String fileName, Bitmap picture, ImageView imageView);
}
