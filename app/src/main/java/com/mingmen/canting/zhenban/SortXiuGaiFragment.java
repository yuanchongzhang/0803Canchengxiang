package com.mingmen.canting.zhenban;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mingmen.canting.R;
import com.mingmen.canting.adapter.LinkLabelAdapter;
import com.mingmen.canting.adapter.MyLabelAdapter;
import com.mingmen.canting.dialog.ButtomDialogView;
import com.mingmen.canting.http.MyOkhttp;
import com.mingmen.canting.http.callback.StringCallback;
import com.mingmen.canting.httpconfig.AppConfig;
import com.mingmen.canting.httpconfig.HttpResponse;
import com.mingmen.canting.model.LinkLabelBean;
import com.mingmen.canting.model.ShopListModel;
import com.mingmen.canting.util.SharePreferenceUtil;
import com.mingmen.canting.zhenbanlibrary.ClassifyNewAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class SortXiuGaiFragment extends Fragment implements ClassifyNewAdapter.OnItemClickListener, View.OnClickListener {
    private RecyclerView mRv;
    private ClassifyNewAdapter mAdapter;


    TextView tv_total;
    CheckBox cb_check;
    RecyclerView recycle_liebiao;

    //    List<MyGoodsInfo> myGoodsInfos = new ArrayList<>();
//    MyGoodsInfo myGoodsInfo;
    LinearLayoutManager linearLayoutManager;
    private String[] str = new String[]{"标题1", "帅哥", "校园", "军事", "悬疑", "科幻"};
    private LinkLabelBean linkLabelBean;

    RecyclerView recycle_biaoqian;
    private List<LinkLabelBean> list = new ArrayList<>();
    Button btn_submit;
    TextView text_del;
    List<ShopListModel.DataBean> data = new ArrayList<>();
    List<String> mylist = new ArrayList<>();
    String token;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sort_detail2, null);
        mRv = (RecyclerView) view.findViewById(R.id.rv);
        tv_total = (TextView) view.findViewById(R.id.tv_total);
        cb_check = (CheckBox) view.findViewById(R.id.cb_check);
        recycle_liebiao = view.findViewById(R.id.recycle_liebiao);
        recycle_biaoqian = view.findViewById(R.id.recycle_biaoqian);
        btn_submit = view.findViewById(R.id.btn_submit);
        text_del = view.findViewById(R.id.text_del);
        text_del.setOnClickListener(this);
        Log.d("dkdkkd", "到这里了么");
        token= (String) SharePreferenceUtil.get(getActivity(),"token","");
        String applyId= (String) SharePreferenceUtil.get(getActivity(),"applyId","");
        String string = getArguments().getString("right");

        Gson gson = new Gson();
        ShopListModel shopListModel = new ShopListModel();
        shopListModel = gson.fromJson(string, ShopListModel.class);
        data = shopListModel.getData();

        Log.d("到这里了么", string);


        linearLayoutManager = new LinearLayoutManager(getActivity());
        recycle_liebiao.setLayoutManager(linearLayoutManager);


        mAdapter = new ClassifyNewAdapter(getActivity());

        recycle_liebiao.setAdapter(mAdapter);
        mAdapter.setDataList(data);

        HttpResponse.showShopDetail(token, applyId, new StringCallback(getActivity()) {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.d("SortXiuGaiFragmentSortXiuGaiFragment",s);
            }
        });


        mAdapter.setOnItemClickListener(this);
        cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (cb_check.isChecked()) {
                        for (int i = 0; i < data.size(); i++) {
                            if (data.get(i).getApplyAmount() == 0) {
                                data.get(i).setChecked(false);
                            } else {
                                data.get(i).setChecked(true);
                                text_del.setVisibility(View.VISIBLE);
                            }
                        }
                        showTotalPrice();
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setChecked(false);
                    }
                    showTotalPrice();
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

        for (int i = 0; i < str.length; i++) {
            linkLabelBean = new LinkLabelBean();
            linkLabelBean.setStr(str[i]);
            list.add(linkLabelBean);
        }
        final GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 4);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = recycle_biaoqian.getAdapter().getItemViewType(position);//获得返回值

                if (type == 99) {
                    return mLayoutManager.getSpanCount();
                } else {
                    return 1;
                }
            }
        });
        recycle_biaoqian.setLayoutManager(mLayoutManager);
        LinkLabelAdapter linkLabelAdapter = new LinkLabelAdapter(getActivity(), list);
        recycle_biaoqian.setAdapter(linkLabelAdapter);
        linkLabelAdapter.setOnItemClickListener(new LinkLabelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, LinkLabelAdapter.ViewName viewName, int position) {
                Toast.makeText(getActivity(), viewName + "viewName" + list.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                public static String DAISHENHE="restaurant/apply/getApplyBillList";
                String token = (String) SharePreferenceUtil.get(getActivity(), "token", "");


                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("commodityId", "商品1");
                    jsonObject.put("commodityAmount", "10");
                    jsonObject.put("categoryId", "0");
                    jsonObject.put("mark", "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject2 = new JSONObject();
                try {
                    jsonObject2.put("commodityId", "商品1");
                    jsonObject2.put("commodityAmount", "10");
                    jsonObject2.put("categoryId", "0");
                    jsonObject2.put("mark", "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mylist.add(jsonObject.toString());
                mylist.add(jsonObject2.toString());
                Log.d("mylistmylistmylist", "dkdkdk");
                Log.d("DKkdkdkdk", jsonObject.toString());

//                [{"commodityId":"商品1","commodityAmount":"10","categoryId":"0","mark":""}, {"commodityId":"商品1","commodityAmount":"10","categoryId":"0","mark":""}]

//                @"8点之前",@"8~10点",@"10~12点",@"12~14点",@"14~16点",@"16~18点",@"18点之后
                MyOkhttp.post(AppConfig.Url + "restaurant/apply/addApplyBill")
                        .params("token", token)
                        .params("limitDate", "2020-08-01")
                        .params("flag", "1")
                        .params("jdata", mylist.toString())
                        .params("timeSpan", "0")
                        .execute(new StringCallback(getActivity()) {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                Log.d("SortDetailFragment", s);
                            }
                        });
            }
        });
        return view;
    }


    @Override
    public void onItemClickListener(View view, int pos, List<ShopListModel.DataBean> myLiveList) {
        switch (view.getId()) {
            case R.id.checkbox:
                float sum = 0;
//                showTotalPrice();
                for (ShopListModel.DataBean cart : data) {
                    if (cart.isChecked()) {   //是否勾上去了
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("commodityId", "商品1");
                            jsonObject.put("commodityAmount", "10");
                            jsonObject.put("categoryId", "0");
                            jsonObject.put("mark", "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        mylist.add(jsonObject.toString());
                    }

                }


                Log.d("ShopListModel", mylist.toString());

             /*   float sum = 0;
                if (!isNull())
                    return sum;

                for (ShopListModel.DataBean cart : data) {
                    if (cart.isChecked()) {   //是否勾上去了
//                sum += cart.getCount() * cart.getPrice();
                        sum += cart.getApplyAmount() * 10;
                    }
                }
                Log.d("sumsumsumsum", sum + "");
                if (sum > 0) {
                    text_del.setVisibility(View.VISIBLE);
                } else {
                    text_del.setVisibility(View.GONE);
                }
                tv_total.setText(Html.fromHtml("合计 ￥<span style='color:#eb4f38'>" + total + "</span>"),
                        TextView.BufferType.SPANNABLE);*/
                Log.d("mylistmylistmylistmylist", mylist.toString() + "");
                if (sum > 0) {
                    text_del.setVisibility(View.VISIBLE);
                } else {
                    text_del.setVisibility(View.GONE);
                }


                break;

            case R.id.text_beizhu:

                MyLabelAdapter myLabelAdapter;
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

                View view1 = layoutInflater.inflate(R.layout.dialog_bottom, null);

                ButtomDialogView bottomSheetDialog = new ButtomDialogView(getActivity(), view1, true, true);
                bottomSheetDialog.setContentView(R.layout.toast_dialog);
                bottomSheetDialog.show();
                RecyclerView recycle_biaoqian = view1.findViewById(R.id.recycle_biaoqian);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
                recycle_biaoqian.setLayoutManager(gridLayoutManager);

                myLabelAdapter = new MyLabelAdapter(getActivity());
                recycle_biaoqian.setAdapter(myLabelAdapter);
                List<String> mystr = new ArrayList<>();

                for (int i = 0; i < mVals.length; i++) {
                    mystr.add(mVals[i]);
                }
                myLabelAdapter.setDataList(mystr);
                DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
                divider.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.custom_divider));

                recycle_biaoqian.addItemDecoration(divider);
                ImageView img_close = view1.findViewById(R.id.img_close);
                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
                myLabelAdapter.setOnItemClickListener(new MyLabelAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(View view, int pos, List<String> myLiveList) {

                    }
                });
                break;

            case R.id.img_add:

                break;
        }
    }

    private String[] mVals = new String[]
            {"一次性筷子", "餐巾纸", "大白菜(大) ", "大白菜(小)", "白菜", "大头菜",
                    "奶白菜", "奶白菜"};

    public void showTotalPrice() {
        float total = getTotalPrice();
        tv_total.setText(Html.fromHtml("合计 ￥<span style='color:#eb4f38'>" + total + "</span>"),
                TextView.BufferType.SPANNABLE);
    }


    private float getTotalPrice() {

        float sum = 0;
        if (!isNull())
            return sum;

        for (ShopListModel.DataBean cart : data) {
            if (cart.isChecked()) {   //是否勾上去了
//                sum += cart.getCount() * cart.getPrice();
                sum += cart.getApplyAmount() * 10;
            }
        }
        Log.d("sumsumsumsum", sum + "");
        if (sum > 0) {
            text_del.setVisibility(View.VISIBLE);
        } else {
            text_del.setVisibility(View.GONE);
        }

        return sum;
    }


    /**
     * 计算总和
     */

    public boolean isNull() {
        return (data != null && data.size() > 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_del:



                break;
        }
    }
}
