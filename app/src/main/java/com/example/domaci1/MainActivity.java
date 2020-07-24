package com.example.domaci1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button prikazi;
    private EditText edit;
    ListView list_view;
    CharacterAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new CharacterAdapter(this);
        adapter.addItem(new Character(getString(R.string.bg)));
        adapter.addItem(new Character(getString(R.string.ns)));
        adapter.addItem(new Character(getString(R.string.nis)));

        list_view = (ListView) findViewById(R.id.lista);
        list_view.setAdapter(adapter);


        prikazi = (Button) findViewById(R.id.prikazi);
        edit = (EditText) findViewById(R.id.text);

        prikazi.setOnClickListener(this);

        list_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.removeCity(position);
                return false;
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.prikazi:
                Character element = new Character(edit.getText().toString());
                if(!edit.getText().toString().isEmpty()){
                    if(adapter.containsElement(element)){
                        Toast.makeText(this, getString(R.string.toastWarning1),Toast.LENGTH_SHORT).show();
                    }else {
                        adapter.addItem(element);

                    }
                    edit.setText("");
                }else{
                    Toast.makeText(this, getString(R.string.toastWarning2),Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
