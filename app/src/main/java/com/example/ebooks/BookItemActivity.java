package com.example.ebooks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ebooks.models.Book;
import com.example.ebooks.models.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import es.dmoral.toasty.Toasty;

public class BookItemActivity extends AppCompatActivity {
    private ImageView bookImage;
    private TextView bookName;
    private TextView bookAuthor;
    private TextView bookAbn;
    private TextView bookPrice;
    private TextView bookDescription;
    private EditText bookQty;
    private Button addToCartButton;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Book bookItem;

    private final String CART = "cart";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_item);

        bookImage = findViewById(R.id.bookItemImage);
        bookName = findViewById(R.id.bookItemName);
        bookAuthor = findViewById(R.id.bookItemAuthor);
        bookAbn = findViewById(R.id.bookItemABN);
        bookPrice = findViewById(R.id.bookItemPrice);
        bookDescription = findViewById(R.id.bookItemDescription);
        bookQty = findViewById(R.id.bookItemQty);
        addToCartButton = findViewById(R.id.bookItemAddToCart);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(CART);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String categoryId = intent.getStringExtra("categoryId");
        String name = intent.getStringExtra("name");
        String author = intent.getStringExtra("author");
        String abnNumber = intent.getStringExtra("abnNumber");
        double price = intent.getDoubleExtra("price", 0);
        String imageUrl = intent.getStringExtra("imageUrl");
        String description = intent.getStringExtra("description");

        bookItem = new Book();
        bookItem.setId(id);
        bookItem.setCategoryId(categoryId);
        bookItem.setName(name);
        bookItem.setAuthor(author);
        bookItem.setABNNumber(abnNumber);
        bookItem.setPrice(price);
        bookItem.setImageUrl(imageUrl);
        bookItem.setDescription(description);

        if (!imageUrl.equals("")) {
            Picasso.get().load(bookItem.getImageUrl()).fit().centerCrop().into(bookImage);
        }
        bookName.setText(bookItem.getName());
        bookAuthor.setText(bookItem.getAuthor());
        bookAbn.setText(bookItem.getABNNumber());
        bookPrice.setText("Rs. "+ String.format("%.2f", bookItem.getPrice()) +"/=");
        bookDescription.setText(bookItem.getDescription());

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String qty = bookQty.getText().toString().trim();

            if (qty.equals("")) {
                Toasty.warning(BookItemActivity.this, "Please enter an amount.", Toasty.LENGTH_SHORT).show();
            } else {
                Cart cart = new Cart();
                cart.setUserId(firebaseAuth.getCurrentUser().getUid());
                cart.setBookId(bookItem.getId());
                cart.setName(bookItem.getName());
                cart.setAuthor(bookItem.getAuthor());
                cart.setPerPrice(bookItem.getPrice());
                cart.setQuantity(Long.parseLong(qty));
                databaseReference.child(databaseReference.push().getKey()).setValue(cart);

                Toasty.success(BookItemActivity.this, "Added to cart successfully.", Toasty.LENGTH_SHORT).show();

                Intent intentBack = new Intent(BookItemActivity.this, MainActivity.class);
                startActivity(intentBack);
            }
            }
        });
    }
}