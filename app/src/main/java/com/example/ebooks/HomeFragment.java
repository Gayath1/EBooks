package com.example.ebooks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebooks.adapters.BookAdapter;
import com.example.ebooks.models.Book;
import com.example.ebooks.utils.RecyclerTouchListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    private List<Book> bookList = new ArrayList<>();
    private BookAdapter bookAdapter;
    private RecyclerView recyclerView;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private final String BOOK = "book";

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.homeRecyclerView);
        bookAdapter = new BookAdapter(bookList);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(BOOK);

        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("pickABook", MODE_PRIVATE);
        final boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(bookAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Book book = bookList.get(position);

                Intent intent;

                if (isAdmin) {
                    intent = new Intent(getActivity().getApplicationContext(), BookEditActivity.class);
                } else {
                    intent = new Intent(getActivity().getApplicationContext(), BookItemActivity.class);
                }
                intent.putExtra("id", book.getId());
                intent.putExtra("categoryId", book.getCategoryId());
                intent.putExtra("name", book.getName());
                intent.putExtra("author", book.getAuthor());
                intent.putExtra("abnNumber", book.getABNNumber());
                intent.putExtra("price", book.getPrice());
                intent.putExtra("imageUrl", book.getImageUrl());
                intent.putExtra("description", book.getDescription());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        bookData();

        return view;
    }

    private void bookData() {
        databaseReference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Book bookItem;

                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
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
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}