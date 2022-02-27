package com.example.selfcare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.selfcare.ml.YogaPoseModel;
import com.squareup.picasso.Picasso;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.schema.TensorType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;

public class PoseDetectionActivity extends AppCompatActivity {
    ImageView image;
    TextView textView;
    Bitmap img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pose_detection);

       image =findViewById(R.id.imageView);
        textView=findViewById(R.id.prediction);
        selectImage();

    }
    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            mlFunction(selectedImage,2);
            Picasso.get().load(selectedImage).into(image);
        }
    }
    public void mlFunction (Uri uri, int k){
        try {
            if(k==2)
                img= MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

            img= Bitmap.createScaledBitmap(img,200,200,true);

            try {
                YogaPoseModel yogaPoseModel= YogaPoseModel.newInstance(this);
                // Creates inputs for reference.
                TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 200, 200, 3}, DataType.FLOAT32);
                TensorImage tensorImage =new TensorImage(DataType.FLOAT32);
                tensorImage.load(img);
                ByteBuffer byteBuffer=tensorImage.getBuffer();
                inputFeature0.loadBuffer(byteBuffer);

                // Runs model inference and gets result.
                YogaPoseModel.Outputs outputs = yogaPoseModel.process(inputFeature0);
                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                // Releases model resources if no longer used.
                yogaPoseModel.close();
                Log.i("output",""+outputFeature0.getBuffer().toString());

                float[]confidence =outputFeature0.getFloatArray();


                int maxPos=0;
                float maxconfidence=0.00f   ;
                for(int i=0;i<confidence.length;i++){
                    Log.i("output2",""+confidence[i]);
                    if(confidence[i]>maxconfidence) {
                        maxconfidence=confidence[i];
                        maxPos=i;
                    }
                }
          String [] categories ={"Downdog","Goddess","Plank","Tree","Warrior"};
                textView.setText(categories[maxPos]+" : "+maxconfidence);

            } catch (IOException e) {
                // TODO Handle the exception
            }

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}