$(function () {
    //获取商品
    listGoods(1, 12);

    //添加分页事件，使用事件委托
    $(".pagination").on("click", ".page-item", function () {
        let $item = $(this);
        //获取当前页
        let pageNo = parseInt($(".pagination>.page-item.current").text());

        if ($item.is(".prev")) {
            listGoods(pageNo - 1);
        } else if ($item.is(".next")) {
            listGoods(pageNo + 1);
        } else {
            listGoods(parseInt($item.text()));
        }
    });


    //添加品牌查询事件
    $(".widget-brand :checkbox").click(function () {
        listGoods(1);
    });

    //添加价格查询事件
    $(".widget-price :checkbox").click(function () {
        listGoods(1);
    });

    //添加一级分类查询事件
    $(".widget-categories .card-header>h6>a").click(function (e) {
        e.preventDefault();
        $(".widget-categories .category-selected").removeClass("category-selected");
        $(this).addClass("category-selected");
        listGoods(1);
    });

    //添加二级分类查询事件
    $(".widget-categories .card-body>ul>li>a").click(function (e) {
        e.preventDefault();
        $(".widget-categories .category-selected").removeClass("category-selected");
        $(this).addClass("category-selected");
        listGoods(1);
    });
});

//获取商品
function listGoods(pageNo, pageSize = 12) {
    let url = ctx + "/api/goods";
    //类别编号
    let category = $(".widget-categories .category-selected").data("id");
    let priceArray = [];
    let priceRange = $(".widget-price :checked");
    priceRange.each(function () {
        let $check = $(this);
        let from = $check.data("price-from");
        let to = $check.data("price-to");
        let obj = {};
        if ($.isNumeric(from)) {
            obj.from = from;
        }
        if ($.isNumeric(to)) {
            obj.to = to;
        }
        priceArray.push(obj);
    });

    let brandArray = [];
    let brands = $(".widget-brand :checked");
    brands.each(function () {
        brandArray.push($(this).data("id"));
    });

    $.ajax(url, {
        method: "get",
        dataType: "json",
        data: {
            pageNo,
            pageSize,
            takeOff: false,
            categoryId: category,
            brandIds: brandArray,
            priceArray: JSON.stringify(priceArray)
        },
        traditional: true,
        success(resp) {
            let rows = resp.rows;
            $("#good-list").empty();
            rows.forEach(function (item) {
                let $item = $("#good-template").clone().removeAttr("id");

                //对模板进行操作赋值
                $item.find(".product-title>a")
                    .text(item.name)
                    .attr("href",ctx + "/good/detail?id="+item.id);
                $item.find(".product-card>a").attr("href",ctx + "/good/detail?id="+item.id);

                $item.find(".product-price>del").text("￥"+item.markPrice);
                $item.find(".product-price>span").text("￥"+item.price);
                $item.find(".card-img-back").css("background", "url(" + (serverUrl + item.picUrl) + ") no-repeat center center/contain");
                $item.find(".card-img-front").css("background", "url(" + (serverUrl + item.picUrl) + ") no-repeat center center/contain");

                $item.appendTo($("#good-list")).show();
            });

            //处理分页
            let pageNo = resp.npi.paginateInfo.pageNo;
            let pageSize = resp.npi.paginateInfo.pageSize;
            let total = resp.npi.paginateInfo.total;
            let pages = resp.npi.paginateInfo.pages;
            let offset = resp.npi.paginateInfo.offset;

            $(".pagebar-top span:first").text(offset + 1);
            $(".pagebar-top span:eq(1)").text(rows.length);
            $(".pagebar-top span:last").text(total);


            $(".pagination>.page-item:not(:first):not(:last)").remove();
            for (let i = resp.npi.navigateStart; i <= resp.npi.navigateEnd; i++) {
                let $pItem = $(`<li class="page-item"><a class="page-link">${i}</a></li>`);
                if (i === pageNo) {
                    $pItem.addClass("current");
                }
                $(".pagination>.next").before($pItem);
            }
        }
    });
}