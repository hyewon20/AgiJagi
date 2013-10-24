package net.babybaby.agijagi.weekly_meal;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.babybaby.agijagi.R;
import net.babybaby.agijagi.cooksearch.CookModel;
import net.babybaby.agijagi.etc.HttpGetRequest;
import net.babybaby.agijagi.login.Login;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Namyun
 * @since 13. 10. 19.
 */
public class WeeklyMealInfoFragment extends Fragment {

    private View rootView;

    private boolean loading = true;
    private Boolean end_bool = false;
    private int currentPage = 1;
    private int previousTotal = 0;

    private int id;
    private String name;
    private String address;
    private String telephone;

    private ArrayList<Comment> comments;
    private Handler mHandler = new Handler();
    ListView commentList;
    InfoListAdaptor infoListAdaptor;
    Thread commentthread = null;
    String feedURL = null;
    EditText et;
    HttpEntity resEntity;


    public WeeklyMealInfoFragment(int id, String name, String address, String telephone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_weekly_info, container, false);

        currentPage = 1;


        comments = new ArrayList<Comment>();
        commentList = (ListView) rootView.findViewById(R.id.meal_infolist);
        infoListAdaptor = new InfoListAdaptor(getActivity(), R.layout.row_comment, comments);
        commentList.setAdapter(infoListAdaptor);

        init();
        updateList();

        return rootView;
    }

    public void init() {
        loading = true;
        end_bool = false;
        currentPage = 1;
        previousTotal = 0;
        infoListAdaptor.clear();
        comments = new ArrayList<Comment>();
        commentList = (ListView) rootView.findViewById(R.id.meal_infolist);
        infoListAdaptor = new InfoListAdaptor(getActivity(), R.layout.row_comment, comments);
        commentList.setAdapter(infoListAdaptor);

    }

    public void post() {
        new Thread() {
            public void run() {
                try {
                    HttpClient client = new DefaultHttpClient();
                    String postURL = "http://babyhoney.kr/api/CreateFeedback/?username=" + Login.id + "&to_id=" + id;
                    HttpPost post = new HttpPost(postURL);
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("message", et.getText().toString()));
                    UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
                    post.setEntity(ent);
                    HttpResponse responsePOST = client.execute(post);
                    HttpEntity resEntity = responsePOST.getEntity();

                    if (resEntity != null) {
                        Log.i("RESPONSE", EntityUtils.toString(resEntity));
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }.start();

    }



    public void updateList() {
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "불러오는 중입니다. 잠시 기다려 주세요", true);
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(3000);
                    HttpGetRequest hgr = new HttpGetRequest();
                    String dest = "http://babyhoney.kr/api/listFeedback/?to_id=" + id;
                    String url = hgr.getHTML(dest);

                    Log.d("url", "" + url);

                    JSONObject response = new JSONObject(url);
                    JSONObject channel = response.getJSONObject("channel");
                    JSONArray data = channel.getJSONArray("item");

                    Log.d("COMMENT", "" + data.length());

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject obj = data.getJSONObject(i);
                        int cm_id = obj.getInt("cm_id");
                        String userId = obj.getString("member_id");
                        int userType = obj.getInt("member_type");
                        String message = obj.getString("message");
                        String date = obj.getString("creation_time");

                        Comment comment = new Comment(userId, userType, message, date);
                        Log.d("COMMENT", "user_id: " + userId + "message: " + message);

                        comments.add(comment);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    public void run() {
                        commentList.post(new Runnable() {
                            public void run() {
                                infoListAdaptor.notifyDataSetChanged();
                                loading = true;
                            }
                        });
                        dialog.hide();
                    }
                });
            }
        }.start();
    }

    private class InfoListAdaptor extends ArrayAdapter<Comment> {
        private Context context;
        private ArrayList<Comment> comments;

        public InfoListAdaptor(Context context, int textViewResourceId, ArrayList<Comment> objects) {
            super(context, textViewResourceId, objects);
            this.comments = objects;
            this.context = context;
        }

        @Override
        public int getCount() {
            return comments.size() + 1;
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
            LayoutInflater vi = null;

            if (v == null) {
                vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                if (i == 0) {
                    v = vi.inflate(R.layout.row_meal_org_info, null);
                } else if (i == (comments.size())) {
                    v = vi.inflate(R.layout.row_comment_edit, null);
                } else {
                    v = vi.inflate(R.layout.row_comment, null);
                }
            }
            if (i == 0) {
                TextView org_name = (TextView) v.findViewById(R.id.row_org_name);
                TextView org_address = (TextView) v.findViewById(R.id.row_org_address);
                TextView org_telephone = (TextView) v.findViewById(R.id.row_org_telephone);

                org_name.setText(name);
                org_address.setText(address);
                org_telephone.setText(telephone);
            } else if (i == (comments.size())) {
                et = (EditText) v.findViewById(R.id.comment_edit);

                Button btn = (Button) v.findViewById(R.id.comment_btn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        post();
                        init();
                        updateList();
                    }
                });
            } else {
                Comment comment = comments.get(i - 1);

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
/*
    class EndlessScrollListener implements AbsListView.OnScrollListener {

        public EndlessScrollListener() {
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            if (loading && !end_bool) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                    currentPage++;
                }
            }
            int lastInScreen = firstVisibleItem + visibleItemCount;
            if (!loading && lastInScreen == totalItemCount && !end_bool) {
                updateList();
                loading = true;
            }
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }
    }
*/
    private class Comment {
        private String userId;
        private int userType;
        private String message;
        private String date;

        public Comment(String userId, int userType, String message, String date) {
            this.userId = userId;
            this.userType = userType;
            this.message = message;
            this.date = date;
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