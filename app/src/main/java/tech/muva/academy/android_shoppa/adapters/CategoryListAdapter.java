package tech.muva.academy.android_shoppa.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tech.muva.academy.android_shoppa.R;
import tech.muva.academy.android_shoppa.models.Category;
import tech.muva.academy.android_shoppa.models.ViewedProduct;
import tech.muva.academy.android_shoppa.ui.activities.CategoryActivity;
import tech.muva.academy.android_shoppa.ui.activities.ProductActivity;

public class CategoryListAdapter    extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Category> mCategoryArrayList;
    String categoryname;


    public CategoryListAdapter(Context context, ArrayList<Category> categoryArrayList) {
        mContext = context;
        mCategoryArrayList = categoryArrayList;
    }

    @NonNull
    @Override
    public CategoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_list, parent, false);
        return new CategoryListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListAdapter.ViewHolder holder, int position) {
        Category category = mCategoryArrayList.get(position);
        categoryname = category.getName();
        holder.categoryName.setText(category.getName());
        holder.mCurrentPosition = position;

    }


    @Override
    public int getItemCount() {
        return mCategoryArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView categoryName;
        ImageButton btn_detail_view;
        public int mCurrentPosition;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name);
            btn_detail_view = itemView.findViewById(R.id.image_button_detail_category);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = ProductActivity.startSelfIntent(mContext,categoryName.getText().toString());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);

                    Toast.makeText(mContext, "Click Home Button To See All Categories",Toast.LENGTH_LONG).show();


//                    Intent i = new Intent(mContext, ProductActivity.class);
////                    Bundle bundle = new Bundle();
//                    startActivity(mContext,i, null);
////                    i.putExtra("frgToLoad", FRAGMENT_A);

                }
            });

            btn_detail_view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int itemId=view.getId();
            if(itemId==R.id.image_button_detail_category) {
                Intent intent = new Intent(mContext, ProductActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }



        }
    }


}
