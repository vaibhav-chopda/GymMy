package com.example.vaibhavchopda.gymmy24v11;

import android.Manifest;
import android.app.Activity;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
//Main navigation drawer
public class MainActivityNavigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView imageView;
    private static final int PICK_IMAGE = 0;
    private int REQUEST_CAMERA =1,SELECT_FILE=1;
    Uri imageUri;
    String path;

// inflating various layout file while you launch the app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);

        //Adding the toolbar to the main activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActivityCompat.requestPermissions(MainActivityNavigation.this,new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headView = navigationView.getHeaderView(0);
        loadImageFromStorage(path);

        //Finding Image section so that user can upload there image
        imageView = headView.findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                selectImage();

            }
        });
    }
// giving options to the user so that they can select image from camera and gallery
    private void selectImage(){

        final CharSequence[] items = {"Camera","Gallery","Cancel"};
        AlertDialog.Builder builder =new AlertDialog.Builder(MainActivityNavigation.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(items[i].equals("Camera"))
                {
                    Intent Cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        startActivityForResult(Cameraintent, REQUEST_CAMERA);
                    }else {
                        String[] permissionRequested = {Manifest.permission.CAMERA};
                        requestPermissions(permissionRequested,REQUEST_CAMERA);

                    }

                }else if (items[i].equals("Gallery"))
                {
                   Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                   intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                    //startActivityForResult(intent.createChooser(intent,"SELECT FILE",SELECT_FILE);

                }else if (items[i].equals("Cancel"))
                {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(MainActivityNavigation.this,LoginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
// when user click on the various tabs available in navigation drawer
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //very very important code
        if (id == R.id.nav_profile) {
          //call intent for user profile
            Intent i = new Intent(MainActivityNavigation.this, UserProfileViewer.class);
            startActivity(i);
        }
        //open intents for the other actions in the navigation drawer
        else if (id == R.id.nav_finder) {
            Intent i = new Intent(MainActivityNavigation.this, MainActivityProfile.class);
            startActivity(i);

        } else if (id == R.id.nav_match) {
            //near by gym
            Intent i = new Intent(MainActivityNavigation.this, MapsActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_stats) {
            Intent i = new Intent(MainActivityNavigation.this, GraphActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_tutorial) {

            Intent i = new Intent(MainActivityNavigation.this, MainTutorial.class);
            startActivity(i);

        } else if (id == R.id.nav_setting) {
            Intent i = new Intent(MainActivityNavigation.this, SettingsActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    //image setting up
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK ) {
            imageUri = data.getData();
           // InputStream inputStream;

            if(resultCode == Activity.RESULT_OK)
            {
                if(requestCode == REQUEST_CAMERA)

                {
                    Bundle bundle = data.getExtras();
                    final Bitmap bmp = (Bitmap) bundle.get("data");
                    imageView.setImageBitmap(bmp);
                    //testing
                    path=saveToInternalStorage(bmp);
                }else if(requestCode == PICK_IMAGE){
                    imageUri = data.getData();
                    imageView.setImageURI(imageUri);
                }

            }
        }

    }
//saving to internal storage
    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());

        File pictureDirectory = Environment.getExternalStorageDirectory();

        // Create imageDir
        File dir = new File(pictureDirectory+"/GymMY/");
        dir.mkdirs();

        File file = new File(dir,"ProfilePic.png");


        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dir.getAbsolutePath();
    }
//loading stored image
    private void loadImageFromStorage(String path)
    {

        try {
            File f=new File(path, "ProfilePic.png");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            ImageView img=(ImageView)findViewById(R.id.imageView);
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
}
