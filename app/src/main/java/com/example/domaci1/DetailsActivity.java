package com.example.domaci1;

import android.annotation.SuppressLint;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button temperatura;
    private Button sunce;
    private Button vetar;
    private Spinner spiner;
    private TextView lokacija;
    private LinearLayout l1, l2, l3;
    private TextView date;
    private Calendar time;
    private TextView Location, Day, Temperature, Sun_set, Sun_rise, Wind_speed, Wind_dir, Pressuere, Humidity;

    ArrayAdapter<String> adapter;
    private HttpHelper httpHelper;
    String location;

    public static String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    public static String API_KEY = "&APPID=099a5ffa49f4ec3f9e04115d59c589dc&units=metric";
    public String GET_WEATHER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        temperatura = (Button) findViewById(R.id.temperatura);
        sunce = (Button) findViewById(R.id.sunce);
        vetar = (Button) findViewById(R.id.vetar);
        lokacija = (TextView) findViewById(R.id.lokacija);
        date = (TextView) findViewById(R.id.date);
        time = Calendar.getInstance();


        Location = findViewById(R.id.lokacija);
        Day = findViewById(R.id.date);
        Temperature = findViewById(R.id.view1);
        Pressuere = findViewById(R.id.view2);
        Humidity = findViewById(R.id.view3);
        Sun_rise = findViewById(R.id.izlazak_sunca);
        Sun_set = findViewById(R.id.zalazak_sunca);
        Wind_speed = findViewById(R.id.brzina_vetra);
        Wind_dir = findViewById(R.id.Pravac);


        temperatura.setOnClickListener(this);
        sunce.setOnClickListener(this);
        vetar.setOnClickListener(this);

        l1 = (LinearLayout) findViewById(R.id.l1);
        l2 = (LinearLayout) findViewById(R.id.l2);
        l3 = (LinearLayout) findViewById(R.id.l3);

        Bundle bundleLocation = getIntent().getExtras();
        Location.setText("Lokacija: " + bundleLocation.get("edit").toString());
        location = bundleLocation.get("edit").toString();
        GET_WEATHER = BASE_URL + location + API_KEY;

        Log.d("URL", "url: " + GET_WEATHER);

        time = Calendar.getInstance();
        String[] days = new String[] {"Ponedeljak", "Utorak", "Sreda", "Cetvrtak", "Petak", "Subota", "Nedelja"};
        date.setText("Dan: " + days[time.get(Calendar.DAY_OF_WEEK) - 2]);

        spiner = findViewById(R.id.spinner);
        String[] drop_down = new String[] {"°C" , "F"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, drop_down);
        spiner.setAdapter(adapter);


        l1.setVisibility(View.INVISIBLE);
        l2.setVisibility(View.INVISIBLE);
        l3.setVisibility(View.INVISIBLE);


        httpHelper = new HttpHelper();

    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.temperatura:
                temperatura.setBackground(getDrawable(R.drawable.button2));
                sunce.setBackground(getDrawable(R.drawable.button1));
                vetar.setBackground(getDrawable(R.drawable.button1));
                l1.setVisibility(View.VISIBLE);
                l2.setVisibility(View.INVISIBLE);
                l3.setVisibility(View.INVISIBLE);

                spiner.setAdapter(adapter);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonobject = httpHelper.getJSONObjectFromURL(GET_WEATHER);
                            JSONObject mainobject = jsonobject.getJSONObject("main");

                            final String temp = mainobject.get("temp").toString();
                            final String pressure = mainobject.get("pressure").toString();
                            final String humidity = mainobject.get("humidity").toString();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    double tempCelsius = Double.parseDouble(temp);
                                    int tempRound = (int)tempCelsius;
                                    final String temperature = Integer.toString(tempRound);
                                    Temperature.setText("Temperatura: " + temperature + " °C");
                                    Pressuere.setText("Pritisak: " + pressure+ " hPA");
                                    Humidity.setText("Vlažnost vazduha: " + humidity + " %");

                                    spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            String selected = parent.getItemAtPosition(position).toString();
                                            if (selected.equals("°C")) {
                                                Temperature.setText("Temperatura: " + temperature + " °C\nPritisak: " + pressure + " hPA" + "\nVlažnost vazduha: " + humidity + " %");
                                            } else {
                                                double tempFarenhite = Double.parseDouble(temp);
                                                tempFarenhite = tempFarenhite * (9/5) + 32;
                                                int tempRound = (int)tempFarenhite;
                                                String temperature = Integer.toString(tempRound);
                                                Temperature.setText("Temperatura: " + temperature + " °F\nPritisak: " + pressure + " hPA" + "\nVlažnost vazduha: " + humidity + " %");
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {
                                            Temperature.setText("Temperatura: " + temperature + " °C\nPritisak: " + pressure + " hPA" + "\nVlažnost vazduha: " + humidity + " %");
                                        }
                                    });
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                break;
            case R.id.sunce:
                temperatura.setBackground(getDrawable(R.drawable.button1));
                sunce.setBackground(getDrawable(R.drawable.button2));
                vetar.setBackground(getDrawable(R.drawable.button1));
                l1.setVisibility(View.INVISIBLE);
                l2.setVisibility(View.VISIBLE);
                l3.setVisibility(View.INVISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonobject = httpHelper.getJSONObjectFromURL(GET_WEATHER);
                            JSONObject sysobject = jsonobject.getJSONObject("sys");

                            long sun = Long.valueOf(sysobject.get("sunrise").toString()) * 1000;
                            Date date1 = new Date(sun);
                            final String sunrise = new SimpleDateFormat("hh:mma", Locale.ENGLISH).format(date1);

                            long night = Long.valueOf(sysobject.get("sunset").toString()) * 1000;
                            Date date2 = new Date(night);
                            final String sunset = new SimpleDateFormat("hh:mma", Locale.ENGLISH).format(date2);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Sun_rise.setText("Izlazak sunca: " + sunrise);
                                    Sun_set.setText("Zalazak sunca: " + sunset);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                break;
            case R.id.vetar:
                temperatura.setBackground(getDrawable(R.drawable.button1));
                sunce.setBackground(getDrawable(R.drawable.button1));
                vetar.setBackground(getDrawable(R.drawable.button2));
                l1.setVisibility(View.INVISIBLE);
                l2.setVisibility(View.INVISIBLE);
                l3.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonobject = httpHelper.getJSONObjectFromURL(GET_WEATHER);
                            JSONObject windobject = jsonobject.getJSONObject("wind");

                            final String wind_speed = windobject.get("speed").toString();

                            double degree = windobject.getDouble("deg");
                            final String wind_direction = degreeToString(degree);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Wind_speed.setText("Brzina vetra: " + wind_speed + " m/s");
                                    Wind_dir.setText("Pravac: " + wind_direction);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                break;


        }

    }

    public String degreeToString(Double degree) {
        if (degree>337.5)
            return "Sever";
        if (degree>292.5)
            return "Severozapad";
        if(degree>247.5)
            return "Zapad";
        if(degree>202.5)
            return "Jugozapad";
        if(degree>157.5)
            return "Jug";
        if(degree>122.5)
            return "Jugoistok";
        if(degree>67.5)
            return "Istok";
        if(degree>22.5){
            return "Severoistok";
        } else
            return "Sever";
    }
}
