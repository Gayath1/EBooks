package com.example.ebooks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ebooks.models.Book;
import com.example.ebooks.models.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class BookEditActivity extends AppCompatActivity {
    private EditText imageUrlField;
    private EditText nameField;
    private EditText authorField;
    private EditText abnField;
    private EditText descriptionField;
    private EditText priceField;
    private Button editButton;
    private Spinner categorySpinner;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseCategoryReference;
    private Book bookForSave;
    private boolean isEdit = false;
    private boolean isAdd = false;
    private ArrayAdapter<Category> categoryArrayAdapter;
    private List<Category> categoryList = new ArrayList<>();

    private final String BOOK = "book";
    private final String CATEGORY = "category";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_edit);

        imageUrlField = findViewById(R.id.bookEditImageUrl);
        nameField = findViewById(R.id.bookEditName);
        authorField = findViewById(R.id.bookEditAuthor);
        abnField = findViewById(R.id.bookEditABN);
        descriptionField = findViewById(R.id.bookEditDescription);
        priceField = findViewById(R.id.bookEditPrice);
        editButton = findViewById(R.id.bookEditButton);
        categorySpinner = findViewById(R.id.bookEditCategorySpinner);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(BOOK);
        databaseCategoryReference = firebaseDatabase.getReference(CATEGORY);

        categoryArrayAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, categoryList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view =  super.getView(position, convertView, parent);
                ((TextView)view.findViewById(android.R.id.text1)).setText(categoryList.get(position).getName());
                return view;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view =  super.getDropDownView(position, convertView, parent);
                ((TextView)view.findViewById(android.R.id.text1)).setText(categoryList.get(position).getName());
                return view;
            }
        };
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(categoryArrayAdapter);

        bookForSave = new Book();

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        if (id.equals("")) {
            isAdd = true;
            editButton.setText("Add");
        } else {
            String categoryId = intent.getStringExtra("categoryId");
            String name = intent.getStringExtra("name");
            String author = intent.getStringExtra("author");
            String abnNumber = intent.getStringExtra("abnNumber");
            double price = intent.getDoubleExtra("price", 0);
            String imageUrl = intent.getStringExtra("imageUrl");
            String description = intent.getStringExtra("description");

            bookForSave.setId(id);
            bookForSave.setCategoryId(categoryId);
            bookForSave.setName(name);
            bookForSave.setAuthor(author);
            bookForSave.setABNNumber(abnNumber);
            bookForSave.setPrice(price);
            bookForSave.setImageUrl(imageUrl);
            bookForSave.setDescription(description);

            updateUI();

            imageUrlField.setEnabled(false);
            nameField.setEnabled(false);
            authorField.setEnabled(false);
            abnField.setEnabled(false);
            descriptionField.setEnabled(false);
            priceField.setEnabled(false);
            categorySpinner.setEnabled(false);
        }

        loadCategories();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUrlField.getText().toString().equals("") || imageUrlField.getText() == null) {
                    Toasty.success(BookEditActivity.this, "Please enter image URL.", Toasty.LENGTH_SHORT).show();
                } else {
                    bookForSave.setImageUrl(imageUrlField.getText().toString());
                    bookForSave.setName(nameField.getText().toString());
                    bookForSave.setAuthor(authorField.getText().toString());
                    bookForSave.setABNNumber(abnField.getText().toString());
                    bookForSave.setPrice(Double.parseDouble(priceField.getText().toString()));
                    bookForSave.setDescription(descriptionField.getText().toString());

                    if (((Category) categorySpinner.getSelectedItem()).getId() != null) {
                        bookForSave.setCategoryId(((Category) categorySpinner.getSelectedItem()).getId());
                    }

                    if (isAdd) {
                        databaseReference.child(databaseReference.push().getKey()).setValue(bookForSave);

                        Toasty.success(BookEditActivity.this, "Book added successfully.", Toasty.LENGTH_SHORT).show();

                        Intent backIntent = new Intent(BookEditActivity.this, AdminMainActivity.class);
                        startActivity(backIntent);
                    } else {
                        if (isEdit) {
                            databaseReference.child(bookForSave.getId()).setValue(bookForSave);

                            Toasty.success(BookEditActivity.this, "Book updated successfully.", Toasty.LENGTH_SHORT).show();
                            Intent backIntent = new Intent(BookEditActivity.this, AdminMainActivity.class);
                            startActivity(backIntent);
                            finish();
                        } else {
                            isEdit = true;

                            imageUrlField.setEnabled(true);
                            nameField.setEnabled(true);
                            authorField.setEnabled(true);
                            abnField.setEnabled(true);
                            priceField.setEnabled(true);
                            descriptionField.setEnabled(true);
                            categorySpinner.setEnabled(true);
                            editButton.setText("Save");
                        }
                    }
                }
            }
        });
    }

    private void loadCategories() {
        databaseCategoryReference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Category categoryItem;

                categoryList.add(new Category("Select a Category", null));

                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot item: dataSnapshot.getChildren()) {
                        categoryItem = item.getValue(Category.class);
                        categoryItem.setId(item.getKey());

                        if (categoryItem != null) {
                            categoryList.add(categoryItem);
                        }
                    }

                    categoryArrayAdapter.notifyDataSetChanged();

                    for (Category item: categoryList) {
                        if (bookForSave.getCategoryId() != null && item.getId() != null && item.getId().equals(bookForSave.getCategoryId())) {
                            categorySpinner.setSelection(categoryList.indexOf(item));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    private void updateUI() {
        imageUrlField.setText(bookForSave.getImageUrl());
        nameField.setText(bookForSave.getName());
        authorField.setText(bookForSave.getAuthor());
        abnField.setText(bookForSave.getABNNumber());
        priceField.setText(Double.toString(bookForSave.getPrice()));
        descriptionField.setText(bookForSave.getDescription());
    }
}