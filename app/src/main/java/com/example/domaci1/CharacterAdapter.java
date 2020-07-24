package com.example.domaci1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class CharacterAdapter extends BaseAdapter {

    private ArrayList<Character> lista;
    private Context contex;
    private RadioButton radio_button = null;
    private Button button = null;



    public CharacterAdapter(Context mcontex) {
        contex = mcontex;
        lista = new ArrayList<Character>();
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    public void addItem(Character character){
        boolean inList = false;

        for(Character c : lista){
            if(c.name.equals(character.name) || character.name.equals("")){
                inList = true;
                break;
            }
        }

        if(!inList){
            lista.add(character);
        }
    }
    public boolean containsElement(Character element){
        boolean ind = false;
        for (Character el : lista) {
            if (el.name.equals(element.name))
                ind = true;
        }
        return ind;
    }

    @Override
    public Object getItem(int position) {
        Object ob = null;
        try{
            ob = lista.get(position);
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return ob;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) contex.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.element_row, null);
            ViewHolder holder = new ViewHolder();
            holder.text_view = (TextView)view.findViewById(R.id.text_element_row);
            holder.radio_button = (RadioButton)view.findViewById(R.id.radio_button_element_row);
            holder.button = (Button)view.findViewById(R.id.button1);
            view.setTag(holder);

        }

        Character character = (Character) getItem(position);
        final ViewHolder holder = (ViewHolder)view.getTag();
        holder.radio_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contex, DetailsActivity.class);
                intent.putExtra("edit", holder.text_view.getText());
                contex.startActivity(intent);
                radio_button = (RadioButton) v;
                radio_button.setChecked(false);
            }
        });

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contex, DetailsActivity.class);
                intent.putExtra("edit", holder.text_view.getText());
                contex.startActivity(intent);
                button = (Button) v;

            }
        });
        holder.text_view.setText(character.name);
        return view;

    }

    public void removeCity(int position){
        lista.remove(position);
        notifyDataSetChanged();
    }

    private class ViewHolder{
        public TextView text_view = null;
        public RadioButton radio_button = null;
        public Button button = null;
    }
}
