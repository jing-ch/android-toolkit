package edu.northeastern.numad26sp_jinghanchen;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class LinkCollectorActivity extends AppCompatActivity {

    RecyclerView peopleRecyclerView;

    List<Person> personList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activitiy_link_collector);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.linkCollect), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Instantiate the arraylist
        personList = new ArrayList<>();

        //Adding a new person object to the personList arrayList
        personList.add(new Person("Lady Gaga", "https://en.wikipedia.org/wiki/Lady_Gaga"));

        peopleRecyclerView = findViewById(R.id.people_recycler_view);

        //This defines the way in which the RecyclerView is oriented
        peopleRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Associates the adapter with the RecyclerView
        peopleRecyclerView.setAdapter(new PersonAdapter(personList, this));


    }
}
