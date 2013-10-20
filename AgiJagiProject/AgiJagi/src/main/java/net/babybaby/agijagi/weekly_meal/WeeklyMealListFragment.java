package net.babybaby.agijagi.weekly_meal;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import net.babybaby.agijagi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Namyun
 * @since 13. 10. 19.
 */
public class WeeklyMealListFragment extends Fragment {

    private View rootView;

    private ArrayList<MealData> data;
    private ArrayList<ListItem> convertData;

    public WeeklyMealListFragment(ArrayList<MealData> data)
    {
        this.data=data;
        convertData=new ArrayList<ListItem>();

        for(MealData element : data)
        {
            String header=null;

            switch (element.getPart())
            {
                case 0:
                    header="아침";
                    break;
                case 1:
                    header="점심";
                    break;
                case 2:
                    header="간식";
                    break;
                case 3:
                    header="저녁";
                    break;
            }

            ListItem item=new ListItem(0,header);
            convertData.add(item);

            for(String recipe : element.getMealList())
            {
                ListItem temp=new ListItem(1,recipe);
                convertData.add(temp);
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_weekly_list, container, false);

        ListView listView=(ListView)rootView.findViewById(R.id.meal_list);
        listView.setAdapter(new MealListAdapter(rootView.getContext(),android.R.layout.simple_list_item_1,convertData));

        return rootView;
    }

    private class MealListAdapter extends ArrayAdapter<ListItem>
    {
        private ArrayList<ListItem> objects;

        public MealListAdapter(Context context, int textViewResourceId, ArrayList<ListItem> objects) {
            super(context, textViewResourceId, objects);
            this.objects=objects;
        }

        @Override
        public int getCount() {
            return objects.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            ListItem item=objects.get(position);

            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                if(item.getType()==1)
                {
                    v=vi.inflate(R.layout.row_meal_item,null);
                }
                else
                {
                    v=vi.inflate(R.layout.row_meal_header,null);
                }
            }

            if (item != null) {
                if(item.getType()==1)
                {
                    TextView item_text=(TextView)v.findViewById(R.id.row_recipe_name);
                    item_text.setText(item.getContent());
                }
                else
                {
                    TextView item_text=(TextView)v.findViewById(R.id.row_header_text);
                    item_text.setText(item.getContent());
                }
            }

            return v;
        }
    }

    private class ListItem
    {
        private int type=0;
        private String content;

        public ListItem(int type, String content)
        {
            this.type=type;
            this.content=content;
        }

        public int getType()
        {
            return type;
        }

        public String getContent()
        {
            return content;
        }
    }
}