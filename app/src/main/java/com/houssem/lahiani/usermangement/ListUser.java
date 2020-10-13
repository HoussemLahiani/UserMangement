package com.houssem.lahiani.usermangement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ListUser extends AppCompatActivity {
ListView ls;
ProgressDialog dialog;
JSONParser parser=new JSONParser();
ArrayList<HashMap<String,String>> values= new ArrayList<HashMap<String, String>>();

int success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        ls=findViewById(R.id.lst);

        new All().execute();

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView t=view.findViewById(R.id.id);

                Intent i=new Intent(ListUser.this, DetailActivity.class);
                i.putExtra("id",t.getText().toString());
                startActivity(i);
            }
        });

    }


    class All extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(ListUser.this);
            dialog.setMessage("Patientez SVP");
            dialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            HashMap<String,String> map = new HashMap<>();
            JSONObject object=parser.makeHttpRequest("http://10.0.2.2/user/all.php","GET",map);
            try {
                success=object.getInt("success");
                if(success==1)
                {
                    JSONArray users= object.getJSONArray("users");
                    for(int i=0;i<users.length();i++)
                    {
                        JSONObject user=users.getJSONObject(i);
                        HashMap<String,String> m= new HashMap<String,String>();
                        m.put("id",user.getString("id"));
                        m.put("nom",user.getString("nom"));
                        m.put("age", user.getString("age"));

                        values.add(m);


                    }



                }



            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.cancel();
            Log.e("useeeer",values.toString());
            SimpleAdapter adapter=new SimpleAdapter(ListUser.this,values,R.layout.item,
                new String[]{"id","nom","age"}, new int[]{R.id.id, R.id.nom,R.id.age}    );

            ls.setAdapter(adapter);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0,1,0,"Ajouter utilsateur");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==1)
        {
            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            
        }


        return super.onOptionsItemSelected(item);
    }
}