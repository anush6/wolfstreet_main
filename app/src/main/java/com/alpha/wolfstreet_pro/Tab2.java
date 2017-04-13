package com.alpha.wolfstreet_pro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Created by Adi on 13/03/17.
 */

public class Tab2 extends Fragment {
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private String uid = "";
    public String[] stocks;
    public int[] buyPrice = new int[200];
    private String email, number, stockList, stockWishList, stockAmount;
    private float bal, worth;
    public User obj;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2, container, false);



        auth = FirebaseAuth.getInstance();
        Firebase.setAndroidContext(getActivity().getApplicationContext());

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                }
            }
        };


        uid = user.getUid();
        final Firebase ref = new Firebase(Config.FIREBASE_URL);
        final Firebase useref = ref.child(uid);



        useref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //so this is an async function, so the call back happens after the network request comes through
                //so you can't use the obj value on the main thread directly. so what you can alternatively do
                //is move this to a function and then call that
                obj = dataSnapshot.getValue(User.class);
                //t.setText(obj.name+" "+obj.phone+" "+obj.stock[5]);

                email = obj.name;
                number = obj.phone;
                stockList = obj.stockList;
                stockWishList = obj.stockWishList;
                stockAmount = obj.stockAmount;
                bal = obj.bal;
                stocks = stockList.split("\\+");
                String[] strings = stockAmount.split(" ");
                for (int i = 0; i < strings.length; i++)
                {
                    buyPrice[i] = Integer.parseInt(strings[i]);
                }
                //STOCKS array is the string array containing all the stocks bought
                //BUY PRICE array is the price at which stock was bought
                //Both arrays should be of the same length
                Toast.makeText(getActivity(),stocks[2]+buyPrice[2],
                        Toast.LENGTH_LONG).show();





            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return rootView;
    }


}
