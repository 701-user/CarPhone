package Page;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.chongjiao.carphone.MainCar;
import com.example.chongjiao.carphone.R;

import org.json.JSONObject;

/**
 * Created by chongjiao on 17-5-16.
 */

public class CarRegisterFragment extends Fragment{

    private EditText car_name;
    private EditText car_type;
    private Button car_register_btn;
    private MainCar mainCar;
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_car_register, container, false);
        car_name = (EditText)view.findViewById(R.id.register_car_name);
        car_type = (EditText)view.findViewById(R.id.register_car_type);
        car_register_btn = (Button)view.findViewById(R.id.car_info_register);
        car_register_btn.setOnClickListener(register);
        mainCar = (MainCar)getActivity();
        return view;
    }
    View.OnClickListener register = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mainCar.upload.setJson(setJson());
            car_name.setText("");
            car_type.setText("");
        }
    };
    private JSONObject setJson(){
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("type",3);
            jsonObject.put("SessionId",mainCar.SessionID);
            jsonObject.put("Car_name",car_name.getText().toString());
            jsonObject.put("Car_type",car_type.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }
}
