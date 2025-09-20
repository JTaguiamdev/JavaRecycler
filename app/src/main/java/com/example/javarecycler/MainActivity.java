package com.example.javarecycler;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> list = new ArrayList<>();
    ItemAdapter adapter;
    EditText txtName;
    Button btnAdd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ///
            recyclerView = findViewById(R.id.recyclerView);
            txtName = findViewById(R.id.editText1);
            btnAdd = findViewById(R.id.button);
            adapter = new ItemAdapter(list, this);
            recyclerView.setAdapter(adapter);
            //
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = txtName.getText().toString();
                    list.add(name);
                    adapter.notifyDataSetChanged();
                    txtName.setText("");
                    txtName.requestFocus();
                }
            });
        ItemTouchHelper.Callback helper = new ItemTouchHelper.Callback(){
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                   return makeMovementFlags(
                           ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                           ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
                   );

            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int source = viewHolder.getAdapterPosition();
                int dest = target.getAdapterPosition();
                Collections.swap(list, source, dest);
                adapter.notifyItemMoved(source, dest);
                adapter.notifyItemChanged(source);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                list.remove(position);
                adapter.notifyItemRemoved(position);
            }
        };
        ItemTouchHelper callback = new ItemTouchHelper(helper);
        callback.attachToRecyclerView(recyclerView);
        ///

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}