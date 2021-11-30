package org.miage.placesearcher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;
import jp.wasabeef.recyclerview.animators.FadeInRightAnimator;
import jp.wasabeef.recyclerview.animators.FlipInBottomXAnimator;
import jp.wasabeef.recyclerview.animators.FlipInLeftYAnimator;
import jp.wasabeef.recyclerview.animators.LandingAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class FeatureActivity extends AppCompatActivity {

    MyRecyclerViewAdapter adapter;
    ArrayList<String> VilleNames;
    ArrayList<String> VilleRandoms;

    @BindView(R.id.recyclerView2)
    RecyclerView mRecyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature);

        ButterKnife.bind(this);

        //initialisation de la liste
        VilleNames = new ArrayList<>();
        VilleNames.add("Nantes");
        VilleNames.add("Paris");
        VilleNames.add("Rennes");

        //animations utilisees lors de la presentation
        //animation 1
        mRecyclerView2.setItemAnimator(new FlipInBottomXAnimator());

        //animation 2
        //mRecyclerView2.setItemAnimator(new FadeInRightAnimator());

        mRecyclerView2.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, VilleNames);
        mRecyclerView2.setAdapter(adapter);
    }

    //ajout ville aléatoire
    public void add(View v) {
        VilleRandoms = new ArrayList<>();
        VilleRandoms.add("Bordeaux"); VilleRandoms.add("Toulouse");
        VilleRandoms.add("Lille"); VilleRandoms.add("Marseille");
        VilleRandoms.add("Brest"); VilleRandoms.add("Nice");
        VilleRandoms.add("Toulon"); VilleRandoms.add("Strasbourg");
        VilleRandoms.add("Caen"); VilleRandoms.add("Vannes");
        VilleRandoms.add("Tours"); VilleRandoms.add("Angers");
        VilleRandoms.add("Grenoble"); VilleRandoms.add("Lyon");

        Random r = new Random();
        int val = r.nextInt(VilleRandoms.size());
        VilleNames.add(VilleRandoms.get(val));

        //notification de l'animation vers l'adapter
        adapter.notifyItemInserted(VilleNames.size());
    }

    //suppression ville
    public void delete(View v) {
        VilleNames.remove(VilleNames.size()-1);

        //notification de l'animation vers l'adapter
        adapter.notifyItemRemoved(VilleNames.size());
    }

    //retour a la page d'accueil
    public void retour(View v) {
        Intent switchToHome = new Intent(this, MainActivity.class);
        startActivity(switchToHome);
    }
}