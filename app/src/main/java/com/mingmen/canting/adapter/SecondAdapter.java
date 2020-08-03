package com.mingmen.canting.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.mingmen.canting.R;
import com.mingmen.canting.chushizhang.ChuShiDingDanFragment;
import com.mingmen.canting.model.SimpleData;

import java.util.List;


/**
 * @author qdafengzi
 */
public class SecondAdapter extends RecyclerView.Adapter<SecondAdapter.ViewHolder> {
    private Context mContext;
    private List<SimpleData.ChildData> mList;

    private int mPosition;

    //定义一个点击事件的接口
    public interface OnitemClick {
        void onItemClick(int position, List<SimpleData.ChildData> mList);
    }

    private OnitemClick onitemClick;   //定义点击事件接口

    public void setOnitemClickLintener(OnitemClick onitemClick) {
        this.onitemClick = onitemClick;
    }

    public SecondAdapter(Context context, List<SimpleData.ChildData> list, int position) {
        mContext = context;
        mList = list;
        mPosition = position;
    }

    /**
     * 新增方法
     *
     * @param position 位置
     */
    public void setPosition(int position) {
        this.mPosition = position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_option, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.lay_option.setTag(position);

        holder.tvOption.setText(mList.get(position).childName);
  /*      if (mList.get(position).select) {
            holder.lay_option.setBackgroundResource(R.drawable.background_grid_unselect);
        } else {
            holder.lay_option.setBackgroundResource(R.drawable.background_grid_select);
        }
*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onitemClick != null) {
                    onitemClick.onItemClick(position,mList);
                }
//                Toast.makeText(mContext, position+"", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout lay_option;
        TextView tvOption;

        ViewHolder(View view) {
            super(view);

            lay_option = view.findViewById(R.id.lay_option);
            tvOption = view.findViewById(R.id.tv_option);


            //这里设置一个tag,作为被嵌套的recycleview item点击事件的 position
         /*   lay_option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mList.get((int) v.getTag()).select = true;

                }
            });*/


        }
    }
}