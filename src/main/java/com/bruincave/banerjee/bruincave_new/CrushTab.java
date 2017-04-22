package com.bruincave.banerjee.bruincave_new;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.banerjee.bruincave_new.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Vikrant Banerjee on 2/8/2017.
 */
public class CrushTab extends Fragment{
    private int offset_crush = 0;
    private int preLast = 0;
    private View footerView;
    private boolean createTab = true;

    public CrushTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        createTab = true;
        offset_crush = 0;
        return inflater.inflate(R.layout.crush_tab, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        if(getView() != null) {
            bringCrush();
        }
        ListView crushListView = (ListView) getView().findViewById(R.id.crushlistView);

        if(createTab){
            createTab = false;
            footerView =  ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);
            crushListView.addFooterView(footerView);
        }


        crushListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if(lastItem == totalItemCount) {
                    if(preLast!=lastItem) {
                        if (totalItemCount != 0) {
                            bringCrush();
                        }

                        Log.d("info-crush", offset_crush + "");

                        preLast = lastItem;
                    }
                }
            }
        });
    }

    CrushAdapter crushAdapter = null;
    public void bringCrush(){
        final View currView = getView();
        Response.Listener<String> crushListener = new Response.Listener<String>() {
            private String[] info;
            @Override
            public void onResponse(String crush) {
                try {
                    Log.d("RSP:", crush);
                    JSONObject crushResponse = new JSONObject(crush);
                    Log.d("Action", "inside try");
                    if (crushResponse != null) {
                        JSONArray crushArray = crushResponse.getJSONArray("crush");
                        //LinearLayout crushLayout = (LinearLayout) findViewById(R.id.crushScroll);
                        ArrayList<Crush> info = new ArrayList<Crush>();

                        for (int i = 0; i < crushArray.length(); i++) {
                            JSONObject crushObject = crushArray.getJSONObject(i);

                            Crush newCrush =  new Crush();
                            newCrush.time_added = crushObject.getString("time_added");
                            newCrush.body = crushObject.getString("body");
                            newCrush.id = crushObject.getInt("id");
                            newCrush.commentscount = crushObject.getInt("commentscount");
                            Log.d("Bug", newCrush.id + "");

                            info.add(newCrush);
                        }

                        ListView crushListView = (ListView) currView.findViewById(R.id.crushlistView);

                        Log.d("Action", "Still in this loop");

                        if (offset_crush == 0) {
                            crushAdapter = new CrushAdapter(getContext(), info);
                            crushListView.setAdapter(crushAdapter);
                        } else {
                            crushAdapter.addAll(info);
                        }
                        offset_crush = offset_crush + 5;

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    ListView crushListView = (ListView) currView.findViewById(R.id.crushlistView);

                    crushListView.removeFooterView(footerView);
                }
            }
        };
        GetCrush getCrush = new GetCrush(offset_crush, crushListener);
        RequestQueue queue1 = Volley.newRequestQueue(getContext());
        queue1.add(getCrush);
    }

}
