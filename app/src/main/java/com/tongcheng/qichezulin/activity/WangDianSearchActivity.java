package com.tongcheng.qichezulin.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiongbull.jlog.JLog;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.pacific.adapter.ExpandableAdapter;
import com.pacific.adapter.ExpandableAdapterHelper;
import com.tongcheng.qichezulin.Param.ParamCityQu;
import com.tongcheng.qichezulin.Param.ParamGetWangDianList;
import com.tongcheng.qichezulin.Param.ParamNearWangDian;
import com.tongcheng.qichezulin.Param.ParamNearWangDian2;
import com.tongcheng.qichezulin.R;
import com.tongcheng.qichezulin.model.BannerShop;
import com.tongcheng.qichezulin.model.CityQuModel;
import com.tongcheng.qichezulin.model.JsonBase;
import com.tongcheng.qichezulin.model.QuModel;
import com.tongcheng.qichezulin.model.QuShopsModel;
import com.tongcheng.qichezulin.utils.Utils;
import com.tongcheng.qichezulin.utils.UtilsJson;
import com.tongcheng.qichezulin.utils.UtilsMath;
import com.tongcheng.qichezulin.utils.UtilsTiaoZhuang;
import com.tongcheng.qichezulin.view.ExpandableListViewForScrollView;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 林尧 on 2016/7/22.
 * 网店查询界面
 */

@ContentView(R.layout.activity_wang_dian_search)
public class WangDianSearchActivity extends Activity implements View.OnClickListener {


    static BannerShop shop; //用来回传到上一个界面
    @ViewInject(R.id.tv_first)
    TextView tv_first;

    @ViewInject(R.id.lv_fu_jin_men_dian)
    ListView lv_fu_jin_men_dian;
    Adapter<BannerShop> shopAdapter;
    List<CityQuModel> cityQuModels;
    @ViewInject(R.id.elv_city)
    ExpandableListView elv_city;
    ExpandableAdapter<CityQuModel, QuModel> quModelExpandableAdapter;
    int flag = 0;//定义标记变量
    @ViewInject(R.id.iv_return)
    ImageView iv_return;
    com.baidu.mapapi.model.LatLng p1;
    @ViewInject(R.id.tv_qu_name) //区域名字
            TextView tv_qu_name;
    @ViewInject(R.id.tv_near_shop)//附近门店
            TextView tv_near_shop;
    @ViewInject(R.id.elv_shop)
    ExpandableListView elv_shop;
    ExpandableAdapter<QuShopsModel, BannerShop> shopsModelShopModelExpandableAdapter;
    List<QuShopsModel> quShopsModels;
    private String latitude; //纬度
    private String lontitude;//经度
    private String isZuCheActivity;//判断是不是从ZuCheActivity 页面过来的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        SDKInitializer.initialize(getApplicationContext());
        setClickListnerOnView();
        getDataFromLast();
        get_city_qu();
        setClickListner();

    }


    private void setClickListnerOnView() {
        iv_return.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_first:
                UtilsTiaoZhuang.ToAnotherActivity(WangDianSearchActivity.this, SearchActivity.class);
                break;
            case R.id.iv_return:
                onBackPressed();
                break;
            case R.id.tv_near_shop:
                get_near_Wang_dian_List(lontitude, latitude, "1");
                tv_qu_name.setText("附近门店");
                tv_qu_name.setVisibility(View.VISIBLE);
                lv_fu_jin_men_dian.setVisibility(View.VISIBLE);
                elv_shop.setVisibility(View.GONE);
                break;
        }
    }

    public void initView() {


    }


    // 获取从上一个界面传过来的参数
    public void getDataFromLast() {
        Bundle bundle = getIntent().getExtras();
        latitude = bundle.getString("latitude");
        lontitude = bundle.getString("lontitude");
        isZuCheActivity = bundle.getString("isZuCheActivity");
        JLog.w("经度:---------" + lontitude + "纬度:+=======" + latitude);
        get_near_Wang_dian_List(lontitude, latitude, "1");
        p1 = new LatLng(Double.parseDouble(latitude), Double.parseDouble(lontitude));
    }


    public void setClickListner() {
        tv_near_shop.setOnClickListener(this);
        tv_first.setOnClickListener(this);
    }

    // 获取二级目录数据
    private void get_city_qu() {
        ParamCityQu paramCityQu = new ParamCityQu();
        Callback.Cancelable cancelable
                = x.http().get(paramCityQu, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                UtilsJson.printJsonData(result);
                Gson gson = new Gson();
                Type type = new TypeToken<JsonBase<ArrayList<CityQuModel>>>() {
                }.getType();
                JsonBase<ArrayList<CityQuModel>> base = gson
                        .fromJson(result, type);

                JLog.w(base.data.size() + "");
                cityQuModels = base.data;
                JLog.w(cityQuModels.size() + "=============");
                if (base.data.size() > 0) {
                    cityQuModels = base.data;
                    quModelExpandableAdapter = new ExpandableAdapter<CityQuModel, QuModel>(getApplicationContext(), R.layout.e_listview_item_father, R.layout.e_listview_item_child) {
                        @Override
                        protected List<QuModel> getChildren(int groupPosition) {
                            return get(groupPosition).quModels;
                        }

                        @Override
                        protected void convertGroupView(final boolean isExpanded, final ExpandableAdapterHelper helper, final CityQuModel item) {
                            helper.getGroupPosition();
                            helper.setText(R.id.tv_city, item.CityName).getView(R.id.rrl_father).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    JLog.w(item.CityName);
                                    if (isExpanded) {
                                        elv_city.collapseGroup(helper.getGroupPosition());
                                        JLog.w(item.CityId);
                                        get_Wang_dian_List_by_cityID(item.CityId);
                                    } else {
                                        elv_city.expandGroup(helper.getGroupPosition(), true);
                                        JLog.w(item.CityId);
                                        get_Wang_dian_List_by_cityID(item.CityId);

                                    }
                                }
                            });
                        }

                        @Override
                        protected void convertChildView(final boolean isLastChild, final ExpandableAdapterHelper helper, final QuModel item) {
                            helper.setText(R.id.tv_qu, item.FName)
                                    .getView(R.id.rrl_child)
                                    .setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            JLog.w(item.FName);
                                            JLog.w(item.PID);
                                            get_Wang_dian_List(item.PID, "", "", item.FName);
                                        }
                                    });
                        }
                    };
                    elv_city.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                        @Override
                        public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                            return false;
                        }
                    });
                    quModelExpandableAdapter.addAll(cityQuModels);
                    elv_city.setAdapter(quModelExpandableAdapter);

                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                JLog.w(isOnCallback + "");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                JLog.w(cex + "");
            }

            @Override
            public void onFinished() {

            }
        });
    }


    // 获取网点 ----根据城市id
    public void get_Wang_dian_List_by_cityID(String city_id) {
        ParamNearWangDian2 paramNearWangDian2 = new ParamNearWangDian2();
        paramNearWangDian2.city_id = city_id;
        Callback.Cancelable cancelable
                = x.http().post(paramNearWangDian2, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                UtilsJson.printJsonData(result);
                try {
                    Gson gson = new Gson();
                    Type type = new TypeToken<JsonBase<ArrayList<QuShopsModel>>>() {
                    }.getType();
                    JsonBase<ArrayList<QuShopsModel>> base = gson
                            .fromJson(result, type);
                    if (base.data.size() > 0) {
                        elv_shop.setVisibility(View.VISIBLE);
                        tv_qu_name.setVisibility(View.GONE);
                        lv_fu_jin_men_dian.setVisibility(View.GONE);
                        quShopsModels = base.data;
                        shopsModelShopModelExpandableAdapter = new ExpandableAdapter<QuShopsModel, BannerShop>(getApplicationContext(), R.layout.e_listview_item_father2, R.layout.listview_item0) {
                            @Override
                            protected List<BannerShop> getChildren(int groupPosition) {
                                return get(groupPosition).ShopModels;
                            }

                            @Override
                            protected void convertGroupView(final boolean isExpanded, final ExpandableAdapterHelper helper, final QuShopsModel item) {
                                if (quShopsModels.get(helper.getGroupPosition()).ShopModels.size() == 0) {
                                    helper.setVisible(R.id.tv_city, View.GONE);
                                } else {
                                    helper.setVisible(R.id.tv_city, View.VISIBLE);
                                    helper.setText(R.id.tv_city, item.CountyName).getView(R.id.rrl_father);
                                }

                            }

                            @Override
                            protected void convertChildView(final boolean isLastChild, final ExpandableAdapterHelper helper, final BannerShop item) {
                                helper
                                        .setText(R.id.tv_shop_name, item.FShopName)
                                        .setText(R.id.tv_shop_address, item.FAddress);
                                LatLng p2 = new LatLng(Double.parseDouble(item.FLatitude), Double.parseDouble(item.FLongitude));
                                int mishu = (int) DistanceUtil.getDistance(p1, p2);
                                helper.setText(R.id.tv_hao_far, UtilsMath.getjuli(mishu));//设置距离
                                helper.getView(R.id.rrl_shop).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        JLog.w("点击了" + item.FShopName);
                                        if (isZuCheActivity.equals("1")) {
                                            shop = item;
                                            onBackPressed();
                                        } else {
                                            UtilsTiaoZhuang.ToAnotherActivity(WangDianSearchActivity.this, WangDianDetailActivity.class, UtilsTiaoZhuang.get_BannerShop(item));
                                        }
                                    }
                                });
                            }
                        };

                        shopsModelShopModelExpandableAdapter.addAll(quShopsModels);
                        elv_shop.setAdapter(shopsModelShopModelExpandableAdapter);
                        for (int i = 0; i < quShopsModels.size(); i++) {
                            elv_shop.expandGroup(i);
                        }

                    }


                } catch (Exception E) {
                    E.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    // 获取网点 ----根据区名
    public void get_Wang_dian_List(String county_id, String keyword, String type_id, final String QUNAME) {
        ParamGetWangDianList paramGetWangDianList = new ParamGetWangDianList();
        paramGetWangDianList.county_id = county_id;
        paramGetWangDianList.keyword = keyword;
        paramGetWangDianList.type_id = type_id;
        Callback.Cancelable cancelable
                = x.http().post(paramGetWangDianList, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                UtilsJson.printJsonData(result);
                Gson gson = new Gson();
                Type type = new TypeToken<JsonBase<ArrayList<BannerShop>>>() {
                }.getType();
                JsonBase<ArrayList<BannerShop>> base = gson
                        .fromJson(result, type);
                if (!base.status.toString().trim().equals("0")) {
                    if (base.data.size() > 0) {
                        tv_qu_name.setText(QUNAME);
                        shopAdapter.clear();
                        shopAdapter.addAll(base.data);
                        shopAdapter.notifyDataSetChanged();
                        tv_qu_name.setVisibility(View.VISIBLE);
                        lv_fu_jin_men_dian.setVisibility(View.VISIBLE);
                        elv_shop.setVisibility(View.GONE);
                    } else {
                        tv_qu_name.setVisibility(View.VISIBLE);
                        tv_qu_name.setText(QUNAME);
                        shopAdapter.clear();
                        shopAdapter.notifyDataSetChanged();
                        Utils.ShowText2(getApplication(), QUNAME + "没有网点");
                    }
                } else {
                    JLog.w(base.info);

                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    // 获取附近网点 |根据距离
    public void get_near_Wang_dian_List(String lng, String lat, String type_id) {
        ParamNearWangDian nearWangDian = new ParamNearWangDian();
        JLog.w(lng + "========" + lat);
        nearWangDian.lng = lng;
        nearWangDian.lat = lat;
        nearWangDian.type_id = type_id;
        Callback.Cancelable cancelable
                = x.http().post(nearWangDian, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                UtilsJson.printJsonData(result);
                Gson gson = new Gson();
                Type type = new TypeToken<JsonBase<ArrayList<BannerShop>>>() {
                }.getType();
                JsonBase<ArrayList<BannerShop>> base = gson
                        .fromJson(result, type);
                if (!base.status.toString().trim().equals("0")) {
                    if (base.data.size() > 0) {
                        if (shopAdapter == null) {
                            shopAdapter = new Adapter<BannerShop>(getApplication(), R.layout.listview_item0) {
                                @Override
                                protected void convert(final AdapterHelper helper, final BannerShop item) {
                                    final int position = helper.getPosition();
                                    helper
                                            .setText(R.id.tv_shop_name, item.FShopName)
                                            .setText(R.id.tv_shop_address, item.FAddress);
                                    LatLng p2 = new LatLng(Double.parseDouble(item.FLatitude), Double.parseDouble(item.FLongitude));
                                    int mishu = (int) DistanceUtil.getDistance(p1, p2);
                                    helper.setText(R.id.tv_hao_far, UtilsMath.getjuli(mishu));//设置距离
                                    helper.getView(R.id.rrl_shop).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            JLog.w("点击了" + item.FShopName);
                                            //    UtilsTiaoZhuang.ToAnotherActivity(WangDianSearchActivity.this, WangDianDetailActivity.class, UtilsTiaoZhuang.get_BannerShop(item));
                                            if (isZuCheActivity.equals("1")) {
                                                shop = item;
                                                onBackPressed();
                                            } else {
                                                UtilsTiaoZhuang.ToAnotherActivity(WangDianSearchActivity.this, WangDianDetailActivity.class, UtilsTiaoZhuang.get_BannerShop(item));
                                            }


                                        }
                                    });


                                }
                            };
                            shopAdapter.addAll(base.data);
                            lv_fu_jin_men_dian.setAdapter(shopAdapter);
                        } else {
                            shopAdapter.clear();
                            shopAdapter.addAll(base.data);
                            shopAdapter.notifyDataSetChanged();
                        }

                    }
                } else {
                    JLog.w(base.info);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        JLog.w("onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        JLog.w("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        JLog.w("onPause");
    }
}
