package zerofox.kr.ttak;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;
    static ListViewAdapter listViewAdapter;
    RoomMakeDialog roomMakeDL;

    String currentTime;

    final int[] default_imgs = new int[]{ R.drawable.asdf, R.drawable.asdfasdf, R.drawable.asdfasdfasdf, R.drawable.ayano };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (ListView) findViewById(R.id.roomList);
        listViewAdapter = new ListViewAdapter();
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "This service is not realized yet. Sorry :(", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                roomMakeDL = new RoomMakeDialog();
                roomMakeDL.show(getFragmentManager(), null);
            }
        });

        listView.setAdapter(listViewAdapter);

        currentTime = new SimpleDateFormat("a hh:mm").format(new Date(System.currentTimeMillis()));
        for (int i=0; i<4; i++)
            listViewAdapter.add(getResources().getDrawable(default_imgs[i]), "제목", "글", currentTime);
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
}
