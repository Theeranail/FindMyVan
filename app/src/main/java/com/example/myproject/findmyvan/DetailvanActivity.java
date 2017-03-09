package com.example.myproject.findmyvan;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myproject.findmyvan.model.CheckAccessInternet;
import com.example.myproject.findmyvan.model.Config;
import com.example.myproject.findmyvan.model.DetailCar;
import com.example.myproject.findmyvan.model.JsonHttp;
import com.example.myproject.findmyvan.model.SharedPreferencesCheck;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class DetailvanActivity extends AppCompatActivity {

    public SharedPreferencesCheck spc;
    private CheckAccessInternet cAn;
    public String Addressnamecar, timeservice, tel, img_car;
    public int id_car;
    public ListView listViewdetail;
    public TextView textvew_nodata, text_name_main, text_time, text_tel;
    public ImageView img_cars;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailvan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        spc = new SharedPreferencesCheck(DetailvanActivity.this);
        spc.checksharedPre();

        Bundle bundle = getIntent().getExtras();
        Addressnamecar = bundle.getString("namecaraddress");
        id_car = bundle.getInt("id_car");
        timeservice = bundle.getString("timeservice");
        tel = bundle.getString("tel");
        img_car = bundle.getString("img_car");

        getSupportActionBar().setTitle("รายละเอียด " + Addressnamecar);

        listViewdetail = (ListView) findViewById(R.id.listviewdetail);
        textvew_nodata = (TextView) findViewById(R.id.textvew_nodata);
        text_name_main = (TextView) findViewById(R.id.text_name_main);
        text_time = (TextView) findViewById(R.id.text_time);
        text_tel = (TextView) findViewById(R.id.text_tel);
        img_cars = (ImageView) findViewById(R.id.img_car);


        cAn = new CheckAccessInternet(DetailvanActivity.this);
        if (cAn.isConnectNet()) {
            new LoadDetailcar(DetailvanActivity.this, id_car).execute();
        } else {
            Toast.makeText(DetailvanActivity.this, "No Internet Connected", Toast.LENGTH_LONG).show();
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

    public class LoadDetailcar extends AsyncTask<Object, Integer, List<DetailCar.Listdetailcar>> {
        private Context context;
        private int id_car;
        private ProgressDialog progress;

        public LoadDetailcar(Context context, int id_car) {
            this.context = context;
            this.id_car = id_car;
        }

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(context);
            progress.setMessage("Loading please wait...");
            progress.setCancelable(false);
            progress.show();
        }

        @Override
        protected List<DetailCar.Listdetailcar> doInBackground(Object... objects) {
            try {
                JsonHttp jsonHttp = new JsonHttp();
                Gson gson = new Gson();
                Log.e("id_car", String.valueOf(id_car).toString());
                RequestBody formbody = new FormBody.Builder()
                        .add("function", "getDetailvan")
                        .add("id_car", String.valueOf(id_car).toString())
                        .build();
                String ressult = jsonHttp.SyncTaskOkHttp(formbody, Config.url_getVan);
                Log.e("ressult", ressult);
                Type listType = new TypeToken<List<DetailCar.Listdetailcar>>() {
                }.getType();
                List<DetailCar.Listdetailcar> posts = gson.fromJson(ressult, listType);

                return posts;
            } catch (RuntimeException e) {
                Log.e("RuntimeException", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<DetailCar.Listdetailcar> ob) {
            super.onPostExecute(ob);

            if (progress.isShowing()) {
                progress.dismiss();
            }

            Glide.with(DetailvanActivity.this)
                    .load(img_car)
                    .placeholder(R.drawable.ic_panorama_black_24dp)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(img_cars);
            text_name_main.setText(" " + Addressnamecar);
            text_time.setText(" เวลาให้บริการ " + timeservice);
            text_tel.setText(" " + tel);

            if (ob != null) {
                if (ob.get(0).getCarD_id() == 0) {
                    textvew_nodata.setVisibility(View.VISIBLE);
                } else {
                    textvew_nodata.setVisibility(View.GONE);
                    listViewdetail.setAdapter(new CreateViewDetail(context, ob));
                }
            } else {
                Toast.makeText(context, "No Internet Connect", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class CreateViewDetail extends BaseAdapter {

        private List<DetailCar.Listdetailcar> list;
        private LayoutInflater inflater;
        private Context context;

        public CreateViewDetail(Context context, List<DetailCar.Listdetailcar> list) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (view == null) {
                view = inflater.inflate(R.layout.content_item_detail, null);
            }

            TextView textview_route = (TextView) view.findViewById(R.id.textview_route);
            TextView textview_driver = (TextView) view.findViewById(R.id.textview_driver);
            TextView tetxtview_tel = (TextView) view.findViewById(R.id.tetxtview_tel);
            TextView textview_LicensePlate = (TextView) view.findViewById(R.id.textview_LicensePlate);
            TextView textview_price = (TextView) view.findViewById(R.id.textview_price);
            ImageView imageView = (ImageView) view.findViewById(R.id.img_detail);

            textview_route.setText("เส้นทาง : " + Addressnamecar + "-" + list.get(i).getCarD_destination());
            textview_driver.setText("ชื่อคนขับ : " + list.get(i).getCarD_name());
            tetxtview_tel.setText("เบอร์โทร : " + list.get(i).getCarD_tel());
            textview_LicensePlate.setText("ป้ายทะเบียน : " + list.get(i).getCarD_license());
            textview_price.setText("ราคาโดยสาร : " + list.get(i).getCarD_price());

            Glide.with(context)
                    .load(list.get(i).getCarD_img())
                    .placeholder(R.drawable.ic_panorama_black_24dp)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
            return view;
        }
    }

}
