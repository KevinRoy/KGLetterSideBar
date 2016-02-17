package com.kevin.kglettersidebar.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.kevin.kglettersidebar.KGLetterSideBar;
import com.kevin.kglettersidebar.sample.model.Person;
import com.kevin.kglettersidebar.util.LanguageUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.sidebar)
    KGLetterSideBar sidebar;
    @Bind(R.id.textdialog)
    TextView dialog;
    @Bind(R.id.list)
    ListView listview;

    private TestAdapter testAdapter;
    private List<Person> persons = new ArrayList<Person>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sidebar.setTextView(dialog);
        sidebar.setOnTouchingLetterChangedListener(new KGLetterSideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                List<Object> sections = testAdapter.getmSections();
                if (sections.indexOf(s) > 0) {
                    int position = testAdapter.getPositionForSection(sections.indexOf(s));
                    if (position != -1) {
                        listview.setSelection(position);
                    }
                } else if (s.equals("#")) {
                    listview.setSelection(0);
                }
            }
        });

        persons.add(new Person("罗小", 19));
        persons.add(new Person("铮哥", 20));
        persons.add(new Person("李小鹏", 21));
        persons.add(new Person("二货", 20));
        persons.add(new Person("三货", 20));
        persons.add(new Person("罗三", 20));
        persons.add(new Person("胡汉三", 20));
        persons.add(new Person("潘冬子", 20));
        persons.add(new Person("辉哥", 20));
        persons.add(new Person("七爷", 20));
        persons.add(new Person("有飞", 20));
        persons.add(new Person("楚留香", 20));
        persons.add(new Person("丁大哥", 20));
        persons.add(new Person("占中", 20));

        testAdapter = new TestAdapter(this, persons);
        listview.setAdapter(testAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
