package com.sfh.shopping.util;

/**
 * 分页对象
 * <p>
 * 注意：满足本项目使用即可，不要过度设计。
 *
 * @author snow1k
 * @version 1.0.0
 */
public final class PaginateInfo {
    private int pageNo;
    private final int pageSize;
    private int total;//总记录数
    private int pages;//总页娄

    public PaginateInfo(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getOffset() {
        return (pageNo - 1) * pageSize;
    }

    public int getLimit() {
        return pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
        this.pages = total / pageSize;
        if (total % pageSize > 0) {
            this.pages++;
        }

        if (pageNo > pages) {
            pageNo = pages;
        }
    }

    public int getPages() {
        return pages;
    }
}
