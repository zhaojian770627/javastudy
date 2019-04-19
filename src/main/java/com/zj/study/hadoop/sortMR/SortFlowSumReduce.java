package com.zj.study.hadoop.sortMR;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class SortFlowSumReduce extends Reducer<FlowBean, NullWritable, Text, FlowBean> {
	@Override
	protected void reduce(FlowBean key, Iterable<NullWritable> values, Context context)
			throws IOException, InterruptedException {
		String phoneNB = key.getPhoneNB();
		context.write(new Text(phoneNB), key);
	}
}
