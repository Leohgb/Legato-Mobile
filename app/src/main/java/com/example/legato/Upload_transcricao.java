package com.example.legato;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class Upload_transcricao extends AppCompatActivity {

    String[] item = {"Material", "Design", "Components", "Android", "5.0"};
    String[] item2 = {"Design", "Material", "Components", "Android", "5.0"};
    String[] item3 = {"Components", "Material", "Design", "Android", "5.0"};
    String[] item4 = {"Components", "Material", "Design", "Android", "5.0"};

    AutoCompleteTextView autoCompleteTextView;
    AutoCompleteTextView autoCompleteTextView2;
    AutoCompleteTextView autoCompleteTextView3;
    AutoCompleteTextView autoCompleteTextView4;

    ArrayAdapter<String> adapterItems;
    ArrayAdapter<String> adapterItems2;
    ArrayAdapter<String> adapterItems3;
    ArrayAdapter<String> adapterItems4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_transcricao_l);

        autoCompleteTextView = findViewById(R.id.auto_Complete_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item_l, item);

        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(Upload_transcricao.this, "Item: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTextView2 = findViewById(R.id.auto_Complete_txt2);
        adapterItems2 = new ArrayAdapter<String>(this, R.layout.list_item_l, item2);

        autoCompleteTextView2.setAdapter(adapterItems2);

        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(Upload_transcricao.this, "Item: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTextView3 = findViewById(R.id.auto_Complete_txt3);
        adapterItems3 = new ArrayAdapter<String>(this, R.layout.list_item_l, item3);

        autoCompleteTextView3.setAdapter(adapterItems3);

        autoCompleteTextView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(Upload_transcricao.this, "Item: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTextView4 = findViewById(R.id.auto_Complete_txt4);
        adapterItems4 = new ArrayAdapter<String>(this, R.layout.list_item_l, item4);

        autoCompleteTextView4.setAdapter(adapterItems4);

        autoCompleteTextView4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(Upload_transcricao.this, "Item: " + item, Toast.LENGTH_SHORT).show();
            }
        });
    }

    int requestcode = 1;

    public void onActivityResult(int requestcode, int resulCode, Intent data) {
        super.onActivityResult(requestcode, resulCode, data);
        Context context = getApplicationContext();
        if (requestcode == requestcode && resulCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            Uri uri = data.getData();
            Toast.makeText(context, uri.getPath(), Toast.LENGTH_SHORT).show();
        }
    }

    public void openfilechooser(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent,requestcode);
    }

}
