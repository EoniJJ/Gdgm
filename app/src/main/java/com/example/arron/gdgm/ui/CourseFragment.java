package com.example.arron.gdgm.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.arron.gdgm.R;
import com.example.arron.gdgm.base.BaseFragment;
import com.example.arron.gdgm.contract.CourseContract;
import com.example.arron.gdgm.model.CourseModel;
import com.example.arron.gdgm.model.FilterMenuModel;
import com.example.arron.gdgm.presenter.CoursePresenter;
import com.example.arron.gdgm.utils.ConvertUtils;

import java.util.List;

/**
 * Created by Arron on 2017/4/17.
 */

public class CourseFragment extends BaseFragment implements CourseContract.View {
    private CourseContract.Presenter presenter;
    private LinearLayout llCourseAndSectionGroup;
    private RelativeLayout rlCourse;
    private LinearLayout llCourseHeader;
    private AlertDialog.Builder chooseDialogBuilder;
    private Dialog chooseDialog;


    private static final int ONE_DAY_SECTION_NUM = 13;//一天共有课程节数
    private int courseHeaderItemWidth;
    private int courseItemHeight;

    private int[] courseColors = {R.color.courseBackground1, R.color.courseBackground2,
            R.color.courseBackground3, R.color.courseBackground4, R.color.courseBackground5,
            R.color.courseBackground6, R.color.courseBackground7};
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CoursePresenter();
        courseItemHeight = getResources().getDimensionPixelSize(R.dimen.courseItemHeight);
        chooseDialogBuilder = new AlertDialog.Builder(getContext());
    }

    @Override
    protected void setMoreButton(TextView tvMore) {
        super.setMoreButton(tvMore);
        tvMore.setText(getString(R.string.setting));
        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chooseDialog == null) {
                    chooseDialog = chooseDialogBuilder.show();
                } else {
                    chooseDialog.show();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        presenter.attachView(this);
        return view;
    }


    @Override
    protected String getTitleName() {
        return getString(R.string.course);
    }

    @Override
    protected int resourceViewId() {
        return R.layout.fragment_course;
    }

    @Override
    protected void initView(View view) {
        back.setVisibility(View.GONE);
        llCourseAndSectionGroup = (LinearLayout)view.findViewById(R.id.ll_course);
        llCourseHeader = (LinearLayout)view.findViewById(R.id.ll_course_header);
        initSection();
    }

    private void initSection() {
        if (llCourseHeader.getChildCount()> 0) {
            courseHeaderItemWidth = llCourseHeader.getChildAt(0).getWidth();
        }
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundResource(R.color.courseHeaderBackground);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(courseHeaderItemWidth, LinearLayout.LayoutParams.MATCH_PARENT));
        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                courseItemHeight);
        for (int i = 1; i <= ONE_DAY_SECTION_NUM; i++) {
            TextView tvSection = new TextView(getContext());
            tvSection.setText(i + "");
            tvSection.setGravity(Gravity.CENTER);
            tvSection.setTextColor(getResources().getColor(R.color.courseTextColor));
            linearLayout.addView(tvSection, itemParams);
        }
        llCourseAndSectionGroup.addView(linearLayout);
        rlCourse = new RelativeLayout(getContext());
        rlCourse.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        rlCourse.setBackgroundResource(R.color.courseCanvasBackground);
        llCourseAndSectionGroup.addView(rlCourse);

    }

    @Override
    protected void initData() {
        showLoadingDialog(getString(R.string.loading));
        initSection();
        presenter.getCourses();
    }

    @Override
    public void showCourses(List<CourseModel> courseModelList) {
        hideLoadingDialog();
        rlCourse.removeAllViews();
        if (courseModelList.isEmpty()) {
            return;
        }
        SparseArray<RelativeLayout> relativeLayoutSparseArray = new SparseArray<>();
        TextView courseTextView;
        int random = 0;
        for (CourseModel courseModel : courseModelList) {
            int position = CourseModel.Day.indexOf(courseModel.getDay());
            if (position == -1) {
                continue;
            }
            RelativeLayout relativeLayout = relativeLayoutSparseArray.get(position);
            if (relativeLayout == null) {
                relativeLayout = new RelativeLayout(getContext());
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(courseHeaderItemWidth, RelativeLayout.LayoutParams.MATCH_PARENT);
                params.setMargins(position * courseHeaderItemWidth, 0, 0, 0);
                relativeLayout.setLayoutParams(params);
                relativeLayoutSparseArray.put(position, relativeLayout);
            }
            courseTextView = new TextView(getContext());
            courseTextView.setGravity(Gravity.CENTER);
            courseTextView.setBackgroundResource(R.drawable.bg_course_item);
            courseTextView.setTextSize(12);
            courseTextView.setTextColor(getResources().getColor(R.color.courseTextColor));
            courseTextView.setText(courseModel.getCourseName() + "\r\n" + courseModel.getWeeks() + "\r\n" +
                    courseModel.getCourseTeacher() + "\r\n" + courseModel.getClassroom());
            courseTextView.setTag(courseModel);
            courseTextView.setOnClickListener(courseClickListener);
            if (courseTextView.getBackground().mutate() instanceof GradientDrawable) {
                ((GradientDrawable) courseTextView.getBackground().mutate()).setColor(getResources().getColor(courseColors[random++]));
                if (random >= courseColors.length) {
                    random = 0;
                }
            }
            int marginBottom = ConvertUtils.dp2px(2);
            int height = (courseModel.getLastSection() - courseModel.getFirstSection() + 1) * courseItemHeight - marginBottom;
            int marginTop = (courseModel.getFirstSection() - 1) * courseItemHeight;
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(courseHeaderItemWidth, height);
            layoutParams.setMargins(0, marginTop, 0, marginBottom);
            relativeLayout.addView(courseTextView, layoutParams);
        }
        for (int i = 0; i < relativeLayoutSparseArray.size(); i++) {
            if (relativeLayoutSparseArray.get(i) != null) {
                rlCourse.addView(relativeLayoutSparseArray.get(i));
            }
        }
    }

    @Override
    public void showFilterMenu(FilterMenuModel filterMenuModel) {
        if (filterMenuModel.getYear().size() > 0 && filterMenuModel.getSemester().size() > 0) {
            presenter.getCourses(filterMenuModel.getYear().get(filterMenuModel.getYear().size() - 1),
                    filterMenuModel.getSemester().get(filterMenuModel.getSemester().size() - 1));
        }
        View view = View.inflate(getContext(), R.layout.dialog_filter, null);
        TextView tvFirst = (TextView) view.findViewById(R.id.tv_first);
        tvFirst.setText(getString(R.string.year));
        final Spinner spFirst = (Spinner) view.findViewById(R.id.sp_first);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, filterMenuModel.getYear());
        spFirst.setAdapter(yearAdapter);
        TextView tvSecond = (TextView) view.findViewById(R.id.tv_second);
        tvSecond.setText(getString(R.string.semester));
        final Spinner spSecond = (Spinner) view.findViewById(R.id.sp_second);
        ArrayAdapter<String> semesterAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, filterMenuModel.getSemester());
        spSecond.setAdapter(semesterAdapter);
        chooseDialogBuilder.setView(view);
        chooseDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                String year = (String) spFirst.getSelectedItem();
                String semester = (String) spSecond.getSelectedItem();
                showLoadingDialog(getString(R.string.loading));
                presenter.getCourses(year,semester);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }


    private View.OnClickListener courseClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CourseModel courseModel = (CourseModel) v.getTag();
            CourseDetailActivity.start(CourseFragment.this.getContext(), courseModel);
        }
    };

}
