package Page;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chongjiao.carphone.MainCar;
import com.example.chongjiao.carphone.R;

import org.w3c.dom.Text;

/**
 * Created by chongjiao on 17-5-17.
 */

public class MeFragment extends Fragment {

    private TextView user_name;
    private TextView user_gender;
    private TextView user_account;
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container,false);
        user_name = (TextView)view.findViewById(R.id.user_name);
        user_gender = (TextView)view.findViewById(R.id.user_gender);
        user_account = (TextView)view.findViewById(R.id.user_account);
        MainCar mainCar = (MainCar)getActivity();
        if(mainCar.userGender != null)
            setMe(mainCar.userName,mainCar.userGender,mainCar.userAccount);
        return view;
    }
    public void setMe(String userName,String userGender,String userAccount){
        if(user_name != null) {
            user_name.setText(userName);
            user_gender.setText(userGender);
            user_account.setText(userAccount);
        }
    }
}
