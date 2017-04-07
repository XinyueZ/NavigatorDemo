package com.demo.navigator.navigation.ui;

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

import com.demo.navigator.R;
import com.demo.navigator.app.App;
import com.demo.navigator.bus.EntryClickEvent;
import com.demo.navigator.databinding.FragmentEntryBinding;
import com.demo.navigator.databinding.ItemExternalLinkBinding;
import com.demo.navigator.databinding.ItemLinkBinding;
import com.demo.navigator.databinding.ItemNodeBinding;
import com.demo.navigator.databinding.ItemSectionBinding;
import com.demo.navigator.ds.model.Entry;
import com.demo.navigator.utils.CustomTabUtils;
import com.demo.navigator.utils.ListDivider;

import java.util.List;

import de.greenrobot.event.EventBus;

import static android.support.v7.widget.RecyclerView.NO_POSITION;


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

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		CustomTabUtils.unbind(App.Instance);
	}

	private void setupList() {
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
		mBinding.entryContentRv.setLayoutManager(linearLayoutManager);
		mBinding.entryContentRv.setHasFixedSize(true);
		mBinding.entryContentRv.addItemDecoration(new ListDivider(getContext()));
	}

	private void showEntries(@NonNull List<Entry> entries) {
		mBinding.entryContentRv.setAdapter(new EntryListAdapter(entries));
	}

	private void showEntry(@NonNull Entry entry) {
		showEntries(entry.getChildren());
	}

	private final static class EntryListAdapter extends RecyclerView.Adapter<EntryViewHolder> {
		private static final int ITEM_LAYOUT_SECTION = R.layout.item_section;
		private static final int ITEM_LAYOUT_NODE = R.layout.item_node;
		private static final int ITEM_LAYOUT_LINK = R.layout.item_link;
		private static final int ITEM_LAYOUT_EXTERNAL_LINK = R.layout.item_external_link;
		private static final int ITEM_TYPE_SECTION = 0x91;
		private static final int ITEM_TYPE_NODE = 0x92;
		private static final int ITEM_TYPE_LINK = 0x93;
		private static final int ITEM_TYPE_EXTERNAL_LINK = 0x94;
		private final @NonNull List<Entry> mEntries;
		private int mSelectedPosition = NO_POSITION;

		public EntryListAdapter(@NonNull List<Entry> entries) {
			mEntries = entries;
		}

		@Override
		public EntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			Context cxt = parent.getContext();
			switch (viewType) {
				case ITEM_TYPE_SECTION:
					ItemSectionBinding sectionBinding = DataBindingUtil.bind(LayoutInflater.from(cxt)
					                                                                       .inflate(ITEM_LAYOUT_SECTION, parent, false));
					return new SectionViewHolder(sectionBinding, mEntries, this);
				case ITEM_TYPE_NODE:
					ItemNodeBinding nodeBinding = DataBindingUtil.bind(LayoutInflater.from(cxt)
					                                                                 .inflate(ITEM_LAYOUT_NODE, parent, false));
					return new NodeViewHolder(nodeBinding, mEntries, this);
				case ITEM_TYPE_LINK:
					ItemLinkBinding linkBinding = DataBindingUtil.bind(LayoutInflater.from(cxt)
					                                                                 .inflate(ITEM_LAYOUT_LINK, parent, false));
					return new LinkViewHolder(linkBinding, mEntries, this);
				default:
					ItemExternalLinkBinding externalLinkBinding = DataBindingUtil.bind(LayoutInflater.from(cxt)
					                                                                                 .inflate(ITEM_LAYOUT_EXTERNAL_LINK, parent, false));
					return new ExternalLinkViewHolder(externalLinkBinding, mEntries, this);
			}
		}

		@Override
		public void onBindViewHolder(EntryViewHolder holder, int position) {
			if(holder == null) return;
			Entry entry = mEntries.get(position);
			switch (getItemViewType(position)) {
				case ITEM_TYPE_SECTION:
					SectionViewHolder sectionViewHolder = (SectionViewHolder) holder;
					sectionViewHolder.mItemSectionBinding.setEntry(entry);
					sectionViewHolder.mItemSectionBinding.setViewholder(sectionViewHolder);
					break;
				case ITEM_TYPE_NODE:
					NodeViewHolder nodeViewHolder = (NodeViewHolder) holder;
					nodeViewHolder.mItemNodeBinding.setEntry(entry);
					nodeViewHolder.mItemNodeBinding.setViewholder(nodeViewHolder);
					break;
				case ITEM_TYPE_LINK:
					LinkViewHolder linkViewHolder = (LinkViewHolder) holder;
					linkViewHolder.mItemLinkBinding.setEntry(entry);
					linkViewHolder.mItemLinkBinding.setViewholder(linkViewHolder);
					linkViewHolder.mItemLinkBinding.setIsSelected(mSelectedPosition == position);
					break;
				case ITEM_TYPE_EXTERNAL_LINK:
					ExternalLinkViewHolder externalLinkViewHolder = (ExternalLinkViewHolder) holder;
					externalLinkViewHolder.mItemExternalLinkBinding.setEntry(entry);
					externalLinkViewHolder.mItemExternalLinkBinding.setViewholder(externalLinkViewHolder);
					CustomTabUtils.bind(App.Instance,
					                    mEntries.get(position)
					                            .getUrl());
					break;
			}
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
					return ITEM_TYPE_LINK;
				case "external-link":
					return ITEM_TYPE_EXTERNAL_LINK;
			}
			return super.getItemViewType(position);
		}
	}

	public abstract static class EntryViewHolder extends RecyclerView.ViewHolder {
		private final @NonNull ViewDataBinding mBinding;
		private final EntryClickEvent mEntryClickedEvent = new EntryClickEvent();
		private final @NonNull List<Entry> mEntries;
		private final @NonNull EntryListAdapter mEntryListAdapter;

		private EntryViewHolder(@NonNull ViewDataBinding binding, @NonNull List<Entry> entries, @NonNull EntryListAdapter adapter) {
			super(binding.getRoot());
			mEntries = entries;
			mBinding = binding;
			mEntryListAdapter = adapter;
		}

		public void onEntryClicked() {
			if (getAdapterPosition() == NO_POSITION) {
				return;
			}
			if (mEntryListAdapter.mSelectedPosition != NO_POSITION) {
				mEntryListAdapter.notifyItemChanged(mEntryListAdapter.mSelectedPosition);
			}
			mEntryListAdapter.mSelectedPosition = getAdapterPosition();
			mEntryClickedEvent.setEntry(mEntries.get(mEntryListAdapter.mSelectedPosition));
			mEntryListAdapter.notifyItemChanged(mEntryListAdapter.mSelectedPosition);
			EventBus.getDefault()
			        .post(mEntryClickedEvent);
		}
	}

	private final static class SectionViewHolder extends EntryViewHolder {
		private final @NonNull ItemSectionBinding mItemSectionBinding;

		private SectionViewHolder(@NonNull ItemSectionBinding binding, @NonNull List<Entry> entries, @NonNull EntryListAdapter adapter) {
			super(binding, entries, adapter);
			mItemSectionBinding = binding;
		}
	}

	private final static class NodeViewHolder extends EntryViewHolder {
		private final @NonNull ItemNodeBinding mItemNodeBinding;

		private NodeViewHolder(@NonNull ItemNodeBinding binding, @NonNull List<Entry> entries, @NonNull EntryListAdapter adapter) {
			super(binding, entries, adapter);
			mItemNodeBinding = binding;
		}
	}

	private final static class LinkViewHolder extends EntryViewHolder {
		private final @NonNull ItemLinkBinding mItemLinkBinding;

		private LinkViewHolder(@NonNull ItemLinkBinding binding, @NonNull List<Entry> entries, @NonNull EntryListAdapter adapter) {
			super(binding, entries, adapter);
			mItemLinkBinding = binding;
		}
	}

	private final static class ExternalLinkViewHolder extends EntryViewHolder {
		private final @NonNull ItemExternalLinkBinding mItemExternalLinkBinding;

		private ExternalLinkViewHolder(@NonNull ItemExternalLinkBinding binding, @NonNull List<Entry> entries, @NonNull EntryListAdapter adapter) {
			super(binding, entries, adapter);
			mItemExternalLinkBinding = binding;
		}
	}
}
