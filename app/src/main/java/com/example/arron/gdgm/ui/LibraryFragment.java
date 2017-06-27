package com.example.arron.gdgm.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.arron.gdgm.R;
import com.example.arron.gdgm.adapter.BooksAdapter;
import com.example.arron.gdgm.base.BaseFragment;
import com.example.arron.gdgm.contract.LibraryContract;
import com.example.arron.gdgm.model.BookModel;
import com.example.arron.gdgm.presenter.LibraryPresenter;

import java.util.List;
import java.util.Map;

/**
 * Created by Arron on 2017/4/17.
 */

public class LibraryFragment extends BaseFragment implements LibraryContract.View, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    private Spinner spLibrary;
    private EditText etLibrary;
    private TextView btnSearch;
    private SwipeRefreshLayout slLibrary;
    private RecyclerView rvLibrary;
    private LibrarySearchItemsAdapter librarySearchItemsAdapter;
    private LibraryContract.Presenter presenter;
    private BooksAdapter booksAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        librarySearchItemsAdapter = new LibrarySearchItemsAdapter();
        presenter = new LibraryPresenter();
        booksAdapter = new BooksAdapter();
        booksAdapter.setOnLoadMoreListener(this);
        booksAdapter.setOnItemClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        presenter.attachView(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    protected String getTitleName() {
        return getResources().getString(R.string.library);
    }

    @Override
    protected int resourceViewId() {
        return R.layout.fragment_library;
    }

    @Override
    protected void initView(View view) {
        back.setVisibility(View.GONE);
        spLibrary = (Spinner) view.findViewById(R.id.sp_library);
        etLibrary = (EditText) view.findViewById(R.id.et_library);
        btnSearch = (TextView) view.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(this);
        slLibrary = (SwipeRefreshLayout) view.findViewById(R.id.sl_library);
        slLibrary.setOnRefreshListener(this);
        rvLibrary = (RecyclerView) view.findViewById(R.id.rv_library);
        rvLibrary.setLayoutManager(new LinearLayoutManager(getContext()));
        rvLibrary.setAdapter(booksAdapter);
    }

    @Override
    protected void initData() {
        showLoadingDialog(getString(R.string.loading));
        presenter.getSearchItems();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btn_search) {
            Map.Entry<String,String> selectItem = (Map.Entry<String, String>) spLibrary.getSelectedItem();
            slLibrary.setRefreshing(true);
            presenter.searchBooks(selectItem.getKey(), etLibrary.getText().toString().trim());
        }
    }

    @Override
    public void onRefresh() {
        Map.Entry<String,String> selectItem = (Map.Entry<String, String>) spLibrary.getSelectedItem();
        presenter.searchBooks(selectItem.getKey(), etLibrary.getText().toString().trim());
    }

    @Override
    public void showSearchItems(Map<String, String> searchItems) {
        hideLoadingDialog();
        librarySearchItemsAdapter.setSearchItems(searchItems);
        spLibrary.setAdapter(librarySearchItemsAdapter);
    }

    @Override
    public void showBooksList(List<BookModel> bookModels) {
        slLibrary.setRefreshing(false);
        booksAdapter.setNewData(bookModels);
    }

    @Override
    public void addBooks(List<BookModel> bookModels) {
        if (bookModels == null) {
            booksAdapter.loadMoreEnd();
            return;
        }
        booksAdapter.loadMoreComplete();
        booksAdapter.addData(bookModels);
    }

    @Override
    public void onLoadMoreRequested() {
        if (slLibrary.isRefreshing()) {
            return;
        }
        presenter.nextPage();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        BookModel bookModel = booksAdapter.getData().get(position);
        BookDetailActivity.start(getContext(), bookModel.getUrlDetail());
    }

    static class LibrarySearchItemsAdapter extends BaseAdapter {

        private Map<String, String> searchItems;

        public Map<String, String> getSearchItems() {
            return searchItems;
        }

        public void setSearchItems(Map<String, String> searchItems) {
            this.searchItems = searchItems;
        }

        @Override
        public int getCount() {
            return searchItems == null ? 0 : searchItems.entrySet().size();
        }

        @Override
        public Object getItem(int position) {
            return searchItems.entrySet().toArray()[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Object [] o = searchItems.keySet().toArray();
            TextView view = (TextView) View.inflate(parent.getContext(), android.R.layout.simple_spinner_dropdown_item, null);
            view.setText(searchItems.get(o[position]));
            return view;
        }
    }


}
