package com.example.fruitofir;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FruitActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<FruitItem> fruitList;
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
