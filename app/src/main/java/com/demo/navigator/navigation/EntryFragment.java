package com.demo.navigator.navigation;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.navigator.BR;
import com.demo.navigator.utils.ListDivider;
import com.demo.navigator.R;
import com.demo.navigator.bus.EntryClickEvent;
import com.demo.navigator.databinding.FragmentEntryBinding;
import com.demo.navigator.ds.Entry;

import java.util.List;

import de.greenrobot.event.EventBus;


public final class EntryFragment extends Fragment {
	private static final String EXTRAS_ENTRY = EntryFragment.class.getName() + ".EXTRAS.entry";
	private static final int LAYOUT = R.layout.fragment_entry;
	private FragmentEntryBinding mBinding;

	public static EntryFragment newInstance(@NonNull Context cxt, @NonNull Entry entry) {
		Bundle args = new Bundle(1);
		args.putSerializable(EXTRAS_ENTRY, entry);
		return (EntryFragment) EntryFragment.instantiate(cxt, EntryFragment.class.getName(), args);
	}


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mBinding = DataBindingUtil.inflate(inflater, LAYOUT, container, false);
		return mBinding.getRoot();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Entry entry = (Entry) getArguments().getSerializable(EXTRAS_ENTRY);
		if (entry == null) {
			return;
		}
		setupList();
		showEntry(entry);
	}

	private void setupList() {
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
		mBinding.entryContentRv.setLayoutManager(linearLayoutManager);
		mBinding.entryContentRv.setHasFixedSize(true);
		mBinding.entryContentRv.addItemDecoration(new ListDivider(getContext()));
	}

	public void showEntries(@NonNull List<Entry> entries) {
		mBinding.entryContentRv.setAdapter(new EntryListAdapter(entries));
	}

	public void showEntry(@NonNull Entry entry) {
		showEntries(entry.getChildren());
	}

	private final static class EntryListAdapter extends RecyclerView.Adapter<EntryViewHolder> {
		private static final int ITEM_LAYOUT_SECTION = R.layout.item_section;
		private static final int ITEM_LAYOUT_NODE = R.layout.item_node;
		private static final int ITEM_LAYOUT_LINK = R.layout.item_link;
		private static final int ITEM_TYPE_SECTION = 0;
		private static final int ITEM_TYPE_NODE = 1;
		private static final int ITEM_TYPE_LINK = 2;
		private final @NonNull List<Entry> mEntries;

		public EntryListAdapter(@NonNull List<Entry> entries) {
			mEntries = entries;
		}

		@Override
		public EntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			Context cxt = parent.getContext();
			ViewDataBinding binding;
			switch (viewType) {
				case ITEM_TYPE_SECTION:
					binding = DataBindingUtil.bind(LayoutInflater.from(cxt)
					                                             .inflate(ITEM_LAYOUT_SECTION, parent, false));
					break;
				case ITEM_TYPE_NODE:
					binding = DataBindingUtil.bind(LayoutInflater.from(cxt)
					                                             .inflate(ITEM_LAYOUT_NODE, parent, false));
					break;
				default:
					binding = DataBindingUtil.bind(LayoutInflater.from(cxt)
					                                             .inflate(ITEM_LAYOUT_LINK, parent, false));
					break;
			}
			return new EntryViewHolder(binding, mEntries);
		}

		@Override
		public void onBindViewHolder(EntryViewHolder holder, int position) {
			holder.mBinding.setVariable(BR.entry, mEntries.get(position));
			holder.mBinding.setVariable(BR.viewholder, holder);
			holder.mBinding.executePendingBindings();
		}

		@Override
		public int getItemCount() {
			return mEntries.size();
		}

		@Override
		public int getItemViewType(int position) {
			Entry entry = mEntries.get(position);
			switch (entry.getType()) {
				case "section":
					return ITEM_TYPE_SECTION;
				case "node":
					return ITEM_TYPE_NODE;
				case "link":
				case "external-link":
					return ITEM_TYPE_LINK;
			}
			return super.getItemViewType(position);
		}
	}

	public final static class EntryViewHolder extends RecyclerView.ViewHolder {
		private ViewDataBinding mBinding;
		private final EntryClickEvent mEntryClickedEvent = new EntryClickEvent();
		private final @NonNull List<Entry> mEntries;

		private EntryViewHolder(ViewDataBinding binding, @NonNull List<Entry> entries) {
			super(binding.getRoot());
			mEntries = entries;
			mBinding = binding;
		}

		public void onEntryClicked() {
			int pos = getAdapterPosition();
			mEntryClickedEvent.setEntry(mEntries.get(pos));
			EventBus.getDefault()
			        .post(mEntryClickedEvent);
		}
	}
}
