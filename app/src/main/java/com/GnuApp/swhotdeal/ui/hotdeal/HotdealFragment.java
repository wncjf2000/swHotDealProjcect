package com.GnuApp.swhotdeal.ui.hotdeal;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GnuApp.swhotdeal.R;
import com.GnuApp.swhotdeal.adapter.OnSearchClickListener;
import com.GnuApp.swhotdeal.data.HotDeal;
import com.GnuApp.swhotdeal.adapter.SearchAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HotdealFragment extends Fragment {

    ArrayList<HotDeal> hotDealArrayList;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    SearchAdapter adapter;
    TextView main_cost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String TAG = "HotdealFragment";
        String beforeString = "";

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_hotdeal, container, false);
        LinearLayout linear = rootView.findViewById(R.id.main_item);


        hotDealArrayList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        adapter = new SearchAdapter();
        recyclerView = rootView.findViewById(R.id.hotdeal_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        main_cost = rootView.findViewById(R.id.main_cost);
        main_cost.setPaintFlags(main_cost.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        recyclerView.setHasFixedSize(true);

        HotDeal hot = new HotDeal();
        getFirebaseData(hotDealArrayList, adapter);

        recyclerView.setAdapter(adapter);

        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://store.steampowered.com/app/973600/Movavi_Video_Suite_18__Video_Making_Software__Edit_Convert_Capture_Screen_and_more"));
                startActivity(intent);
            }
        });

        adapter.setOnItemClickListener((onClickListener, view, position) -> {
            HotDeal item = adapter.getItem(position);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getPlatAddress()));
            startActivity(intent);
        });

        return rootView;
    }

    public void getFirebaseData(ArrayList<HotDeal> arrayList, SearchAdapter adapter){
        FirebaseFirestore db = FirebaseFirestore.getInstance();// ?????????????????? ?????????????????? ??????
        db.collection("ScrapingDB").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    arrayList.clear(); //?????? ?????????????????? ???????????? ?????? ??????????????????.
                    for (QueryDocumentSnapshot document : task.getResult()){
                        HotDeal hotDeal = document.toObject(HotDeal.class);
                        hotDeal.setDivide100();
                        Log.d("getFireBaseData", document.getId());
                        arrayList.add(hotDeal);
                        Log.d("Hotdeal", document.getId() + "=>" + document.getData());
                    }
                    adapter.setItems(arrayList);
                    adapter.notifyDataSetChanged(); //????????? ?????? ??? ????????????
                } else {
                    Log.w("", "Error getting documents.", task.getException());
                }
            }
        });
    }
}