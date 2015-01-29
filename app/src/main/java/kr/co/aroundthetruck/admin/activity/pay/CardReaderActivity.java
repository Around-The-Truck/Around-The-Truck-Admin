package kr.co.aroundthetruck.admin.activity.pay;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.activity.register.RegisterAdminActivity;
import kr.co.aroundthetruck.admin.activity.register.RegisterFoodMenuActivity;
import kr.co.aroundthetruck.admin.ui.ATTActivity;

public class CardReaderActivity extends ATTActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_reader);

        setLayout();
        initialize();
    }

    @Override
    public void setLayout() {

    }

    @Override
    public void initialize() {

    }

    private void finishActivity(){
        startActivity(new Intent(this, CardSignActivity.class));
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //     }
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            finishActivity();
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            finishActivity();
        }
            return super.onKeyDown(keyCode, event);
//        return true;
    }

//    private static IntentFilter plugIntentFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
//    private static BroadcastReceiver plugStateChangeReceiver = null;
//
//
//
//// ** onCreate
//    plugStateChangeReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            AudioManager audio = (AudioManager) getSystemService(BaseActivity.this.AUDIO_SERVICE);
//            int systemVolume = audio.getStreamVolume(AudioManager.STREAM_SYSTEM);
//
//            // plug状態を取得
//            isPlugged = (intent.getIntExtra("state", 0) > 0) ? true : false;
//
//            if (isPlugged) {
//                Log.i(null, "Earphone is plugged");
//            } else {
//                Log.i(null, "Earphone is unPlugged");
//            }
//
//        }
//    };
//
//
//
//    // ** 리스너 등록
//// 리스너 등록할때, 플러그 체크도 같이 해줌
//    registerReceiver(plugStateChangeReceiver, plugIntentFilter);
//
//
//    // ** 리스너 해제
//    unregisterReceiver(plugStateChangeReceiver);
//


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card_reader, menu);
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
