package net.babybaby.agijagi.weekly_meal;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import net.babybaby.agijagi.R;
import net.babybaby.agijagi.cooksearch.CookModel;
import net.babybaby.agijagi.etc.HttpGetRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author Namyun
 * @since 13. 10. 19.
 */
public class WeeklyMealInfoFragment extends Fragment {

    private View rootView;

    private boolean loading = true;

    private int id;
    private String name;
    private String address;
    private String telephone;

    private ArrayList<Comment> comments;
    private Handler mHandler = new Handler();
    ListView commentList;
    InfoListAdaptor infoListAdaptor;

    public WeeklyMealInfoFragment(int id, String name, String address, String telephone)
    {
        this.id=id;
        this.name=name;
        this.address=address;
        this.telephone=telephone;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_weekly_info, container, false);

        comments = new ArrayList<Comment>();
        commentList =(ListView)rootView.findViewById(R.id.meal_infolist);
        infoListAdaptor = new InfoListAdaptor(getActivity(), R.layout.row_comment, comments);
        commentList.setAdapter(infoListAdaptor);

        init();
        updateList();

        return rootView;
    }

    public void init(){
        infoListAdaptor.clear();
        comments = new ArrayList<Comment>();
        commentList = (ListView) rootView.findViewById(R.id.meal_infolist);
        infoListAdaptor = new InfoListAdaptor(getActivity(), R.layout.row_comment, comments);
        commentList.setAdapter(infoListAdaptor);

    }

    public void updateList(){
        new Thread() {
            public void run() {
                try {
                    HttpGetRequest hgr = new HttpGetRequest();
                    String dest = "http://babyhoney.kr/api/listFeedback/?to_id="+id;
                    String url = hgr.getHTML(dest);

                    Log.d("url", "" + url);

                    JSONObject response = new JSONObject(url);
                    JSONObject channel = response.getJSONObject("channel");
                    JSONArray data = channel.getJSONArray("item");

                    Log.d("COMMENT",""+data.length());

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject obj = data.getJSONObject(i);
                        int cm_id = obj.getInt("cm_id");
                        String userId = obj.getString("member_id");
                        int userType = obj.getInt("member_type");
                        String message = obj.getString("message");
                        String date = obj.getString("creation_time");

                        Comment comment=new Comment(userId,userType,message,date);
                        Log.d("COMMENT","user_id: "+userId+"message: "+message);

                        comments.add(comment);
                    }

                    mHandler.post(new Runnable() {
                        public void run() {
                            commentList.post(new Runnable() {
                                public void run() {
                                    infoListAdaptor.notifyDataSetChanged();
                                    loading = true;
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private class InfoListAdaptor extends ArrayAdapter<Comment>
    {
        private Context context;
        private ArrayList<Comment> comments;

        public InfoListAdaptor(Context context, int textViewResourceId, ArrayList<Comment> objects)
        {
            super(context,textViewResourceId,objects);
            this.comments=objects;
            this.context=context;
        }

        @Override
        public int getCount() {
            return comments.size()+1;
        }

        @Override
        public Comment getItem(int i) {
            return comments.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = view;
            LayoutInflater vi=null;

            if (v == null) {
                vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                if(i==0)
                {
                    v = vi.inflate(R.layout.row_meal_org_info,null);
                }
                else
                {
                    v = vi.inflate(R.layout.row_comment, null);
                }
            }

            if(i==0)
            {
                TextView org_name=(TextView)v.findViewById(R.id.row_org_name);
                TextView org_address=(TextView)v.findViewById(R.id.row_org_address);
                TextView org_telephone=(TextView)v.findViewById(R.id.row_org_telephone);

                org_name.setText(name);
                org_address.setText(address);
                org_telephone.setText(telephone);
            }
            else
            {
                Comment comment = comments.get(i-1);

                if (comment != null) {
                    TextView user_id = (TextView) v.findViewById(R.id.row_comment_user_id);
                    TextView message = (TextView) v.findViewById(R.id.row_comment_message);
                    TextView date = (TextView) v.findViewById(R.id.row_comment_info);
                    user_id.setText(comment.getUserId());
                    message.setText(comment.getMessage());
                    date.setText(comment.getDate());
                }
            }

            return v;
        }
    }

    private class Comment
    {
        private String userId;
        private int userType;
        private String message;
        private String date;

        public Comment(String userId, int userType, String message, String date)
        {
            this.userId=userId;
            this.userType=userType;
            this.message=message;
            this.date=date;
        }

        private String getUserId() {
            return userId;
        }

        private int getUserType() {
            return userType;
        }

        private String getMessage() {
            return message;
        }

        private String getDate() {
            return date;
        }

    }
}