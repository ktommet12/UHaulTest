package com.kyletommet.uhaultest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.kyletommet.uhaultest.DB.AddNewPost;
import com.kyletommet.uhaultest.DB.DBWorkerDelegate;
import com.kyletommet.uhaultest.User.Post;

import org.json.JSONObject;

public class AddNewPostActivity extends AppCompatActivity implements DBWorkerDelegate{
    private EditText title, body;
    private AddNewPost addNewPost;
    private final String TAG = "AddNewPostActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_post);

        title   = (EditText) findViewById(R.id.txt_title);
        body    = (EditText) findViewById(R.id.txt_post_body);


    }
    public void addNewPost(View v){
        String postTitle    = title.getText().toString();
        String postBody     = body.getText().toString();
        int tempPostersID   = 1;

        Post newPost = new Post(postTitle, postBody, tempPostersID);

        addNewPost = new AddNewPost();
        addNewPost.setOnFinishedListener(this);
        addNewPost.execute(newPost);
    }

    @Override
    public void taskFinished(JSONObject returnedJSON) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        title.setText("");
        body.setText("");
        Toast.makeText(this, "Post Successfully Made", Toast.LENGTH_SHORT).show();

    }
}
