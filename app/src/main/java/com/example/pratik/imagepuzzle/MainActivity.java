package com.example.pratik.imagepuzzle;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_REQUEST = 1888;
    int oldImagePosition = 1000;
    Bitmap oldImageBitmap = null;
    private ImageButton imageButton;
    private Button phoneImageButton;
    private Uri uri;

    private GridView gridView;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();// here we find all view in this method


        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
                imageButton.startAnimation(animation);
                Toast.makeText(MainActivity.this, "Please take square Image....Ok", Toast.LENGTH_LONG).show();

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
        phoneImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent phoneImageButtonIntent = new Intent(MainActivity.this,ALLImageActivity.class);
//                startActivity(phoneImageButtonIntent);
                //  getFilePaths();
    }
});



    }

    private void
    initial() {


        imageButton = (ImageButton) findViewById(R.id.takeImage_btn);
        gridView = (GridView) findViewById(R.id.gridview);
        phoneImageButton = (Button) findViewById(R.id.phone_image_button);
    }


    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            Bitmap bMapScaled = Bitmap.createScaledBitmap(photo, 240, 240, true);
            final HashMap<Integer,Bitmap> bitMapBundle = new HashMap<Integer, Bitmap>();

            //final List<Bitmap> bitMapBundleCopy = new ArrayList<Bitmap>();

            //lets conver data(image) into chunks i.e 3*3 total 9 Images and adding chunks of Images into bitmapBundle List
            bitMapBundle.put(0,Bitmap.createBitmap(bMapScaled, 0, 0, 80, 80));
            bitMapBundle.put(1,Bitmap.createBitmap(bMapScaled, 80, 0, 80, 80));
            bitMapBundle.put(2,Bitmap.createBitmap(bMapScaled, 160, 0, 80, 80));
            bitMapBundle.put(3,Bitmap.createBitmap(bMapScaled, 0, 80, 80, 80));
            bitMapBundle.put(4,Bitmap.createBitmap(bMapScaled, 80, 80, 80, 80));
            bitMapBundle.put(5,Bitmap.createBitmap(bMapScaled, 160, 80, 80, 80));
            bitMapBundle.put(6,Bitmap.createBitmap(bMapScaled, 0, 160, 80, 80));
            bitMapBundle.put(7,Bitmap.createBitmap(bMapScaled, 80, 160, 80, 80));
            bitMapBundle.put(8,Bitmap.createBitmap(bMapScaled, 160, 160, 80, 80));
            int a=0;
//            while (a!=bitMapBundle.size()){
//                bitMapBundleCopy.add(bitMapBundle.get(a));
//                a++;
//            }



            //Collections.shuffle(Collections.singletonList(bitMapBundle));//shuffleing List order
            //

            //setting UI for Playing Puzzle
            imageButton.setVisibility(View.GONE);
            phoneImageButton.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);

            gridView.setAdapter(new GrideViewImageAdaptor(this, bitMapBundle));




            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                public void onItemClick(AdapterView<?> parent, View v,
                                        final int position, long id) {
                    animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
                    if (oldImagePosition > 8) //if only one Image is Selected
                    {
                        oldImagePosition = position;
                        oldImageBitmap = bitMapBundle.get(position);
                        v.startAnimation(animation);
                    } else //run after selecting second image for swapping of both Images
                    {
                        Bitmap newImageBitmap = bitMapBundle.get(position);
                        bitMapBundle.remove(position);
                        bitMapBundle.put(position, oldImageBitmap);
                        bitMapBundle.remove(oldImagePosition);
                        bitMapBundle.put(oldImagePosition, newImageBitmap);
                        //compare(oldImageBitmap,newImageBitmap);
                        oldImagePosition = 1000;

                        v.startAnimation(animation);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            v.setForeground(getDrawable(R.drawable.shadow));
                        }

                        gridView.setAdapter(new GrideViewImageAdaptor(MainActivity.this, bitMapBundle));

                        oldImageBitmap = null;
                        int count =0;
//                        for (int i=0; i<=8; i++){
//                            if((bitMapBundle.get(i)).sameAs(bitMapBundleCopy.get(i))){
//                                count++;
//                                if(count==8){
//                                    Toast.makeText(MainActivity.this, "Doing Pearfect", Toast.LENGTH_SHORT).show();
//                                }
//
//                            }
//                            count=0;
//                        }



//                        isPuzzleSolved(bitMapBundle,bitMapBundleCopy);

                    }
                   // Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    private  boolean compare(Bitmap oldImageBitmap, Bitmap newImageBitmap) {
        if (oldImageBitmap.getWidth() == newImageBitmap.getWidth() && oldImageBitmap.getHeight() == newImageBitmap.getHeight()) {
            int[] pixels1 = new int[oldImageBitmap.getWidth() * oldImageBitmap.getHeight()];
            int[] pixels2 = new int[newImageBitmap.getWidth() * newImageBitmap.getHeight()];
            oldImageBitmap.getPixels(pixels1, 0, oldImageBitmap.getWidth(), 0, 0, oldImageBitmap.getWidth(), oldImageBitmap.getHeight());
            newImageBitmap.getPixels(pixels2, 0, newImageBitmap.getWidth(), 0, 0, newImageBitmap.getWidth(), newImageBitmap.getHeight());
            if (Arrays.equals(pixels1, pixels2)) {
                Toast.makeText(this, "YOU WIN", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(this, "try again", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(this, "try againp'oiujyhtgrfesw4560", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

//    private void isPuzzleSolved(List<Bitmap> bitMapBundle, List<Bitmap> bitMapBundleCopy) {
//
//    }


    @Override
    public void onBackPressed() {

        gridView.setVisibility(View.GONE);
        imageButton.setVisibility(View.VISIBLE);
        phoneImageButton.setVisibility(View.VISIBLE);
        oldImageBitmap=null;  }

//    public List<Bitmap> getFilePaths()
//    {
//
//
//        Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//        String[] projection = {MediaStore.Images.ImageColumns.DATA};
//        Cursor c = null;
//        SortedSet<String> dirList = new TreeSet<String>();
//        ArrayList<String> resultIAV = new ArrayList<String>();
//
//        String[] directories = null;
//        if (u != null)
//        {
//            c = managedQuery(u, projection, null, null, null);
//        }
//
//        if ((c != null) && (c.moveToFirst()))
//        {
//            do
//            {
//                String tempDir = c.getString(0);
//                tempDir = tempDir.substring(0, tempDir.lastIndexOf("/"));
//                try{
//                    dirList.add(tempDir);
//                }
//                catch(Exception e)
//                {
//
//                }
//            }
//            while (c.moveToNext());
//            directories = new String[dirList.size()];
//            dirList.toArray(directories);
//
//        }
//
//        for(int i=0;i<dirList.size();i++)
//        {
//            File imageDir = new File(directories[i]);
//            File[] imageList = imageDir.listFiles();
//            if(imageList == null)
//                continue;
//            for (File imagePath : imageList) {
//                try {
//
//                    if(imagePath.isDirectory())
//                    {
//                        imageList = imagePath.listFiles();
//
//                    }
//                    if ( imagePath.getName().contains(".jpg")|| imagePath.getName().contains(".JPG")
//                            || imagePath.getName().contains(".jpeg")|| imagePath.getName().contains(".JPEG")
//                            || imagePath.getName().contains(".png") || imagePath.getName().contains(".PNG")
//                            || imagePath.getName().contains(".gif") || imagePath.getName().contains(".GIF")
//                            || imagePath.getName().contains(".bmp") || imagePath.getName().contains(".BMP")
//                            )
//                    {
//
//
//
//                        String path= imagePath.getAbsolutePath();
//                        resultIAV.add(path);
//
//                    }
//                }
//                //  }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//
//
//        List<Bitmap> bitBundle = new ArrayList<Bitmap>();
//        for(int i=0;i<resultIAV.size();i++)
//        {
//            bitBundle.add(BitmapFactory.decodeFile(resultIAV.get(i)));
//        }
//        imageButton.setVisibility(View.GONE);
//        phoneImageButton.setVisibility(View.GONE);
//        gridView.setVisibility(View.VISIBLE);
//        gridView.setAdapter(new GrideViewImageAdaptor(this, (Map<Integer, Bitmap>) bitBundle));
//
//
////        Bitmap bmImg = BitmapFactory.decodeFile(resultIAV.get(4));
////        imageButton.setImageBitmap(bmImg);
//          return bitBundle;
//
//
//    }

}
