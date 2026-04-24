package com.example.rishiq_prevlab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<Order> orderList;
    private int selectedPosition = -1;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public int getCount() { return orderList.size(); }

    @Override
    public Object getItem(int position) { return orderList.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        }

        Order order = orderList.get(position);

        CheckBox cbSelect = convertView.findViewById(R.id.cbSelect);
        TextView tvId = convertView.findViewById(R.id.tvId);
        TextView tvItem1 = convertView.findViewById(R.id.tvItem1);
        TextView tvItem2 = convertView.findViewById(R.id.tvItem2);
        TextView tvCost1 = convertView.findViewById(R.id.tvCost1);
        TextView tvCost2 = convertView.findViewById(R.id.tvCost2);
        TextView tvQty1 = convertView.findViewById(R.id.tvQty1);
        TextView tvQty2 = convertView.findViewById(R.id.tvQty2);
        TextView tvTotal = convertView.findViewById(R.id.tvTotal);

        tvId.setText(String.valueOf(order.getId()));
        tvItem1.setText(order.getItem1());
        tvItem2.setText(order.getItem2());
        tvCost1.setText(String.valueOf(order.getCost1()));
        tvCost2.setText(String.valueOf(order.getCost2()));
        tvQty1.setText(String.valueOf(order.getQty1()));
        tvQty2.setText(String.valueOf(order.getQty2()));
        tvTotal.setText(String.valueOf(order.getTotal()));

        cbSelect.setOnCheckedChangeListener(null);
        cbSelect.setChecked(position == selectedPosition);

        cbSelect.setOnClickListener(v -> {
            if (selectedPosition == position) {
                selectedPosition = -1;
            } else {
                selectedPosition = position;
            }
            notifyDataSetChanged();
        });

        return convertView;
    }

    public Order getSelectedOrder() {
        if (selectedPosition != -1) {
            return orderList.get(selectedPosition);
        }
        return null;
    }

    public void updateData(List<Order> newOrders) {
        this.orderList = newOrders;
        this.selectedPosition = -1;
        notifyDataSetChanged();
    }
}
