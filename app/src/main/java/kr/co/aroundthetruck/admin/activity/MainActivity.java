package kr.co.aroundthetruck.admin.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.fragment.TodayResultFragment;
import kr.co.aroundthetruck.admin.ui.ATTActivity;

public class MainActivity extends ATTActivity {
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLayout();
        initialize();
    }

    @Override
    public void setLayout() {

    }

    @Override
    public void initialize() {
        getFragmentManager()
                .beginTransaction()
                .add(R.id.activity_main_container, TodayResultFragment.newInstance()).commit();
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
