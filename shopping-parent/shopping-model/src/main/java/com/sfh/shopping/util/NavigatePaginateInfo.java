package com.sfh.shopping.util;
public class NavigatePaginateInfo {
    private final PaginateInfo paginateInfo;

    private final int navigateTotal;//共导航几页

    private int navigateStart;//导航首页
    private int navigateEnd;//导航尾页

    public NavigatePaginateInfo(PaginateInfo paginateInfo, int navigateTotal) {
        this.paginateInfo = paginateInfo;
        this.navigateTotal = navigateTotal;
        init();
    }

    private void init() {
        int pageNo = paginateInfo.getPageNo();
        int pages = paginateInfo.getPages();
        int half = navigateTotal / 2;

        navigateStart = pageNo - half;
        if (navigateStart < 1) {
            navigateStart = 1;
        }

        navigateEnd = navigateStart + navigateTotal - 1;
        if (navigateEnd > pages) {
            navigateEnd = pages;
            navigateStart = navigateEnd - navigateTotal + 1;
            if (navigateStart < 1) {
                navigateStart = 1;
            }
        }
    }

    public PaginateInfo getPaginateInfo() {
        return paginateInfo;
    }

    public int getNavigateTotal() {
        return navigateTotal;
    }

    public int getNavigateStart() {
        return navigateStart;
    }

    public int getNavigateEnd() {
        return navigateEnd;
    }

    public int getPageNo() {
        return paginateInfo.getPageNo();
    }

    public int getPageSize() {
        return paginateInfo.getPageSize();
    }

    public int getTotal() {
        return paginateInfo.getTotal();
    }

    public int getPages() {
        return paginateInfo.getPages();
    }
}
