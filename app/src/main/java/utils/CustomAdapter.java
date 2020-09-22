package utils;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scanqr_pdf.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    ArrayList<Bitmap> bitmapList;
    public CustomAdapter(ArrayList<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View imgPage =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_page, parent, false);


        return new MyViewHolder(imgPage);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imgPage.setImageBitmap(bitmapList.get(position));
    }

    @Override
    public int getItemCount() {
        return bitmapList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgPage;
        public MyViewHolder(View itemView) {
            super(itemView);
            imgPage = itemView.findViewById(R.id.img_page);
        }
    }
}
