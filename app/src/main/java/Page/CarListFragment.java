package Page;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.chongjiao.carphone.MainCar;
import com.example.chongjiao.carphone.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.id;

/**
 * Created by chongjiao on 17-5-17.
 */

public class CarListFragment extends Fragment {

    public ListView listView;
    public List<HashMap<String,String>> data;
    public Button fresh;
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_car_list, container, false);

        listView = (ListView)view.findViewById(R.id.carList);
        fresh = (Button)view.findViewById(R.id.list_fresh);
        fresh.setOnClickListener(reFresh);
        initListView(MainCar.data);
        return view;
    }
    private View.OnClickListener reFresh = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            initListView(MainCar.data);
        }
    };
    /**
     *设备列表初始化
     */
    public void initListView(List<HashMap<String,String>> data){
        SimpleAdapter carAdpter = new SimpleAdapter(getContext(),data,R.layout.item,
                new String[]{"car_name","car_type"},new int[]{R.id.car_name,R.id.car_type});
        listView.setAdapter(carAdpter);
        listView.setOnItemClickListener(new ItemClickListener());
    }
    /**
     *设备列表每一项监听点击事件
     */
    private final class ItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ListView listView = (ListView) parent;
            HashMap<String, String> data = (HashMap<String, String>) listView.getItemAtPosition(position);
            String car_name = data.get("car_name");
            String car_type = data.get("car_type");
            MainCar mainCar = (MainCar)getActivity();
            MainCar.car_name = car_name;
            MainCar.car_type = car_type;
            mainCar.mCarFragment.setCarInfo(MainCar.car_name,MainCar.car_type);
            mainCar.mViewPager.setCurrentItem(3);
        }
    }
}
