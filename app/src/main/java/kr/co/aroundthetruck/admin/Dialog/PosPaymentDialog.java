package kr.co.aroundthetruck.admin.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.fragment.PosPointFragment;

/**
 * Created by 윤석 on 2015-01-13.
 */
public class PosPaymentDialog extends Dialog implements View.OnClickListener{

    private Context mContext;
    private ImageButton btn1;
    private ImageButton btn2;

    public int n = 0;
    public PosPaymentDialog(Context context){
        super(context);
        this.mContext = context;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_pos_payment);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btn1 = (ImageButton) findViewById(R.id.dialog_pos_payment_imagebutton1);
        btn2 = (ImageButton) findViewById(R.id.dialog_pos_payment_imagebutton2);

    }

    public void setOnclickListenr1(View.OnClickListener listener1){
        btn1.setOnClickListener(listener1);
    }

    public void setOnclickListener2(View.OnClickListener listener2){
        btn2.setOnClickListener(listener2);
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
