package bzu.androidstudioproject.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.logging.Logger;

import bzu.androidstudioproject.R;
import bzu.androidstudioproject.databinding.FragmentTodayBinding;
import bzu.androidstudioproject.loggedinuser.LoggedInUser;

public class ProfileFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,
                container, false);

        TextView emailView = view.findViewById(R.id.tv_email);
        TextView firstnameView = view.findViewById(R.id.tv_firstname);
        TextView lastNameView = view.findViewById(R.id.tv_lastname);
        TextView fullname = view.findViewById(R.id.tv_name);

        emailView.setText(LoggedInUser.loggedIn.getEmail());
        firstnameView.setText(LoggedInUser.loggedIn.getFirstName());
        lastNameView.setText(LoggedInUser.loggedIn.getLastName());
        fullname.setText(LoggedInUser.loggedIn.getFirstName() + " " + LoggedInUser.loggedIn.getLastName());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}