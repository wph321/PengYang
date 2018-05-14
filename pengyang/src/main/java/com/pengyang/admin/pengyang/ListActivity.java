package com.pengyang.admin.pengyang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.pengyang.com.response.modle.Patient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    List<Patient> patientList = new ArrayList<>();
    List<HashMap<String,Object>> data=new ArrayList<HashMap<String,Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initPatient();
        ListView listView=(ListView) findViewById(R.id.patient_list_item);
        SimpleAdapter adapter=new SimpleAdapter(this,
                data,
                R.layout.item_list,
                new String[]{"id","name","age","sex","telphone"},
                new int[]{R.id.patient_id,R.id.patient_name,R.id.patient_age,R.id.patient_sex,R.id.patient_tele});
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //adapterView是被点击的那个ListView
                ListView listView = (ListView) adapterView;
                HashMap<String, Object> item = (HashMap<String, Object>)listView.getItemAtPosition(i);
                //获取被选中行对应的ID
                Object scoreId=item.get("id");
                Toast.makeText(ListActivity.this,scoreId.toString(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(ListActivity.this,ShowPDFActivity.class);
                intent.putExtra("pdf_id",scoreId.toString());
                startActivity(intent);
            }
        });

    }

    public void  initPatient(){
        Intent intent=getIntent();
        String tmp = intent.getStringExtra("patient");

        try {
            JSONArray jsonArray =new JSONArray(tmp);
            for(int i = 0; i<jsonArray.length();i++){
                Patient p = new Patient();
                JSONObject json = jsonArray.getJSONObject(i);
                String name = json.getString("name");
                p.setId(json.getInt("id"));
                p.setName(json.getString("name"));
                p.setAge(json.getString("age"));
                p.setSex(json.getString("sex"));
                p.setTelphone(json.getString("telphone"));
                patientList.add(p);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(Patient patient:patientList){

            HashMap<String, Object>item=new HashMap<String, Object>();
            item.put("id",patient.getId());
            item.put("name",patient.getName());
            item.put("age",patient.getAge());
            item.put("sex",patient.getSex());
            item.put("telphone",patient.getTelphone());
            data.add(item);
        }
    }

}
