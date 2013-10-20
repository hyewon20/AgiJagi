package net.babybaby.agijagi.notice;

import java.util.ArrayList;

import javax.xml.transform.Source;
import org.json.JSONArray;
import org.json.JSONObject;

import net.babybaby.agijagi.notice.borad.BoardActivity;
import net.babybaby.agijagi.etc.HttpGetRequest;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.babybaby.agijagi.R;


public class NoticeActivity extends Fragment{

    private ArrayList<NoticeModel> lists;
    Handler mHandler = new Handler();
    private Source source;
    View rootView = null;
    ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_notice, container, false);

        lists = new ArrayList<NoticeModel>();

        final ProgressDialog dialog = ProgressDialog.show(this.getActivity(),"","공지사항 내용을 불러오는 중입니다.");
        new Thread() {
            public void run() {
                try{
                    HttpGetRequest hgr = new HttpGetRequest();
                    String url = hgr.getHTML("http://winspec.sshel.com/api/GetNotice");

                    JSONObject response = new JSONObject(url);
                    JSONObject channel = response.getJSONObject("channel");
                    JSONArray data = channel.getJSONArray("item");
                    for(int i = 0; i < data.length(); i++)
                    {
                        JSONObject obj = data.getJSONObject(i);
                        NoticeModel nModel = new NoticeModel();
                        nModel.SetTitle(obj.getString("nt_title"));

                        String lineSep = System.getProperty("line.separator");
                        String yourString= obj.getString("nt_description");
                        yourString = yourString.replaceAll("<br/>", lineSep);

                        nModel.SetDescription(yourString);
                        nModel.SetRegDate(obj.getString("nt_creation_time"));
                        lists.add(nModel);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    public void run() {
                        setListView();
                        dialog.hide();

                    }
                });
            }
        }.start();

        return rootView;
    }

    private void setListView()
    {
        listView = (ListView) rootView.findViewById(R.id.listView1);
        NoticeAdapter b1_adapter = new NoticeAdapter(this.getActivity(), R.layout.row_notice, lists);



        listView.setAdapter(b1_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NoticeModel nModel = lists.get(position);
                Intent intent = new Intent(getActivity(), BoardActivity.class);
                intent.putExtra("title", nModel.getTitle());
                intent.putExtra("date",nModel.getRegDate());
                intent.putExtra("description", nModel.getDescription());
                startActivity(intent);
            }
        });


    }
  /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
  */

    public class NoticeAdapter extends ArrayAdapter<NoticeModel>{

        public ArrayList<NoticeModel> items;
        private Context mcontext;

        public NoticeAdapter(Context context, int textViewResourceId, ArrayList<NoticeModel> items) {
            super(context, textViewResourceId, items);
            mcontext = context;
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.row_notice, null);
            }

            NoticeModel areaInfo = items.get(position);

            if (areaInfo != null) {
                TextView title1 = (TextView) v.findViewById(R.id.notice_title);
                TextView title2 = (TextView) v.findViewById(R.id.notice_date);
                TextView title3 = (TextView) v.findViewById(R.id.notice_description);

                title1.setText(areaInfo.getTitle());
                title2.setText(areaInfo.getRegDate().substring(0, 10));

                if(areaInfo.getDescription().length() > 20){
                    title3.setText(areaInfo.getDescription().substring(0, 20) + "...");
                }else{
                    title3.setText(areaInfo.getDescription());
                }
            }
            return v;
        }
    }
}