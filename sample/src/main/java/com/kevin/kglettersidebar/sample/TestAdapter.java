package com.kevin.kglettersidebar.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.kevin.kglettersidebar.sample.model.Person;
import com.kevin.kglettersidebar.util.LanguageUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kevin on 16/2/17.
 */
public class TestAdapter extends BaseAdapter implements SectionIndexer {

    private List<Person> mPersons = new ArrayList<Person>();
    private List<Object> mGroups = new ArrayList<Object>();
    private List<Object> mSections = new ArrayList<Object>();
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private char letter, temp = 0;

    public TestAdapter(Context context, List<Person> persons) {
        mContext = context;
        mPersons = persons;
        mLayoutInflater = LayoutInflater.from(mContext);

        initList();
    }

    public List<Object> getmSections() {
        return mSections;
    }

    private void initList() {
        if (mPersons.size() > 0) {

            Collections.sort(mPersons, new Comparator<Person>() {
                @Override
                public int compare(Person lhs, Person rhs) {
                    return LanguageUtil.getPinYin(lhs.getName()).compareTo(LanguageUtil.getPinYin(rhs.getName()));
                }
            });

            for (int i = 0; i < mPersons.size(); i++) {
                Person person = mPersons.get(i);
                letter = LanguageUtil.getPinYin(person.getName(), false).charAt(0);
                if (letter != temp) {
                    String l = Character.toString(letter);
                    mSections.add(l);
                    mGroups.add(l);
                    temp = letter;
                }
                mGroups.add(mPersons.get(i));
            }
        }
    }

    @Override
    public int getCount() {
        return mGroups.size();
    }

    @Override
    public Object getItem(int position) {
        return mGroups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object object = mGroups.get(position);

        if (object instanceof String) {
            TitleViewHolder titleViewHolder;
            if (convertView == null || convertView.getTag(R.layout.item_title) == null) {
                convertView = mLayoutInflater.inflate(R.layout.item_title, null);
                titleViewHolder = new TitleViewHolder(convertView);
                convertView.setTag(R.id.tag_title_key, titleViewHolder);
            } else {
                titleViewHolder = (TitleViewHolder) convertView.getTag(R.id.tag_title_key);
            }
            titleViewHolder.title.setText(String.valueOf(mGroups.get(position)));
        } else {
            ItemViewHolder itemViewHolder;
            if (convertView == null || convertView.getTag(R.layout.item_list) == null) {
                convertView = mLayoutInflater.inflate(R.layout.item_list, null);
                itemViewHolder = new ItemViewHolder(convertView);
                convertView.setTag(R.id.tag_item_key, itemViewHolder);
            } else {
                itemViewHolder = (ItemViewHolder) convertView.getTag(R.id.tag_item_key);
            }
            itemViewHolder.text.setText(((Person)object).getName());
        }

        return convertView;
    }

    @Override
    public Object[] getSections() {
        return mSections.toArray();
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        Object item = mSections.get(sectionIndex);
        for (int i = 0; i < mGroups.size(); i++) {
            if (item == mGroups.get(i)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    static class ItemViewHolder {
        @Bind(R.id.text)
        TextView text;

        ItemViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class TitleViewHolder {
        @Bind(R.id.title)
        TextView title;

        TitleViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
