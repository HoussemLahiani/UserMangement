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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
EditText nom,age;
Button ajouter;
ProgressDialog dialog;

JSONParser parser=new JSONParser();
int success;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nom=findViewById(R.id.nom);
        age=findViewById(R.id.age);
        ajouter=findViewById(R.id.add);

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Add().execute();
            }
        });

    }

    class Add extends AsyncTask<String, String,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(MainActivity.this);
            dialog.setMessage("Patientez SVP");
            dialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            HashMap<String,String> map=new HashMap<String,String>();
            map.put("nom",nom.getText().toString());
            map.put("age", age.getText().toString());

            JSONObject object=parser.makeHttpRequest("http://10.0.2.2/user/add.php","GET",map);

            try {
                success=object.getInt("success");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            dialog.cancel();

            if(success==1)
            {
                Toast.makeText(MainActivity.this,"Ajout effectué",Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this,ListUser.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(MainActivity.this,"Echec !!!!!",Toast.LENGTH_LONG).show();
            }

        }
    }

}