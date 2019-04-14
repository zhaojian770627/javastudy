package com.zj.study.hadoop.flowsum;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class FlowSumReduce extends Reducer<Text, FlowBean, Text, FlowBean> {
	@Override
	protected void reduce(Text key, Iterable<FlowBean> values, Context context)
			throws IOException, InterruptedException {
		long up_flow_counter = 0;
		long d_flow_counter = 0;

		for (FlowBean bean : values) {
			up_flow_counter += bean.getUp_flow();
			d_flow_counter += bean.getD_flow();
		}

		context.write(key, new FlowBean(key.toString(), up_flow_counter, d_flow_counter));
	}
}
