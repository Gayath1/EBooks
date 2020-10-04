package com.example.ebooks.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pickabook.R;
import com.example.pickabook.models.Book;
import com.squareup.picasso.Picasso;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {

    private List<Book> bookList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name, author, price;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.itemBookImage);
            name = (TextView) view.findViewById(R.id.itemBookName);
            author = (TextView) view.findViewById(R.id.itemBookAuthor);
            price = (TextView) view.findViewById(R.id.itemBookPrice);
        }
    }

    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Book book = bookList.get(position);
        if (book.getImageUrl() != null || !book.getImageUrl().equals("")) {
            Picasso.get().load(book.getImageUrl()).fit().centerCrop().into(holder.image);
        }
        holder.name.setText(book.getName());
        holder.author.setText(book.getAuthor());
        holder.price.setText("Rs. "+ String.format("%.2f", book.getPrice()) +"/=");
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
