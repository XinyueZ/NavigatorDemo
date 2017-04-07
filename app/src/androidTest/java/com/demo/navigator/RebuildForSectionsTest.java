package com.demo.navigator;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.demo.navigator.ds.model.Entry;
import com.demo.navigator.utils.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class RebuildForSectionsTest {
	@Test
	public void testPreConditions() {
		Entry entry1 = new Entry("link", "1 Entry", "http://g.cn", new ArrayList<Entry>());

		Entry entry2 = new Entry("node", "2 Entry", "http://g.cn", new ArrayList<Entry>());
		Entry entry3 = new Entry("link", "3 Entry", "http://g.cn", new ArrayList<Entry>());
		Entry entry4 = new Entry("link", "4 Entry", "http://g.cn", new ArrayList<Entry>());
		entry2.getChildren().add(entry3);
		entry2.getChildren().add(entry4);

		Entry entry5 = new Entry("section", "5 Entry", "http://g.cn", new ArrayList<Entry>());
		Entry entry6 = new Entry("link", "6 Entry", "http://g.cn", new ArrayList<Entry>());
		Entry entry7 = new Entry("link", "7 Entry", "http://g.cn", new ArrayList<Entry>());
		Entry entry8 = new Entry("link", "8 Entry", "http://g.cn", new ArrayList<Entry>());

		Entry entry9 = new Entry("node", "9 Entry", "http://g.cn", new ArrayList<Entry>());
		Entry entry10 = new Entry("link", "10 Entry", "http://g.cn", new ArrayList<Entry>());
		Entry entry11 = new Entry("link", "11 Entry", "http://g.cn", new ArrayList<Entry>());
		Entry entry12 = new Entry("link", "12 Entry", "http://g.cn", new ArrayList<Entry>());
		entry9.getChildren().add(entry10);
		entry9.getChildren().add(entry11);
		entry9.getChildren().add(entry12);

		entry5.getChildren().add(entry6);
		entry5.getChildren().add(entry7);
		entry5.getChildren().add(entry8);
		entry5.getChildren().add(entry9);

		List<Entry> testCase = new ArrayList<>();
		testCase.add(entry1);
		testCase.add(entry2);
		testCase.add(entry5);

		final List<Entry> rebuiltList = Utils.rebuildForSections(testCase);
		assertNotNull(rebuiltList);
		assertEquals(rebuiltList.size(), 7);

		assertEquals(rebuiltList.get(2).getType(), "section");
		assertEquals(rebuiltList.get(3).getType(), "link");
		assertEquals(rebuiltList.get(4).getType(), "link");
		assertEquals(rebuiltList.get(5).getType(), "link");
		assertEquals(rebuiltList.get(6).getType(), "node");
	}
}
