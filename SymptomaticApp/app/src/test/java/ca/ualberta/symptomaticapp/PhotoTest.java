package ca.ualberta.symptomaticapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import junit.framework.TestCase;

import java.util.Date;

public class PhotoTest extends TestCase {

    public void testConstructor(){
        byte [] byteArray = new byte[] { (byte)0xba, (byte)0x8a, 0x0d, 0x45, 0x25, (byte)0xad, (byte)0xd0, 0x11, (byte)0x98, (byte)0xa8, 0x08, 0x00, 0x36, 0x1b, 0x11, 0x03 };
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray , 0, byteArray.length);

        Photo photo = new Photo(bmp);
        assertTrue("Photo Constructor Failed", photo.getPhotoBitmap() == bmp);

    }

    public void testPhotoSize(){
        byte [] byteArray = new byte[] { (byte)0xba, (byte)0x8a, 0x0d, 0x45, 0x25, (byte)0xad, (byte)0xd0, 0x11, (byte)0x98, (byte)0xa8, 0x08, 0x00, 0x36, 0x1b, 0x11, 0x03 };
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray , 0, byteArray.length);

        Photo photo = new Photo(bmp);
        assertTrue("Photo size doesn't match", photo.getPhotoSize() == byteArray.length);
    }

    public void testCompression(){
        byte [] byteArray = new byte[] { (byte)0xba, (byte)0x8a, 0x0d, 0x45, 0x25, (byte)0xad, (byte)0xd0, 0x11, (byte)0x98, (byte)0xa8, 0x08, 0x00, 0x36, 0x1b, 0x11, 0x03 };
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray , 0, byteArray.length);

        Photo photo = new Photo(bmp);
        assertTrue("Photo is not compressed", photo.getPhotoSize() < 65536);
    }

    public void testByteArray(){
        byte [] byteArray = new byte[] { (byte)0xba, (byte)0x8a, 0x0d, 0x45, 0x25, (byte)0xad, (byte)0xd0, 0x11, (byte)0x98, (byte)0xa8, 0x08, 0x00, 0x36, 0x1b, 0x11, 0x03 };
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray , 0, byteArray.length);

        Photo photo = new Photo(bmp);
        assertTrue("Photo byte array is not the same", photo.getPhotoByteArray() == byteArray);
    }
    
}
