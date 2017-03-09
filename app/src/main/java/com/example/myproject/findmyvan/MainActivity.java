package com.example.myproject.findmyvan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.Language;
import com.akexorcist.googledirection.constant.RequestResult;
import com.akexorcist.googledirection.constant.TransitMode;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.constant.Unit;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Info;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myproject.findmyvan.model.CheckAccessInternet;
import com.example.myproject.findmyvan.model.Config;
import com.example.myproject.findmyvan.model.CreateFuction;
import com.example.myproject.findmyvan.model.CustomViewmap;
import com.example.myproject.findmyvan.model.HistorySearch;
import com.example.myproject.findmyvan.model.JsonHttp;
import com.example.myproject.findmyvan.model.ModifyImages;
import com.example.myproject.findmyvan.model.MyLocation;
import com.example.myproject.findmyvan.model.SearchCar;
import com.example.myproject.findmyvan.model.SharedPreferencesCheck;
import com.example.myproject.findmyvan.model.Van;
import com.example.myproject.findmyvan.model.getlisternerlocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, getlisternerlocation {

    public String serverkey = "AIzaSyDfSAxypIgjayw4LkpfFknYJf9zbI0Sm2g";
    public List<Van.listVan> listarrayvan, listVansearch;
    public Van van;
    public MyLocation myLocation;
    public SharedPreferencesCheck spc;
    public double Lattitude = 0;
    public double Logtitude = 0;
    private CheckAccessInternet cAn;
    public FloatingActionButton btn_iconremylocation, btn_iconrefresh, btn_iconNavigate, btn_iconserch;
    public GoogleMap gMap;
    public String name = "";
    public ArrayList<Marker> markerArrayList;
    public Marker myMarker = null;
    public boolean FocusSatrt = false;
    public boolean statusbtn_Navigate = true;
    public ListView list_item_search, list_view_showSearch, list_view_historysearch;
    public String inputsesrch = "";
    double distace = 0;
    public LatLng latLngStart, latLngStop;
    public PolylineOptions polygonOptions;
    public Polyline line;
    public SharedPreferencesCheck sp;
    private ProgressDialog progressl;
    public RelativeLayout contentcenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn_iconremylocation = (FloatingActionButton) findViewById(R.id.btn_iconremylocation);
        btn_iconrefresh = (FloatingActionButton) findViewById(R.id.btn_iconrefresh);
        btn_iconNavigate = (FloatingActionButton) findViewById(R.id.btn_iconNavigate);
        btn_iconserch = (FloatingActionButton) findViewById(R.id.btn_iconserch);

        btn_iconremylocation.setOnClickListener(this);
        btn_iconrefresh.setOnClickListener(this);
        btn_iconNavigate.setOnClickListener(this);
        btn_iconserch.setOnClickListener(this);

        spc = new SharedPreferencesCheck(MainActivity.this);
        spc.checksharedPre();

        myLocation = new MyLocation(MainActivity.this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.viewmap);
        mapFragment.getMapAsync(this);

        gMap = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.viewmap)).getMap();

        cAn = new CheckAccessInternet(MainActivity.this);
        if (cAn.isConnectNet()) {
            new LoadVan(MainActivity.this).execute();
        } else {
            Toast.makeText(MainActivity.this, "No Internet Connected", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        spc.checksharedPre();
        myLocation.StartLocation();
        gMap = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.viewmap)).getMap();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myLocation.Stoplocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        spc.checksharedPre();
        myLocation.StartLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        myLocation.Stoplocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ((progressl != null) && progressl.isShowing()) {
            progressl.dismiss();
            progressl = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            SharedPreferencesCheck.Logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_iconremylocation:
                setFocusMyLocation();
                break;
            case R.id.btn_iconrefresh:
                if (cAn.isConnectNet()) {
                    if (line != null)
                        line.remove();
                    new LoadVan(MainActivity.this).execute();
                } else {
                    Toast.makeText(MainActivity.this, "No Internet Connected", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_iconNavigate:
                if (statusbtn_Navigate == true) {
                    FocusSatrt = true;
                    statusbtn_Navigate = false;
                    btn_iconNavigate.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#757575")));
                    Toast.makeText(MainActivity.this, "Close Navigate", Toast.LENGTH_LONG).show();
                } else {
                    statusbtn_Navigate = true;
                    FocusSatrt = false;
                    btn_iconNavigate.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2196F3")));
                    Toast.makeText(MainActivity.this, "Open Navigate", Toast.LENGTH_LONG).show();

                }
                break;
            case R.id.btn_iconserch:
                DialogSerch();
                break;
        }
    }

    @Override
    public void getLatliogLocation(Location location) {

        Lattitude = location.getLatitude();
        Logtitude = location.getLongitude();

        if (Lattitude != 0) {
            if (FocusSatrt == false) {
                setFocusMyLocation(Lattitude, Logtitude);
            }
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        final LatLng coordinateDefult = new LatLng(13.9474573, 98.7277476);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinateDefult, 16));

    }

    public void setFocusMyLocation(Double lattitude, Double logtitude) {
        if (myMarker != null)
            myMarker.remove();
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lattitude, logtitude), 16));
        myMarker = gMap.addMarker(new MarkerOptions()
                .position(new LatLng(lattitude, logtitude))
                .title("Mylocation")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mypoint)));
    }

    public void setFocusMyLocation() {
        if (myMarker != null)
            myMarker.remove();
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Lattitude, Logtitude), 16));
        myMarker = gMap.addMarker(new MarkerOptions()
                .position(new LatLng(Lattitude, Logtitude))
                .title("Mylocation")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mypoint)));
    }

    private void removeMarkers() {
        for (Marker marker : markerArrayList) {
            marker.remove();
        }
        markerArrayList.clear();
    }

    public class LoadVan extends AsyncTask<Object, Integer, List<Van.listVan>> {


        private Context context;

        public LoadVan(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {

            progressl = new ProgressDialog(context);
            progressl.setMessage("Loading please wait...");
            progressl.setCancelable(false);
            progressl.show();
        }

        @Override
        protected List<Van.listVan> doInBackground(Object... params) {

            try {

                JsonHttp jsonHttp = new JsonHttp();
                Gson gson = new Gson();
                String ressult = jsonHttp.LoadVan();
                Log.e("ressult", ressult);
                Type listType = new TypeToken<List<Van.listVan>>() {
                }.getType();
                List<Van.listVan> posts = gson.fromJson(ressult, listType);

                if (posts != null) {

                    setmarker(posts);
                }

                return posts;

            } catch (RuntimeException e) {
                String msg = (e.getMessage() == null) ? "Load failed!" : e.getMessage();
                Log.e("RuntimeException", msg);
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Van.listVan> ob) {
            super.onPostExecute(ob);
            if (progressl != null) {
                if (progressl.isShowing()) {
                    progressl.dismiss();
                }
            }
            if (ob != null) {
                if (ob.get(0).getId_car() == 0) {
                    Toast.makeText(context, "ไม่มีข้อมูล", Toast.LENGTH_LONG).show();
                } else {
                    listarrayvan = ob;
                }

            } else {
                Toast.makeText(context, "No Internet Connect", Toast.LENGTH_LONG).show();
            }

        }
    }

    public void setmarker(final List<Van.listVan> list) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ModifyImages modifyImages = new ModifyImages();
                View vMarker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);

                markerArrayList = new ArrayList<Marker>();
                removeMarkers();
                for (int i = 0; i < list.size(); i++) {
                    Marker marker = null;
                    marker = gMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.valueOf(list.get(i).getLattitude()).doubleValue(),
                                    Double.valueOf(list.get(i).getLogtitude()).doubleValue()))
                            .icon(BitmapDescriptorFactory.fromBitmap(modifyImages.createDrawableFromView(MainActivity.this, vMarker)))
                            .title(list.get(i).getAddress())
                            .snippet("เวลาเดินรถ:" + list.get(i).getTimeservie() + ", " + "M" + i)
                            .anchor(0.5f, 1));
                    markerArrayList.add(marker);
                    markerArrayList.get(i).hideInfoWindow();

                    gMap.setInfoWindowAdapter(new CustomViewmap.setViewTitle(MainActivity.this));
                    gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            //Toast.makeText(MainActivity.this,marker.getId(),Toast.LENGTH_LONG).show();
                            ClickShowdetail(CreateFuction.splitsnippet(marker.getSnippet()));
                        }
                    });
                }
            }
        });
    }

    public void setFocusMarker(Double lattitude, Double logtitude) {
        final LatLng coordinateDefult = new LatLng(lattitude, logtitude);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinateDefult, 16));
        for (int i = 0; i < markerArrayList.size(); i++) {


            if (markerArrayList.get(i).getPosition().equals(coordinateDefult)) {
                markerArrayList.get(i).hideInfoWindow();
                markerArrayList.get(i).showInfoWindow();
                CreateDirectionMap(coordinateDefult);
                Log.e("ok", markerArrayList.get(i).getTitle());
                return;
            }
        }


    }

    public void ClickShowdetail(int position) {

        Intent intent = new Intent(MainActivity.this, DetailvanActivity.class);
        intent.putExtra("namecaraddress", listarrayvan.get(position).getAddress());
        intent.putExtra("id_car", listarrayvan.get(position).getId_car());
        intent.putExtra("timeservice", listarrayvan.get(position).getTimeservie());
        intent.putExtra("tel", listarrayvan.get(position).getTel());
        intent.putExtra("img_car", listarrayvan.get(position).getImg_car());
        startActivity(intent);
    }

    public void DialogSerch() {

        final AlertDialog.Builder dai_builder = new AlertDialog.Builder(MainActivity.this);
        SearchCar searchCar = new SearchCar();
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.content_serach, null);

        list_item_search = (ListView) view.findViewById(R.id.list_item_search);
        list_view_showSearch = (ListView) view.findViewById(R.id.list_view_showSearch);
        list_view_historysearch = (ListView) view.findViewById(R.id.list_view_historysearch);
        contentcenter = (RelativeLayout) view.findViewById(R.id.contentcenter);

        final EditText inputSearch = (EditText) view.findViewById(R.id.inputSearch);

        inputSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    inputsesrch = inputSearch.getText().toString();
                    setSearch(name, inputsesrch, Lattitude, Logtitude);
                }
                return false;
            }
        });

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e("beforeTextChanged", String.valueOf(charSequence));
                LoadHistoryserch("");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e("onTextChanged", String.valueOf(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e("afterTextChanged", String.valueOf(editable));

                LoadHistoryserch(String.valueOf(editable));

            }
        });

        SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, searchCar.AddrayyObj(), R.layout.listmenu_search
                , new String[]{"name", "img"}, new int[]{R.id.namemenu, R.id.imgicon});
        list_item_search.setAdapter(simpleAdapter);

        list_item_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchCar searchCar1 = new SearchCar();
                name = searchCar1.AddrayyObj().get(position).get("name").toString();
                setSearch(name, inputsesrch, Lattitude, Logtitude);

            }
        });

        dai_builder.setTitle("ค้นหารถตู้");
        dai_builder.setView(view);

        dai_builder.setNegativeButton("ปิด", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        final AlertDialog ad = dai_builder.show();

        list_view_showSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ad.dismiss();
                setFocusMarker(Double.valueOf(listVansearch.get(position).getLattitude()).doubleValue(), Double.valueOf(listVansearch.get(position).getLogtitude()).doubleValue());
            }
        });
    }


    public void setSearch(String naSearch, String inputSearch, Double lattitude, Double logtitude) {
        new GetSeachCar(MainActivity.this, naSearch, inputSearch, lattitude, logtitude).execute();
    }

    public class GetSeachCar extends AsyncTask<Object, Integer, List<Van.listVan>> {
        public Context context;
        public ProgressDialog progress;
        public String naSearch;
        public String inputSearch;
        public Double lattitude;
        public Double logtitude;

        public GetSeachCar(Context context, String naSearch, String inputSearch, Double lattitude, Double logtitude) {
            this.context = context;
            this.naSearch = naSearch;
            this.inputSearch = inputSearch;
            this.lattitude = lattitude;
            this.logtitude = logtitude;
        }

        @Override
        protected void onPreExecute() {

            progress = new ProgressDialog(context);
            progress.setMessage("กำลังค้นหา...");
            progress.setCancelable(false);
            progress.show();

        }

        @Override
        protected List<Van.listVan> doInBackground(Object... params) {
            try {

                JsonHttp jsonHttp = new JsonHttp();
                Gson gson = new Gson();

                RequestBody formBody = new FormBody.Builder()
                        .add("function", "SearchCar")
                        .add("inputeasrch", inputSearch)
                        .add("latti", String.valueOf(lattitude))
                        .add("longtitude", String.valueOf(logtitude))
                        .add("keyword", naSearch)
                        .add("id_member", sp.id_user)
                        .build();

                String ressult = jsonHttp.SyncTaskOkHttp(formBody, Config.url_getVan);
                Log.e("ressultGetSeachfood", ressult);
                Type listType = new TypeToken<List<Van.listVan>>() {
                }.getType();
                List<Van.listVan> posts = gson.fromJson(ressult, listType);

                return posts;

            } catch (RuntimeException e) {
                Log.e("RuntimeException", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Van.listVan> ob) {
            if (progress.isShowing()) {
                progress.dismiss();
            }
            if (ob != null) {
                name = "";
                if (ob.get(0).getId_car() == 0) {
                    contentcenter.setVisibility(View.VISIBLE);
                } else {
                    contentcenter.setVisibility(View.GONE);
                    listVansearch = ob;
                    list_view_showSearch.setAdapter(new CreateViewSerch(MainActivity.this, ob));
                }
            } else {
                Toast.makeText(context, "No Internet Connect", Toast.LENGTH_LONG).show();
            }

        }
    }

    public void LoadHistoryserch(String inputeasrch) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String id_member = sp.id_user;

        RequestBody formbody = new FormBody.Builder()
                .add("function", "Gethistorysearch")
                .add("id_member", id_member)
                .add("inputeasrch", inputeasrch)
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(Config.url_getVan).post(formbody).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("erroimg", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
                Gson gson = new Gson();
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        Log.e("result", result);
                        Type listType = new TypeToken<List<HistorySearch>>() {
                        }.getType();
                        List<HistorySearch> posts = gson.fromJson(result, listType);
                        if (posts != null) {
                            setViewlisthistory(posts);
                        }
                    } catch (IOException e) {
                        Log.e("erroimgonResponse", e.getMessage());
                    }
                } else {
                    Log.e("erroimgisSuccessful", " Not Success - code : " + response.code());
                }

            }
        });
    }

    public void setViewlisthistory(final List<HistorySearch> list) {

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (list != null) {
                    if (list.get(0).getId_historysearch() == 0) {
                        list_view_historysearch.setVisibility(View.GONE);
                    } else {
                        list_view_historysearch.setVisibility(View.VISIBLE);
                    }
                } else {
                    list_view_historysearch.setVisibility(View.GONE);
                }
                list_view_historysearch.setAdapter(new HistorySearch.AdapterViewList(MainActivity.this, list));
                list_view_historysearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        name = "";
                        setSearch(name, list.get(i).getKeyword(), Lattitude, Logtitude);
//                        Toast.makeText(MainActivity.this, list.get(i).getKeyword(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    public class CreateViewSerch extends BaseAdapter {

        private List<Van.listVan> list;
        private LayoutInflater inflater;
        Context context;

        public CreateViewSerch(Context context, List<Van.listVan> objects) {
            this.context = context;
            this.list = objects;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.content_item_list_serch, null);
            }

            TextView TextnameFood = (TextView) convertView.findViewById(R.id.person_search_name);
            TextnameFood.setText(list.get(position).getAddress());

            TextView textAddress = (TextView) convertView.findViewById(R.id.text_search_location);
            String strAddress = new String(list.get(position).getAddress());
            if (strAddress.length() > 59)
                strAddress = strAddress.substring(0, 58) + "...";
            textAddress.setText("ตำแหน่ง: " + strAddress);


            TextView text_distane = (TextView) convertView.findViewById(R.id.content_search_distane);

            distace = myLocation.CalculationDistance(Lattitude, Logtitude, Double.valueOf(list.get(position).getLattitude()).doubleValue(), Double.valueOf(list.get(position).getLogtitude()).doubleValue());
            text_distane.setText(String.format("%.2f", distace) + " กม.");

            ImageView img_show = (ImageView) convertView.findViewById(R.id.img_search_show);


            Glide.with(context)
                    .load(list.get(position).getImg_car())
                    .placeholder(R.drawable.ic_panorama_black_24dp)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img_show);


            return convertView;
        }
    }

    public void CreateDirectionMap(LatLng latLngStop) {

        Double lat = null;
        Double longt = null;

        lat = Lattitude;
        longt = Logtitude;

        Log.e("latLngStop", String.valueOf(latLngStop.latitude));
        Log.e("lat", String.valueOf(lat));

        if (lat == null)
            lat = 13.7335222;
        if (longt == null)
            longt = 100.5375236;

        latLngStart = new LatLng(lat, longt);

        if (latLngStart != null) {
            GoogleDirection.withServerKey(serverkey)
                    .from(latLngStart)
                    .to(latLngStop)
                    .transportMode(TransportMode.TRANSIT)
                    .language(Language.THAI)
                    .unit(Unit.METRIC)
                    .transitMode(TransitMode.BUS)
                    .alternativeRoute(true)
                    .execute(new DirectionCallback() {
                        @Override
                        public void onDirectionSuccess(Direction direction, String rawBody) {
                            String status = direction.getStatus();
                            if (status.equals(RequestResult.OK)) {

                                Route route = direction.getRouteList().get(0);
                                Leg leg = route.getLegList().get(0);
                                ArrayList<LatLng> pointList = leg.getDirectionPoint();

                                Info distanceInfo = leg.getDistance();
                                Info durationInfo = leg.getDuration();

                                String distance = distanceInfo.getText();
                                String duration = durationInfo.getText();
//                                gMap.clear();
//
//                                List<Step> stepList = direction.getRouteList().get(0).getLegList().get(0).getStepList();
//                                ArrayList<PolylineOptions> polylineOptionses = DirectionConverter.createTransitPolyline(MainActivity.this, stepList, 5, Color.RED, 3, Color.BLUE);
//                                for (PolylineOptions polylineOption : polylineOptionses) {
//                                    gMap.addPolyline(polylineOption);
//
//                                }

                                if (line != null)
                                    line.remove();

                                ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();

                                polygonOptions = DirectionConverter
                                        .createPolyline(MainActivity.this, directionPositionList, 5, Color.RED);
                                line = gMap.addPolyline(polygonOptions);


                            } else if (status.equals(RequestResult.REQUEST_DENIED)) {

                            } else if (status.equals(RequestResult.UNKNOWN_ERROR)) {

                            }
                        }

                        @Override
                        public void onDirectionFailure(Throwable t) {

                        }
                    });
        }
    }
}
