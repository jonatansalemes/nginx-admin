package com.jslsolucoes.nginx.admin.database;

import java.nio.file.Path;

public class FileSequence implements Comparable<FileSequence> {
	private Path path;
	private Long score;

	public FileSequence(Path path, Long score) {
		this.path = path;
		this.score = score;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	@Override
	public int compareTo(FileSequence o) {
		return score.compareTo(o.getScore());
	}

}