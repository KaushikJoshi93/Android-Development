package com.example.animationmaker;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StudentModelAdaptor extends RecyclerView.Adapter<StudentModelAdaptor.ViewHolder> {
    Context context;
    ArrayList<StudentModel> arrlist;
    public StudentModelAdaptor(Context context , ArrayList<StudentModel> arrlist) {
        this.context = context;
        this.arrlist = arrlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview = LayoutInflater.from(context).inflate(R.layout.student_layout , parent , false);
        ViewHolder viewHolder = new ViewHolder(myview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(arrlist.get(position).getName());
        holder.mobile_no.setText(arrlist.get(position).getMobile_no());
        holder.image_res.setImageResource(arrlist.get(position).getImage_res());
        setanimation(holder.itemView , position);
        holder.llview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.add_update_dialog);
                dialog.setCancelable(false);
                TextView heading = dialog.findViewById(R.id.heading);
                Button update_btn = dialog.findViewById(R.id.add_btn);
                ImageView close_btn = dialog.findViewById(R.id.close_btn);
                TextView name = dialog.findViewById(R.id.name);
                TextView mobile_no = dialog.findViewById(R.id.mobile_no);
                heading.setText("Update Student");
                update_btn.setText("Update");
                name.setText(arrlist.get(position).getName());
                mobile_no.setText(arrlist.get(position).getMobile_no());
                update_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!name.getText().toString().equals("") && !mobile_no.getText().toString().equals("")){
                            String nwname = name.getText().toString();
                            String nwmobile_no = mobile_no.getText().toString();
                            arrlist.set(position , new StudentModel(nwname , nwmobile_no , R.drawable.internet));
                            notifyItemChanged(position);
                            dialog.dismiss();
                        }
                        else{
                            Toast.makeText(context, "Name cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                close_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });

        holder.llview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                        .setTitle("Delete Student")
                        .setMessage("Do you really want to delete this student data??")
                        .setCancelable(false)
                        .setIcon(R.drawable.ic_baseline_delete_24);

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        arrlist.remove(position);
                        notifyItemRemoved(position);
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.show();
                return  true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView mobile_no;
        public ImageView image_res;
        public LinearLayout llview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            mobile_no = itemView.findViewById(R.id.mobile_no);
            image_res = itemView.findViewById(R.id.imageView);
            llview = itemView.findViewById(R.id.llview);

        }
    } ;

    public void setanimation(View item , int position){
        Animation myanimation  = AnimationUtils.loadAnimation(context , android.R.anim.slide_in_left);
        item.startAnimation(myanimation);

    }

}
