package edu.northeastern.numad26sp_jinghanchen;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 * This is a recyclerview adapter class, the purpose of this class is to act as a bridge between the
 * collection (arraylist) and the view (recyclerview). This class provides 3 methods that are
 * utilised for binding the data to the view. The explanation of each method is provided in comments
 * within the methods.
 */
public class PersonAdapter extends RecyclerView.Adapter<PersonViewHolder> {

    private final List<Person> people;
    private final Context context;

    /**
     * Creates a PersonAdapter with the provided arraylist of Person objects.
     *
     * @param people    arraylist of person object.
     * @param context   context of the activity used for inflating layout of the viewholder.
     */
    public PersonAdapter(List<Person> people, Context context) {
        this.people = people;
        this.context = context;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create an instance of the viewholder by passing it the layout inflated as view and no root.
        return new PersonViewHolder(LayoutInflater.from(context).inflate(R.layout.item_person, null));
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        Person person = people.get(position);

        // sets the name of the person to the name textview of the viewholder.
        holder.name.setText(person.getName());
        // sets the url of the person to the url textview of the viewholder.
        holder.url.setText(String.valueOf(person.getUrl()));

        // click url to open in browser
        holder.url.setOnClickListener(view -> {
            String url = person.getUrl();

            // Make sure it starts with http:// or https://
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(browserIntent);
        });

        // click edit button to edit
        holder.editButton.setOnClickListener(view -> {
            showEditDialog(position);
        });
    }

    @Override
    public int getItemCount() {
        // Returns the size of the recyclerview that is the list of the arraylist.
        return people.size();
    }

    private void showEditDialog(int position) {
        Person person = people.get(position);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_link, null);
        EditText nameEditText = dialogView.findViewById(R.id.edit_name);
        EditText urlEditText = dialogView.findViewById(R.id.edit_url);

        // Pre-fill existing data
        nameEditText.setText(person.getName());
        urlEditText.setText(person.getUrl());

        new AlertDialog.Builder(context)
                .setTitle("Edit Link")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newName = nameEditText.getText().toString().trim();
                    String newUrl = urlEditText.getText().toString().trim();

                    if (!newName.isEmpty() && !newUrl.isEmpty()) {
                        // Update the object
                        people.set(position, new Person(newName, newUrl));
                        notifyItemChanged(position);

                        // Show Snackbar after edit
                        if (context instanceof AppCompatActivity) {
                            View rootView = ((AppCompatActivity) context).findViewById(android.R.id.content);
                            Snackbar.make(rootView, "Link updated", Snackbar.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Both fields are required", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

}
