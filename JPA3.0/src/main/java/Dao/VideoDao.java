package Dao;

import entity.Video;
import utils.Page;

public interface VideoDao {

	public Video find(Long id);

	public void save(Video e);

	public Video update(Video e);

	public void delete(Long id);

	// SEARCH + PHÂN TRANG
	public Page<Video> search(String q, int page, int size);

}
