package com.perasia.laughinfo.recycleview;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.perasia.laughinfo.R;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseQuickAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected static final String TAG = BaseQuickAdapter.class.getSimpleName();

    private static final int HEADER_VIEW = 2;
    private static final int LOADING_VIEW = 1;
    private static final int FOOTER_VIEW = 3;

    private boolean mNextLoad;
    private boolean mIsLoadingMore;

    protected final Context content;

    protected final int layoutResId;

    protected final List<T> data;

    private ArrayList<View> mHeaderViews = new ArrayList<>();
    private ArrayList<View> mFooterViews = new ArrayList<>();

    private OnRecycleViewItemClickListener itemClickListener;

    private RequestLoadMoreListener loadMoreListener;

    protected abstract void convert(BaseViewHolder helper, T item);

    public interface OnRecycleViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface RequestLoadMoreListener {
        void onLoadMoreRequest();
    }

    public void setOnRecycleViewItemClickListener(OnRecycleViewItemClickListener l) {
        this.itemClickListener = l;
    }

    public void setOnLoadMoreListener(int pageSize, RequestLoadMoreListener l) {
        if (getItemCount() < pageSize) {
            return;
        }

        mNextLoad = true;
        this.loadMoreListener = l;
    }


    public BaseQuickAdapter(Context content, int layoutResId) {
        this(content, layoutResId, null);
    }

    public BaseQuickAdapter(Context content, int layoutResId, List<T> data) {
        this.data = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
        this.content = content;
        this.layoutResId = layoutResId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = null;
        if (viewType == LOADING_VIEW) {
            item = LayoutInflater.from(parent.getContext()).inflate(R.layout.def_loading, parent, false);
            return new FooterViewHolder(item);
        } else if (viewType == HEADER_VIEW) {
            return new HeadViewHolder(mHeaderViews.get(0));
        } else if (viewType == FOOTER_VIEW) {
            return new HeadViewHolder(mFooterViews.get(0));
        } else {
            item = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
            return new BaseViewHolder(content, item);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        BaseViewHolder baseViewHolder = (BaseViewHolder) holder;
        int type = getItemViewType(position);
        int index;
        if (type == 0) {
            index = position - getHeaderViewCount();
            convert(baseViewHolder, data.get(index));
            if (itemClickListener != null) {
                baseViewHolder.getView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClickListener.onItemClick(v, position - getHeaderViewCount());
                    }
                });
            }
        } else if (type == LOADING_VIEW) {
            if (mNextLoad && !mIsLoadingMore && loadMoreListener != null) {
                mIsLoadingMore = true;
                loadMoreListener.onLoadMoreRequest();
                if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
                    StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                    params.setFullSpan(true);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        int i = mNextLoad ? 1 : 0;
        return data.size() + i + getHeaderViewCount() + getFooterViewCount();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getHeaderViewCount()) {
            return HEADER_VIEW;
        } else if (position == data.size() + getHeaderViewCount()) {
            if (mNextLoad) {
                return LOADING_VIEW;
            } else {
                return FOOTER_VIEW;
            }
        }

        return super.getItemViewType(position);
    }

    public void remove(int position) {
        if (position < 0 || position > data.size() - 1) {
            return;
        }

        data.remove(position);
        notifyItemRemoved(position);
    }

    public void add(int position, T item) {
        data.add(position, item);
        notifyItemInserted(position);
    }

    public List getData() {
        return data;
    }

    public int getHeaderViewCount() {
        return mHeaderViews.size();
    }

    public int getFooterViewCount() {
        return mFooterViews.size();
    }

    public void addHeadView(View header) {
        if (header == null) {
            throw new RuntimeException("header is null");
        }
        if (mHeaderViews.size() == 0) {
            mHeaderViews.add(header);
        }

        this.notifyDataSetChanged();
    }

    public void addFooterView(View header) {
        mNextLoad = false;
        if (header == null) {
            throw new RuntimeException("header is null");
        }
        if (mFooterViews.size() == 0)
            mFooterViews.add(header);
        this.notifyDataSetChanged();
    }

    public void isNextLoad(boolean isNextLoad) {
        mNextLoad = isNextLoad;
        mIsLoadingMore = false;
        notifyDataSetChanged();
    }


    public class FooterViewHolder extends BaseViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView.getContext(), itemView);
        }
    }

    public class HeadViewHolder extends BaseViewHolder {

        public HeadViewHolder(View itemView) {
            super(itemView.getContext(), itemView);
        }
    }

}
