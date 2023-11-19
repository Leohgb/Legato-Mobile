package com.example.legato;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class upload_transcricao extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_transcricao);

        autoCompleteTextView = findViewById(R.id.auto_Complete_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, item);

        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(upload_transcricao.this, "Item: "+item, Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTextView2 = findViewById(R.id.auto_Complete_txt2);
        adapterItems2 = new ArrayAdapter<String>(this, R.layout.list_item, item2);

        autoCompleteTextView2.setAdapter(adapterItems2);

        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(upload_transcricao.this, "Item: "+item, Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTextView3 = findViewById(R.id.auto_Complete_txt3);
        adapterItems3 = new ArrayAdapter<String>(this, R.layout.list_item, item3);

        autoCompleteTextView3.setAdapter(adapterItems3);

        autoCompleteTextView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(upload_transcricao.this, "Item: "+item, Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTextView4 = findViewById(R.id.auto_Complete_txt4);
        adapterItems4 = new ArrayAdapter<String>(this, R.layout.list_item, item4);

        autoCompleteTextView4.setAdapter(adapterItems4);

        autoCompleteTextView4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(upload_transcricao.this, "Item: "+item, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
