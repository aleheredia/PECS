package br.com.herediadesign.pecs.activities.pec;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import br.com.herediadesign.pecs.R;
import br.com.herediadesign.pecs.activities.MainActivity;
import br.com.herediadesign.pecs.db.repo.CategoryRepo;
import br.com.herediadesign.pecs.db.repo.PecRepo;
import br.com.herediadesign.pecs.model.Category;
import br.com.herediadesign.pecs.model.Pec;
import br.com.herediadesign.pecs.util.ImageUtils;

public class PecFormActivity extends Activity {

    PecRepo pecRepo = new PecRepo(this);
    CategoryRepo categoryRepo = new CategoryRepo(this);

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;

    Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pec_form);

        final Spinner categorySpinner = (Spinner)findViewById(R.id.category_spinner);
        final EditText pecLabel = (EditText)findViewById(R.id.pec_label);
        final ImageView pecImage = (ImageView)findViewById(R.id.pec_image_preview);

        ArrayList<String> catsLabels = new ArrayList<>();
        ArrayList<Category> categoryList = categoryRepo.getCategoryList();

        for(Category cat : categoryList){
            catsLabels.add(cat.getLabel());
        }

        ArrayAdapter<String> cats_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, catsLabels);
        cats_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(cats_adapter);


        if(this.getIntent().getExtras()!=null){
            final int pecId = (int)this.getIntent().getExtras().get("pecId");

            final Pec pec = pecRepo.getPecById(pecId);

            categorySpinner.setSelection(pec.getCategoryId()-1); // TODO verificar isso!

            pecLabel.setText(pec.getLabel());
            try {
                File f = new File(this.getFilesDir(), "pec_"+pec.getLabel().toLowerCase()+".png");

                Bitmap bmp = BitmapFactory.decodeStream(new FileInputStream(f));

                pecImage.setImageBitmap(bmp);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            RelativeLayout layout = (RelativeLayout)findViewById(R.id.pec_form_viewgroup);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, ((Button) findViewById(R.id.btn_save_pec)).getId());

            Button btnDelete = new Button(this);

            btnDelete.setText("Apagar");

            btnDelete.setLayoutParams(layoutParams);
            layout.addView(btnDelete);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog ad = new AlertDialog.Builder(PecFormActivity.this)
                            .setTitle("Apagar PEC")
                            .setMessage("Tem certeza que deseja apagar essa PEC")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    File file = new File(PecFormActivity.this.getFilesDir(), "pec_"+pec.getLabel().toLowerCase()+".png");
                                    boolean resultDel = file.delete();
                                    if(resultDel) {
                                        pecRepo.delete(pec.getId());
                                        Intent pecList = new Intent(PecFormActivity.this, PecListActivity.class);
                                        pecList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        PecFormActivity.this.startActivity(pecList);
                                        PecFormActivity.this.finish();
                                    }
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });
        }

        Button changeImage = (Button)findViewById(R.id.btn_add_image);

        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //final File file = new File(Environment.getExternalStorageDirectory().getName() + File.separatorChar + "tmp"+File.separatorChar+PecFormActivity.this.getPackageName()+File.separatorChar, "temp.jpg");

                File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                try {
                    File file = File.createTempFile("temp", ".jpg", storageDir);
                    //if (!file.exists()) {

//                        file.mkdirs();
  //                      file.createNewFile();
    //                }

                    selectedImage = Uri.fromFile(file);

                }catch (IOException e) {
                    Toast.makeText(PecFormActivity.this, "Could not create temp file.", Toast.LENGTH_LONG)
                            .show();
                }
                final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImage);

                final Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");

                final Intent chooserIntent = Intent.createChooser(galleryIntent, "Selecione origem da imagem");

                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { captureIntent });
                startActivityForResult(chooserIntent, RESULT_LOAD_IMG);

                //startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });

        Button btnSave = (Button)findViewById(R.id.btn_save_pec);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pec pec = new Pec();

                Category cat = categoryRepo.getCategoryById(categorySpinner.getSelectedItemPosition()+1);
                pec.setCategoryId(cat.getId());

                pec.setLabel(pecLabel.getText().toString());

                pecImage.buildDrawingCache();

                Bitmap bmp = pecImage.getDrawingCache();

                OutputStream fOut = null;

                try {
                    File file = new File(PecFormActivity.this.getFilesDir(), "pec_"+pec.getLabel().toLowerCase()+".png");
                    fOut = new FileOutputStream(file);

                    bmp.compress(Bitmap.CompressFormat.PNG,100,fOut);

                    fOut.flush();
                    fOut.close();

                    pec.setPath(PecFormActivity.this.getFilesDir().toString());
                }catch(Exception e){
                    Toast.makeText(PecFormActivity.this, "Something went wrong", Toast.LENGTH_LONG)
                            .show();
                }
                if(PecFormActivity.this.getIntent().getExtras()!=null) {
                    pec.setId(PecFormActivity.this.getIntent().getExtras().getInt("pecId"));
                    pecRepo.update(pec);
                }else{
                    pecRepo.insert(pec);
                }

                Intent pecList = new Intent(PecFormActivity.this, PecListActivity.class);
                pecList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PecFormActivity.this.startActivity(pecList);
                PecFormActivity.this.finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {
                // Get the Image from data

                Bitmap bmp;

                Bitmap pecBase = BitmapFactory.decodeResource(PecFormActivity.this.getResources(),R.drawable.pec_base);

                int maxSide = Math.round(pecBase.getHeight()*.65f);

                if(data!=null) {
                    selectedImage = data.getData();
                    String[] filePathColumn = new String[]{MediaStore.Images.Media.DATA};

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();

                    bmp = ImageUtils.crop(BitmapFactory.decodeFile(imgDecodableString), maxSide);
                }else{
                    bmp = ImageUtils.crop(BitmapFactory.decodeFile(selectedImage.getPath()), maxSide);
                }

                ImageView imgView = (ImageView) findViewById(R.id.pec_image_preview);
                // Set the Image in ImageView after decoding the String
                imgView.setImageResource(android.R.color.transparent);

                imgView.setImageBitmap(bmp);

            } else {
                Toast.makeText(this, "Você não escolheu nenhuma imagem.",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Ocorreu um erro: "+e.getLocalizedMessage() , Toast.LENGTH_LONG)
                    .show();
        }
    }
}
