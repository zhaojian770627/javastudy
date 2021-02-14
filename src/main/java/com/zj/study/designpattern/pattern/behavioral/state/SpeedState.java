package com.zj.study.designpattern.pattern.behavioral.state;

public class SpeedState extends CourseVideoState {
	@Override
	public void play() {
		super.courseVideoContext.setCourseVideoState(CourseVideoContext.PLAY_STATE);
	}

	@Override
	public void speed() {
		System.out.println("正常快进课程视频状态");
	}

	@Override
	public void pause() {
		super.courseVideoContext.setCourseVideoState(CourseVideoContext.PAUSE_STATE);
	}

	@Override
	public void stop() {
		super.courseVideoContext.setCourseVideoState(CourseVideoContext.STOP_STATE);
	}
}
