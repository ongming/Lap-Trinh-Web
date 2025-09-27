package utils;

import java.util.List;

public class Page<T> {
    public final List<T> items;
    public final int page;
    public final int size;
    public final long total;

    public Page(List<T> items, int page, int size, long total) {
        this.items = items;
        this.page = page;
        this.size = size;
        this.total = total;
    }
    public int getTotalPages(){ return (int)Math.ceil((double) total / size); }
}
