package com.uiresource.cookit;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uiresource.cookit.recycler.CommentsAdapter;
import com.uiresource.cookit.recycler.ItemComment;
import com.uiresource.cookit.recycler.ItemPreparation;
import com.uiresource.cookit.recycler.ItemShopping;
import com.uiresource.cookit.recycler.PreparationAdapter;
import com.uiresource.cookit.recycler.ShoppingAdapter;
import com.uiresource.cookit.utils.CircleGlide;

import java.util.ArrayList;
import java.util.List;

public class Detail extends BaseActivity implements PreparationAdapter.ViewHolder.ClickListener{
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private RecyclerView recyclerView;
    private ShoppingAdapter mAdapter;
    private RecyclerView recyclerViewPreparation;
    private PreparationAdapter mAdapterPreparation;
    private RecyclerView recyclerViewComments;
    private CommentsAdapter mAdapterComments;
    private CoordinatorLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        rootView = (CoordinatorLayout) findViewById(R.id.rootview);
        setupToolbar(R.id.toolbar, "김치찌개 레시피", android.R.color.white, android.R.color.transparent, R.drawable.ic_arrow_back);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerShopping);

        mAdapter = new ShoppingAdapter(generateShopping(), this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerViewPreparation = (RecyclerView) findViewById(R.id.recyclerPreparation);

        mAdapterPreparation = new PreparationAdapter(getBaseContext(), generatePreparation(),this);
        LinearLayoutManager mLayoutManagerPreparation = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewPreparation.setLayoutManager(mLayoutManagerPreparation);
        recyclerViewPreparation.setItemAnimator(new DefaultItemAnimator());
        recyclerViewPreparation.setAdapter(mAdapterPreparation);

        recyclerViewComments = (RecyclerView) findViewById(R.id.recyclerComment);

        mAdapterComments = new CommentsAdapter(generateComments(), this);
        LinearLayoutManager mLayoutManagerComment = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewComments.setLayoutManager(mLayoutManagerComment);
        recyclerViewComments.setItemAnimator(new DefaultItemAnimator());
        recyclerViewComments.setAdapter(mAdapterComments);


        final ImageView imageComment = (ImageView) findViewById(R.id.iv_user);
        Glide.with(this)
                .load(Uri.parse("https://github.com/itlockit2/CookingHelper/blob/master/images/youbong.jpg?raw=true"))
                .transform(new CircleGlide(this))
                .into(imageComment);

        final ImageView image = (ImageView) findViewById(R.id.image);
        Glide.with(this).load(Uri.parse("https://github.com/itlockit2/CookingHelper/blob/master/images/kimchi_detail.jpg?raw=true")).into(image);

    }

    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public boolean onItemLongClicked(int position) {
        toggleSelection(position);
        return true;
    }

    private void toggleSelection(int position) {
        mAdapterPreparation.toggleSelection(position);
        final RelativeLayout rlShare = (RelativeLayout) findViewById(R.id.rl_share);
        if (position == mAdapterPreparation.getItemCount()-1) {

            Log.d("selected", "toggleSelection: "+position+" "+(mAdapterPreparation.getItemCount()-1));
            rlShare.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        Drawable drawable = menu.findItem(R.id.action_search).getIcon();

        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(this,android.R.color.white));
        menu.findItem(R.id.action_search).setIcon(drawable);
        return true;
    }

    public List<ItemShopping> generateShopping(){
        List<ItemShopping> itemList = new ArrayList<>();
        String name[] = {"묵은지", "돼지고기", "쌀뜨물", "된장", "청양고추", "다진마늘",
                "고춧가루", "대파", "국간장", "새우젓"};
        String pieces[] = {"1/4포기", "150g", "4컵", "0.5큰술", "1개(취향껏)",
                "1큰술", "2큰술", "적당량","1큰술","1큰술"};
        float rating[] = {3, 4, 4, 3, 5, 4, 4, 3};

        for (int i = 0; i<name.length; i++){
            ItemShopping item = new ItemShopping();
            item.setPieces(pieces[i]);
            item.setName(name[i]);
            itemList.add(item);
        }
        return itemList;
    }
    public List<ItemComment> generateComments(){
        List<ItemComment> itemList = new ArrayList<>();
        String username[] = {"김형준"};
        String date[] = {"2018-11-13"};
        String comments[] = {" 진짜 맛있습니다 매번 이 레시피로 찌개 끓여 먹어야겠어요 ㅎ 저는 쌀뜨물 대신 다시마육수물 넣었는데 정말 맛있었습니다 제가이런맛을내다니" +
                " 감격스럽습니다ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ진짜맛있게먹었습니다 요리 자주망하는데 진짜ㅠㅠ좋은레시피너무감사합니다ㅎㅎ♡♡♡♡ 다음에는 쌀뜨물로 도전해보겠습니다 "};
        String userphoto[] = {"https://github.com/itlockit2/CookingHelper/blob/master/images/hungjun.jpg?raw=true"};
        String img1[] = {"https://github.com/itlockit2/CookingHelper/blob/master/images/review.PNG?raw=true"};
        String img2[] = {"https://images.pexels.com/photos/134574/pexels-photo-134574.jpeg?h=350&auto=compress&cs=tinysrgb"};

        for (int i = 0; i<username.length; i++){
            ItemComment comment = new ItemComment();
            comment.setUsername(username[i]);
            comment.setUserphoto(userphoto[i]);
            comment.setDate(date[i]);
            comment.setComments(comments[i]);
            comment.setImg1(img1[i]);
            comment.setImg2(img2[i]);
            itemList.add(comment);
        }
        return itemList;
    }

    public List<ItemPreparation> generatePreparation(){
        List<ItemPreparation> itemList = new ArrayList<>();
        String step[] = {"먼저 속을 털어낸 묵은지 1/4포기를 잘게 썰어서 준비한다. 돼지고기도 한 컵 준비한다. 그리고 대파와 청양고추도 송송 썰어 준비합니다. 김치와 돼지고기의 비율은 3 :1",
                "냄비에 쌀뜨물 4컵과 돼지고기 1컵을 넣어준다. 김치찌개의 깊은 맛과 돼지고기의 잡내 제거를 위해 된장도 반 큰 술도 넣어준다. 그리고 쌀뜨물이 끓으면서 떠오르는 불순물과 거품은 모두 건져준다.",
                "돼지기름이 국물에 충분히 우러나온 것 같다 싶으면 잘게 썰어둔 묵은지를 넣어준다.",
                "고춧가루 2 큰 술과 다진 마늘 1 큰 술, 국간장 1 큰 술을 넣어준다. 간은 새우젓으로한다",
                "맛이 2%가 부족한 것 같다면 김치 국물을 3-4 큰 술 넣어주셔도 좋다. 어슷 썬 청양고추 1개도 넣는다. 추가로 팽이버섯도 조금 넣어주면 좋다.",
                "마지막으로 대파까지 올려주면 완성!"};

        for (int i = 0; i<step.length; i++){
            ItemPreparation item = new ItemPreparation();
            item.setStep(step[i]);
            item.setNumber(String.valueOf(i+1));
            itemList.add(item);
        }
        return itemList;
    }
}

