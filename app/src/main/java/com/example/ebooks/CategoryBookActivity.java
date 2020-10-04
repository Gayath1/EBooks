package com.example.ebooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.pickabook.adapters.BookAdapter;
import com.example.pickabook.models.Book;
import com.example.pickabook.utils.RecyclerTouchListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class CategoryBookActivity extends AppCompatActivity {
    private List<Book> bookList = new ArrayList<>();
    private BookAdapter bookAdapter;
    private RecyclerView recyclerView;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private final String BOOK = "book";
    private String categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_book);

        recyclerView = findViewById(R.id.categoryBookRecyclerView);
        bookAdapter = new BookAdapter(bookList);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(BOOK);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(bookAdapter);

        categoryId = getIntent().getStringExtra("id");

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Book book = bookList.get(position);

                Intent intent = new Intent(getApplicationContext(), BookItemActivity.class);
                intent.putExtra("id", book.getId());
                intent.putExtra("categoryId", book.getCategoryId());
                intent.putExtra("name", book.getName());
                intent.putExtra("author", book.getAuthor());
                intent.putExtra("abnNumber", book.getABNNumber());
                intent.putExtra("price", Double.toString(book.getPrice()));
                intent.putExtra("imageUrl", book.getImageUrl());
                intent.putExtra("description", book.getDescription());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) { }
        }));

        bookData();
    }

    private void bookData() {
        databaseReference.orderByChild("categoryId").equalTo(categoryId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Book bookItem;

                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot item: dataSnapshot.getChildren()) {
                        bookItem = item.getValue(Book.class);
                        bookItem.setId(item.getKey());

                        if (bookItem != null) {
                            bookList.add(bookItem);
                        }
                    }
                    bookAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}