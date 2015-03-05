/*
 * Alistair Jewers
 * 
 * Copyright (c) 2015 Sofia Software Solutions. All Rights Reserved.
 * 
 */
package sofia;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;

/**
 * This class encapsulates the video handler, and enables video playback and
 * control.
 * 
 * @author Alistair Jewers
 * @version 1.0 24 Feb 2015
 */
public class VideoHandler {
	/* Reference to the group on which to draw videos */
	private Group group;

	/* Array List of the videos currently on the screen */
	private List<Video> videos;

	/** Constructor Method */
	public VideoHandler(Group nGroup) {
		/* Set the group reference */
		this.group = nGroup;

		/* Initialise the video list */
		this.videos = new ArrayList<Video>();
	}

	/** Add a video frame to a group */
	public void createVideo(double x, double y, double width, String sourcefile, boolean autoPlay, boolean loop) {
		videos.add(new Video(group, x, y, width, sourcefile, autoPlay, loop));
	}

	/** Play a video */
	public void playVideo(int videoId) {
		videos.get(videoId).play();
	}

	/** Pause a video */
	public void pauseVideo(int videoId) {
		videos.get(videoId).pause();
	}

	/** Stop a video */
	public void stopVideo(int videoId) {
		videos.get(videoId).stop();
	}

	/** Scan a video */
	public void scanVideo(int videoId, double percent) {
		videos.get(videoId).scan(percent);
	}

	/** Clear all the videos currently being handled */
	public void clearVideos() {
		/* Remove all the media views (videos) from the group */
		for (int i = 0; i < videos.size(); i++) {
			videos.get(i).dispose();
			group.getChildren().remove(videos.get(i));
		}

		/* Clear the array list */
		videos.clear();
	}
}
