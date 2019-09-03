package tech.muva.academy.android_shoppa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import tech.muva.academy.android_shoppa.R;
import tech.muva.academy.android_shoppa.models.ViewedProduct;

public class OrdersRecyclerAdapter   extends RecyclerView.Adapter<OrdersRecyclerAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<ViewedProduct> mOrderArrayList;


    public OrdersRecyclerAdapter(Context context, ArrayList<ViewedProduct> orderArrayList) {
        mContext = context;
        mOrderArrayList = orderArrayList;
    }

    @NonNull
    @Override
    public OrdersRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_recycler_view, parent, false);
        return new OrdersRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewedProduct viewedProduct = mOrderArrayList.get(position);
        holder.orderPrice.setText(viewedProduct.getTotal().toString());
        holder.orderID.setText(viewedProduct.getId().toString());
        holder.orderDate.setText(viewedProduct.getCreatedAt());
        holder.noOfItems.setText(viewedProduct.getQuantity().toString());
        holder.mCurrentPosition = position;

    }


    @Override
    public int getItemCount() {
        return mOrderArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView orderPrice, orderNumber, noOfItems, orderDate, orderID;
        ImageButton btn_detail_view;
        public int mCurrentPosition;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderPrice = itemView.findViewById(R.id.total_price_text);
            orderNumber = itemView.findViewById(R.id.order_number_text);
            noOfItems = itemView.findViewById(R.id.no_of_items_text);
            orderDate = itemView.findViewById(R.id.order_date);
            orderID = itemView.findViewById(R.id.order_id_text);
//            btn_detail_view = itemView.findViewById(R.id.image_button_detail_order);

        }

        @Override
        public void onClick(View view) {

        }
    }


}
