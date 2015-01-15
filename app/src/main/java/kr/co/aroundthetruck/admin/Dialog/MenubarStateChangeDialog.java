package kr.co.aroundthetruck.admin.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import kr.co.aroundthetruck.admin.R;

/**
 * Created by 윤석 on 2015-01-15.
 */
public class MenubarStateChangeDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private Button btn1;
    private Button btn2;

    private ImageView backgroundImageview;

    public int n = 0;
    public MenubarStateChangeDialog(Context context, int state){
        super(context);
        this.mContext = context;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_menubar_state_change);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        backgroundImageview = (ImageView) findViewById(R.id.dialog_menubar_state_change_background_imageview);


        btn1 = (Button) findViewById(R.id.dialog_menubar_state_change_button1);
        btn2 = (Button) findViewById(R.id.dialog_menubar_state_change_button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }

    public void setBackgroundImageview(Drawable d){
        backgroundImageview.setImageDrawable(d);
        //        if (state == 0){
//            backgroundImageview.setImageDrawable(getOwnerActivity().getResources().getDrawable(R.drawable.dialog_closed) );
//        }else if (state == 1){
//            backgroundImageview.setImageDrawable(getOwnerActivity().getResources().getDrawable(R.drawable.dialog_open) );
//        }
    }

    public void setOnclickListenr1(View.OnClickListener listener1){
        btn1.setOnClickListener(listener1);
    }

    @Override
    public void onClick( View view){
        if (view == btn1){

            n = 1;
        } else if (view == btn2){

            n = 2;
        }
        dismiss();
    }
}
