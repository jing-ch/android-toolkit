package edu.northeastern.numad26sp_jinghanchen;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class LinkCollectorActivity extends AppCompatActivity {

    RecyclerView peopleRecyclerView;

    List<Person> personList;
    PersonAdapter adapter;

    private static final String KEY_PERSON_LIST = "person_list";


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

        peopleRecyclerView = findViewById(R.id.people_recycler_view);

        //This defines the way in which the RecyclerView is oriented
        peopleRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Associates the adapter with the RecyclerView
        adapter = new PersonAdapter(personList, this);
        peopleRecyclerView.setAdapter(adapter);

        // handle FAB
        FloatingActionButton fab = findViewById(R.id.fab_add);

        fab.setOnClickListener(v -> {
            showAddLinkDialog();
        });

        // handle swipe left or right to delete
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            Person recentlyDeletedPerson;
            int recentlyDeletedPosition;

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false; // We are not supporting drag & drop
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // Get position
                int position = viewHolder.getAdapterPosition();
                recentlyDeletedPerson = personList.get(position);
                recentlyDeletedPosition = position;

                // Remove the item from list
                personList.remove(position);
                adapter.notifyItemRemoved(position);

                // Show snackbar with undo
                View rootView = findViewById(android.R.id.content);
                Snackbar.make(rootView, "Link deleted", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", v -> {
                            // Restore the deleted item
                            personList.add(recentlyDeletedPosition, recentlyDeletedPerson);
                            adapter.notifyItemInserted(recentlyDeletedPosition);
                        }).show();
            }
        };

        // initializing for persissting data while rotating
        // Attach to RecyclerView
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(peopleRecyclerView);
        // Instantiate the arraylist
        if (savedInstanceState != null) {
            personList = savedInstanceState.getParcelableArrayList(KEY_PERSON_LIST);
        } else {
            personList = new ArrayList<>();
        }
        // Setup adapter
        adapter = new PersonAdapter(personList, this);
        peopleRecyclerView.setAdapter(adapter);
    }

    private void showAddLinkDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_link, null);

        EditText nameEditText = dialogView.findViewById(R.id.edit_name);
        EditText urlEditText = dialogView.findViewById(R.id.edit_url);

        new AlertDialog.Builder(this)
                .setTitle("Add New Link")
                .setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    String name = nameEditText.getText().toString().trim();
                    String url = urlEditText.getText().toString().trim();

                    if (!name.isEmpty() && !url.isEmpty()) {
                        personList.add(new Person(name, url));
                        adapter.notifyItemInserted(personList.size() - 1);

                        // Show Snackbar message
                        Snackbar.make(findViewById(R.id.linkCollect),
                                        name + " added to the list",
                                        Snackbar.LENGTH_SHORT)
                                .show();
                    } else {
                        Toast.makeText(this, "Both fields are required", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the list as an ArrayList of Parcelable
        outState.putParcelableArrayList(KEY_PERSON_LIST, new ArrayList<>(personList));
    }
}

