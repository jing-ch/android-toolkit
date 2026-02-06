package edu.northeastern.numad26sp_jinghanchen.link_collector;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import edu.northeastern.numad26sp_jinghanchen.R;

/**
 * An implementation of the recyclerview viewholder that is created specifically with respect to the
 * item_person.xml file. The aim of this class is to provide each item in the recyclerview to the
 * adapter, another important purpose of this class is to expose the TextViews in the xml file as
 * java objects for binding the data.
 */
public class PersonViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView url;
    public MaterialButton editButton;


    public PersonViewHolder(@NonNull View itemView) {
        super(itemView);
        this.name = itemView.findViewById(R.id.name);
        this.url = itemView.findViewById(R.id.url);
        this.editButton = itemView.findViewById(R.id.btn_edit);
    }
}

