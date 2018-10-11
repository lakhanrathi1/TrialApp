package lakhan.trialapp;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myref = firebaseDatabase.getReference("main");

    EditText t1,t2,t3,t4;
    Button b1;
    ProgressDialog mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1 = (EditText) findViewById(R.id.name);
        t2 = (EditText) findViewById(R.id.phone);
        t3 = (EditText) findViewById(R.id.occupation);
        t4 = (EditText) findViewById(R.id.location);
        b1 = (Button)findViewById(R.id.addButton);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aname = t1.getText().toString();
                String amob = t2.getText().toString();
                String aoc = t3.getText().toString();
                String alocation = t4.getText().toString();
                String key = myref.child("userinfo").push().getKey();
                Map<String,String> map = new HashMap<String, String>();
                map.put("name",aname);
                map.put("location",alocation);
                map.put("occupation",aoc);
                map.put("phone",amob);

                mp = new ProgressDialog(MainActivity.this);
                mp.setTitle("Submitting the Question");
                mp.setMessage("Wait for a Min");
                mp.setCanceledOnTouchOutside(false);
                mp.show();
                myref.child("userinfo").child(key).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mp.dismiss();
                            t1.setText(null);
                            t2.setText(null);
                            t3.setText(null);
                            t4.setText(null);
                            Toast.makeText(MainActivity.this, "Sucessfull", Toast.LENGTH_LONG).show();

                        }
                        else if(!task.isSuccessful()){

                            mp.dismiss();
                            Toast.makeText(MainActivity.this, "Something WRONG", Toast.LENGTH_LONG).show();

                        }
                    }
                });


            }

        });


    }
}
