

package com.mianasad.pico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.mianasad.pico.databinding.ActivityResultBinding;

import java.io.File;
import java.io.FileOutputStream;

public class ResultActivity extends AppCompatActivity {

    ActivityResultBinding binding;
    ImageView img;
    Button btn;
    ImageView img2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        binding.image.setImageURI(getIntent().getData());
        img = findViewById(R.id.image);
        btn = findViewById(R.id.share);
        img2  = findViewById(R.id.home);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openmainactivity1();
            }
            private void openmainactivity1() {

                Intent i = new Intent(ResultActivity.this , MainActivity.class);
                startActivity(i);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               image();
            }
        });
     }
    private void image()
    {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        BitmapDrawable drawable=(BitmapDrawable) img.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        File f= new File(getExternalCacheDir()+"/"+getResources().getString(R.string.app_name)+".png");
        Intent shareint;

        try{
            FileOutputStream outputStream = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
            outputStream.flush();
            outputStream.close();
            shareint= new Intent(Intent.ACTION_SEND);
            shareint.setType("Image/*");
            shareint.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
            shareint.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

           }
         catch (Exception e){
            throw new RuntimeException(e);
        }
        startActivity(Intent.createChooser(shareint,"share image"));


    }
}