package com.kyletommet.uhaultest;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.kyletommet.uhaultest.DB.DBWorkerDelegate;
import com.kyletommet.uhaultest.DB.GetUsers;
import com.kyletommet.uhaultest.User.User;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DBWorkerDelegate, View.OnClickListener {
    private GetUsers getUsers;
    private EditText title, body;
    private final String TAG = "MainActivity";
    private ArrayList<User> users;
    private TableLayout userTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userTable = (TableLayout) findViewById(R.id.user_table);



        //Since for some reason the REST retrieve is not working
        users = new ArrayList<>();
        users.add(new User("Kyle", "ktommet", ""));
        users.add(new User("Mary", "mashru12", ""));
        users.add(new User("Kevin", "kevin124", ""));




        //GetPosts getPosts = new GetPosts();
        //getPosts.setOnFinishedListener(this);
        //getPosts.execute(1);

        GetUsers getUsers = new GetUsers();
        getUsers.setOnFinishedListener(this);
        getUsers.execute();

        //startActivity(new Intent(this, AddNewPostActivity.class));
    }

    @Override
    public void taskFinished(JSONObject returnedJSON) {
        for(int i = 0; i < returnedJSON.length(); i++){
            //Todo: create User objects from returned json string
        }
        for(User user : users){
            TableRow    tr = (TableRow) getLayoutInflater().inflate(R.layout.user_table_row, null);
            tr.setOnClickListener(this);
            TextView    userName = (TextView) tr.findViewById(R.id.user_name),
                        userPost = (TextView) tr.findViewById(R.id.user_post);

            userName.setText(user.getName());
            userTable.addView(tr);
        }
    }

    @Override
    public void onClick(View v) {
        TextView name = (TextView) v.findViewById(R.id.user_name);
        String userName = name.getText().toString();

        Log.i(TAG, userName);
        Intent intent = new Intent(this, UserSelectedActivity.class);
        intent.putExtra("usersName", userName);
        startActivity(intent);
    }
}
