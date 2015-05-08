package list.mios.com.listsimple;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ToDoList extends Activity implements View.OnClickListener {

    ArrayList<String> todoItems;
    EditText myEditText;
    ArrayAdapter<String> aa;

    @Override
    public void onClick(View view) {
        todoItems.add(0, myEditText.getText().toString());
        aa.notifyDataSetChanged();
        myEditText.setText("");
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list);
        
     // Get references to UI widgets
     ListView myListView = (ListView)findViewById(R.id.myListView);
     myEditText = (EditText)findViewById(R.id.myEditText);
     final Button addItemButton = (Button)findViewById(R.id.addItemButton);
     addItemButton.setOnClickListener(this);
     
     // Create the array list of to do items
     todoItems = new ArrayList<String>();
     // Create the array adapter to bind the array to the listview
     aa = new ArrayAdapter<String>(this,
    		 					   R.layout.row,
    		 					   todoItems);
     // Bind the array adapter to the listview.
     myListView.setAdapter(aa);
    }
}