package com.ench.lavanya1369.learnmap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    ListView listView;
    private GoogleMap mMap;
    private TrackGPS gps;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        listView=(ListView)findViewById(R.id.listtome);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gps = new TrackGPS(MapsActivity.this);
        mMap = googleMap;
        Double lat =gps .getLatitude();
        Double lng =  gps.getLongitude();
        mMap.getUiSettings().setZoomControlsEnabled(true);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.current_position_tennis_ball);
        LatLng sydney = new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").icon(icon));
        new kilomilo().execute(App_Url_Global.INSERT_DATA);
        /* mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);*/
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(sydney)      // Sets the center of the map to Mountain View
                .zoom(10)                   // Sets the zoom
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.addCircle(new CircleOptions().center(sydney)
                .radius(5000)
                .strokeWidth(1.0f)
                .strokeColor(Color.BLUE)
                /*.fillColor(0x550000FF)*/);
    }




    public class MovieAdap extends ArrayAdapter {

        private List<alldays_main> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;
        MovieAdap(Context context, int resource, List<alldays_main> objects) {
            super(context, resource, objects);
            movieModelList = objects;
            this.context =context;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        }
        @Override
        public int getViewTypeCount() {

            return 1;
        }

        @Override
        public int getItemViewType(int position) {

            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final ViewHolder holder  ;

            if(convertView == null){
                convertView = inflater.inflate(resource,null);
                holder = new ViewHolder();

                holder.textid=(TextView)  convertView.findViewById(R.id.text45);
                convertView.setTag(holder);
                holder.textid1=(TextView)  convertView.findViewById(R.id.text46);
                convertView.setTag(holder);

            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            alldays_main ccitac=movieModelList.get(position);
            holder.textid.setText(ccitac.getLat_andro());
            holder.textid1.setText(ccitac.getLong_andro());
         Double   latitud=Double.parseDouble(ccitac.getLat_andro());
          Double  longitud=Double.parseDouble(ccitac.getLong_andro());

            mMap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin2))
                    .title("lavanya"));
            return convertView;
        }

        class ViewHolder{
            public TextView textid,textid1;
        }
    }




    public class kilomilo extends AsyncTask<String,String, List<alldays_main>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<alldays_main> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("result");
                List<alldays_main> milokilo = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    alldays_main catego = gson.fromJson(finalObject.toString(), alldays_main.class);
                    milokilo.add(catego);

                }

                return milokilo;

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;

        }

        @Override
        protected void onPostExecute(final List<alldays_main> movieMode) {
            super.onPostExecute(movieMode);
            if (movieMode.size() > 0) {
                MovieAdap adapter = new MovieAdap(getApplicationContext(), R.layout.list_main_show, movieMode);
                listView.setAdapter(adapter);
            }


        }


    }










}
