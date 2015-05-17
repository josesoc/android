package com.mycom.sqlitecompleteexample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    TodoItemDatabase database;
    EditText body_ed;
    EditText priority_ed;
    TextView output_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        body_ed=(EditText)findViewById(R.id.body_ed);
        priority_ed=(EditText)findViewById(R.id.priority_ed);
        output_txt=(TextView)findViewById(R.id.output_txt);

        database=new TodoItemDatabase(this);
    }

    public void addNew(View view) {
        Toast.makeText(this, "addNew", Toast.LENGTH_SHORT).show();
        if (body_ed.getText().length() == 0 ||
            priority_ed.getText().length() == 0)
            return;

        int priority=Integer.parseInt(priority_ed.getText().toString());
        database.addTodoItem(new TodoItem(body_ed.getText().toString(), priority));
    }

    public void getAll(View view) {
        Toast.makeText(this, "getAll", Toast.LENGTH_SHORT).show();

        List<TodoItem> todoItems=database.getAllTodoItems();
        output_txt.setText(todoItems.toString());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        database.close();
        super.onPause();
    }
}
