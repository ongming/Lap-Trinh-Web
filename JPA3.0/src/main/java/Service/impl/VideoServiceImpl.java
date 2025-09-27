package Service.impl;

import Dao.VideoDao;
import entity.Video;
import utils.Page;

public class VideoServiceImpl {
    private final VideoDao dao = new VideoDao();

    public Page<Video> search(String q, int page, int size){ return dao.search(q, page, size); }
    public Video find(Long id){ return dao.find(id); }
    public void save(Video e){ dao.save(e); }
    public Video update(Video e){ return dao.update(e); }
    public void delete(Long id){ dao.delete(id); }
}
