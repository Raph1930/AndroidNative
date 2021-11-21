package org.miage.placesearcher;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.otto.Subscribe;

import org.miage.placesearcher.event.EventBusManager;
import org.miage.placesearcher.event.SearchResultEvent;
import org.miage.placesearcher.model.PlaceAddress;
import org.miage.placesearcher.ui.PlaceAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private PlaceAdapter mPlaceAdapter;

    @BindView(R.id.recyclerView2)
    RecyclerView mRecyclerView2;
    private PlaceAdapter mPlaceAdapter2;

    @BindView(R.id.activity_main_search_adress_edittext)
    EditText mSearchEditText;

    @BindView(R.id.activity_main_loader)
    ProgressBar mProgressBar;

    /*
    @BindView((R.id.AddStreet))
    Button mButton;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // Instancie un placeAdapter vide
        mPlaceAdapter = new PlaceAdapter(this, new ArrayList<>());

        //creation du type de defilement
        SlideInRightAnimationAdapter defilement = new SlideInRightAnimationAdapter(mPlaceAdapter);

        //duree affichage animation
        defilement.setDuration(1000);

        //balancement du bloc affichage
        defilement.setInterpolator(new OvershootInterpolator(4f));
        defilement.setFirstOnly(false);

        //set
        mRecyclerView.setAdapter(new SlideInRightAnimationAdapter(defilement));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        // Set textfield value according to intent
        if (getIntent().hasExtra("currentSearch")) {
            mSearchEditText.setText(getIntent().getStringExtra("currentSearch"));
        }

        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Nothing to do when texte is about to change
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // While text is changing, hide list and show loader
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Once text has changed
                // Show a loader
                mProgressBar.setVisibility(View.VISIBLE);

                // Launch a search through the PlaceSearchService
                PlaceSearchService.INSTANCE.searchPlacesFromAddress(editable.toString());
            }
        });

        // Log current token (if any define, otherwise our toekn service will be notified)
        /*FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isComplete()) {
                    Log.d("[FireBase Token]", "Current token: " + task.getResult());
                }
            }
        });*/
    }

    @Override
    protected void onResume() {
        // Do NOT forget to call super.onResume()
        super.onResume();

        // Register to Event bus : now each time an event is posted, the activity will receive it if it is @Subscribed to this event
        EventBusManager.BUS.register(this);

        // Refresh search
        PlaceSearchService.INSTANCE.searchPlacesFromAddress(mSearchEditText.getText().toString());
    }

    @Override
    protected void onPause() {
        // Unregister from Event bus : if event are posted now, the activity will not receive it
        EventBusManager.BUS.unregister(this);

        super.onPause();
    }

    @Subscribe
    public void searchResult(final SearchResultEvent event) {
        // Here someone has posted a SearchResultEvent
        runOnUiThread (() -> {
            // Step 1: Update adapter's model
            mPlaceAdapter.setPlaces(event.getPlaces());
            mPlaceAdapter.notifyDataSetChanged();

            // Step 2: hide loader
            mProgressBar.setVisibility(View.GONE);
        });

    }

    @OnClick(R.id.activity_main_switch_button)
    public void clickedOnSwitchToMap() {
        Intent switchToMapIntent = new Intent(this, MapActivity.class);
        switchToMapIntent.putExtra("currentSearch", mSearchEditText.getText().toString());
        startActivity(switchToMapIntent);
    }
/*
    @OnClick(R.id.AddStreet)
    public void buttonAdd(){
        PlaceAddress place = new PlaceAddress();
        this.mPlaceAdapter.addPlaces(place);
    }*/
}
