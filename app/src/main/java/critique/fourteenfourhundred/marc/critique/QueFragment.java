package critique.fourteenfourhundred.marc.critique;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;


public class QueFragment extends Fragment {

        View rootView;
        JSONObject post = new JSONObject();


        public QueFragment(){


        }




    public static QueFragment newInstance(String text) {

        QueFragment f = new QueFragment();

        Bundle b = new Bundle();
        b.putString("post", text);
        f.setArguments(b);
        return f;
    }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_que, container, false);

            try {
                post = (JSONObject) new JSONObject(this.getArguments().getString("post"));
                loadPost();

                //getParentFragment().onAttach(getContext());





            }catch (Exception e){
                e.printStackTrace();
            }



            return rootView;
        }



        public void loadPost(){

                    try{

                        ((TextView) rootView.findViewById(R.id.postTitle)).setText(post.getString("title"));
                        ((TextView) rootView.findViewById(R.id.postContent)).setText(post.getString("content"));
                        ((TextView) rootView.findViewById(R.id.postSender)).setText(post.getString("username"));
                        ((TextView) rootView.findViewById(R.id.postVoteCount)).setText(post.getString("votes")+" votes");

                        API.getPatch(getActivity(), post.getString("username"), new Callback() {
                            public void onResponse(Bitmap img) {
                                ((ImageView) rootView.findViewById(R.id.queProfilePic)).setImageBitmap(img);
                            }
                        });

                        //currentPost=post;

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }










        @Override
        public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
            super.onCreateOptionsMenu(menu, inflater);


        }

    public void myClickMethod(View v) {

    }



}
