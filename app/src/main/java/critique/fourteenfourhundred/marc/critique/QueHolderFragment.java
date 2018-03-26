package critique.fourteenfourhundred.marc.critique;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class QueHolderFragment extends Fragment implements View.OnClickListener {

    public View rootView;
    public ViewFlipper layout;
    //public ArrayList<QueFragment> posts=new ArrayList<QueFragment>();
    public Animation slide_in_left, slide_out_right;
    public JSONObject currentPost;

    public int remainingPosts=2;
    public int currentPostCount=0;

    public boolean queLoading=true;

    public static ArrayList<JSONObject> que = new ArrayList<JSONObject>();

    public QueHolderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onClick(View view) {
        try {
            currentPost=que.get(currentPostCount);
            switch (view.getId()) {
                case R.id.voteGood:

                    API.castVotes(getActivity(), currentPost.getString("_id"),1,new Callback(){
                        @Override
                        public void onResponse(JSONObject response) {
                            loadPost(false);
                        }
                    });

                    return;
                case R.id.voteBad:
                    //Data.lastVote=0;

                    API.castVotes(getActivity(), currentPost.getString("_id"),0,new Callback(){
                        @Override
                        public void onResponse(JSONObject response) {
                            loadPost(false);
                        }
                    });
                    return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_que_holder, container, false);

        layout = (ViewFlipper) rootView.findViewById(R.id.contentHolder);

        slide_in_left = AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_in_left);
        slide_out_right = AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_out_right);

        layout.setInAnimation(slide_in_left);
        layout.setOutAnimation(slide_out_right);


        ImageButton voteBad = (ImageButton) rootView.findViewById(R.id.voteBad);
        voteBad.setOnClickListener(this);

        ImageButton voteGood = (ImageButton) rootView.findViewById(R.id.voteGood);
        voteGood.setOnClickListener(this);

        loadPost(true);


        return rootView;
    }


    public void loadPost(final boolean start){



            remainingPosts--;

            if((remainingPosts==2 || start) && queLoading) {

                Log.i("MESSAGE","loading posts");

                //int x=l;
                if(!start){
                    //layout.removeViewAt(layout.getDisplayedChild()+1);
                }

                layout.showNext();

                API.getQue(getActivity(), new Callback() {
                    public void onResponse(final JSONObject json) {

                        //layout.showNext();
                        try {
                            if (json.getString("status").equals("ok")) {

                                final JSONArray posts = new JSONArray(json.getString("message"));

                                remainingPosts = posts.length();
                                currentPostCount=0;

                                //for(View f:)
                                que.clear();

                                layout.post(new Runnable() {
                                    public void run() {
                                        try {

                                            //Util.showDialog(getActivity(),layout.getChildCount()+"");
                                            //layout.removeAllViews();



                                            if(!start) {
                                                int x = layout.getDisplayedChild();

                                                //layout.removeViewAt(1);

                                                //Util.showDialog(getActivity(),"count "+x);






                                            }


                                            int end = posts.length();

                                            if(posts.length()==0)queLoading=false;

                                            //if(start)end--;

                                            for (int i = 0; i < end; i++) {

                                                JSONObject ar = new JSONObject(posts.get(i).toString());

                                                //Util.showDialog(getActivity(),ar.getString("title"));

                                                Log.e("HMMM",ar.getString("title"));

                                                QueFragment post = new QueFragment();
                                                Bundle bundle = new Bundle();
                                                bundle.putString("post", posts.get(i).toString());
                                                post.setArguments(bundle);
                                                que.add(new JSONObject(posts.get(i).toString()));
                                                getFragmentManager().beginTransaction().add(layout.getId(), post).commit();
                                            }
                                            Log.e("HMMM","---------");
                                        }catch (Exception e){
                                            e.printStackTrace();

                                        }
                                    }
                                });

                                //layout.showNext();
                            }else{
                                Util.showDialog(getActivity(),json.getString("message"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }else {


                currentPostCount++;
                layout.showNext();

                //layout.removeViewAt(currentPostCount);
            }

            //if(!start)layout.removeViewAt(remainingPosts);

            Log.e("AT",layout.getDisplayedChild()+"");


    }



}

