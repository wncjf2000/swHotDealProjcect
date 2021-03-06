package com.GnuApp.swhotdeal.ui.search;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GnuApp.swhotdeal.Controller;
import com.GnuApp.swhotdeal.R;
import com.GnuApp.swhotdeal.data.HotDeal;
import com.GnuApp.swhotdeal.adapter.SearchAdapter;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Iterator;

public class SearchFragment extends Fragment {

    ArrayList<HotDeal> hotDealArrayList;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    SearchView searchView;
    SearchAdapter adapter;
    Controller controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String TAG = "SearchFragment";
        String beforeString = "";

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);

        hotDealArrayList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        adapter = new SearchAdapter();
        controller = new Controller();
        recyclerView = rootView.findViewById(R.id.search_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        searchView = rootView.findViewById(R.id.search_view);

        getFirebaseData(hotDealArrayList, adapter);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((onClickListener, view, position) -> {
            HotDeal item = adapter.getItem(position);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getPlatAddress()));
            startActivity(intent);
        });
        // ????????? ????????? ?????? ???????????? ?????????
        
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
        // ????????? ??? ????????? ??? ??????

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String[] stringArray;
                Log.d("onQueryTextSubmit", hotDealArrayList.toString());
                Log.d("search", hotDealArrayList.toString());
                Log.d("search", query);

                stringArray = query.toLowerCase().split(" "); // ???????????? ?????? ??????, ???????????? ????????? ??????
                for (String s : stringArray) {
                    for (Iterator<HotDeal> iter = hotDealArrayList.iterator(); iter.hasNext(); ) {
                        HotDeal item = iter.next();
                        if (!(item.getSWName().toLowerCase().contains(s)))
                            iter.remove();
                    }
                }

                Log.d("arrayList2", hotDealArrayList.toString());

                adapter.notifyDataSetChanged();
                recyclerView.setVisibility(View.VISIBLE);

                for (int i = 0; i < hotDealArrayList.size(); i++)
                    Log.d("loaded", hotDealArrayList.get(i).getSWName());

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("onQueryTextChange", hotDealArrayList.toString());
                if (newText.equals(""))
                    //recyclerView.setVisibility(View.GONE);
                    getFirebaseData(hotDealArrayList, adapter);
                return true;
            }
        });
    return rootView;
    }

    public void getFirebaseData(ArrayList<HotDeal> arrayList, SearchAdapter adapter){
        FirebaseFirestore db = FirebaseFirestore.getInstance();// ?????????????????? ?????????????????? ??????
        db.collection("ScrapingDB").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                arrayList.clear(); //?????? ?????????????????? ???????????? ?????? ??????????????????.
                for (QueryDocumentSnapshot document : task.getResult()){
                    HotDeal hotDeal = document.toObject(HotDeal.class);
                    hotDeal.setDivide100();
                    Log.d("getFireBaseData", document.getId());
                    // adapter.addItem(hotDeal); //???????????? ArrayList??? ?????? ????????????????????? mHotdeal??? ??????
                    arrayList.add(hotDeal);
//                        adapter.setItems(hotDeal);
                    Log.d("Hotdeal", document.getId() + "=>" + document.getData());
                }
                adapter.setItems(arrayList);
                adapter.notifyDataSetChanged(); //????????? ?????? ??? ????????????
            } else {
                Log.w("", "Error getting documents.", task.getException());
            }
        });
    }
}