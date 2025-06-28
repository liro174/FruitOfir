package com.example.fruitofir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FruitActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<FruitItem> fruitList;
    private FloatingActionButton addFruitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fruit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        setUpRecyclerView();
        setUpFloatingActionButton();


    }
    private void setUpFloatingActionButton() {
        FloatingActionButton fab = findViewById(R.id.FloataddFruit);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Inflate the custom dialog layout
                View dialogView = LayoutInflater.from(FruitActivity.this).inflate(R.layout.dialouge_fruit, null);

                EditText nameInput = dialogView.findViewById(R.id.fruitName);
                EditText descInput = dialogView.findViewById(R.id.fruitDescription);
                CheckBox fav = dialogView.findViewById(R.id.checkBoxFav);
                ImageView imgCh1 = dialogView.findViewById(R.id.imageViewCh1);
                ImageView imgCh2 = dialogView.findViewById(R.id.imageViewCh2);
                ImageView imgCh3 = dialogView.findViewById(R.id.imageViewCh3);
                ImageView imgFruit = dialogView.findViewById(R.id.imageMain);

                // Set default image
                imgFruit.setImageResource(R.drawable.redheart);
                imgFruit.setTag(R.drawable.redheart);

                // ✅ Set images in the choices and their tags (replace with your real images)
                imgCh1.setImageResource(R.drawable.strawberry);
                imgCh1.setTag(R.drawable.strawberry);

                imgCh2.setImageResource(R.drawable.apple);
                imgCh2.setTag(R.drawable.apple);

                imgCh3.setImageResource(R.drawable.banana);
                imgCh3.setTag(R.drawable.banana);

                // 👆 When user clicks on small image, update main image
                imgCh1.setOnClickListener(v -> {
                    imgFruit.setImageResource((int) imgCh1.getTag());
                    imgFruit.setTag(imgCh1.getTag());
                });

                imgCh2.setOnClickListener(v -> {
                    imgFruit.setImageResource((int) imgCh2.getTag());
                    imgFruit.setTag(imgCh2.getTag());
                });

                imgCh3.setOnClickListener(v -> {
                    imgFruit.setImageResource((int) imgCh3.getTag());
                    imgFruit.setTag(imgCh3.getTag());
                });

                // Build dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(FruitActivity.this);
                builder.setTitle("Add New Fruit")
                        .setView(dialogView)
                        .setPositiveButton("Add", (dialog, which) -> {
                            String name = nameInput.getText().toString();
                            String description = descInput.getText().toString();
                            boolean favStatus = fav.isChecked();
                            int imageResource = (int) imgFruit.getTag();
                            fruitList.add(new FruitItem(imageResource, name, description, favStatus));
                            myAdapter.notifyItemInserted(fruitList.size() - 1);
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .setCancelable(false);

                builder.show();
            }
        });
    }

    private void setUpRecyclerView() {

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Sample fruit data
        fruitList = new ArrayList<>();
        fruitList.add(new FruitItem(R.drawable.apple, "Apple", "Rich in fiber and vitamin C.",false));
        fruitList.add(new FruitItem(R.drawable.banana, "Banana", "Great source of potassium.",false));
        fruitList.add(new FruitItem(R.drawable.orange, "Orange", "Loaded with vitamin C.",false));
        fruitList.add(new FruitItem(R.drawable.strawberry, "Strawberry", "Full of antioxidants.",false));
        fruitList.add(new FruitItem(R.drawable.watermelon, "Watermelon", "Very refreshing and hydrating.",false));
        // Add more fruits as desired

        myAdapter = new MyAdapter(fruitList);
        recyclerView.setAdapter(myAdapter);

        // use ItemTouchHelper for drag and drop or swipe actions if needed
        // For example, you can implement drag and drop functionality here
        // ItemTouchHelper for swipe-to-delete
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN, // Enable drag directions
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT // Enable swipe left and right
        ) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPos = viewHolder.getAdapterPosition();
                int toPos = target.getAdapterPosition();
                // Swap items and notify adapter
                Collections.swap(fruitList, fromPos, toPos);
                myAdapter.notifyItemMoved(fromPos, toPos);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                fruitList.remove(position);
                myAdapter.notifyItemRemoved(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
    // Optional: Set item click listener if needed

}
