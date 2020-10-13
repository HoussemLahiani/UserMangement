package com.houssem.lahiani.usermangement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {

    EditText nom,age;
    Button mod,sup;
    String id;

    ProgressDialog dialog;
    JSONParser parser=new JSONParser();
    int success, success_mod,success_sup;
    String name,old;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        nom=findViewById(R.id.nom);
        age=findViewById(R.id.age);
        mod=findViewById(R.id.button);
        sup=findViewById(R.id.button2);

        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
            id=extras.getString("id");

            new Select().execute();
        }

        mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Modifier().execute();
            }
        });


sup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        new Supprimer().execute();
    }
});

    }

    class Select extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(DetailActivity.this);
            dialog.setMessage("Patientez SVP");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            HashMap<String,String> map=new HashMap<String,String>();
            map.put("id",id);

            JSONObject object=parser.makeHttpRequest("http://10.0.2.2/user/select_one.php","GET",map);

            try {
                success=object.getInt("success");
                if(success==1)
                {
                    JSONArray user=object.getJSONArray("user");
                    JSONObject o=user.getJSONObject(0);
                    name=o.getString("nom");
                    old=o.getString("age");

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
            nom.setText(name);
            age.setText(old);

        }
    }


    class Modifier extends AsyncTask<String,String,String>
    {


        @Override
        protected String doInBackground(String... strings)
        {

            HashMap<String,String> map=new HashMap<>();
            map.put("id",id);
            map.put("nom",nom.getText().toString());
            map.put("age",age.getText().toString());

            JSONObject ob= parser.makeHttpRequest("http://10.0.2.2/user/update.php","GET",map);
            try {
                success_mod=ob.getInt("success");
                if(success_mod==1)
                {
                    Intent i=new Intent(DetailActivity.this,ListUser.class);
                    startActivity(i);
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(success_mod==0)
            {
                Toast.makeText(DetailActivity.this,"Erreur de modification",Toast.LENGTH_LONG).show();
            }
        }
    }



    class Supprimer extends AsyncTask<String,String,String>
    {


        @Override
        protected String doInBackground(String... strings)
        {

            HashMap<String,String> map=new HashMap<>();
            map.put("id",id);


            JSONObject ob= parser.makeHttpRequest("http://10.0.2.2/user/delete.php","GET",map);
            try {
                success_sup=ob.getInt("success");
                if(success_sup==1)
                {
                    Intent i=new Intent(DetailActivity.this,ListUser.class);
                    startActivity(i);
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(success_sup==0)
            {
                Toast.makeText(DetailActivity.this,"Erreur de supression",Toast.LENGTH_LONG).show();
            }
        }
    }


}