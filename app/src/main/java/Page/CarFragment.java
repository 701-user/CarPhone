package Page;

import android.app.Activity;
import android.content.Context;
import android.net.sip.SipAudioCall;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chongjiao.carphone.MainCar;
import com.example.chongjiao.carphone.R;

import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by chongjiao on 17-5-17.
 */

public class CarFragment extends Fragment implements View.OnClickListener {
    TextView infoCarName;
    TextView infoCarType;
    Button openDoor;
    Button openRifi;
    Button showCarinfo;
    Button refesh;
    TextView carData;
    MainCar mainCar  = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_car, container, false);
        infoCarName = (TextView) view.findViewById(R.id.Info_car_name);
        infoCarType = (TextView) view.findViewById(R.id.Info_car_type);
        openDoor = (Button)view.findViewById(R.id.open_door);
        openDoor.setOnClickListener(this);
        openRifi = (Button)view.findViewById(R.id.open_refri);
        openRifi.setOnClickListener(this);
        carData = (TextView)view.findViewById(R.id.car_data);
        showCarinfo = (Button)view.findViewById(R.id.show_car_info);
        showCarinfo.setOnClickListener(this);
        refesh = (Button)view.findViewById(R.id.refesh);
        refesh.setOnClickListener(this);
        mainCar =  (MainCar)getActivity();
        setCarInfo(MainCar.car_name,MainCar.car_type);
        return view;
    }
    public void setCarInfo(String carName,String carType){
        infoCarName.setText(carName);
        infoCarType.setText(carType);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Override
    public void onClick(View v) {
        //待敲定
        switch (v.getId()){
            case R.id.show_car_info:
                mainCar.upload.setJson(setJson(4,MainCar.car_type,1,MainCar.SessionID));
                break;
            case R.id.open_door:
                mainCar.upload.setJson(setJson(4,MainCar.car_type,2,MainCar.SessionID));
                break;
            case R.id.open_refri:
                mainCar.upload.setJson(setJson(4,MainCar.car_type,3,MainCar.SessionID));
                break;
            case R.id.refesh:
                carData.setText(MainCar.car_data);
            default:
                break;
        }
    }
    private JSONObject setJson(int type,String car_name,int data,String SessionId){
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("type",type);
            jsonObject.put("data",data);
            jsonObject.put("Car_type",car_name);
            jsonObject.put("SessionId",SessionId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }

}
